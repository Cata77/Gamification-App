package spring.gamificationapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.gamificationapp.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findUserByUserName(String username);
    Optional<User> findUserByUserNameAndPassword(String username,String password);
}
