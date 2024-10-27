package com.saz.se.goat.utils;

import com.saz.se.goat.user.UserEntity;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

       @Autowired private JavaMailSender javaMailSender;
       @Autowired JWTService jwtService;

       @Value("${spring.mail.username}")
       private String sender;

    public void sendConfirmationEmail(UserEntity user) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(user.getEmail());
        helper.setSubject("Confirm your E-Mail - HENAMEDMARKT Registration");
        helper.setText(/*"<html>" +
                        "<body>" +
                        "<h2>Dear "+ user.getLastName() + ",</h2>"
                        + "<br/> We're excited to have you get started. " +
                        "Please click on below link to confirm your account."
                        + "<br/> "  + generateConfirmationLink(user.getEmail())+"" +
                        "<br/> Regards,<br/>" +
                        "MFA Registration team" +
                        "</body>" +
                        "</html>"*/

                "<html>\n" +
                        "   <body>\n" +
                        "       <h1>Welcome!</h1>\n" +
                        "       <h2>Active your HenaMedMarkt account</h2>\n" +
                         "<br/> "  + generateConfirmationLink(user.getEmail())+"" +
                        "       <p>If you don’t use this link within 30 minutes, it will expire.</p>\n" +
                        "       <p>Thanks,<br>HenaMedMarkt Team</p>\n" +
                        "   </body>\n" +
                        "</html>"
                , true);

        javaMailSender.send(message);
    }

    private String generateConfirmationLink(String email){
        String token = jwtService.generateToken(email);
        return "<a href=http://localhost:8080/api/v1/auth/confirmEmail?token="+token+"><button style='background-color: #28a745; color: white; border: none; padding: 10px 20px; font-size: 16px;'>Active Account</button></a>";
    }
}