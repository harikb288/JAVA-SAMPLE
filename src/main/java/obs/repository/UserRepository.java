package obs.repository;

import org.springframework.data.repository.CrudRepository;

import obs.domain.User;



public interface UserRepository extends CrudRepository<User,String> {

}
