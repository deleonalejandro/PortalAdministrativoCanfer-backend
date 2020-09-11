package com.canfer.app.mail;

import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class EmailReceiver {
	
	@Autowired
	private EmailService emailService;
	
	private Properties getServerProperties() {

		// General IMAP configuration for Mail
		String protocol = "imap";
		String host = "imap.gmail.com";
		String port = "993";

		Properties properties = new Properties();

		// server setting
		properties.put(String.format("mail.%s.host", protocol), host);
		properties.put(String.format("mail.%s.port", protocol), port);

		// SSL setting
		properties.setProperty(String.format("mail.%s.socketFactory.class", protocol),
				"javax.net.ssl.SSLSocketFactory");
		properties.setProperty(String.format("mail.%s.socketFactory.fallback", protocol), "false");
		properties.setProperty(String.format("mail.%s.socketFactory.port", protocol), String.valueOf(port));

		return properties;
	}

	@Async
	public void downloadEmails(Boolean open) {

		String userName = "yasminfemerling@gmail.com";
		String password = "miriamteamo";
		String protocol = "imap";

		try {

			// Include the properties
			Properties properties = getServerProperties();

			// Make session
			Session session = Session.getDefaultInstance(properties);

			// connects to the message store
			Store store = session.getStore(protocol);
			store.connect(userName, password);

			// opens the inbox folder
			Folder folderInbox = store.getFolder("INBOX");
			folderInbox.open(Folder.READ_WRITE);

			// fetches new messages from server
            Flags seen = new Flags(Flags.Flag.SEEN);
            FlagTerm unseenFlagTerm = new FlagTerm(seen,false);
            Message[] messages = folderInbox.search(unseenFlagTerm);
            folderInbox.setFlags(messages, new Flags(Flags.Flag.SEEN), true);
            
            //Processing E-mails
            System.out.println(messages.length);
            
            // just call the method when emails are present
            if (messages.length > 0) {
            	// handle new messages
            	emailService.handleEmails(messages);
			}
            

		} catch (NoSuchProviderException ex) {
			System.out.println("Ningun proveedor para el protocolo: " + protocol);
			ex.printStackTrace();

		} catch (MessagingException ex) {
			System.out.println("No se pudo conectar al servicio de mensajeria");
			ex.printStackTrace();

		} catch (NoResultException e) {
			System.out.println("Los bandeja fue procesada: " + e.getMessage());
		}
	}
	
}
