package master;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.csme.csmeapi.CsmeApiApplication;
import com.csme.csmeapi.dto.Pet;
import com.csme.csmeapi.service.CsmeService;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = CsmeApiApplication.class)
@ComponentScan(basePackages = { "master" })
public class PetTest {

	@MockBean
	private CsmeService service;

	@Autowired
	private MockMvc mvc;

	@Test
	public void givenPets_whenGetPet_thenReturnJsonArray() throws Exception {

		Pet pet = new Pet();
		pet.setId(1l);

		given(service.getPetById(1L)).willReturn(pet);

		mvc.perform(get("/pet/1?userId=1").contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
	}

	/*@Test
	public void givenPets_whenGetPet_thenReturnJsonArray_wthoutUserId() throws Exception {

		Pet pet = new Pet();
		pet.setId(1l);

		given(service.getPetById(1L)).willReturn(pet);

		mvc.perform(get("/pet/1").contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
	}*/

}