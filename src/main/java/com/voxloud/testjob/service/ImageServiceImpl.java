package com.voxloud.testjob.service;


import com.voxloud.testjob.bucket.BucketName;
import com.voxloud.testjob.domain.Image;
import com.voxloud.testjob.domain.User;
import com.voxloud.testjob.exception.ImageNotFoundException;
import com.voxloud.testjob.exception.UserNotFoundException;
import com.voxloud.testjob.repository.ImageRepository;
import com.voxloud.testjob.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileSystemNotFoundException;
import java.util.*;

import static org.apache.http.entity.ContentType.*;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final FileStore fileStore;
    private final ImageRepository repository;
    private final UserRepository userRepository;


    @Override
    public List<Image> saveImages(String[] titles, String[] descriptions, MultipartFile[] files, String username) {

        if (files.length == 0) {
            throw new IllegalStateException("Cannot upload empty file");
        }

        //Check if the file is an image
        for (MultipartFile file : files) {
            if (!Arrays.asList(IMAGE_PNG.getMimeType(),
                    IMAGE_BMP.getMimeType(),
                    IMAGE_GIF.getMimeType(),
                    IMAGE_JPEG.getMimeType()).contains(file.getContentType())) {
                throw new IllegalStateException("FIle uploaded is not an image");
            }
        }

        List<HashMap<String, String>> metadata = new ArrayList<>();
        List<Image> images = new ArrayList<>();

        var wrapper = new Object(){ int ordinal = 0; };

        Arrays.stream(files).forEach(file -> {

            //get file metadata
            metadata.add(new HashMap<>());
            metadata.get(wrapper.ordinal).put("Content-Type", file.getContentType());
            metadata.get(wrapper.ordinal).put("Content-Length", String.valueOf(file.getSize()));
            //Save Image in S3 and then save Image in the database
            String path = String.format("%s/%s", BucketName.TODO_IMAGE.getBucketName(), UUID.randomUUID());
            String fileName = String.format("%s", file.getOriginalFilename());
            try {
                fileStore.upload(path, fileName, Optional.of(metadata.get(wrapper.ordinal)), file.getInputStream());
            } catch (IOException e) {
                throw new IllegalStateException("Failed to upload file", e);
            }

            Optional<User> userByUsername = userRepository.findUserByUsername(username);

            if(userByUsername.isPresent()){
                Image image = Image.builder()
                        .tag(descriptions[wrapper.ordinal])
                        .title(titles[wrapper.ordinal])
                        .imagePath(path)
                        .user(userByUsername.get())
                        .imageFileName(fileName)
                        .build();
                repository.save(image);
                images.add(image);

            }
            wrapper.ordinal++;
        });

        return images;
    }

    @Override

    public byte[] downloadTodoImage(Long id, String username) {

        Optional<Image> image = repository.findById(id);

        if(image.isPresent()){
            return fileStore.download(image.get().getImagePath(), image.get().getImageFileName());
        }
        throw new FileSystemNotFoundException("images with id-" + id + " don't found ");
    }

    @Override
    public List<Image> getAllImages(String username) {
        List<Image> images = new ArrayList<>();

        Optional<User> userByUsername = userRepository.findUserByUsername(username);
        userByUsername.ifPresent(user -> user.getImages().forEach(images::add));

        return images;
    }

    @Override
    public Image updateImage(Long id, String username, Image image){

        User userByUsername = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        Optional<Image> first = userByUsername.getImages()
                .stream()
                .filter(img -> img.getId().equals(id))
                .findFirst();

        if(first.isPresent()){
            image.setUser(userByUsername);
            return repository.save(image);
        }else {
            throw new ImageNotFoundException(id);
        }
    }

}
