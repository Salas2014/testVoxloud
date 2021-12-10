package com.voxloud.testjob.exception;

public class ImageNotFoundException extends RuntimeException{
    public ImageNotFoundException(Long id){
        super("Could not find image " + id);
    }
}
