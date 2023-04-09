package spring.gamificationapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spring.gamificationapp.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findUserByUserName(String username);
    Optional<User> findUserByUserNameAndPassword(String username,String password);
    @Query("SELECT u FROM User u ORDER BY u.tokens DESC")
    List<User> findAllUsersByTokensDesc();
}
