package com.bunge.mypage.service;

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
public class MypageSendEmail {
    private JavaMailSenderImpl mailSender;

    @Autowired
    public MypageSendEmail(JavaMailSenderImpl mailSender) {
        this.mailSender=mailSender;
}
        public void mypagesendmail(Mail mail) {
            MimeMessagePreparator mp = new MimeMessagePreparator()
            {
                @Override
                public void prepare(MimeMessage mimeMessage) throws Exception {
                    String randomToken = randomchar(6,mail);
                    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                    helper.setFrom(mail.getFrom());
                    helper.setTo(mail.getTo());
                    helper.setSubject("정보수정 인증코드입니다..");
                    helper.setText("이메일 인증 코드입니다." + randomToken);
                }
            };
            mailSender.send(mp);
            log.info("메일 전송되었습니다.");
    }
    //인증토큰생성
    public String randomchar(int length, Mail mail){
        String text = "0123456789";
        StringBuilder token = new StringBuilder();
        Random random = new Random();
        for (int i=0; i <length; i++) {
            int emailtoken = random.nextInt(text.length());
            token.append(text.charAt(emailtoken));
        }
        String emailtoken = token.toString();
        mail.setRandom(emailtoken);
        return emailtoken;
    }
}

