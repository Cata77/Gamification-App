package spring.gamificationapp.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.gamificationapp.dto.UserProfileDto;
import spring.gamificationapp.dto.UserTokensDto;
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

    @GetMapping("/profile")
    public ResponseEntity<UserProfileDto> showUserProfile(@ModelAttribute("user") User user) {
        UserProfileDto userProfileDto = applicationService.showAppUserProfile(user);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userProfileDto);
    }

    @GetMapping("/rankings")
    public ResponseEntity<List<UserTokensDto>> showRankingsByNumberOfTokens(@ModelAttribute("user") User user) {
        List<User> users = applicationService.showRankingsByNumberOfTokens();

        List<UserTokensDto> userTokensDtoList = users.stream()
                .map(u -> modelMapper.map(u, UserTokensDto.class))
                .toList();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userTokensDtoList);
    }
}
