package com.voxloud.testjob.controller;

import com.voxloud.testjob.domain.Image;
import com.voxloud.testjob.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/todo")
@AllArgsConstructor
@CrossOrigin("*")
public class TodoController {

    ImageService imageService;

    @GetMapping
    public ResponseEntity<List<Image>> getImages() {
        return new ResponseEntity<>(imageService.getAllTodos(), HttpStatus.OK);
    }

    @PostMapping(
            path = "",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Image> saveImage(@RequestParam("title") String title,
                                           @RequestParam("description") String description,
                                           @RequestParam("file") MultipartFile file) {
        return new ResponseEntity<>(imageService.saveTodo(title, description, file), HttpStatus.OK);
    }

    @GetMapping(value = "{id}/image/download")
    public byte[] downloadImage(@PathVariable("id") Long id) {
        return imageService.downloadTodoImage(id);
    }
}
