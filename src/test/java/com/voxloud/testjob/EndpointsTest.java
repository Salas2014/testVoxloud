package com.voxloud.testjob;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.voxloud.testjob.service.ImageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("vlad")
public class EndpointsTest {

    @Autowired
    private MockMvc mockMvc;
    private static ObjectMapper mapper = new ObjectMapper();
    @MockBean
    private ImageService imageService;

    @Test
    public void createImage() throws Exception{
        String uri = "/api/v1/todo/images";

        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt",
                "text/plain", "Spring Framework".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart(uri).file(multipartFile).param("title", "tit")
                .param("description", "1"))
                .andExpect(status().isCreated())
                .andExpect(content().string("[]"));
    }

    @Test
    void downloadTodoImage() {
    }
}
