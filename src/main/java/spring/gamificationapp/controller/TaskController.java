package spring.gamificationapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.gamificationapp.dto.TaskDto;
import spring.gamificationapp.service.TaskService;

@RestController
@RequestMapping("/v1/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
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
