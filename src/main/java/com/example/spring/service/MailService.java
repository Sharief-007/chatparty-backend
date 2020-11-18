package com.example.spring.service;

import com.example.spring.dto.NotificationEmail;
import com.example.spring.model.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {
    private final TemplateEngine templateEngine;
    private final JavaMailSender mailSender;

    @Async
    public void sendTokenToUser(String token, User user) {
        var url = "http://localhost:8080/token/account-activation/"+token;
        var notification = NotificationEmail.builder()
                .receiver(user.getEmail())
                .subject("Account Activation")
                .body(build(url))
                .build();
        send(notification);
    }
    private void send(NotificationEmail notification) {
        MimeMessagePreparator message = mimeMessage ->{
            var helper = new MimeMessageHelper(mimeMessage);
            helper.setTo(notification.getReceiver());
            helper.setSubject(notification.getSubject());
            helper.setText(notification.getBody(),true);
        };

        try {
            mailSender.send(message);
        }catch (RuntimeException e){
            e.printStackTrace();
            log.debug(message.toString());
        }

    }

    private String build(String url){
        var context = new Context();
        context.setVariable("message",url);
        return templateEngine.process("otpTemplate",context);
    }
}
