package obs.email;

import java.io.File;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EmailSender {
	
	private static final Logger logger					= LogManager.getLogger(EmailSender.class );
	
	public static final String TEXT_HTML_CONTENT_TYPE = "text/html";
	
	public void sendFromCustomerService(String to, String subject, String body) {
		send(new EmailConfiguration().getUserServiceEmailAddress(), to, null, subject, body);
	}

	public void sendMailWithCCOfPerson(String to, String cc, String subject, String body){
		send(new EmailConfiguration().getUserServiceEmailAddress(), to, cc, subject, body);
	}
	
	public void sendMailWithMultiplePerson(List<String> to, String subject, String body){
		sendMultiple(new EmailConfiguration().getUserServiceEmailAddress(), to, null, subject, body);
	}

	public void sendWithAttachement(String to, String subject, String bodyString, String attachmentUrl, String attachmentName) {
		try {
			Session mailSession = createSession();
			String from = new EmailConfiguration().getUserServiceEmailAddress();
			Message message = composeMessageWithAttachement(mailSession, from , to, subject, bodyString, attachmentUrl, attachmentName);
			Transport.send(message);
		} catch (MessagingException e) {
			logger.error(e);
		}
	}
	
	public void sendMultiple(String from, List<String> to, String cc, String subject, String body) {
		try {
			
			Session mailSession = createSession();
			Message message = composeMultipleMessage(mailSession, from, to, cc, subject, body, TEXT_HTML_CONTENT_TYPE);
			Transport.send(message);
		} catch (MessagingException e) {
			e.getMessage();
			logger.error(e);
		}
	}

	public void send(String from, String to, String cc, String subject, String body) {
		try {
			
			Session mailSession = createSession();
			Message message = composeMessage(mailSession, from, to, cc, subject, body, TEXT_HTML_CONTENT_TYPE);
			Transport.send(message);
		} catch (MessagingException e) {
			logger.error(e);
		}
	}

	private Session createSession() {
		EmailConfiguration emailConfiguration = new EmailConfiguration();
		Authenticator account = new SMTPAuthenticator(emailConfiguration.smtpUser(), emailConfiguration.smtpPassword());

		if(emailConfiguration.isAuth())
			return Session.getInstance(emailProperties(emailConfiguration), account);

		return Session.getInstance(emailProperties(emailConfiguration)); 
	}

	private Properties emailProperties(EmailConfiguration emailConfiguration) {
		Properties emailProperties = new Properties();
		emailProperties.put("mail.smtp.host", emailConfiguration.smtpHost());
		emailProperties.put("mail.smtp.port", emailConfiguration.smtpPort());
		emailProperties.put("mail.smtp.starttls.enable", "true");
		if(emailConfiguration.isAuth())
			emailProperties.put("mail.smtp.auth", "true");

		return emailProperties;
	} 

	private Message composeMessage(Session mailSession, String from, String to, String cc, String subject, String body, String contentType) throws MessagingException {
		MimeMessage message = new MimeMessage(mailSession);
		message.setFrom(new InternetAddress(from));
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
		if(cc != null)
			message.setRecipient(Message.RecipientType.CC, new InternetAddress(cc));
		message.setSubject(subject);
		message.setContent(body, contentType);
		message.saveChanges(); 
		return message;
	}
	
	private Message composeMultipleMessage(Session mailSession, String from, List<String> to, String cc, String subject, String body, String contentType) throws MessagingException {
		MimeMessage message = new MimeMessage(mailSession);
		message.setFrom(new InternetAddress(from));
		int m = 0;
		if(to != null && !to.isEmpty()){
			m = to.size();
		}
		InternetAddress[] sendTo = new InternetAddress[m];
		if(to != null && !to.isEmpty()){
		for (int i = 0; i <to.size(); i++) {
		
				sendTo[i] = new InternetAddress(to.get(i));		
		}
		}
		message.setRecipients(Message.RecipientType.TO, sendTo);
		if(cc != null)
			message.setRecipient(Message.RecipientType.CC, new InternetAddress(cc));
		message.setSubject(subject);
		message.setContent(body, contentType);
		message.saveChanges(); 
		return message;
	}

	private Message composeMessageWithAttachement(Session mailSession, String from, String to, String subject, String bodyString,String attachmentUrl,String attachmentName) throws MessagingException {
		MimeMessage message = new MimeMessage(mailSession);
		message.setFrom(new InternetAddress(from));
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
		message.setSubject(subject);
		MimeBodyPart body = new MimeBodyPart();
		body.setContent(bodyString,TEXT_HTML_CONTENT_TYPE);
		MimeBodyPart attachMent = new MimeBodyPart();
		FileDataSource dataSource = new FileDataSource(new File(attachmentUrl));
		attachMent.setDataHandler(new DataHandler(dataSource));
		if(attachmentName!=null && attachmentName!=""){
			attachMent.setFileName(attachmentName);
		}
		else{
			attachMent.setFileName("report.pdf");
		}
		attachMent.setDisposition(Part.ATTACHMENT);
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(body);
		multipart.addBodyPart(attachMent);
		message.setContent(multipart);

		message.setContent(multipart);
		message.saveChanges(); 
		return message;
	}

	public void sendinvoiceMailWithCCOfPerson(String emailAddressFinal, String adminEmail, String invoiceSubject,
			String body){
		send(new EmailConfiguration().getUserServiceEmailAddress(), emailAddressFinal, adminEmail, invoiceSubject, body);
	}
}
