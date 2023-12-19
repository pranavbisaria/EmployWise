package com.employwise.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotifyService {
    private final EmailService emailService;

    @Async
    public void notifyManager(String managerEmail, String managerName, String employName, String employEmail, String employPhoneNumber){
        String subject = "New Employee Added";
        String message = "<div>Dear " + managerName + "," +
                "<br><br>" + employName + " will now work under you." +
                "<br>Mobile number is " + employPhoneNumber + " and Email is <a href=\"mailto:" + employEmail + "\">" + employEmail + "</a>." +
                "<br><br>Regards," +
                "<br>EmployWise</div>";
        String to = managerEmail;
        this.emailService.sendEmail(subject, message, to);
    }
}