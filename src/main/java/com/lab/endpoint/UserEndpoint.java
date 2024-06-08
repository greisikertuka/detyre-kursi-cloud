package com.lab.endpoint;

import com.lab.model.LoginRequest;
import com.lab.model.LoginResponse;
import com.lab.model.User;
import com.lab.model.enums.Role;
import com.lab.repository.UserRepository;
import com.lab.service.UserService;
import com.lab.utils.TokenService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;


@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserEndpoint {

    @Inject
    UserRepository userRepository;

    @Inject
    TokenService tokenService;
    @Inject
    UserService userService;


    @POST
    @Path("/login")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Login successful!", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = LoginResponse.class))),
            @APIResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = MediaType.TEXT_PLAIN)),
            @APIResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = MediaType.TEXT_PLAIN)),
    })
    public Response login(LoginRequest loginRequest) {
        // Find the user by username
        Optional<User> optionalUser = userRepository.findByUsername(loginRequest.getUsername());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // Verify the password using BCrypt
            if (BCrypt.checkpw(loginRequest.getPassword(), user.getPassword())) {
                String token = generateJwtToken(user);
                return Response.ok(new LoginResponse(token)).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).entity("Please check your password!").build();
            }
        }

        // Failed login
        return Response.status(Response.Status.NOT_FOUND).entity("The user does not exist!").build();
    }

    private String generateJwtToken(User user) {
        return tokenService.generateUserToken(user);
    }

    @POST
    @Path("/signUp")
    @APIResponses({
            @APIResponse(responseCode = "201", description = "Sign up successful", content = @Content(mediaType = "application/json")),
            @APIResponse(responseCode = "409", description = "This username already exists!", content = @Content(mediaType = "text/plain")),
    })
    @Transactional
    public Response signUp(User user) {
        // Find the user by username
        var optionalUser = userRepository.findByUsername(user.getUsername());

        if (optionalUser.isPresent()) {
            return Response.status(Response.Status.CONFLICT).entity("This username already exists!").build();
        }
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        user.setRole(Role.USER);
        userRepository.persist(user);
        return Response.status(Response.Status.CREATED).entity("{\"message\": \"Created user successfully!\"}").build();
    }

    @RolesAllowed({com.lab.utils.Role.USER, com.lab.utils.Role.ADMIN})
    @PUT
    @Path("/updateOwnProfile")
    @APIResponses({
            @APIResponse(responseCode = "204", description = "Edited profile successfully!", content = @Content(mediaType = "application/json")),
            @APIResponse(responseCode = "409", description = "User with selected username exists!", content = @Content(mediaType = "text/plain"))
    })
    public Response updateProfile(User user) {
        return userService.getUpdateProfileResponse(user);
    }

    @RolesAllowed({com.lab.utils.Role.ADMIN})
    @PUT
    @Path("/update")
    @APIResponses({
            @APIResponse(responseCode = "204", description = "Edited profile successfully!", content = @Content(mediaType = "application/json")),
            @APIResponse(responseCode = "409", description = "User with selected username exists!", content = @Content(mediaType = "text/plain"))
    })
    public Response updateUser(User user) {
        userService.updateUser(user);
        return Response.status(201).build();
    }

    @RolesAllowed({com.lab.utils.Role.ADMIN})
    @GET
    @APIResponse(responseCode = "200", description = "List of users found", content = @Content(mediaType = "application/json", schema = @Schema(type = SchemaType.ARRAY, implementation = User.class)))
    @Path("/all")
    public Response getAllUsers() {
        var response = userService.findAllUsers();
        if (response != null) {
            return Response.status(200).entity(response).build();
        } else {
            return Response.status(404).build();
        }
    }

    @RolesAllowed({com.lab.utils.Role.ADMIN})
    @DELETE
    @Path("/delete/{id}")
    public Response delete(@PathParam("id") Long id) {
        userService.deleteUser(id);
        return Response.status(201).build();
    }

    @RolesAllowed({com.lab.utils.Role.ADMIN})
    @POST
    @Path("/create")
    public Response create(User user) {
        userService.insertUser(user);
        return Response.status(201).build();
    }

    @GET
    @Path("/get/{id}")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "User found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @APIResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "text/plain"))
    })
    public Response getCarById(@PathParam("id") Long id) {
        var user = userService.findUserById(id);
        if (user != null) {
            user.setPassword("");

            return Response.status(200).entity(user).build();
        } else {
            return Response.status(404).build();
        }
    }
}
