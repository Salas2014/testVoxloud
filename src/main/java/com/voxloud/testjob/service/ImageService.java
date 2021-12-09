package com.voxloud.testjob.service;

import com.voxloud.testjob.domain.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    byte[] downloadTodoImage(Long id, String username);

    List<Image> getAllImages(String username);

    List<Image> saveImages(String[] titles, String[] descriptions, MultipartFile[] files, String username);


}
