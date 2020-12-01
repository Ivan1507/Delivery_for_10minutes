package sample;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class MailSender {

    public static void send3(){

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        Session session = Session.getInstance(props, null);

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom("delivery1523@gmail.com");
            msg.setRecipients(Message.RecipientType.TO,
                    "delivery1523@gmail.com");
            msg.setSubject("JavaMail hello world example");
            msg.setSentDate(new Date());
            msg.setText("Hello, world!\n");
            Transport.send(msg, "delivery1523@gmail.com", "Ivanap1507");
        } catch (MessagingException mex) {
            System.out.println("send failed, exception: " + mex);
        }

    }

    public static void send4(){
        final String username = "delivery1523@gmail.com";

        // Gmail password
        final String password = "Ivanap1507";

        // Receiver's email ID
        String receiver = "luckyghost33@gmail.com";

        // Sender's email ID
        String sender = "delivery1523@gmail.com";

        // Sending email from gmail
        String host = "smtp.gmail.com";

        // Port of SMTP
        String port = "587";

        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);

        // Create session object passing properties and authenticator instance
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try
        {
            // Create MimeMessage object
            MimeMessage message = new MimeMessage(session);

            // Set the Senders mail to From
            message.setFrom(new InternetAddress(sender));

            // Set the recipients email address
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));

            // Subject of the email
            message.setSubject("Java Send Email Gmail SMTP with TLS Authentication");

            // Body of the email
            message.setText("Welcome to Java Interviewpoint");
            System.out.println("Send");
            // Send email.
            Transport.send(message);
            System.out.println("Mail sent successfully");
        } catch (Exception r){
    r.printStackTrace();
        }

    }
}