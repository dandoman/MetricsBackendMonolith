package com.cante.metrics.client.impl;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import lombok.Setter;

import com.cante.metrics.client.EmailClient;
import com.cante.metrics.entity.pojo.Monitor;

public class EmailClientImpl implements EmailClient {

	@Setter
	private String username;
	@Setter
	private String password;
	@Setter
	private String host;
	@Setter
	private String port;
	private Session session;

	public void init() {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);

		session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
	}

	public void sendAlarm(Monitor m) throws Exception {
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress("cante.metrics@gmail.com"));
		message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(m.getEmailRecipient()));
		message.setSubject("Metric Alert");
		message.setText(m.getDescription());

		Transport.send(message);
	}
}
