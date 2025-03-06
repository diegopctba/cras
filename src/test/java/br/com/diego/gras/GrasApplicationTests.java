package br.com.diego.gras;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class GrasApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testLifeCheck() throws Exception {
		mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(content().string("success... this app is online and read to work"));
	}

	@Test
	void testAwardsWinners() throws Exception {
		mockMvc.perform(get("/movies/awards"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.min").exists())
				.andExpect(jsonPath("$.max").exists())
				.andExpect(jsonPath("$.min").isArray())
				.andExpect(jsonPath("$.max").isArray())
				.andExpect(result -> {
					if (result.getResponse().getContentAsString().contains("\"min\":[{")) {
						mockMvc.perform(get("/movies/awards"))
								.andExpect(jsonPath("$.min[0].producer").isString())
								.andExpect(jsonPath("$.min[0].interval").isNumber())
								.andExpect(jsonPath("$.min[0].previousWin").isNumber())
								.andExpect(jsonPath("$.min[0].followingWin").isNumber());
					}
				})
				.andExpect(result -> {
					if (result.getResponse().getContentAsString().contains("\"max\":[{")) {
						mockMvc.perform(get("/movies/awards"))
								.andExpect(jsonPath("$.max[0].producer").isString())
								.andExpect(jsonPath("$.max[0].interval").isNumber())
								.andExpect(jsonPath("$.max[0].previousWin").isNumber())
								.andExpect(jsonPath("$.max[0].followingWin").isNumber());
					}
				});
	}

}
