package spring.gamificationapp.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import spring.gamificationapp.dto.TaskDto;
import spring.gamificationapp.exception.CategoryNotFoundException;
import spring.gamificationapp.exception.NotEnoughOptionsException;
import spring.gamificationapp.exception.NotEnoughTokensException;
import spring.gamificationapp.exception.UserNotFoundException;
import spring.gamificationapp.model.Category;
import spring.gamificationapp.model.Task;
import spring.gamificationapp.model.User;
import spring.gamificationapp.repository.OptionRepository;
import spring.gamificationapp.repository.TaskRepository;
import spring.gamificationapp.repository.UserRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final OptionRepository optionRepository;
    private final ModelMapper modelMapper;

    public TaskService(UserRepository userRepository, TaskRepository taskRepository, OptionRepository optionRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.optionRepository = optionRepository;
        this.modelMapper = modelMapper;
    }

    public void createTask(User user, TaskDto taskDto) {
        Task task = modelMapper.map(taskDto, Task.class);

        if (!checkIfCategoryExists(taskDto.getCategory(),task))
            throw new CategoryNotFoundException();

        if (taskDto.getTokens().compareTo(BigDecimal.valueOf(10)) < 0 || taskDto.getTokens().compareTo(BigDecimal.valueOf(50)) > 0)
            throw new NotEnoughTokensException();

        if (taskDto.getOptions().size() != 4)
            throw new NotEnoughOptionsException();

        List<Task> userTasks = user.getForbiddenTasks();
        userTasks.add(task);
        user.setForbiddenTasks(userTasks);

        optionRepository.saveAll(task.getOptions());
        taskRepository.save(task);
        userRepository.save(user);

    }

    public static boolean checkIfCategoryExists(String category, Task task) {
        for (Category c : Category.values())
            if (c.name().equalsIgnoreCase(category)) {
                task.setCategory(c);
                return true;
            }
        return false;
    }
}
