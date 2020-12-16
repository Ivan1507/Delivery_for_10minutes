package sample;


import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.net.ssl.HttpsURLConnection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpClient;
import java.util.*;

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

    public static void SendMail(String topic, String text){
        final String username = "delivery1523@gmail.com";

        // Gmail password
        final String password = "Ivanap1507";

        // Receiver's email ID
        String receiver = "lucky-pa777@yandex.ru";

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
            message.setSubject(topic);

            // Body of the email
            message.setText(text);

            // Send email.
            Transport.send(message);

        } catch (Exception r){
    r.printStackTrace();
        }

    }

    public static void sendUrl() throws IOException {

        String url = "http://localhost:8080/test";
        URL urlObj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");


        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());

        String urlPostParameters = "userID=250&userName=Mike";
        outputStream.writeBytes(urlPostParameters);

        outputStream.flush();
        outputStream.close();

        System.out.println("Send 'HTTP GET' request to : " + url);

        Integer responseCode = connection.getResponseCode();
        System.out.println("Response Code : " + responseCode);


    }
}