package obs.helper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import obs.customdomain.UserInput;
import obs.domain.User;
import obs.domain.UserSecurity;
import obs.email.EmailManager;
import obs.email.EmailObject;
import obs.messages.MessageConstants;
import obs.messages.ResponseMessage;
import obs.messages.ResponseStatus;
import obs.messages.ResponseStatusCode;
import obs.repository.UserRepository;
import obs.repository.UserSecurityRepository;
import obs.service.IGenericService;
import obs.service.IUserService;
import obs.util.CommonProperties;
import obs.util.CommonUtils;
import obs.util.RandomPasswordGenerator;
import obs.validation.UserValidation;



@PropertySource("classpath:application-msg.properties")
@Service
public class LoginServiceHelper {
	private static final Logger logger = LogManager.getLogger(LoginServiceHelper.class);

	@Autowired
	private Environment env;

	@Autowired
	private IUserService userservice;

	@Autowired
	private UserRepository userrepository;

	@Autowired
	private UserSecurityRepository usersecurityrepository;

	@Autowired
	private UserValidation uservalidation;

	@Autowired
	CommonUtils commonUtils;

	@Autowired
	RandomPasswordGenerator randomPasswordGenerator;

	@Autowired
	PasswordEncoder passwordencoder;
	
	@Autowired
	IGenericService genericService;

	/**
	 * 
	 * @param userObj
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */	
	public ResponseMessage<User> registerUser(UserInput userObj,HttpServletRequest request,final HttpServletResponse response) {
		ResponseStatus status = null;
		User user=null;
		User userEntity=null;
		response.setHeader("Cache-Control", "no-cache");	
		String validation=uservalidation.registrationValidation(userObj);
		try
		{
			if(validation.equalsIgnoreCase(MessageConstants.SUCCESS)){
				user= userservice.getUserBylogin(userObj.getEmail());
				
				if(user == null)
				{
					String id = saveUserInput(new User(), userObj);					
					if(id != null){
						userEntity = userservice.getUserById(id);
						saveUserSecurity(userEntity,userObj.getPassword());
						}
					sendWelcomeMail(userEntity);
					response.setStatus(ResponseStatusCode.STATUS_OK);
					status=new ResponseStatus(ResponseStatusCode.STATUS_OK,env.getProperty("user.Register"));
				}
				else
				{
					status=new ResponseStatus(ResponseStatusCode.STATUS_ALREADY_EXISTS,env.getProperty("user.AlreadyExists"));
					return new ResponseMessage<>(status, null);
				}
			}
			else
			{
				status=new ResponseStatus(ResponseStatusCode.STATUS_REQUIRED,validation);
				return new ResponseMessage<>(status,null);
			}
		}catch(Exception e)
		{
			logger.error("registerMobileUser Exception", e);
			status = new ResponseStatus(ResponseStatusCode.STATUS_INTERNAL_ERROR,env.getProperty("InternalError"));
			response.setStatus(ResponseStatusCode.STATUS_INTERNAL_ERROR); 
		}
		return new ResponseMessage<>(status, userEntity);
	}
	
