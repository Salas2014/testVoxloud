package com.voxloud.testjob.controller;

import com.voxloud.testjob.domain.Image;
import com.voxloud.testjob.domain.User;
import com.voxloud.testjob.service.ImageService;
import com.voxloud.testjob.utils.CustomUserDetail;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/todo")
@AllArgsConstructor
@CrossOrigin("*")
public class TodoController {

    ImageService imageService;
    @Autowired
    private final UserDetailsService userDetailsService;

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Image>> getImages(Pageable pageable,
                                                 PagedResourcesAssembler assembler,
                                                 Authentication authentication) {


        return new ResponseEntity<>(imageService.getAllTodos(authentication.getName()), HttpStatus.OK);
    }

    @PostMapping(
            path = "",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Image> saveImage(@RequestParam("title") String title,
                                           @RequestParam("description") String description,
                                           @RequestParam("file") MultipartFile file,
                                           Authentication authentication) {

        return new ResponseEntity<>(imageService.saveTodo(title, description, file, authentication.getName()), HttpStatus.OK);
    }

    @GetMapping(value = "{id}/image/download")
    public byte[] downloadImage(@PathVariable("id") Long id) {
        return imageService.downloadTodoImage(id);
    }
}
