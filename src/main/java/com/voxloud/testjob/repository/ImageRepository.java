package com.voxloud.testjob.repository;

import com.voxloud.testjob.domain.Image;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<Image, Long> {
    Image findByTitle(String title);
}
