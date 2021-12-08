package com.voxloud.testjob.service;

import com.voxloud.testjob.bucket.BucketName;
import com.voxloud.testjob.domain.Image;
import com.voxloud.testjob.domain.User;
import com.voxloud.testjob.repository.ImageRepository;
import com.voxloud.testjob.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.apache.http.entity.ContentType.*;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final FileStore fileStore;
    private final ImageRepository repository;
    private final UserRepository userRepository;



    @Override
    public Image saveTodo(String title, String description, MultipartFile file, String username) {
        //check if the file is empty
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file");
        }
        //Check if the file is an image
        if (!Arrays.asList(IMAGE_PNG.getMimeType(),
                IMAGE_BMP.getMimeType(),
                IMAGE_GIF.getMimeType(),
                IMAGE_JPEG.getMimeType()).contains(file.getContentType())) {
            throw new IllegalStateException("FIle uploaded is not an image");
        }
        //get file metadata
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        //Save Image in S3 and then save Todo in the database
        String path = String.format("%s/%s", BucketName.TODO_IMAGE.getBucketName(), UUID.randomUUID());
        String fileName = String.format("%s", file.getOriginalFilename());
        try {
            fileStore.upload(path, fileName, Optional.of(metadata), file.getInputStream());
        } catch (IOException e) {
            throw new IllegalStateException("Failed to upload file", e);
        }

        Optional<User> userByUsername = userRepository.findUserByUsername(username);

        Image image = Image.builder()
                .description(description)
                .title(title)
                .imagePath(path)
                .user(userByUsername.get())
                .imageFileName(fileName)
                .build();
        repository.save(image);
        return repository.findByTitle(image.getTitle());
    }

    @Override
    public byte[] downloadTodoImage(Long id) {
        Image image = repository.findById(id).get();
        return fileStore.download(image.getImagePath(), image.getImageFileName());
    }

    @Override
    public List<Image> getAllTodos(String username) {
        List<Image> images = new ArrayList<>();

        Optional<User> userByUsername = userRepository.findUserByUsername(username);
        if(userByUsername.isPresent()){
            userByUsername.get().getImages().forEach(images::add);
        }
        return images;
    }
}
