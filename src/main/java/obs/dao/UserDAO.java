package obs.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import obs.domain.User;
import obs.domain.UserSecurity;

@Repository
@Transactional
public class UserDAO implements IUserDAO{
	
	private static final Logger logger = LogManager.getLogger(UserDAO.class);
	

	@Autowired
	private EntityManager em;
    
    @SuppressWarnings("rawtypes")
	public User getUserById(String userId){
		List list = null;
		try {			
			TypedQuery<User> query = em.createQuery("select u from User u where u.userId=?",User.class).setParameter(0, userId);
	        list = query.getResultList();
		} catch (Exception e) {
			logger.error("getUserById DAO",e);
		}
		return (list!=null && !list.isEmpty())?(User)list.get(0):null;  	
    }
    
	@SuppressWarnings("rawtypes")
	@Override

	public User getUserByUserName(String userName){
		List list = null;
		try {			
			TypedQuery<User> query = em.createQuery("select u from User u where u.userName=?",User.class).setParameter(0, userName);
	        list = query.getResultList();
		} catch (Exception e) {
			logger.error("getUserByUserName DAO",e);
		}
		return (list!=null && !list.isEmpty())?(User)list.get(0):null;
	}
	
	@SuppressWarnings("rawtypes")
	public User getUserByUserIdAndNotification(String userId, boolean notification){
		List list = null;
		try {			
			TypedQuery<User> query = em.createQuery("select u from User u where u.userId=? and u.notification=?",User.class).setParameter(0, userId).setParameter(1, notification);
	        list = query.getResultList();
		} catch (Exception e) {
			logger.error("getUserByUserIdAndNotification DAO",e);
		}
		return (list!=null && !list.isEmpty())?(User)list.get(0):null;
	}

	public User getUserByUserPhone(String phone,String countryDialcode) {
		List<User> userList = null;
		try {
			TypedQuery<User> query = em.createQuery("select u from User u where u.phone=? and u.countryDialCode=?",User.class).setParameter(0, phone).setParameter(1, countryDialcode);
			userList = query.getResultList();
		} catch (Exception e) {
			logger.error("getUserByUserPhone DAO",e);
		}
		return (userList!=null && !userList.isEmpty())?userList.get(0):null;
	}
	
	@Override
	public User getUserBylogin(String login) {
		List<User> userList = null;
		try {
			TypedQuery<User> query = em.createNamedQuery("user.login", User.class).setParameter("id", login);
			userList = query.getResultList();
		} catch (Exception e) {
			logger.error("getUserByUserPhone DAO",e);
		}
		return (userList!=null && !userList.isEmpty())?userList.get(0):null;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public UserSecurity getUserSecurityById(String userId){
		List list = null;
		try {
			TypedQuery<UserSecurity> query = em.createQuery("select u from UserSecurity u where u.user.userId=?",UserSecurity.class).setParameter(0, userId);
	        list = query.getResultList();
		} catch (Exception e) {
			logger.error("getUserSecurityById DAO",e);
		}
		return (list!=null && !list.isEmpty())?(UserSecurity)list.get(0):null;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public UserSecurity getUserSecurityByUserName(String username){
		List list = null;
		try {
			TypedQuery<UserSecurity> query = em.createQuery("select u from UserSecurity u where u.userName=?",UserSecurity.class).setParameter(0, username);
	        list = query.getResultList();
		} catch (Exception e) {
			logger.error("getUserSecurityByUserName DAO",e);
		}
		return (list!=null && !list.isEmpty())?(UserSecurity)list.get(0):null;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public UserSecurity getUserSecurityByLogin(String username){
		List list = null;
		try {
			TypedQuery<UserSecurity> query = em.createQuery("select u from UserSecurity u where u.userName=:uname or u.user.email =:uname or concat(u.user.countryDialCode,u.user.phone)=:uname or u.user.phone=:uname or u.user.facebookId=:uname",UserSecurity.class).setParameter("uname", username);
	        list = query.getResultList();
		} catch (Exception e) {
			logger.error("getUserSecurityByLogin DAO",e);
		}
		return (list!=null && !list.isEmpty())?(UserSecurity)list.get(0):null;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public UserSecurity getUserSecurityByUserId(String userId){
		List list = null;
		try {
			TypedQuery<UserSecurity> query = em.createQuery("select u from UserSecurity u where u.user.userId=?",UserSecurity.class).setParameter(0, userId);
	        list = query.getResultList();
		} catch (Exception e) {
			logger.error("getUserSecurityByUserName DAO",e);
		}
		return (list!=null && !list.isEmpty())?(UserSecurity)list.get(0):null;
	}
	
	@SuppressWarnings("rawtypes")
	public User getUserBySocialId(String socialId,String type){
		List list = null;
		try {		
			String qry = "";
			if(type.equalsIgnoreCase("twitter")){
				qry = " twitterId ";
			} else if(type.equalsIgnoreCase("google")){
				qry = " googleId ";
			} else if(type.equalsIgnoreCase("facebook")){
				qry = " facebookId ";
			}
			TypedQuery<User> query = em.createQuery("from User where "+qry+"=? ",User.class).setParameter(0, socialId);
	        list = query.getResultList();
		} catch (Exception e) {
			logger.error("getUserBySocialId DAO",e);
		}
		return (list!=null && !list.isEmpty())?(User)list.get(0):null;
	}

}
