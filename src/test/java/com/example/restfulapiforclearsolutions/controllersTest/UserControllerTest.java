package com.example.restfulapiforclearsolutions.controllersTest;

import com.example.restfulapiforclearsolutions.controllers.UserController;
import com.example.restfulapiforclearsolutions.controllers.exceptionHendlers.exceptions.ValidationException;
import com.example.restfulapiforclearsolutions.models.User;
import com.example.restfulapiforclearsolutions.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void testGetUsersByBirthDateRange_ValidDates_Success() {
        LocalDate firstDate = LocalDate.of(1990, 1, 1);
        LocalDate lastDate = LocalDate.of(2000, 1, 1);
        User user = new User(1L, "John", "Doe", "john@example.com", LocalDate.of(1995, 1, 1), "Odesa", 1234567890);
        when(userService.getUsersByBirthDateRange(firstDate, lastDate)).thenReturn(Arrays.asList(user));

        ResponseEntity<?> response = userController.getUsersByBirthDateRange(firstDate, lastDate);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testGetUsersByBirthDateRange_InvalidDates_ValidationException() {
        LocalDate firstDate = LocalDate.of(2000, 1, 1);
        LocalDate lastDate = LocalDate.of(1990, 1, 1);

        assertThrows(ValidationException.class, () -> userController.getUsersByBirthDateRange(firstDate, lastDate));
    }

    @Test
    public void testGetUsers_UsersExist_Success() {
        User user = new User(1L, "John", "Doe", "john@example.com", LocalDate.of(1995, 1, 1), "Odesa", 1234567890);
        when(userService.getAllUsers()).thenReturn(Arrays.asList(user));

        ResponseEntity<?> response = userController.getUsers();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testGetUsers_NoUsers_Success() {
        when(userService.getAllUsers()).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = userController.getUsers();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testGetUser_UserExists_Success() {
        long userId = 1L;
        User user = new User(userId, "John", "Doe", "john@example.com", LocalDate.of(1995, 1, 1), "Odesa", 1234567890);
        when(userService.getUserById(userId)).thenReturn(user);

        ResponseEntity<?> response = userController.getUser(userId);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testGetUser_UserDoesNotExist_Success() {
        long userId = 1L;
        when(userService.getUserById(userId)).thenReturn(null);

        ResponseEntity<?> response = userController.getUser(userId);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }
}
