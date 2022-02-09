package obs.controller;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import obs.customdomain.UserInput;
import obs.domain.User;
import obs.helper.LoginServiceHelper;
import obs.messages.ResponseMessage;



@RestController
public class LoginController {

	@Autowired
	LoginServiceHelper loginServiceHelper;
	
	@PostMapping("/register")
	public @ResponseBody ResponseMessage<User> registerMobileUser(@RequestBody UserInput userObj,HttpServletRequest request,final HttpServletResponse res)
	{
		return loginServiceHelper.registerUser(userObj,request,res);
	}
	
	@PostMapping("/login")
	private @ResponseBody Map<?, ?> doLogin(@FormParam("username") String username,
			@FormParam("password") String password, HttpServletRequest request, final HttpServletResponse res) throws UnsupportedEncodingException, ServletException, ParseException  {
		return loginServiceHelper.doLogin(username, password, request, res);
	}
	
	
}
