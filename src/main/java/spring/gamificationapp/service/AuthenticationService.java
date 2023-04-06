package spring.gamificationapp.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import spring.gamificationapp.dto.AuthenticatedUserDto;
import spring.gamificationapp.exception.UserAlreadyTakenException;
import spring.gamificationapp.model.User;
import spring.gamificationapp.repository.UserRepository;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    public AuthenticationService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public void registerUser(AuthenticatedUserDto authenticatedUserDto) {
        if (!checkIfUserExists(authenticatedUserDto)) {
            User user = modelMapper.map(authenticatedUserDto, User.class);
            userRepository.save(user);
        } else throw new UserAlreadyTakenException();
    }

    public boolean checkIfUserExists(AuthenticatedUserDto authenticatedUserDto) {
        Optional<User> foundUser = userRepository.findUserByUserName(authenticatedUserDto.getUserName());
        return foundUser.isPresent();
    }
}
