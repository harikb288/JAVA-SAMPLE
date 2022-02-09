package obs.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import obs.customdomain.UserInput;
import obs.messages.MessageConstants;

@PropertySource("classpath:ValidationMessage/userValidation-msg.properties")
@Service
public class UserValidation {

	@Autowired
	private Environment env;
	
	@Autowired
	private BasicValidation basicvalidation;


	public String registrationValidation(UserInput userObj)
	{
		if(userObj == null)
		{
			return env.getProperty("datarequired");
		}
		if(!basicvalidation.passwordValidation(userObj.getPassword()))
		{
			return env.getProperty("passwordValidation");
		}
		if(!basicvalidation.checkStringnullandempty(userObj.getCountryCode()))
		{
			return env.getProperty("countryCode");
		}
		if(!basicvalidation.checkStringnullandempty(userObj.getPhone()))
		{
			return env.getProperty("phone");
		}
		if(!basicvalidation.checkStringnullandempty(userObj.getCountryDialCode()))
		{
			return env.getProperty("CountryDialcode");
		}

		if(!basicvalidation.emailValidation(userObj.getEmail()))
		{
			return env.getProperty("emailValidation");
		}
		if(!basicvalidation.futureDateValidation(userObj.getDob()))
		{
			return env.getProperty("futureDateValidation");
		}
		
		if(!basicvalidation.nameValidation(userObj.getFirstName()))
		{
			return env.getProperty("firstName");
		}
		if(!basicvalidation.nameValidation(userObj.getLastName()))
		{
			return env.getProperty("lastName");
		}
		return MessageConstants.SUCCESS;
	}



}
