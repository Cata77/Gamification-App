package spring.gamificationapp.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.gamificationapp.dto.CategoryTaskDto;
import spring.gamificationapp.dto.TaskDto;
import spring.gamificationapp.model.Task;
import spring.gamificationapp.model.User;
import spring.gamificationapp.service.TaskService;

import java.util.List;

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

    @PostMapping("/add-task")
    public ResponseEntity<TaskDto> addTask(
            @ModelAttribute("user") User user,
            @RequestBody TaskDto taskDto) {
        taskService.createTask(user, taskDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taskDto);
    }

    @GetMapping("/{category}")
    public ResponseEntity<List<CategoryTaskDto>> showTasksFromCategory(
            @ModelAttribute("user") User user,
            @PathVariable String category) {
        List<Task> tasks = taskService.findTasksFromCategory(user, category);

        List<CategoryTaskDto> categoryTaskDtoList = tasks.stream()
                .map(t -> modelMapper.map(t, CategoryTaskDto.class))
                .toList();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryTaskDtoList);
    }

    @PostMapping("/solve")
    public ResponseEntity<String> solveTask(
            @ModelAttribute("user") User user,
            @RequestParam String category,
            @RequestParam String answer) {
        taskService.solveTaskFromCategory(user, category, answer);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("You responded correctly and gained tokens!");
    }
}
