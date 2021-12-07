package com.voxloud.testjob.service;

import com.voxloud.testjob.domain.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    Image saveTodo(String title, String description, MultipartFile file);

    byte[] downloadTodoImage(Long id);

    List<Image> getAllTodos();
}
