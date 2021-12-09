package com.voxloud.testjob;

import com.voxloud.testjob.controller.ImageController;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class LoginTest {

	@Autowired
	private ImageController imageController;
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void contextLoads() throws Exception {
		assertThat(imageController).isNotNull();
	}

	@Test
	public void accessDeniedTest() throws Exception{
		this.mockMvc.perform(get("/api/v1/todo"))
				.andDo(print())
				.andExpect(status().isUnauthorized());
	}

	@Test
	public void basicAuth() throws Exception {
		this.mockMvc
				.perform(get("/api/v1/todo").header(HttpHeaders.AUTHORIZATION,
						"Basic " + Base64Utils.encodeToString("vlad:12345".getBytes())))
				.andExpect(status().isOk());
	}



}