	private void sendWelcomeMail(User userEntity)
	{
		EmailObject emobj = new EmailObject("admin@tcApp.com", userEntity.getEmail(), "", "admin@tcApp.com");
		emobj.setRecieveFirstName(userEntity.getFirstName());
		emobj.setRecieveUserName(userEntity.getUserName());
		EmailManager.sentWelcomemail(emobj);
	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @param request
	 * @param res
	 * @return
	 * @throws ServletException
	 * @throws UnsupportedEncodingException
	 * @throws ParseException
	 * @throws InetAddressLocatorException 
	 */	
	@SuppressWarnings("unchecked")
	public JSONObject doLogin(String username, String password, HttpServletRequest request, HttpServletResponse res)
			throws ServletException, UnsupportedEncodingException, ParseException {

		JSONObject json1 = new JSONObject();
		JSONObject json2 = new JSONObject();
		String baseUrl = CommonProperties.getBaseURL() + CommonProperties.getContextPath() + MessageConstants.OAUTH;
		HashMap<String,String> input = new HashMap<>();
		input.put("username", username);
		input.put("password", password);
		input.put("grant_type","password");
		String errDescription = "";
		User user = userservice.getUserBylogin(username);
		UserSecurity userSecurity =(user != null ) ?userservice.getUserSecurityByUserId(user.getUserId()):null;
		if( userSecurity != null){
			if(user.getInvalidAttempt() >= 5 && user.getBlockedOn() != null)
			{
				Instant then = user.getBlockedOn().toInstant();
				Instant now = Instant.now();
				Instant twentyFourHoursEarlier = now.minus( 24 , ChronoUnit.HOURS );
				boolean check = then.isBefore(twentyFourHoursEarlier);
				if(!check)
				{
					errDescription = env.getProperty("error.exceedAttemps");
					json2.put("error", "blocked");
					json2.put("error_description", errDescription);
					json2.put("error_code", 2);
					res.setStatus(ResponseStatusCode.STATUS_INVALID);
					return json2;
				}else
				{			
					user.setBlockedOn(null);
					user.setInvalidAttempt(0);
					userrepository.save(user);
				}
			}
			if(user.getAccountstatus() != null && user.getAccountstatus().getId() == 0)
			{

				errDescription = env.getProperty("error.unverifiedUser");
				json2.put("error", "invalid Account");
				json2.put("error_description", errDescription);
				json2.put("error_code", 3);
				res.setStatus(ResponseStatusCode.STATUS_UNAUTHORIZED);
				return json2;
			}			
		}
		String body = getDataString(input);
		System.out.println( "CommonUtils.getBasicAuthHeader()  "+CommonUtils.getBasicAuthHeader());
		Client client = ClientBuilder.newClient();
		Builder builder = client.target(baseUrl).request();
		Invocation inv = builder
				.header("Content-type", MediaType.APPLICATION_FORM_URLENCODED)
				.header("Authorization", CommonUtils.getBasicAuthHeader())
				.buildPost(Entity.entity(body, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		Response response = inv.invoke();
		String responseAsString = response.readEntity(String.class);		
		if(responseAsString != null)
		{
			JSONParser parser = new JSONParser();
			json1 = (JSONObject) parser.parse(responseAsString);	

		}

		res.setStatus(response.getStatus());
		return json1;
	}

	/**
	 * 
	 * @param params
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String getDataString(HashMap<String, String> params) throws UnsupportedEncodingException{
		StringBuilder result = new StringBuilder();
		boolean first = true;
		for(Map.Entry<String, String> entry : params.entrySet()){
			if (first)
				first = false;
			else
				result.append("&");    
			result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
			result.append("=");
			result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
		}    
		return result.toString();
	}



	/**
	 * 
	 * @param user
	 * @param userObj
	 * @return
	 * @throws Exception
	 */
	private String saveUserSecurity(User user,String password) {
		String id = null;
		try{
			UserSecurity userSecurity = new UserSecurity();
			userSecurity.setUser(user);
			userSecurity.setUserName(user.getUserName());		
			userSecurity.setPassword(passwordencoder.encode(password));			
			UserSecurity usersecurity = usersecurityrepository.save(userSecurity);
			if(usersecurity != null){
				id = usersecurity.getUserSecurityId();
			}
		}
		catch(Exception e)
		{
			logger.error("Exception", e);
		}
		return id;
	}


	/**
	 * 
	 * @param user
	 * @param userObj
	 * @return
	 * @throws Exception
	 */
	private String saveUserInput(User user,UserInput userObj) {
		String id = null;
		try{
			String userName = randomPasswordGenerator.generatePwd(userObj.getEmail());
			user.setPhone(userObj.getPhone());
			user.setEmail(userObj.getEmail());
			user.setCountryDialCode(userObj.getCountryDialCode());
		
			user.setUserName(userName);
			user.setAccountstatus(genericService.getAccountStatusById(1));
			user.setCreatedOn(new Date());		
			user.setInvalidAttempt(0);
			user.setUserType(genericService.getUserTypeById(1));
			user.setActive(true);
			User userSave = userrepository.save(user);
			if(userSave != null){
				id = userSave.getUserId();
			}
		}
		catch(Exception e)
		{
			logger.error("Exception", e);
		}
		return id;
	}

}
