package obs.email;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class EmailManager {
	
	private EmailManager(){}
	private static final Logger logger					= LogManager.getLogger(EmailManager.class );

	protected static EmailConfiguration emailConfig = new EmailConfiguration();

	static Thread emailThread;


	public static boolean getUserVerify(EmailObject emailObj) {
		emailThread = new Thread(){
			@Override
			public void run() {
				try {
					EmailConfiguration emailConfig = new EmailConfiguration();
					EmailSender sender = new EmailSender();
					String message = emailConfig.getUserVerify(emailObj);
					sender.sendMailWithCCOfPerson(emailObj.getRecieveEmail(), emailObj.getAdminEmail(), emailConfig.getNewUserSubject(), message);
				} catch (Exception e) {
					logger.error("getUserVerify Email manager"+e);
				}
				finally{
					emailThread.interrupt();
					emailThread = null;
				}
			}
		};
		emailThread.start();
		return true;
	}

	public static boolean sentWelcomemail(EmailObject emailObj) {
		emailThread = new Thread(){
			@Override
			public void run() {
				try {
					EmailConfiguration emailConfig = new EmailConfiguration();
					EmailSender sender = new EmailSender();
					String message = emailConfig.sentWelcomemail(emailObj);
					sender.sendMailWithCCOfPerson(emailObj.getRecieveEmail(), emailObj.getAdminEmail(), emailConfig.getNewUserSubject(), message);
				} catch (Exception e) {
					logger.error("sentWelcomemail Email Manager"+e);
				}
				finally{
					emailThread.interrupt();
					emailThread = null;
				}
			}
		};
		emailThread.start();
		return true;
	}

       public static boolean forgotPassword(EmailObject emailObj) {
		emailThread = new Thread(){
			@Override
			public void run() {
				try {
					EmailConfiguration emailConfig = new EmailConfiguration();
					EmailSender sender = new EmailSender();
					String message = emailConfig.getForgotPasswordEmailMessage(emailObj);
					sender.sendFromCustomerService(emailObj.getRecieveEmail(), emailConfig.getForgotPasswordSubject(), message);
				} catch (Exception e) {
					logger.error("forgotPassword EmailManger"+e);
				} 
				finally{
					emailThread.interrupt();
					emailThread = null;
				}
			}
		};
		emailThread.start();
		return true;
	}

	public static boolean changePassword(EmailObject emailObj) {
		emailThread = new Thread(){
			@Override
			public void run() {
				try {
					EmailConfiguration emailConfig = new EmailConfiguration();
					EmailSender sender = new EmailSender();
					String message = emailConfig.getChangePasswordEmailMessage(emailObj);
					sender.sendFromCustomerService(emailObj.getRecieveEmail(), emailConfig.getForgotPasswordSubject(), message);
				} catch (Exception e) {
					logger.error("changePassword Email Manager"+e);
				} 
				finally{
					emailThread.interrupt();
					emailThread = null;
				}
			}
		};
		emailThread.start();
		return true;
	}


	public static boolean verifyEmail(EmailObject emailObj) {
		emailThread = new Thread(){
			@Override
			public void run() {
				try {
					EmailConfiguration emailConfig = new EmailConfiguration();
					EmailSender sender = new EmailSender();
					String message = emailConfig.getVerifyEmailMessage(emailObj);
					sender.sendFromCustomerService(emailObj.getRecieveEmail(), emailConfig.getVerifyEmailSubject(), message);
				} catch (Exception e) {
					logger.error("verifyEmail  EmailManager"+e);
				} 
				finally{
					emailThread.interrupt();
					emailThread = null;
				}
			}
		};
		emailThread.start();
		return true;
	}
	
	
	public static boolean twowayAuthentication(EmailObject emailObj) {
		emailThread = new Thread(){
			@Override
			public void run() {
				try {
					EmailConfiguration emailConfig = new EmailConfiguration();
					EmailSender sender = new EmailSender();
					String message = emailConfig.getTwoWayAuthenticationMessage(emailObj);
					sender.sendFromCustomerService(emailObj.getRecieveEmail(), emailConfig.getPasswordChangeSubject(), message);
				} catch (Exception e) {
					logger.error("twowayAuthentication Email Manager"+e);
				} 
				finally{
					emailThread.interrupt();
					emailThread = null;
				}
			}
		};
		emailThread.start();
		return true;
	}
	public static boolean userActivationByAdminMail(EmailObject emailObj) {
		emailThread = new Thread(){
			@Override
			public void run() {
				try {
					EmailConfiguration emailConfig = new EmailConfiguration();
					EmailSender sender = new EmailSender();
					String message = emailConfig.userActivationByAdminMail(emailObj);
					sender.sendFromCustomerService(emailObj.getRecieveEmail(), emailConfig.getUserActivationAdminSubject(), message);
				} catch (Exception e) {
					logger.error("userActivationByAdminMail Email Manager"+e);
				} 
				finally{
					emailThread.interrupt();
					emailThread = null;
				}
			}
		};
		emailThread.start();
		return true;
	}


	public static boolean sendNotificationEmail(EmailObject emailObj){
		emailThread = new Thread(){
			@Override
			public void run() {
				try {
					EmailSender sender = new EmailSender();
					String message = emailConfig.sentNotificationEmail(emailObj);
					sender.sendFromCustomerService(emailObj.getRecieveEmail(), emailObj.getTitle(), message);
				} catch (Exception e) {
					logger.error("sendNotificationEmail Email Manger"+e);
				}
				finally{
					emailThread.interrupt();
				}
			}
		};
		emailThread.start();
		return true;
	}

}


