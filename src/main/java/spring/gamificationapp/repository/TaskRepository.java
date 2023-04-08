package spring.gamificationapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.gamificationapp.model.Task;
@Repository
public interface TaskRepository extends JpaRepository<Task,Integer> {
}
