package com.adidas.backend.emailservice.service;


public interface IStatusEmailService {

    public String initSendingEmail(String idMember, String email, String link);
    public void completedSendingEmail(String idEmail, String html);
    public void errorSendingEmail(String idEmail);
    
}
