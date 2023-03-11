package by.itstep.stpnbelko.service.impl;

import by.itstep.stpnbelko.service.EmailService;
import by.itstep.stpnbelko.util.AppConstants;
import jakarta.mail.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;


@Service
public class EmailServiceImpl implements EmailService {

//    @Bean
//    public JavaMailSenderImpl mailSender() {
//        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
//
//        javaMailSender.setProtocol("SMTP");
//        javaMailSender.setHost("smtp.rambler.ru");
//        javaMailSender.setPort(2525);
//        javaMailSender.setUsername("rambler12020@rambler.ru");
//        javaMailSender.setPassword("Rambler12020");
//        return javaMailSender;
//    }

//    private JavaMailSender emailSender = mailSender();

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendSimpleMessage(
            String to, String subject, String text) {

        System.out.println("send start");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(AppConstants.NEW_MAIL_ACC);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        try {
            emailSender.send(message);
        } catch (Exception e) {
            System.out.println("exception!!!!");
            throw new RuntimeException(e);
        }
        System.out.println("send finish");
    }
}
