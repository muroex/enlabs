package com.enlabs.service;

import com.enlabs.model.dto.UserDto;
import com.enlabs.model.entity.User;
import com.enlabs.repository.UserRepository;
import com.enlabs.servce.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    BCryptPasswordEncoder mockBCryptPasswordEncoder;
    @InjectMocks
    UserService userService;


    User user;
    @BeforeEach
    void setUp() {
        user=new User();
        user.setFirstName("AA");
        user.setLastName("BB");
        user.setEmail("aa@aa.co");
        user.setPassword("123456");
    }

    @Test
    void testAddUser() {

        User user=new User();
        user.setFirstName("AA");
        user.setLastName("BB");
        user.setEmail("aa@aa.co");
        user.setPassword("123456");

        UserDto userDto=new UserDto();
        userDto.setFirstName("AA");
        userDto.setLastName("BB");
        userDto.setEmail("aa@aa.co");
        userDto.setPassword("123456");

        Mockito.lenient().when(userRepository.existsByEmail(anyString())).thenReturn(false);
        Mockito.lenient().when(userRepository.save(new User())).thenReturn(user);

        User result = userService.addUser(userDto);
        Assertions.assertEquals(user.getFirstName(), result.getFirstName());
        Assertions.assertEquals(user.getEmail(), result.getEmail());
    }

    @Test
    void testUpdate() {
        User oldUser=new User();
        oldUser.setFirstName("AAC");
        oldUser.setLastName("BBC");
        oldUser.setEmail("aa@aa.coo");
        oldUser.setPassword("123456");

        UserDto userDto=new UserDto();
        userDto.setFirstName("AA");
        userDto.setLastName("BB");
        userDto.setEmail("aa@aa.co");
        userDto.setPassword("123456");

        User savedUser=new User();
        savedUser.setFirstName("AA");
        savedUser.setLastName("BB");
        savedUser.setEmail("aa@aa.co");
        savedUser.setPassword("123456");

        Mockito.lenient().when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.ofNullable(oldUser));
        Mockito.lenient().when(userRepository.save(new User())).thenReturn(savedUser);
        User result = userService.update(userDto);
        Assertions.assertEquals(savedUser.getFirstName(), result.getFirstName());
        Assertions.assertEquals(savedUser.getLastName(), result.getLastName());
        Assertions.assertEquals(savedUser.getEmail(), result.getEmail());
    }

    @Test
    void testFindUserByEmail() {
        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.ofNullable(user));
        String email="aa@aa.co";

        User result = userService.findUserByEmail(email);
        Assertions.assertEquals(user.getEmail(), result.getEmail());
    }
}