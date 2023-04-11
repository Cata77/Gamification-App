package spring.gamificationapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.gamificationapp.dto.CategoryTaskDto;
import spring.gamificationapp.dto.TaskDto;
import spring.gamificationapp.exception.CategoryNotFoundException;
import spring.gamificationapp.exception.NoTaskFoundException;
import spring.gamificationapp.exception.NotEnoughOptionsException;
import spring.gamificationapp.exception.NotEnoughTokensException;
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
    @Operation(
            tags = {"Task"},
            description = "This endpoint allows user to create a task. If the task has a wrong category," +
                    "not enough options or tokens, an error will occur with a suggestive message.",
            responses = {
                    @ApiResponse(responseCode = "201",
                            description = "The task has been created successfully",
                            content = @Content(schema = @Schema(implementation = TaskDto.class),
                                    examples = @ExampleObject(value = """
                                            {
                                               "description": "Which access modifier in Java allows for the widest accessibility?",
                                               "options": [
                                                 {
                                                   "optionText": "private"
                                                 },
                                                 {
                                                   "optionText": "protected"
                                                 },
                                                 {
                                                   "optionText": "default"
                                                 },
                                                 {
                                                   "optionText": "public"
                                                 }
                                               ],
                                               "correctOption": "4",
                                               "tokens": 22,
                                               "category": "Java"
                                             }
                                            """))),
                    @ApiResponse(responseCode = "400 #1",
                            description = "The category was not found",
                            content = @Content(schema = @Schema(implementation = CategoryNotFoundException.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "message": "Category not found! Choose one from : Programming,Economy,Geography,Music,Movies,Sports"
                                            }
                                            """))),
                    @ApiResponse(responseCode = "400 #2",
                            description = "The number of tokens are not between 10 and 50",
                            content = @Content(schema = @Schema(implementation = NotEnoughTokensException.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "message": "Number of tokens awarded should be between 10 and 50!"
                                            }
                                            """))),
                    @ApiResponse(responseCode = "400 #3",
                            description = "The number of options is not 4",
                            content = @Content(schema = @Schema(implementation = NotEnoughOptionsException.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "message": "4 options required!"
                                            }
                                            """)))
            }
    )
    @PostMapping("/add-task")
    public ResponseEntity<TaskDto> addTask(
            @Parameter(hidden = true) @ModelAttribute("user")  User user,
            @RequestBody TaskDto taskDto) {
        taskService.createTask(user, taskDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taskDto);
    }

    @Operation(
            tags = {"Task"},
            description = "This endpoint allows user to see available tasks from a category. If the user specifies a wrong category," +
                    "or there are no available tasks, an error will occur with a suggestive message.",
            parameters = {@Parameter(name = "category", description = "category", example = "Programming")},
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "The list of tasks has been generated successfully",
                            content = @Content(schema = @Schema(implementation = CategoryTaskDto.class),
                                    examples = @ExampleObject(value = """
                                            [
                                                  {
                                                      "description": "What is the capital of Canada?",
                                                      "options": [
                                                          {
                                                              "optionText": "Toronto"
                                                          },
                                                          {
                                                              "optionText": "Ottawa"
                                                          },
                                                          {
                                                              "optionText": "Montreal"
                                                          },
                                                          {
                                                              "optionText": "Vancouver"
                                                          }
                                                      ],
                                                      "tokens": 27.00
                                                  },
                                                  {
                                                      "description": "What is the highest mountain in Africa?",
                                                      "options": [
                                                          {
                                                              "optionText": "Mount Kilimanjaro"
                                                          },
                                                          {
                                                              "optionText": "Mount Elgon"
                                                          },
                                                          {
                                                              "optionText": "Mount Kenya"
                                                          },
                                                          {
                                                              "optionText": "Mount Meru"
                                                          }
                                                      ],
                                                      "tokens": 29.00
                                                  }
                                              ]
                                            """))),
                    @ApiResponse(responseCode = "400 #1",
                            description = "The category was not found",
                            content = @Content(schema = @Schema(implementation = CategoryNotFoundException.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "message": "Category not found! Choose one from : Programming,Economy,Geography,Music,Movies,Sports"
                                            }
                                            """))),
                    @ApiResponse(responseCode = "400 #2",
                            description = "There aro no available task for a category",
                            content = @Content(schema = @Schema(implementation = NoTaskFoundException.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "message": "No new tasks for this category have been found!"
                                            }
                                            """)))
            }
    )
    @GetMapping("/{category}")
    public ResponseEntity<List<CategoryTaskDto>> showTasksFromCategory(
            @Parameter(hidden = true) @ModelAttribute("user") User user,
            @PathVariable String category) {
        List<Task> tasks = taskService.findTasksFromCategory(user, category);

        List<CategoryTaskDto> categoryTaskDtoList = tasks.stream()
                .map(t -> modelMapper.map(t, CategoryTaskDto.class))
                .toList();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryTaskDtoList);
    }

    @Operation(
            tags = {"Task"},
            description = "This endpoint allows user to solve a tasks from a category. If the user specifies a wrong category, wrong answer," +
                    "or there are no available tasks, an error will occur with a suggestive message.",
            parameters = {@Parameter(name = "category", description = "category", example = "Programming"),
                    @Parameter(name = "answer", description = "answer", example = "2")},
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "The user solved correctly a task",
                            content = @Content(schema = @Schema(implementation = CategoryTaskDto.class),
                                    examples = @ExampleObject(value = """
                                            You responded correctly and gained tokens!
                                            """))),
                    @ApiResponse(responseCode = "400 #1",
                            description = "The category was not found",
                            content = @Content(schema = @Schema(implementation = CategoryNotFoundException.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "message": "Category not found! Choose one from : Programming,Economy,Geography,Music,Movies,Sports"
                                            }
                                            """))),
                    @ApiResponse(responseCode = "400 #2",
                            description = "There aro no available task for a category",
                            content = @Content(schema = @Schema(implementation = NoTaskFoundException.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "message": "No new tasks for this category have been found!"
                                            }
                                            """))),
                    @ApiResponse(responseCode = "400 #3",
                            description = "There user offered a wrong answer",
                            content = @Content(schema = @Schema(implementation = NoTaskFoundException.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "message": "Wrong answer!"
                                            }
                                            """)))
            }
    )
    @PostMapping("/solve")
    public ResponseEntity<String> solveTask(
            @Parameter(hidden = true) @ModelAttribute("user") User user,
            @RequestParam String category,
            @RequestParam String answer) {
        taskService.solveTaskFromCategory(user, category, answer);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("You responded correctly and gained tokens!");
    }
}
