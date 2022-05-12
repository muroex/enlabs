package com.enlabs.servce;

import com.enlabs.exceptions.UserAlreadyExist;
import com.enlabs.exceptions.UserNotFoundException;
import com.enlabs.model.dto.UserDto;
import com.enlabs.model.entity.User;
import com.enlabs.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public User addUser(UserDto userDto){
        User user =new User();
        if(userRepository.existsByEmail(userDto.getEmail()))
            throw new UserAlreadyExist();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        userRepository.save(user);
        return user;

    }

    public User update(UserDto userDto){
        User user=findUserByEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        String password=passwordEncoder.encode(userDto.getPassword());
        if(!userDto.getPassword().equals(user.getPassword())){
           user.setPassword(password);
        }
        user.setEmail(userDto.getEmail());
        userRepository.save(user);
        return user;

    }

    public User findUserByEmail(String email){

        User user = userRepository.findUserByEmail(email).orElseThrow(UserNotFoundException::new);
        return user;

    }

}
