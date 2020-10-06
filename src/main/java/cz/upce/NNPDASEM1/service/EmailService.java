package cz.upce.NNPDASEM1.service;

import cz.upce.NNPDASEM1.domain.user.User;
import cz.upce.NNPDASEM1.security.JwtTokenProvider;
import cz.upce.NNPDASEM1.security.JwtTokenType;
import cz.upce.NNPDASEM1.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;

@Service
public class EmailService {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private JavaMailSender emailSender;

    @Value("${email.from}")
    private String emailFrom;

    @Value("${api.path}")
    private String baseUrl;


    @Async
    public void sendRegisterEmail(User user) {
        String url = baseUrl + "/auth/activate/" + jwtTokenProvider.createToken(user.getUsername(), JwtTokenType.ACCOUNT_ACTIVATION);
        String subject = MessageUtils.getMessage("email.activate.account.subject", Locale.getDefault());
        String body = MessageUtils.getMessage("email.activate.account.body", Locale.getDefault(), url);
        sendEmail(user, subject, body);
    }


    public void sendNewPasswordEmail(User user) {
        String header = "Password update";
        String body = "URL: 127.0.0.1:8080/users/new-password-apply/" + jwtTokenProvider.createToken(user.getUsername(), JwtTokenType.PASSWORD_RESET);

        sendEmail(user, header, body);
    }

    private void sendEmail(User user, String header, String body) {

        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
            message.setFrom(emailFrom);
            message.setTo(user.getEmail());
            message.setSubject(header);
            message.setText(body, true);
            emailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}
