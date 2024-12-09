package com.mostdevwill.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User Controller", description = "Operations related to users")
public class UserController {

    @GetMapping("/{id}")
    @Operation(
            summary = "Get user by ID",
            description = "Fetches a user by their unique ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User found",
                                 content = @Content(schema = @Schema(implementation = UserResponse.class))),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    public ResponseEntity<UserResponse> getUserById(@PathVariable String id) {
        // Dummy implementation
        return ResponseEntity.ok(new UserResponse(id, "John Doe", "john.doe@example.com"));
    }

    @PostMapping
    @Operation(
            summary = "Create a new user",
            description = "Creates a new user in the system.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request data")
            }
    )
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest) {
        // Dummy implementation
        return ResponseEntity.status(201).body("User created successfully");
    }

    // Response and Request classes
    public static class UserResponse {
        public String email;
        public String id;
        public String name;

        public UserResponse(String id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }
    }

    public static class UserRequest {
        public String email;
        public String name;
    }
}
