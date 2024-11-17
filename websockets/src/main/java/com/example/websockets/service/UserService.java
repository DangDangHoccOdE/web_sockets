package com.example.websockets.service;

import com.example.websockets.repository.UserRepository;
import com.example.websockets.user.Status;
import com.example.websockets.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void saveUser(User user) {
        user.setStatus(Status.ONLINE);
        userRepository.save(user);
    }

    public void disconnect(User user) {
        var storedUser = userRepository.findById(user.getNickName())
                .orElse(null);

        if(storedUser != null) {
            storedUser.setStatus(Status.OFFLINE);
            userRepository.save(storedUser);
        }
    }

    public List<User> findAllConnection(){
        return userRepository.findAllByStatus(Status.ONLINE);
    }
}
