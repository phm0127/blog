package com.min.blog.util;

public interface MailSender {
    public void sendNotification(String email,String boardTitle, String url, String userNickname, String content);
}
