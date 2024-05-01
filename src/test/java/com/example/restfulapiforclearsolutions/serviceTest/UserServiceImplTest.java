package com.example.restfulapiforclearsolutions.serviceTest;

import com.example.restfulapiforclearsolutions.DTO.UserDTO;
import com.example.restfulapiforclearsolutions.models.User;
import com.example.restfulapiforclearsolutions.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private HashMap<Long, User> userDatabase = new HashMap<>();

    @InjectMocks
    private UserServiceImpl userService;


    @Test
    public void testCreateUser_ValidInput_Success() {
        UserDTO userDTO = new UserDTO("John", "Doe", "john@example.com", LocalDate.of(1990, 1, 1), "Odesa", 1234567890);

        User createdUser = userService.createUser(userDTO);

        assertNotNull(createdUser);
        assertEquals(userDTO.getFirstName(), createdUser.getFirstName());
    }

    @Test
    public void testUpdateUser_UserExists_Success() {
        long userId = 1L;
        UserDTO firstUser = new UserDTO("John", "Doe", "john@example.com", LocalDate.of(1990, 1, 1), "Paris", 1237890);
        UserDTO secondUser = new UserDTO( "Jane", "Roe", "jane@example.com", LocalDate.of(1995, 1, 1), "Prague", 9873210L);
        userService.createUser(firstUser);


        userService.updateUser(userId, secondUser);

        assertNotNull(userService.getUserById(userId));
        assertEquals(secondUser.getFirstName(), userService.getUserById(userId).getFirstName());
    }

    @Test
    public void testUpdateUser_UserDoesNotExist_ValidationException() {
        long userId = 1L;
        UserDTO userDTO = new UserDTO("John", "Doe", "john@example.com", LocalDate.of(1990, 1, 1), "Odesa", 1234567890);

        assertFalse(userService.updateUser(userId, userDTO));
    }

    @Test
    public void testGetUserById_UserExists_Success() {
        long userId = 1L;
        User expectedUser = new User(userId, "John", "Doe", "john@example.com", LocalDate.of(1990, 1, 1), "Odesa", 1234567890);
        when(userDatabase.get(userId)).thenReturn(expectedUser);
        userDatabase.put(userId,expectedUser);

        User actualUser = userDatabase.get(userId);

        assertNotNull(actualUser);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void testGetUserById_UserDoesNotExist_ReturnsNull() {
        long userId = 1L;

        User actualUser = userService.getUserById(userId);

        assertNull(actualUser);
    }

    @Test
    public void testUpdateUserPartly_UserExists_Success() {
        long userId = 1L;
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("John");
        UserDTO existUser = new UserDTO("Tim", "Kuk", "tim@gmail.com", LocalDate.of(2000,5,5),"Madrid", 467567567);
        userService.createUser(existUser);

        userService.updateUserPartly(userId, userDTO);

        assertEquals(userDTO.getFirstName(), userService.getUserById(userId).getFirstName());

    }

    @Test
    public void testUpdateUserPartly_UserDoesNotExist() {
        long userId = 1L;
        UserDTO userDTO = new UserDTO();

        assertFalse(userService.updateUserPartly(userId, userDTO));
    }

    @Test
    public void testDeleteUser_UserExists_ReturnsTrue() {
        long userId = 1L;
        UserDTO user1 = new UserDTO( "John", "Doe", "john@example.com", LocalDate.of(1990, 5, 10), "London", 1234567890);
        userService.createUser(user1);

        boolean result = userService.deleteUser(userId);

        assertTrue(result);
    }

    @Test
    public void testDeleteUser_UserDoesNotExist_ReturnsFalse() {
        long userId = 1L;

        boolean result = userService.deleteUser(userId);

        assertFalse(result);
    }

    @Test
    public void testGetUsersByBirthDateRange_ReturnsUsersInGivenRange() {
        UserDTO user1 = new UserDTO( "John", "Doe", "john@example.com", LocalDate.of(1990, 5, 10), "London", 1234567890);
        UserDTO user2 = new UserDTO( "Jane", "Doe", "jane@example.com", LocalDate.of(1995, 8, 15), "Paris", 9876543210L);
        UserDTO user3 = new UserDTO( "Alice", "Smith", "alice@example.com", LocalDate.of(1985, 2, 20), "New York", 5555555555L);
        userService.createUser(user1); userService.createUser(user2); userService.createUser(user3);

        LocalDate startDate = LocalDate.of(1990, 1, 1);
        LocalDate endDate = LocalDate.of(1995, 12, 31);

        List<User> usersInRange = userService.getUsersByBirthDateRange(startDate, endDate);

        assertNotNull(usersInRange);
        assertEquals(2, usersInRange.size());
        assertEquals(usersInRange.get(0).getFirstName(), user1.getFirstName());
        assertEquals(usersInRange.get(1).getFirstName(), user2.getFirstName());
    }

}
