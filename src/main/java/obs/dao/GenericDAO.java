package obs.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import obs.domain.AccountStatus;
import obs.domain.UserType;

@Repository
@Transactional
public class GenericDAO implements IGenericDAO {
private static final Logger logger = LogManager.getLogger(GenericDAO.class);
	

	@Autowired
	private EntityManager em;
	
@Override
		public UserType getUserTypeById(long id){
			List<UserType> list = null;
			try {			
				TypedQuery<UserType> query = em.createQuery("select u from UserType u where u.userTypeId=?",UserType.class).setParameter(0, id);
		        list = query.getResultList();
			} catch (Exception e) {
				logger.error("getUserTypeById DAO",e);
			}
			return (list!=null && !list.isEmpty())?list.get(0):null;  	
	    }
		
@Override
		public AccountStatus getAccountStatusById(long id){
			List<AccountStatus> list = null;
			try {			
				TypedQuery<AccountStatus> query = em.createQuery("select u from AccountStatus u where u.id=?",AccountStatus.class).setParameter(0, id);
		        list = query.getResultList();
			} catch (Exception e) {
				logger.error("getAccountStatusById DAO",e);
			}
			return (list!=null && !list.isEmpty())?list.get(0):null;  	
	    }
}
