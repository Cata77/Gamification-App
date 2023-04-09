package spring.gamificationapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.gamificationapp.model.UserTask;
@Repository
public interface UserTaskRepository extends JpaRepository<UserTask,Long> {
}
