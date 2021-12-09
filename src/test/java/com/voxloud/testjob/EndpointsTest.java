package com.voxloud.testjob;

import com.voxloud.testjob.domain.Image;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
public class EndpointsTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createImage() throws Exception{
        String uri = "/images";
//        String title = "two";
//        Map<String, String> params = new HashMap<>(){{
//            put("title", "value1");
//            put("descriptions", "value2");
//        }};
//
//        MultipartFile[] files =
//                new MultipartFile[]{new MockMultipartFile("data",
//                "filename.png", "image/png", "multipart/form-data".getBytes()),
//                new MockMultipartFile("data",
//                        "filename.png", "image/png", "multipart/form-data".getBytes())};
//
//        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt",
//                "text/plain", "Spring Framework".getBytes());
//
//        this.mockMvc.perform(MockMvcRequestBuilders.multipart(uri)
//                .file(multipartFile)
//                .accept(MediaType.parseMediaType("image/png")
        Image image = new Image();
        String inputJson = super.mapToJson(product);




    }
}
