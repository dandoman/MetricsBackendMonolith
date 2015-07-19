package com.cante.metrics.tests.clients;

import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;

import lombok.Data;
import lombok.extern.log4j.Log4j;

@Log4j
@Data
public class EmailClient {

	public boolean areThereNewMessages() {
		String host = "imap.gmail.com";
		String username = "cante.metrics@gmail.com";
		String password = "CanteMetrics2212";
		Properties props = new Properties();
		props.put("mail.imaps.partialfetch", "false");
		props.put("mail.mime.base64.ignoreerrors", "true");


		Session session = Session.getInstance(props);
		Store store;
		try {
			store = session.getStore("imaps");
			store.connect(host, username, password);
			Folder emailFolder = store.getFolder("INBOX");
			emailFolder.open(Folder.READ_WRITE);
			int msgCount = emailFolder.getMessageCount();
			if(msgCount == 0){
				return false;
			}
			
			Message[] msgs = emailFolder.getMessages();
			Flags deleted = new Flags(Flags.Flag.DELETED);
			emailFolder.setFlags(msgs, deleted, true);
			emailFolder.close(true);
			return true;
		} catch (Exception e) {
			throw new RuntimeException();
		}

	}
}