package obs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import obs.dao.IUserDAO;
import obs.domain.User;
import obs.domain.UserSecurity;


@Service
public class UserService implements IUserService {
	
	@Autowired
	private IUserDAO userDAO;
	
	@Override
	public User getUserById(String userId){
		return userDAO.getUserById(userId);
	}
	
	@Override
	public User getUserByUserName(String userName){
		return userDAO.getUserByUserName(userName);
	}
	
	@Override
	public User getUserByUserIdAndNotification(String userId, boolean notification){
		return userDAO.getUserByUserIdAndNotification(userId, notification);
	}
	
	@Override
	public User getUserByUserPhone(String phone,String countryDialcode){
		return userDAO.getUserByUserPhone(phone, countryDialcode);
	}
	
	@Override
	public UserSecurity getUserSecurityById(String userId){
		return userDAO.getUserSecurityById(userId);
	}
	
	@Override
	public UserSecurity getUserSecurityByUserName(String username){
		return userDAO.getUserSecurityByUserName(username);
	}

	@Override
	public User getUserBySocialId(String socialId,String type){
		return userDAO.getUserBySocialId(socialId, type);
	}

	@Override
	public User getUserBylogin(String login) {
		return userDAO.getUserBylogin(login);
	}

	@Override
	public UserSecurity getUserSecurityByUserId(String userId) {
		return userDAO.getUserSecurityByUserId(userId);
	}

	@Override
	public UserSecurity getUserSecurityByLogin(String username) {
		return userDAO.getUserSecurityByLogin(username);
	}
}

