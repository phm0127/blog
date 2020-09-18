package com.min.blog.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class MailSenderImpl implements MailSender {

    @Autowired
    public JavaMailSender javaMailSender;

    @Async
    public void sendNotification(String email,String boardTitle, String url, String userNickname, String content) {

        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper message = new MimeMessageHelper(
                    mimeMessage, true, "UTF-8");

            message.setFrom("noReply@1000min.kr", "Just TTa It!");
            message.setTo(email);
            message.setText("게시물에 댓글이 달렸습니다. \n" +
                    userNickname+"님이 "+ boardTitle+"에 게시글을 남겼습니다.\n" +
                    "댓글 내용 --- \n" +
                    content + "\n"+
                    "-----------\n"+
                    "\n 바로가기"+ url);
            message.setSubject("이메일 인증");

        };
        javaMailSender.send(mimeMessagePreparator);
    }
}
