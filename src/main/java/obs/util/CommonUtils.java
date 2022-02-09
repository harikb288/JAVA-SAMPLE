package obs.util;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.Principal;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plivo.api.Plivo;
import com.plivo.api.models.message.Message;

import obs.domain.User;
import obs.messages.MessageConstants;
import obs.service.IUserService;

@Service
public class CommonUtils {
	 static final Logger logger = LogManager.getLogger(CommonUtils.class);	
	@Autowired
	IUserService userService;
	public void smsgenerate(String destination,String msg)
	{
		String authId = MessageConstants.PLIVOAUTHID;
        String authToken = MessageConstants.PLIVOAUTHTOKEN;
        Plivo.init(authId, authToken);
        try {
        	Message.creator("16044494754", Collections.singletonList(destination), msg)
            .create();
        } catch (Exception e) {
            logger.error("smsgenerate",e);
        }
	}
	  public static String getBasicAuthHeader() {
	        return "Basic " + base64Encode(MessageConstants.OAUTHTC + ":" + MessageConstants.OAUTHTC);
	    }
	  private static String base64Encode(String token) {
	        byte[] encodedBytes = Base64.encodeBase64(token.getBytes());
	        return new String(encodedBytes, Charset.forName("UTF-8"));
	    }	
	  public String encode(String src) {
			try { 
				MessageDigest md;
				md = MessageDigest.getInstance( "SHA-256" ); 
				byte[] bytes= src.getBytes( "UTF-8" ); 
				md.update(bytes, 0, bytes. length ); 
				byte[] sha1hash = md.digest(); 
				return convertToHex(sha1hash);
			}    
			catch(Exception e){ 
				logger.error(e);
				return e.getMessage();
			} 
		}
	private String convertToHex(byte[] sha1hash) {
			StringBuilder builder = new StringBuilder(); 
			for (int i = 0; i < sha1hash. length ; i++) { 
				byte c = sha1hash[i]; 
				addHex(builder, (c >> 4) & 0xf); 
				addHex(builder, c & 0xf);
			} 
			return builder.toString();
		} 	
	
	private static void addHex(StringBuilder builder, int c) { 
		if (c < 10) 
			builder.append((char) (c + '0' ));
		else 
			builder.append((char) (c + 'a' - 10));
	}	
	
	public User getUser(final HttpServletRequest request){		
		if(request != null && request.getUserPrincipal()!= null){
		  Principal principal = request.getUserPrincipal();
		  return userService.getUserByUserName(principal.getName());
		} else {
			return null;
		}
	}
	
	public int checkQueryString(String username)
	{
		int a =0;
		if(username != null)
		{
			if(username.indexOf('@')!=-1)
			{
				a=1;
			} 
			if(username.matches("[+0-9]+") && username.length() > 0)
			{
				a=2;
			} 
			if((username.indexOf('@')==-1) && !username.matches("[+0-9]+") && username.length() > 0)
			{
				a=3;
			}
		}
		return a;
	}

}
