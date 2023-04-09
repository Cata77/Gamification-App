package spring.gamificationapp.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import spring.gamificationapp.dto.TaskDto;
import spring.gamificationapp.dto.UserProfileDto;
import spring.gamificationapp.model.Task;
import spring.gamificationapp.model.User;
import spring.gamificationapp.repository.TaskRepository;
import spring.gamificationapp.repository.UserRepository;

import java.util.List;

@Service
public class ApplicationService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public ApplicationService(ModelMapper modelMapper, UserRepository userRepository, TaskRepository taskRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    public UserProfileDto showAppUserProfile(User user) {
        UserProfileDto userProfileDto = modelMapper.map(user,UserProfileDto.class);
        List<Task> userForbiddenTasks = taskRepository.findForbiddenUserTasks(user.getId());

        List<TaskDto> list = userForbiddenTasks.stream()
                .map(task -> modelMapper.map(task,TaskDto.class))
                .toList();
        userProfileDto.setForbiddenTasks(list);
        return userProfileDto;
    }

    public List<User> showRankingsByNumberOfTokens() {
        return userRepository.findAllUsersByTokensDesc();
    }
}
