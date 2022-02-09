package obs.service;

import obs.domain.User;
import obs.domain.UserSecurity;

public interface IUserService {

	User getUserBylogin(String login);
	
	UserSecurity getUserSecurityByUserId(String userId);
	
	public User getUserById(String userId);

	User getUserByUserName(String userName);
	
	User getUserByUserIdAndNotification(String userId, boolean notification);
	
	User getUserByUserPhone(String phone,String countryDialcode);
	
	public UserSecurity getUserSecurityById(String userId);
	
	public UserSecurity getUserSecurityByUserName(String username);

	public User getUserBySocialId(String socialId,String type);
	
	UserSecurity getUserSecurityByLogin(String username);
}
