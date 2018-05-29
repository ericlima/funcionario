package com.educapps;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class MailService {
	
	private final static String userAuthentication = "educapps2009@gmail.com";
	private final static String passwordUserAuthentication = "teste123";
	private final static String sender = "educapps2009@gmail.com";
	private final static String smtp = "smtp.gmail.com";
	private final static boolean authentication = true;
	
	@SuppressWarnings("deprecation")
	public static void sendMail(String message, String subject, String receiver)
			throws EmailException {
		SimpleEmail email = new SimpleEmail();
		email.setHostName(smtp);
		email.setAuthentication(userAuthentication, passwordUserAuthentication);
		email.setSSL(authentication);
		email.addTo(receiver);
		email.setFrom(sender);
		email.setSubject(subject);
		email.setMsg(message);
		email.send();
		email = null;
	}
}
