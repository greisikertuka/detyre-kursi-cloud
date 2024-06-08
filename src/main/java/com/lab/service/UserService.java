package com.lab.service;

import com.lab.model.User;
import com.lab.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

@Slf4j
@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    @Transactional
    public void insertUser(User user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        userRepository.persist(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> findAllUsers() {
        List<User> usersFromDatabase = userRepository.listAll();
        for (var user : usersFromDatabase) {
            user.setPassword("");
        }
        return usersFromDatabase;
    }

    @Transactional
    public Response getUpdateProfileResponse(User user) {
        var fetcheduser = userRepository.findById(user.getId());

        //user is editing his own profile
        if (fetcheduser.getUsername().equals(user.getUsername())) {
            user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
            userRepository.getEntityManager().merge(user);
            return Response.status(Response.Status.NO_CONTENT).entity("{\"message\": \"Updated user successfully!\"}").build();
        }

        var existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            return Response.status(Response.Status.CONFLICT).entity("The username exists!").build();
        } else {
            user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
            userRepository.getEntityManager().merge(user);
            return Response.status(Response.Status.NO_CONTENT).entity("{\"message\": \"Updated user successfully!\"}").build();
        }
    }


    @Transactional
    public void updateUser(User user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        userRepository.getEntityManager().merge(user);
    }

    public User findUserById(Long id) {
        return userRepository.findById(id);
    }
}
