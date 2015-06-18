package com.cante.metrics.client.impl;


import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.cante.metrics.client.EmailClient;

public class EmailClientImpl implements EmailClient {
	public static void main(String[] args) {
		// Recipient's email ID needs to be mentioned.
		String to = "tristano.tenaglia@gmail.com";

		// Sender's email ID needs to be mentioned
		String from = "bro@cannucci.com";

		String host = "imap.gmail.com";
		final String username = "email@email.com";
		final String password = "password";

		// Get system properties
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		
		Session session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				  });


		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(to));
			message.setSubject("Testing Subject");
			message.setText("I am a teapot");
 
			Transport.send(message);
 
			System.out.println("Done");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

}
