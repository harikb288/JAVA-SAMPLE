package obs.email;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.StringUtils;

import obs.util.CommonProperties;
import obs.util.IoUtils;


public class EmailConfiguration {
	 static final Logger logger = LogManager.getLogger(EmailConfiguration.class);
	private static ResourceBundle config = getResourceBundle();


	private static final String BUNDLENAME = "emailConfig";

	private static ResourceBundle getResourceBundle() {
		ResourceBundle bundle = null;
		bundle = ResourceBundle.getBundle(BUNDLENAME);
		return bundle;
	}

	private static String getString(String key) {
		return config.getString(key);
	}

	public boolean isAuth() {
		String isAuthStr = getString("isAuth");
		return ("YES".equalsIgnoreCase(isAuthStr)) ? true:false;
	}

	public String smtpHost() {
		return getString("smtpHost");
	}

	public int smtpPort() {
		String strPort = getString("smtpPort");
		return Integer.parseInt(strPort);
	}

	public String smtpUser() {
		return getString("smtpUser");
	}

	public String smtpPassword() {
		return getString("smtpPassword");
	}


	public String getUserServiceEmailAddress() {
		return getString("userServiceEmailAddress");
	}

	//------------------------------------------------------------------

	public String getForgotPasswordSubject() {
		return getString("forgotPassword.subject");
	}

	public String getVerifyEmailSubject() {
		return getString("verifyEmail.subject");
	}

	public String getPasswordChangeSubject() {
		return getString("changepassword.subject");
	}
	
	public String getForgotUsernameSubject() {
		return getString("forgotUsername.subject");
	}

	public String getNewUserSubject() {
		return getString("newUser.subject");
	}

	public String getInvoiceSubject() {
		return getString("invoice.subject");
	}
	public String getOrganizationCreatedSubject() {
		return getString("organizationCreated.subject");
	}

	public String getPdfReportSubject() {
		return getString("sendPdfReport.subject");
	}

	public String getActivateUserSubject() {
		return getString("activateUser.subject");
	}

	public String getNotificationEmailSubject() {
		return getString("notificationEmail.subject");
	}
	
	public String getUserActivationAdminSubject() {
		return getString("adminUserActivation.subject");
	}
	@SuppressWarnings("unused")
	private String readFile(String messageFile, String token, String tokenValue) throws IOException {
		HashMap<String,String> tokens = new HashMap<>();
		tokens.put(token, tokenValue);
		return readFile(messageFile, tokens);
	}

	@SuppressWarnings("rawtypes")
	private String readFile(String messageFile, Map tokens) throws IOException {
		String filePath = CommonProperties.getBasePath()+CommonProperties.getContextPath()+messageFile;
		try {
			String message = new IoUtils().read(filePath);
			return replaceTokens(message, tokens);
		} catch (IOException e) {
			logger.info( "error message -"+ e.getMessage());
		return e.getMessage();
		}
	}

	@SuppressWarnings("rawtypes")
	private String replaceTokens(String message, Map tokens) {
		for (Iterator iterator = tokens.keySet().iterator(); iterator.hasNext();) {
			String token = (String) iterator.next();
			message = StringUtils.replace(message, token, (String)tokens.get(token));
		}
		return message;
	}


	
	private HashMap<String,String> setBasicmapInfo(EmailObject emailObj)
	{
		HashMap<String,String> mapBasic = new HashMap<>();
		mapBasic.put("${email}", emailObj.getRecieveEmail());
		mapBasic.put("${username}", emailObj.getRecieveUserName());
		mapBasic.put("${firstname}", emailObj.getRecieveFirstName());
		mapBasic.put("${password}", emailObj.getPasscode());
		mapBasic.put("${url}", emailObj.getLogoUrl());
		mapBasic.put("${logoUrl}", emailObj.getLogoUrl());
		return mapBasic;
	}
	
	public String getForgotPasswordEmailMessage(EmailObject emailObj) throws IOException {
		HashMap<String,String> map = setBasicmapInfo(emailObj);
		String messageFile = config.getString("forgotPassword.fileName");
		return readFile(messageFile, map);
	}

	public String getChangePasswordEmailMessage(EmailObject emailObj) throws IOException {
		HashMap<String,String> map = setBasicmapInfo(emailObj);
		String messageFile = config.getString("changePasswordSuccess.fileName");
		return readFile(messageFile, map);
	}

	public String getVerifyEmailMessage(EmailObject emailObj) throws IOException {
		HashMap<String,String> map = setBasicmapInfo(emailObj);
		String messageFile = config.getString("verifyEmail.fileName");
		return readFile(messageFile, map);
	}

	public String getTwoWayAuthenticationMessage(EmailObject emailObj) throws IOException {
		HashMap<String,String> map = setBasicmapInfo(emailObj);
		String messageFile = config.getString("changePassword.fileName");
		return readFile(messageFile, map);
	}
	
	public String userActivationByAdminMail(EmailObject emailObj) throws IOException {
		HashMap<String,String> map = setBasicmapInfo(emailObj);
		String messageFile = config.getString("accountVerifyEmail.fileName");
		return readFile(messageFile, map);
	}


	public String getUserVerify(EmailObject emailObj) throws IOException {
		HashMap<String,String> map = setBasicmapInfo(emailObj);
		String messageFile = config.getString("newUser_activate.fileName");
		return readFile(messageFile, map);
	}
	
	public String sentWelcomemail(EmailObject emailObj) throws IOException {
		HashMap<String,String> map = setBasicmapInfo(emailObj);
		String messageFile = config.getString("newUser_welcome.fileName");
		return readFile(messageFile, map);
	}


	public String sentNotificationEmail(EmailObject emailObj) throws IOException {
		//HashMap<String,String> map = new HashMap<>();
		HashMap<String,String> map = setBasicmapInfo(emailObj);
		map.put("${sendername}", emailObj.getSendFirstName());
		map.put("${recievername}", emailObj.getRecieveFirstName());
		map.put("${title}", emailObj.getTitle());
		map.put("${msg1}", emailObj.getMessage1());
		map.put("${msg2}", emailObj.getMessage2());
		map.put("${msg3}", emailObj.getMessage3());
		map.put("${sentBy}", emailObj.getSendEmail());
		String messageFile = config.getString("notificationEmail.fileName");
		return readFile(messageFile, map);
	}



}