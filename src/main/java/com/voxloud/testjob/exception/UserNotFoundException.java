package com.voxloud.testjob.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;


public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String username){
        super("Could not find user " + username);
    }
}
