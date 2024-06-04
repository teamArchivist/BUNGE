package com.bunge.member.service;

import com.bunge.member.domain.Mail;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

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
                    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                    helper.setFrom(mail.getFrom());
                    helper.setTo(mail.getTo());
                    helper.setSubject("비밀번호 재설정 메일입니다.");
                    helper.setText("<a href='http://localhost:8080/member/pwdset'>비밀번호 재설정 링크입니다.</a>", true);
                }
            };
            mailSender.send(mp);
            log.info("메일 전송되었습니다.");
        }
    }
