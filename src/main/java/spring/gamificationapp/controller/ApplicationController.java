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
import spring.gamificationapp.dto.*;
import spring.gamificationapp.model.User;
import spring.gamificationapp.service.ApplicationService;

import java.util.List;

@RestController
@RequestMapping("/v1/app")
@SessionAttributes("user")
public class ApplicationController {
    private final ModelMapper modelMapper;
    private final ApplicationService applicationService;

    public ApplicationController(ModelMapper modelMapper, ApplicationService applicationService) {
        this.modelMapper = modelMapper;
        this.applicationService = applicationService;
    }

    @Operation(
            tags = {"Application"},
            description = "This endpoint allows user to see his profile",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "The user profile was generated successfully",
                            content = @Content(schema = @Schema(implementation = UserProfileDto.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "userName": "testUser",
                                                "forbiddenTasks": [
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
                                                        "tokens": 22.00,
                                                        "category": "PROGRAMMING"
                                                    },
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
                                                        "tokens": 10.00,
                                                        "category": "PROGRAMMING"
                                                    },
                                                    {
                                                        "description": "Who is the lead singer of the band 'The Rolling Stones'?",
                                                        "options": [
                                                            {
                                                                "optionText": "Mick Jagger"
                                                            },
                                                            {
                                                                "optionText": "Paul McCartney"
                                                            },
                                                            {
                                                                "optionText": "Freddie Mercury"
                                                            },
                                                            {
                                                                "optionText": "Bruce Springsteen"
                                                            }
                                                        ],
                                                        "correctOption": "1",
                                                        "tokens": 12.00,
                                                        "category": "MUSIC"
                                                    },
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
                                                        "correctOption": "2",
                                                        "tokens": 27.00,
                                                        "category": "GEOGRAPHY"
                                                    }
                                                ],
                                                "tokens": 57.00,
                                                "contributedTasks": 3,
                                                "correctSolvedTasks": 1,
                                                "wrongSolvedTasks": 0
                                            }
                                            """)))
            }
    )
    @GetMapping("/profile")
    public ResponseEntity<UserProfileDto> showUserProfile(@Parameter(hidden = true) @ModelAttribute("user") User user) {
        UserProfileDto userProfileDto = applicationService.showAppUserProfile(user);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userProfileDto);
    }

    @Operation(
            tags = {"Application"},
            description = "This endpoint prints shows users rankings by the number of tokens",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "The list was generated successfully",
                            content = @Content(schema = @Schema(implementation = UserTokensDto.class),
                                    examples = @ExampleObject(value = """
                                            [
                                                 {
                                                     "userName": "testUser",
                                                     "tokens": 57.00
                                                 },
                                                 {
                                                     "userName": "testUser3",
                                                     "tokens": 40.00
                                                 },
                                                 {
                                                     "userName": "testUser2",
                                                     "tokens": 20.00
                                                 }
                                             ]
                                            """)))
            }
    )
    @GetMapping("/rankings/tokens")
    public ResponseEntity<List<UserTokensDto>> showRankingsByNumberOfTokens(@Parameter(hidden = true) @ModelAttribute("user") User user) {
        List<User> users = applicationService.showRankingsByNumberOfTokens();

        List<UserTokensDto> userTokensDtoList = users.stream()
                .map(u -> modelMapper.map(u, UserTokensDto.class))
                .toList();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userTokensDtoList);
    }

    @Operation(
            tags = {"Application"},
            description = "This endpoint prints shows users rankings by the number of contributions",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "The list was generated successfully",
                            content = @Content(schema = @Schema(implementation = UserContributionsDto.class),
                                    examples = @ExampleObject(value = """
                                            [
                                                  {
                                                      "userName": "testUser3",
                                                      "contributions": 3
                                                  },
                                                  {
                                                      "userName": "testUser",
                                                      "contributions": 2
                                                  },
                                                  {
                                                      "userName": "testUser2",
                                                      "contributions": 1
                                                  }
                                              ]
                                            """)))
            }
    )
    @GetMapping("/rankings/contributions")
    public ResponseEntity<List<UserContributionsDto>> showRankingsByNumberOfContributions(@Parameter(hidden = true) @ModelAttribute("user") User user) {
        List<UserContributionsDto> userContributionsDtoList = applicationService.showRankingsByNumberOfContributions();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userContributionsDtoList);
    }

    @Operation(
            tags = {"Application"},
            description = "This endpoint prints shows users rankings by the number of correct solved tasks",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "The list was generated successfully",
                            content = @Content(schema = @Schema(implementation = UserCorrectSolvedTasksDto.class),
                                    examples = @ExampleObject(value = """
                                            [
                                                   {
                                                       "userName": "testUser2",
                                                       "correctSolvedTasks": 1
                                                   },
                                                   {
                                                       "userName": "testUser",
                                                       "correctSolvedTasks": 1
                                                   },
                                                   {
                                                       "userName": "testUser3",
                                                       "correctSolvedTasks": 0
                                                   }
                                               ]
                                            """)))
            }
    )
    @GetMapping("/rankings/correct-tasks")
    public ResponseEntity<List<UserCorrectSolvedTasksDto>> showRankingsByNumberOfCorrectSolvedTasks(@Parameter(hidden = true) @ModelAttribute("user") User user) {
        List<UserCorrectSolvedTasksDto> userCorrectSolvedTasksDtoList = applicationService.showRankingsByNumberOfCorrectSolvedTasks();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userCorrectSolvedTasksDtoList);
    }
}
