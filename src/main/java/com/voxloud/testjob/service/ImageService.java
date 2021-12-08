package com.voxloud.testjob.service;

import com.voxloud.testjob.domain.Image;
import com.voxloud.testjob.domain.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public interface ImageService {

    byte[] downloadTodoImage(Long id);

    List<Image> getAllTodos(String username);

    List<Image> saveImages(String[] titles, String[] descriptions, MultipartFile[] files, String username);

}
