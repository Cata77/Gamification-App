package spring.gamificationapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.gamificationapp.model.Category;
import spring.gamificationapp.model.Task;
import spring.gamificationapp.model.TaskResult;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    @Query("SELECT t FROM Task t WHERE t.category = :category AND t.id NOT IN "
            + "(SELECT ut.task.id FROM UserTask ut WHERE ut.user.id = :userId)")
    List<Task> findTasksByCategoryAndExcludeUserTasks(
            @Param("category") Category category,
            @Param("userId") Long userId);

    @Query("SELECT t FROM Task t JOIN UserTask ut ON t.id = ut.task.id WHERE ut.user.id = :userId")
    List<Task> findForbiddenUserTasks(@Param("userId") Long userId);

    @Query("SELECT t FROM Task t JOIN UserTask ut ON t.id = ut.task.id WHERE ut.user.id = :userId "
            + "AND ut.taskResult = :taskResult")
    List<Task> findForbiddenUserTasksByTaskResult(
            @Param("userId") Long userId,
            @Param("taskResult") TaskResult taskResult);
}
