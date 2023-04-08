package spring.gamificationapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.gamificationapp.model.Category;
import spring.gamificationapp.model.Task;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Integer> {
    List<Task> findAllByCategory(Category category);
}
