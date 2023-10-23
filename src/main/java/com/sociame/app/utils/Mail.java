package com.sociame.app.utils;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.constraints.NotNull;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Properties;

/**
 * A utility class (component) for sending mails.
 *
 * @author Tasos Daris <tasos.daris@datawise.ai>
 */
@Slf4j
@ToString
@Component
public class Mail {
    @NotNull
    @Value("${mail.from}")
    private String from;

    @NotNull
    @Value("${mail.host}")
    private String host;

    @NotNull
    @Value("${mail.username}")
    private String username;

    @NotNull
    @Value("${mail.password}")
    private String password;

    @NotNull
    @Value("${mail.port}")
    private String port;

    /**
     * Sends email to a given receiver.
     *
     * @param receiver  The email of the receiver.
     * @param subject   The email subject.
     * @param body      The ema il body.
     */
    public void send(String receiver, String subject, String body) {
        log.info("Sending to: {}", receiver);

        log.info("Mail: {}", this);

        // @TODO: Maybe this can be autoconfigured.
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", port);
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");

        log.info("Properties: {}", properties);

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));

            message.setSubject(subject, "UTF-8");
            message.setText(body, "UTF-8");
            message.setSentDate(new Date());

            message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));

            Transport.send(message);
        } catch (MessagingException e) {
            log.error(e.getMessage(), e);
        }
    }

}
