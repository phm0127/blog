package com.min.blog.util;

public interface MailSender {
    public void sendNotification(String email,String boardTitle, String url, String userNickname, String content);
    public void sendEditNotification(String email,String boardTitle, String url, String userNickname, String content);
}
