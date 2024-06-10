package com.bunge.mypage.service;

import com.bunge.member.domain.Member;
import com.bunge.member.mapper.MemberMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Random;

@Service
public class ProfileService {

    @Autowired
    private MemberMapper memberMapper;

    @Value("${my.savefolder}")
    private String saveFolder;

    public String fileDBName(String fileName, String saveFolder) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int date = c.get(Calendar.DATE);

        String homedir = saveFolder + "/" + year + "-" + month + "-" + date;
        File path1 = new File(homedir);
        if (!path1.exists()) {
            path1.mkdirs();
        }

        Random r = new Random();
        int random = r.nextInt(100000000);

        int index = fileName.lastIndexOf(".");
        String fileExtension = fileName.substring(index + 1);

        String refileName = "bbs" + year + month + date + random + "." + fileExtension;

        return File.separator + year + "-" + month + "-" + date + File.separator + refileName;
    }

    public void updateProfileImage(Member member, MultipartFile uploadfile) throws IOException {
        if (uploadfile != null && !uploadfile.isEmpty()) {
            String fileName = uploadfile.getOriginalFilename();
            member.setProfile_original(fileName);

            String fileDBName = fileDBName(fileName, saveFolder);
            uploadfile.transferTo(new File(saveFolder + fileDBName));
            member.setProfile(fileDBName);
        } else {
            member.setProfile(""); // Clear the profile if no new file uploaded
            member.setProfile_original(""); // Clear the original file name
        }
        memberMapper.update(member);
    }
}

