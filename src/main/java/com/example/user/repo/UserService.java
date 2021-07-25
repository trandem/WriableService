package com.example.user.repo;

import com.example.user.model.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepo repo;

    public UserService(UserRepo repo) {
        this.repo = repo;
    }

    public User findUserById(int userId) {
        Optional<User> user = repo.findById(userId);
        return user.orElse(null);
    }

    @Transactional(rollbackOn = Exception.class)
    public User updateUser(User user) {
        return repo.save(user);
    }
}
