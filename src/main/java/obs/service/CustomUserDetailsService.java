package obs.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import obs.dao.IUserDAO;


@Service
public class CustomUserDetailsService implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

	@Autowired
	private IUserDAO iuserDAO;
	@Autowired
	public CustomUserDetailsService(IUserDAO iuserDAO) {
		this.iuserDAO = iuserDAO;
	}
	@SuppressWarnings({  "unchecked" })
	@Override
	public UserDetails loadUserByUsername(String username){
		obs.domain.UserSecurity usrsecure = iuserDAO.getUserSecurityByLogin(username);
		if (usrsecure == null) {
			throw new UsernameNotFoundException(String.format("User %s does not exist!", username));
		}
		obs.domain.User usr = usrsecure.getUser();
		String role = usr.getUserType().getUserTypeName();
		return new User(usr.getUserId(), usrsecure.getPassword(), getAuthorities(role));
	}
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private List getAuthorities(String role) {  
		List authList = new ArrayList();  
		authList.add(new SimpleGrantedAuthority("ROLE_USER"));       
		if (role != null && role.trim().length() > 0) {  
			if (role.equals("admin")) {
				authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));  
			}  
			if (role.equals("user")) {  
				authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));  
			} 
		}    
		return authList;  
	}

	public obs.domain.User viewProfile(String username){
		obs.domain.User user=null;
		try {
			user=iuserDAO.getUserById(username);
		}catch(Exception e){
			logger.error("view profile", e);
		}
		return user;
	}
}