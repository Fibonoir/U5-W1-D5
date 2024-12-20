package it.epicode.bookings_manager.service;

import it.epicode.bookings_manager.entity.User;
import it.epicode.bookings_manager.repository.UserRepository;
import it.epicode.bookings_manager.service.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getByUsername(String username){
        User user = userRepository.findByUsername(username);
        if(user == null) throw new ResourceNotFoundException("User not found: " + username);
        return user;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }
}
