//package pl.nataliabratek.project.domain.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//@Service
//public class EmailService {
//    @Autowired
//    private JavaMailSender mailSender;
//
//    public void sendConfirmationEmail(String to, String token){
//        String subject = "Potwierdzenie rejestracji";
//        String confirmationUrl = "http://localhost:8080/api/v1/users/confirm/" + token;
//        String messageText = "Kliknij w link, aby potwierdzic konto";
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(messageText);
//        mailSender.send(message);
//    }
//}
