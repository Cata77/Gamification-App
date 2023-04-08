package spring.gamificationapp.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.gamificationapp.dto.TaskDto;
import spring.gamificationapp.model.Task;
import spring.gamificationapp.service.TaskService;

import java.util.logging.Logger;

@RestController
@RequestMapping("/v1/tasks")
public class TaskController {
    private final TaskService taskService;
    private final ModelMapper modelMapper;

    public TaskController(TaskService taskService, ModelMapper modelMapper) {
        this.taskService = taskService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/add-task/{username}")
    public ResponseEntity<TaskDto> addTask(
            @PathVariable String username,
            @RequestBody TaskDto taskDto) {
        taskService.createTask(username, taskDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taskDto);
    }
}
