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

            message.setFrom("noReply@1000min.kr", "1000min");
            message.setTo(email);
            message.setText("게시물에 댓글이 달렸습니다. \n" +
                    userNickname+"님이 "+ boardTitle+"에 댓글을 남겼습니다.\n" +
                    "댓글 내용 --- \n" +
                    content + "\n"+
                    "-----------\n"+
                    "\n 바로가기"+ url);
            message.setSubject("블로그에 댓글이 달렸습니다.");

        };
        javaMailSender.send(mimeMessagePreparator);
    }

    @Async
    public void sendEditNotification(String email,String boardTitle, String url, String userNickname, String content) {

        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper message = new MimeMessageHelper(
                    mimeMessage, true, "UTF-8");

            message.setFrom("noReply@1000min.kr", "1000min");
            message.setTo(email);
            message.setText("게시물에 댓글이 수정되었습니다. \n" +
                    userNickname+"님이 "+ boardTitle+"에 있는 댓글을 수정했습니다.\n" +
                    "댓글 내용 --- \n" +
                    content + "\n"+
                    "-----------\n"+
                    "\n 바로가기"+ url);
            message.setSubject("블로그에 댓글이 수정되었습니다.");

        };
        javaMailSender.send(mimeMessagePreparator);
    }
}
