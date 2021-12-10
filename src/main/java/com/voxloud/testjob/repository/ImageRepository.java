package com.voxloud.testjob.repository;

import com.voxloud.testjob.domain.Image;

import com.voxloud.testjob.domain.User;
import org.springframework.data.repository.CrudRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ImageRepository extends CrudRepository<Image, Long> {
    Image findByTitle(String title);
    Image findImageByUser(User user);
}
