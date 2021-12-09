package com.voxloud.testjob.service;

import com.voxloud.testjob.bucket.BucketName;
import com.voxloud.testjob.domain.Image;
import com.voxloud.testjob.domain.Role;
import com.voxloud.testjob.domain.User;
import com.voxloud.testjob.repository.ImageRepository;
import com.voxloud.testjob.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {

    @Mock private ImageRepository imageRepository;
    @Mock private FileStore fileStore;
    @Mock private UserRepository userRepository;
    String[] titles;
    String[] description;
    MockMultipartFile[] files;
    String path;

    private ImageService underTest;
    private List<Image> images;

    @BeforeEach
    void setUp() {
        underTest = new ImageServiceImpl(fileStore, imageRepository, userRepository);
        titles = new String[]{"first","second"};
        description = new String[]{"one","two"};
        files = new MockMultipartFile[]{new MockMultipartFile("data",
                "filename.png", "image/png", "multipart/form-data".getBytes()),
                new MockMultipartFile("data",
                        "filename.png", "image/png", "multipart/form-data".getBytes())};
        path = String.format("%s/%s", BucketName.TODO_IMAGE.getBucketName(), UUID.randomUUID());

        User user = new User("vlad", "aaaa", List.of(new Role()));
        images = new ArrayList<>();

        for(int i = 1; i<files.length; i++){
            Image image = Image.builder()
                    .description(description[i])
                    .title(titles[i])
                    .imagePath(path)
                    .user(user)
                    .imageFileName("filename.png")
                    .build();
            imageRepository.save(image);
            images.add(image);
        }

    }

    @Test
    @Disabled
    void saveImages() {
        // given
        //when
        underTest.saveImages(titles, description,
                files, "Vlad");
        //then
        ArgumentCaptor<Image> imageArgumentCaptor =
                ArgumentCaptor.forClass(Image.class);

        verify(imageRepository).save(imageArgumentCaptor.capture());
        ArrayList<Image> objects = new ArrayList<>();
        for (Image image:
             images) {
            objects.add(image);
        }

        assertThat(objects).isEqualTo(images);
    }

    @Test
    void willThrowWhenFileIsNotImage(){


//        assertThatThrownBy(() -> )
    }

    @Test
    void downloadTodoImage() {
    }

    @Test
    void getAllImages() {
    }
}