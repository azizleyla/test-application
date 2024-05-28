package com.unibank.unitech.service.impl;

import com.unibank.unitech.dto.request.UserRequestDto;
import com.unibank.unitech.entity.User;
import com.unibank.unitech.exception.UserAlreadyExistException;
import com.unibank.unitech.repository.UserRepository;
import com.unibank.unitech.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Override
    public void saveUser(UserRequestDto userRequestDto) {
        if (userRepository.findUserInfoByPinIgnoreCase(userRequestDto.getPin()).isPresent()) {
            throw new UserAlreadyExistException();
        }

        var user = User.builder()
                .pin(userRequestDto.getPin())
                .password(bCryptPasswordEncoder.encode(userRequestDto.getPassword()))
                .build();
        userRepository.save(user);
    }

}