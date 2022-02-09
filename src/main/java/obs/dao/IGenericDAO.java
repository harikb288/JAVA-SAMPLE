package obs.dao;

import obs.domain.AccountStatus;
import obs.domain.UserType;

public interface IGenericDAO {

	UserType getUserTypeById(long id);

	AccountStatus getAccountStatusById(long id);

}
