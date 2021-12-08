package com.voxloud.testjob.service;

import com.voxloud.testjob.domain.User;
import com.voxloud.testjob.repository.UserRepository;
import com.voxloud.testjob.utils.CustomUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public CustomUserDetail loadUserByUsername(String username) throws UsernameNotFoundException {

        Supplier<UsernameNotFoundException> s = () -> new UsernameNotFoundException("Problem during authentication!");

        User user = userRepository
                        .findUserByUsername(username)
                        .orElseThrow(s);


        return new CustomUserDetail(user);
    }
}
