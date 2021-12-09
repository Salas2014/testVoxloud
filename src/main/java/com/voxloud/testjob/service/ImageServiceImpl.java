package com.voxloud.testjob.service;

import com.amazonaws.services.mq.model.NotFoundException;
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

        Arrays.stream(files).forEach(file -> {
            int a = 0;
            //get file metadata
            metadata.add(new HashMap<>());
            metadata.get(a).put("Content-Type", file.getContentType());
            metadata.get(a).put("Content-Length", String.valueOf(file.getSize()));
            //Save Image in S3 and then save Image in the database
            String path = String.format("%s/%s", BucketName.TODO_IMAGE.getBucketName(), UUID.randomUUID());
            String fileName = String.format("%s", file.getOriginalFilename());
            try {
                fileStore.upload(path, fileName, Optional.of(metadata.get(a)), file.getInputStream());
            } catch (IOException e) {
                throw new IllegalStateException("Failed to upload file", e);
            }

            Optional<User> userByUsername = userRepository.findUserByUsername(username);

            if(userByUsername.isPresent()){
                Image image = Image.builder()
                        .description(descriptions[a])
                        .title(titles[a])
                        .imagePath(path)
                        .user(userByUsername.get())
                        .imageFileName(fileName)
                        .build();
                repository.save(image);
                images.add(image);
            }

            a++;
        });

        return images;
    }

    @Override
    public byte[] downloadTodoImage(Long id) {
        Optional<Image> image = repository.findById(id);
        if(image.isPresent()){
            return fileStore.download(image.get().getImagePath(), image.get().getImageFileName());
        }
        throw new NotFoundException("images with id-" + id + " don't found ");
    }

    @Override
    public List<Image> getAllImages(String username) {
        List<Image> images = new ArrayList<>();

        Optional<User> userByUsername = userRepository.findUserByUsername(username);
        userByUsername.ifPresent(user -> user.getImages().forEach(images::add));

        return images;
    }

}
