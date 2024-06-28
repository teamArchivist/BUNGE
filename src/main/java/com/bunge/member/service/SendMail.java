package com.bunge.member.service;

import com.bunge.member.domain.Mail;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import java.util.Random;

@Slf4j
@Component
public class SendMail {

    private JavaMailSenderImpl mailSender;

    @Autowired
    public SendMail(JavaMailSenderImpl mailSender) {
        this.mailSender=mailSender;
    }

        public void sendMail(Mail mail) {

            MimeMessagePreparator mp = new MimeMessagePreparator() {

                @Override
                public void prepare(MimeMessage mimeMessage) throws Exception {
                    //두 번째 인자 true는 멀티 파트 메시지를 사용하겠다는 의미입니다.
                   String random = randomchar(10,mail);
                    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                    helper.setFrom(mail.getFrom());
                    helper.setTo(mail.getTo());
                    helper.setSubject("비밀번호 재설정 메일입니다.");
                    helper.setText("<a href='http://43.203.229.201:9100/member/pwdset?random="+random+"&email="+mail.getTo()+"'>비밀번호 재설정 링크입니다.</a>", true);
                }
            };
            mailSender.send(mp);
            log.info("메일 전송되었습니다.");
        }

    public void inquiryMail(String recipientEmail) {
        MimeMessagePreparator mp = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                Mail mail = new Mail();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                helper.setFrom(mail.getFrom()); // 발신자 이메일 주소
                helper.setTo(recipientEmail);
                helper.setSubject("작성하신 문의글에 답변이 완료되었습니다.");
                helper.setText("작성하신 문의글에 답변이 완료되었습니다.");
            }
        };
        try {
            System.out.println("메일 전송 중: " + recipientEmail); // 디버그 로그 추가
            mailSender.send(mp);
            System.out.println("메일 전송되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("메일 전송에 실패했습니다. 에러 메시지: " + e.getMessage());
        }
    }


        //랜덤 토큰생성
        public String randomchar(int length, Mail mail){
            String text = "";
            String possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
            for (int i=0; i <length; i++) {
                text += possible.charAt((int)(Math.random() * possible.length()));
        }
            mail.setRandom(text);
        return text;
    }
}
