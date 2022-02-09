package obs.persistance;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import obs.customdomain.UserInput;
import obs.domain.User;
import obs.domain.UserSecurity;
import obs.repository.UserRepository;
import obs.repository.UserSecurityRepository;
import obs.service.IUserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginPesistanceTest{

	@Autowired
	private UserRepository urp;
	
	@Autowired
	private UserSecurityRepository usrp;
	
	@MockBean
	IUserService userservice;
	
	static UserInput up = null;
	
	@BeforeClass
	public static void getinputData()
	{
		if(up == null)
		{
			up = new UserInput();
			up.setCountryCode("IN");
			up.setCountryDialCode("+91");
			up.setFirstName("hari");
			up.setPhone("9940721527");
			up.setUserName("hari87");
		}
	}

	
	@Test
	public void checkRegistationpersistance()
	{
		User user = new User();
		
		user.setUserName(up.getUserName());
		user.setCountryDialCode(up.getCountryDialCode());
		user.setFirstName(up.getFirstName());
		user.setPhone(up.getPhone());
		
		User userEntity = urp.save(user);
		assertThat(userEntity.getUserId()).isNotNull();
		
		UserSecurity usersecurity = new UserSecurity();
		usersecurity.setUserName(up.getUserName());
		usersecurity.setUser(user);
		usersecurity.setPassword("password");
		UserSecurity usersecurityEntity =usrp.save(usersecurity);
		assertThat(usersecurityEntity.getUserSecurityId()).isNotNull();
		
	}
	
	@Test
	public void checklogin()
	{
	User u=	userservice.getUserBylogin("rk@yopmail.com");
	String uname = (u != null)?u.getUserName():"rk@yopmail.com";
	System.out.println("test uname "+uname);
	}
}
