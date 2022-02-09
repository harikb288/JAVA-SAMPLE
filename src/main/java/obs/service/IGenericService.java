package obs.service;

import obs.domain.AccountStatus;
import obs.domain.UserType;

public interface IGenericService {

	UserType getUserTypeById(long id);

	AccountStatus getAccountStatusById(long id);
}
