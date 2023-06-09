package spring.gamificationapp.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import spring.gamificationapp.dto.TaskDto;
import spring.gamificationapp.exception.*;
import spring.gamificationapp.model.*;
import spring.gamificationapp.repository.OptionRepository;
import spring.gamificationapp.repository.TaskRepository;
import spring.gamificationapp.repository.UserRepository;
import spring.gamificationapp.repository.UserTaskRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final UserTaskRepository userTaskRepository;
    private final OptionRepository optionRepository;
    private final ModelMapper modelMapper;

    public TaskService(UserRepository userRepository, TaskRepository taskRepository, UserTaskRepository userTaskRepository, OptionRepository optionRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.userTaskRepository = userTaskRepository;
        this.optionRepository = optionRepository;
        this.modelMapper = modelMapper;
    }

    public void createTask(User user, TaskDto taskDto) {
        Task task = modelMapper.map(taskDto, Task.class);

        Optional<Category> category = checkIfCategoryExists(taskDto.getCategory());
        if (category.isPresent())
            task.setCategory(category.get());
        else throw new CategoryNotFoundException();

        if (taskDto.getTokens().compareTo(BigDecimal.valueOf(10)) < 0 || taskDto.getTokens().compareTo(BigDecimal.valueOf(50)) > 0)
            throw new NotEnoughTokensException();

        if (taskDto.getOptions().size() != 4)
            throw new NotEnoughOptionsException();

        UserTask userTask = new UserTask();
        userTask.setUser(user);
        userTask.setTask(task);
        userTask.setTaskResult(TaskResult.CONTRIBUTION);

        List<UserTask> userTasks = user.getForbiddenTasks();
        userTasks.add(userTask);
        user.setForbiddenTasks(userTasks);
        user.setTokens(user.getTokens().add(BigDecimal.valueOf(10)));

        optionRepository.saveAll(task.getOptions());
        taskRepository.save(task);
        userTaskRepository.save(userTask);
        userRepository.save(user);
    }

    public void solveTaskFromCategory(User user, String category, String answer) {
        List<Task> availableTasks = findTasksFromCategory(user, category);
        Task taskToSolve = availableTasks.get(0);

        UserTask userTask = new UserTask();
        userTask.setUser(user);
        userTask.setTask(taskToSolve);

        if (answer.equals(taskToSolve.getCorrectOption())) {
            userTask.setTaskResult(TaskResult.CORRECT_ANSWER);
            user.setTokens(user.getTokens().add(taskToSolve.getTokens()));
        } else {
            userTask.setTaskResult(TaskResult.WRONG_ANSWER);
            user.setTokens(user.getTokens().add(BigDecimal.valueOf(5)));
        }

        List<UserTask> forbiddenTasks = user.getForbiddenTasks();
        forbiddenTasks.add(userTask);
        user.setForbiddenTasks(forbiddenTasks);

        userTaskRepository.save(userTask);
        userRepository.save(user);

        if (userTask.getTaskResult() == TaskResult.WRONG_ANSWER)
            throw new WrongAnswerException();
    }

    public List<Task> findTasksFromCategory(User user, String category) {
        Optional<Category> categoryFound = checkIfCategoryExists(category);
        if (categoryFound.isEmpty())
            throw new CategoryNotFoundException();

        List<Task> tasks = taskRepository.findTasksByCategoryAndExcludeUserTasks(
                categoryFound.get(), user.getId());
        if (tasks.isEmpty())
            throw new NoTaskFoundException();

        return tasks;
    }

    public Optional<Category> checkIfCategoryExists(String category) {
        for (Category c : Category.values())
            if (c.name().equalsIgnoreCase(category))
                return Optional.of(c);
        return Optional.empty();
    }
}
