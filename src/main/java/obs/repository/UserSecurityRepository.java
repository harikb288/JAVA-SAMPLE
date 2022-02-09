package obs.repository;

import org.springframework.data.repository.CrudRepository;

import obs.domain.UserSecurity;

public interface UserSecurityRepository extends CrudRepository<UserSecurity,String> {

}
