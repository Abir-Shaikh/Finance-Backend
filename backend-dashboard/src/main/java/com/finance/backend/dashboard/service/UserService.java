package com.finance.backend.dashboard.service;

import com.finance.backend.dashboard.model.Role;
import com.finance.backend.dashboard.model.User;
import com.finance.backend.dashboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User createUser(User user){
        if (repository.existsByEmail(user.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT , "Email already Registered");
        }
        if (user.getRole() == null) user.setRole(Role.VIEWER);
        user.setActive(true);
        return repository.save(user);
    }

    public List<User> getAllUsers(){
        return repository.findAll();
    }

    public User getUserById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND , "User not found"));
    }

    public User updateRole(Long id , Role role){
        User user = getUserById(id);
        user.setRole(role);

        return repository.save(user);
    }

    public User updateStatus(Long id , boolean active){
        User user = getUserById(id);
        user.setActive(active);
        return  repository.save(user);
    }


    public void deleteUser(Long id){
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , "User not found");
        }
        repository.deleteById(id);
    }

}