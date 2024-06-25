package com.example.loanprovisioning.service.impl;

import com.example.loanprovisioning.config.AppConstants;
import com.example.loanprovisioning.config.properties.EmailConfig;
import com.example.loanprovisioning.config.properties.MailerConfig;
import com.example.loanprovisioning.dto.NotificationDto;
import com.example.loanprovisioning.exception.GeneralException;
import com.example.loanprovisioning.service.NotificationService;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Properties;

@Service
@Slf4j
public class EmailService implements NotificationService {
    private final MailerConfig mailerConfig;
    private final EmailConfig emailConfig;

    public EmailService(MailerConfig mailerConfig, EmailConfig emailConfig) {
        this.mailerConfig = mailerConfig;
        this.emailConfig = emailConfig;
    }

    @Override
//    @Async
    public boolean sendNotification(NotificationDto emailDto) {
        log.info(AppConstants.LOG_PREFIX, "##### Sending email initiated #####");
        log.info(AppConstants.LOG_PREFIX, "Preparing SMTP properties");
        Properties properties = getSMTPProperties();
        log.info(AppConstants.LOG_PREFIX, "Finished preparing SMTP properties");
        log.info(AppConstants.LOG_PREFIX, "Preparing session for email");
        Session session = getEmailSession(properties);
        log.info(AppConstants.LOG_PREFIX, "Finished preparing session for email");
        log.info(AppConstants.LOG_PREFIX, "Preparing message for email ");
        Message msg = getEmailMessage(session, emailDto.getSendTo(), emailDto.getSubject());
        try {
            Multipart multipart = new MimeMultipart();
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(emailDto.getBody(), AppConstants.TEXT_HTML);
            multipart.addBodyPart(messageBodyPart);
            msg.setContent(multipart);
        } catch (Exception e) {
            log.error(AppConstants.EXCEPTION_ON_FORMATTING_EMAIL, e);
            throw new GeneralException(AppConstants.EXCEPTION_ON_FORMATTING_EMAIL);
        }
        log.info(AppConstants.LOG_PREFIX, "Finished preparing message for email ");

        try {
            log.info(AppConstants.LOG_PREFIX, "Sending email.");
            Transport.send(msg);
            log.info(AppConstants.LOG_PREFIX, "Email successfully sent to configured email address.");
            return true;
        } catch (Exception e) {
            log.error(AppConstants.EXCEPTION_ON_SENDING_EMAIL, e);
            return false;
        }
    }

    private Session getEmailSession(Properties properties) {
        try {
            Authenticator auth = new Authenticator() {
                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(mailerConfig.username(), mailerConfig.password());
                }
            };
            return Session.getInstance(properties, auth);
        } catch (Exception e) {
            log.info(AppConstants.LOG_PREFIX, "Exception while Preparing session for email", e);
            throw new GeneralException("Exception while Preparing session for email");
        }
    }

    private Properties getSMTPProperties() {
        try {
            Properties properties = new Properties();
            properties.put("mail.smtp.host", mailerConfig.host());
            properties.put("mail.smtp.port", mailerConfig.port());
            properties.put("mail.smtp.auth", mailerConfig.smtpAuth());
//            properties.put("mail.smtp.starttls.enable", mailerConfig.isSmtpStarttlsEnable());
            if (mailerConfig.sslEnable()) {
                properties.put("mail.smtp.ssl.enable", "true");
                properties.put("mail.smtp.socketFactory.port", mailerConfig.port());
                properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            } else if (mailerConfig.smtpStarttlsEnable()) {
                properties.put("mail.smtp.starttls.enable", "true");
            }
            properties.put("mail.user", mailerConfig.username());
            properties.put("mail.password", mailerConfig.password());
            properties.put("mail.smtp.ssl.enable", mailerConfig.sslEnable());
            return properties;
        } catch (Exception e) {
            log.info(AppConstants.EXCEPTION_ON_PREPARING_SMTP_PROPS, e);
            throw new GeneralException(AppConstants.EXCEPTION_ON_PREPARING_SMTP_PROPS);
        }
    }

    private Message getEmailMessage(Session session, final String sendTo, final String subject) {
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(emailConfig.from()));
            InternetAddress[] toAddresses = InternetAddress.parse(sendTo);
            msg.setRecipients(Message.RecipientType.TO, toAddresses);
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            return msg;
        } catch (AddressException e) {
            log.error(AppConstants.LOG_PREFIX, "AddressException while preparing email message {}", e);
            throw new GeneralException("AddressException while preparing email message");
        } catch (MessagingException e) {
            log.error(AppConstants.LOG_PREFIX, "MessagingException while preparing email message {}", e);
            throw new GeneralException("MessagingException while preparing email message");
        }
    }
    //                        mailerSendService.sendEmail(
//                                "trial-ynrw7gyno7o42k8e.mlsender.net",
//                                "danimulerr@gmail.com",
//                                "Test email",
//                                "Hello this is a test email",
//                                "html Hello this is header");
//            emailService.sendNotification(NotificationDto.builder()
//                    .sendTo("danimulerr@gmail.com")
//                    .subject("Test")
//                    .body("Body")
//                    .build());

}
