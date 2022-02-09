package obs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import obs.dao.IGenericDAO;
import obs.domain.AccountStatus;
import obs.domain.UserType;

@Service
public class GenericService implements IGenericService{
	
	@Autowired
	private IGenericDAO genericDAO;

	@Override
	public UserType getUserTypeById(long id) {
		return genericDAO.getUserTypeById(id);
	}

	@Override
	public AccountStatus getAccountStatusById(long id) {
		return genericDAO.getAccountStatusById(id);
	}

}
