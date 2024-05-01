package com.example.restfulapiforclearsolutions.controllers;

import com.example.restfulapiforclearsolutions.DTO.UserDTO;
import com.example.restfulapiforclearsolutions.controllers.exceptionHendlers.exceptions.ValidationException;
import com.example.restfulapiforclearsolutions.models.User;
import com.example.restfulapiforclearsolutions.services.UserService;
import com.example.restfulapiforclearsolutions.utills.DataResponse;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/byBirthRange/")
    public ResponseEntity<DataResponse<?>> getUsersByBirthDateRange(@RequestParam("firstDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate firstDate,
    @RequestParam("lastDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate lastDate) {
            if (firstDate.isAfter(lastDate)) throw new ValidationException("The first date must be earlier than the last date");
            else {
                List<User> users = userService.getUsersByBirthDateRange(firstDate, lastDate);
                if (users != null && !users.isEmpty()) return ResponseEntity.ok(new DataResponse<>(users));
                else return ResponseEntity.ok(new DataResponse<>("No matches"));

            }
    }

    @GetMapping()
    public ResponseEntity<DataResponse<?>> getUsers() {
        List<User> users = userService.getAllUsers();
        if (users != null && !users.isEmpty()) return ResponseEntity.ok(new DataResponse<>(users));
        else return ResponseEntity.ok(new DataResponse<>("We have not any user now"));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<DataResponse<?>> getUser(@PathVariable long userId) {
        User user = userService.getUserById(userId);
        if (user != null) return ResponseEntity.ok(new DataResponse<>(user));
        else return ResponseEntity.ok(new DataResponse<>("No matches"));
    }

    @PostMapping()
    public ResponseEntity<?> createUser( @RequestBody UserDTO userDTO) {
            userService.createUser(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("New user successfully created");
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable long userId, @RequestBody UserDTO userDTO) {
        if (userService.updateUser(userId, userDTO)) return ResponseEntity.ok().body("User successfully updated");
        else return ResponseEntity.ok().body("No matches");
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<?> updateUserPartly(@PathVariable long userId, @RequestBody UserDTO userDTO) {
        if (userService.updateUserPartly(userId, userDTO)) return ResponseEntity.ok().body("Fields successfully updated");
        else return ResponseEntity.ok().body("No matches");
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

}
