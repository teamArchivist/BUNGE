package com.bunge.study.service;

import com.bunge.study.domain.FolderItem;
import com.jcraft.jsch.*;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


@Service
public class SftpService {

    private static final String USER = "ec2-user";
    private static final String HOST = "ec2-43-201-236-54.ap-northeast-2.compute.amazonaws.com";
    private static final int PORT = 22;
    private static final String PEM_FILE = "/home/ec2-user/bunge.pem";

    private ChannelSftp setupJsch() throws JSchException {
        JSch jsch = new JSch();
        jsch.addIdentity(PEM_FILE);
        Session jschSession = jsch.getSession(USER, HOST, PORT);
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        jschSession.setConfig(config);
        jschSession.connect();
        return (ChannelSftp) jschSession.openChannel("sftp");
    }

    public List<FolderItem> listItems(String directoryPath) throws JSchException, SftpException {
        List<FolderItem> items = new ArrayList<>();
        ChannelSftp channelSftp = setupJsch();
        channelSftp.connect();
        Vector<ChannelSftp.LsEntry> entries = channelSftp.ls(directoryPath);
        for (ChannelSftp.LsEntry entry : entries) {
            if (!entry.getFilename().equals(".") && !entry.getFilename().equals("..")) {
                boolean isDirectory = entry.getAttrs().isDir();
                String path = directoryPath + "/" + entry.getFilename();
                items.add(new FolderItem(entry.getFilename(), isDirectory));
            }
        }
        channelSftp.disconnect();
        return items;
    }

    public boolean createFolder(String folderPath) {
        ChannelSftp channelSftp = null;
        try {
            channelSftp = setupJsch();
            channelSftp.connect();
            channelSftp.mkdir(folderPath);
            return true;
        } catch (JSchException | SftpException e) {
            return false;
        } finally {
            if (channelSftp != null && channelSftp.isConnected()) {
                channelSftp.disconnect();
            }
        }
    }

    public void uploadFile(InputStream inputStream, String remoteDir, String fileName) throws JSchException, SftpException {
        ChannelSftp channelSftp = setupJsch();
        try {
            channelSftp.connect();
            channelSftp.cd(remoteDir); // 원격 디렉토리로 이동
            channelSftp.put(inputStream, fileName); // 파일을 업로드합니다.
        } finally {
            channelSftp.disconnect();
        }
    }

    public void downloadFile(String remoteFilePath, String localFilePath) throws JSchException, SftpException, IOException {
        ChannelSftp channelSftp = null;
        try {
            channelSftp = setupJsch();
            channelSftp.connect();
            channelSftp.get(remoteFilePath, localFilePath); // 원격 파일을 로컬 파일 경로로 다운로드
        } finally {
            if (channelSftp != null && channelSftp.isConnected()) {
                channelSftp.disconnect();
            }
        }
    }

    public boolean deleteFiles(List<String> filePaths) {
        ChannelSftp channelSftp = null;
        try {
            channelSftp = setupJsch();
            channelSftp.connect();
            for (String filePath : filePaths) {
                System.out.println("Deleting file: " + filePath); // 디버그용 출력
                channelSftp.rm(filePath);
            }
            return true;
        } catch (JSchException | SftpException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (channelSftp != null && channelSftp.isConnected()) {
                channelSftp.disconnect();
            }
        }
    }

}
