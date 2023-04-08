package spring.gamificationapp.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.gamificationapp.dto.TaskDto;
import spring.gamificationapp.dto.UserProfileDto;
import spring.gamificationapp.model.User;
import spring.gamificationapp.service.TaskService;

@RestController
@RequestMapping("/v1/tasks")
@SessionAttributes("user")
public class TaskController {
    private final TaskService taskService;
    private final ModelMapper modelMapper;

    public TaskController(TaskService taskService, ModelMapper modelMapper) {
        this.taskService = taskService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileDto> showUserProfile(@ModelAttribute("user") User user) {
        UserProfileDto userProfileDto = modelMapper.map(user, UserProfileDto.class);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userProfileDto);
    }

    @PostMapping("/add-task")
    public ResponseEntity<TaskDto> addTask(
            @ModelAttribute("user") User user,
            @RequestBody TaskDto taskDto) {
        taskService.createTask(user, taskDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taskDto);
    }
}
