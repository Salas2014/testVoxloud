package com.voxloud.testjob.exception;

public class ConflictUniqueValue extends RuntimeException{
    public ConflictUniqueValue(String tag){
        super(tag + " must be unique");
    }
}
