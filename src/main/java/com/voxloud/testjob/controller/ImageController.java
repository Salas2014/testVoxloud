package com.voxloud.testjob.controller;

import com.voxloud.testjob.domain.Image;
import com.voxloud.testjob.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/todo")
@AllArgsConstructor
@CrossOrigin("*")
public class ImageController {

    ImageService imageService;

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Image>> getImages(Authentication authentication, Pageable p) {

        List<Image> allImages = imageService.getAllImages(authentication.getName());

        int start = (int) p.getOffset();
        int end = (Math.min((start + p.getPageSize()), allImages.size()));
        Page<Image> page
                = new PageImpl<>(allImages.subList(start, end), p, allImages.size());

        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    // create single entity or []
    @PostMapping(
            path= "/images",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Image>> saveImages(@RequestParam("title") String[] titles,
                                                  @RequestParam("description") String[] descriptions,
                                                  @RequestParam("file") MultipartFile[] files,
                                                  Authentication authentication){

        return new ResponseEntity<>(imageService.saveImages(titles, descriptions, files, authentication.getName()), HttpStatus.OK);
    }

    @GetMapping(value = "{id}/image/download")
    public byte[] downloadImage(@PathVariable("id") Long id) {
        return imageService.downloadTodoImage(id);
    }
}
