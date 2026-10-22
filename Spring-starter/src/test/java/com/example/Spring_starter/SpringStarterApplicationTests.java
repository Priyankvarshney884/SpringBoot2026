package com.example.Spring_starter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class SpringStarterApplicationTests {

    @Autowired
    private MockMvc mockMvc;

	@Test
    void userApiSupportsCreateListGetUpdateAndNotFound() throws Exception {
	// Ek hi flow mein rakhne se in-memory users ka test order unpredictable nahi hota.
	mockMvc.perform(post("/users")
			.contentType(MediaType.APPLICATION_JSON)
			.content("{\"name\":\"Asha\"}"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value(1))
		.andExpect(jsonPath("$.name").value("Asha"));

	mockMvc.perform(get("/users"))
		.andExpect(status().isOk())
		.andExpect(content().json("[{\"id\":1,\"name\":\"Asha\"}]"));

	mockMvc.perform(get("/users/1"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.name").value("Asha"));

	mockMvc.perform(put("/users/1")
			.contentType(MediaType.APPLICATION_JSON)
			.content("{\"name\":\"Asha Sharma\"}"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.name").value("Asha Sharma"));

	mockMvc.perform(get("/users/1"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.name").value("Asha Sharma"));

	mockMvc.perform(get("/users/999"))
		.andExpect(status().isNotFound());
	}

}
