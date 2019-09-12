package me.ham.circuitbreaker;

import me.ham.circuitbreaker.resilience.controller.ResilienceController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CircuitbreakerApplicationTests {

	@Autowired
	ResilienceController resilienceController;

	private MockMvc mockMvc;
	@Before
	public void setup(){
		mockMvc = MockMvcBuilders.standaloneSetup(resilienceController).build();
	}

	@Test(timeout = 1500)
	public void test() throws Exception {
////		for(int i=0; i<100; i++){
			this.mockMvc.perform(get("/resilience/success"))
					.andExpect(status().isOk())
					.andExpect(content().string("This is success."));
			System.out.println("!!");
//		}
	}

}
