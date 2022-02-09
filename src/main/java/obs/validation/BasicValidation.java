package obs.validation;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.springframework.stereotype.Service;

@Service
public class BasicValidation {

	public boolean checkStringnullandempty(String content)
	{
		return (content != null && !content.isEmpty())?true:false;
	}
	 
	public boolean checkListnullandsize(List<?> content)
		{
			return (content != null && !content.isEmpty())?true:false;
		}
	
	public boolean emailValidation(String content)
	{
		return (content != null && !content.isEmpty() && isValidEmailAddress(content))?true:false;
	}
	
	private boolean isValidEmailAddress(String email) {
		   boolean result = true;
		   try {
		      InternetAddress emailAddr = new InternetAddress(email);
		      emailAddr.validate();
		   } catch (AddressException ex) {
		      result = false;
		   }
		   return result;
		}
	
	public boolean futureDateValidation(Date date)
	{
		if(date != null)
		{
		Instant givenInstant = date.toInstant();
		Instant currentdate = Instant.now();
		return currentdate.isAfter(givenInstant)?true:false;
		}
		return false;
	}
	
	private boolean isAlphaNumericWithspecialCharacters(String s){
		Pattern pattern = Pattern.compile("((?=.*\\d)(?=.*[a-zA-Z])(?=.*[!?@#$%^&*()._-]).{8,20})");
		return pattern.matcher(s).matches();
	}
	
	public boolean passwordValidation(String password)
	{
		return (isAlphaNumericWithspecialCharacters(password) )?true:false;
	}
	
	public boolean isalphabets(String s)
	{
		Pattern pattern = Pattern.compile("^[a-zA-Z]+$");
		return pattern.matcher(s).matches();
	}
	

	
	public boolean nameValidation(String name)
	{
		if(name != null && !name.isEmpty())
		{
		Pattern pattern = Pattern.compile("^[a-zA-Z .]+$");
		return pattern.matcher(name).matches();
		}
		return false;
	}
}
