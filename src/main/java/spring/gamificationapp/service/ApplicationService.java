package spring.gamificationapp.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import spring.gamificationapp.dto.TaskDto;
import spring.gamificationapp.dto.UserContributionsDto;
import spring.gamificationapp.dto.UserCorrectSolvedTasksDto;
import spring.gamificationapp.dto.UserProfileDto;
import spring.gamificationapp.model.Task;
import spring.gamificationapp.model.TaskResult;
import spring.gamificationapp.model.User;
import spring.gamificationapp.repository.TaskRepository;
import spring.gamificationapp.repository.UserRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

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

        userProfileDto.setContributedTasks(taskRepository.findForbiddenUserTasksByTaskResult(user.getId(), TaskResult.CONTRIBUTION).size());
        userProfileDto.setCorrectSolvedTasks(taskRepository.findForbiddenUserTasksByTaskResult(user.getId(), TaskResult.CORRECT_ANSWER).size());
        userProfileDto.setWrongSolvedTasks(taskRepository.findForbiddenUserTasksByTaskResult(user.getId(), TaskResult.WRONG_ANSWER).size());
        return userProfileDto;
    }

    public List<User> showRankingsByNumberOfTokens() {
        return userRepository.findAllUsersByTokensDesc();
    }
    public List<UserContributionsDto> showRankingsByNumberOfContributions() {
        List<User> users = userRepository.findAll();
        List<UserContributionsDto> userContributionsDtoList = new java.util.ArrayList<>(users.stream()
                .map(user -> modelMapper.map(user, UserContributionsDto.class))
                .toList());

        IntStream.range(0, users.size())
                .forEach(i -> userContributionsDtoList.get(i).setContributions(
                        taskRepository.findForbiddenUserTasksByTaskResult(
                                users.get(i).getId(), TaskResult.CONTRIBUTION).size()));

        userContributionsDtoList.sort(Comparator.comparing(UserContributionsDto::getContributions).reversed());
        return userContributionsDtoList;
    }

    public List<UserCorrectSolvedTasksDto> showRankingsByNumberOfCorrectSolvedTasks() {
        List<User> users = userRepository.findAll();
        List<UserCorrectSolvedTasksDto> userCorrectSolvedTasksDtoList = new java.util.ArrayList<>(users.stream()
                .map(user -> modelMapper.map(user, UserCorrectSolvedTasksDto.class))
                .toList());

        IntStream.range(0, users.size())
                .forEach(i -> userCorrectSolvedTasksDtoList.get(i).setCorrectSolvedTasks(
                        taskRepository.findForbiddenUserTasksByTaskResult(
                                users.get(i).getId(), TaskResult.CORRECT_ANSWER).size()));

        userCorrectSolvedTasksDtoList.sort(Comparator.comparing(UserCorrectSolvedTasksDto::getCorrectSolvedTasks).reversed());
        return userCorrectSolvedTasksDtoList;
    }
}
