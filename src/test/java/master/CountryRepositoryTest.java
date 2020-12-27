/*package master;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.olam.config.Application;
import com.olam.config.controller.CountryController;
import com.olam.config.dto.CountryDTO;
import com.olam.config.dto.GeographyDTO;
import com.olam.config.dto.UserDTO;
import com.olam.config.repository.CountryRepository;
import com.olam.config.service.CountryService;
import com.olam.config.serviceimpl.CountryServiceImpl;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = Application.class)
@ComponentScan(basePackages= {"master"})
@Transactional
public class CountryRepositoryTest {
	
	@Autowired
	Environment environment;
	
	@Autowired
	TokenGeneratorService tokenGeneratorService;
	
	private static String tokenString;
	
	public static final String OT_APP_ID = "2";
	
	 private JacksonTester < CountryDTO > jsonTester;
	 
	 private JacksonTester <List<GeographyDTO>  > jsonTester2;
	 
	
	   @Autowired
	   MockMvc mockMvc;
	
       @Autowired
       CountryRepository countryRepository;
       
       @Autowired
       CountryController countryController;
       
       @Autowired
       CountryService countryService;
       
       @Autowired
       CountryServiceImpl countryServiceImpl;
       
       @Autowired
       ObjectMapper objectMapper;
    
	
	UserDTO user=new UserDTO();

	@Before
	public void setUser() {
		user.setApplicationId(1L);
		user.setUserName("Test");
		user.setLanguageIsoCode("en");
		 JacksonTester.initFields(this, objectMapper);
		 
	}
       
	@Before
	public void initialSetupBeforeEveryTestCall()
	{
		TokenAuthenticateDTO dto = new TokenAuthenticateDTO();
		dto.setUrl(environment.getProperty("url.for.authenticate"));
		dto.setLanguage(environment.getProperty("authentication.language"));
		dto.setApplication(environment.getProperty("authentication.application"));
		dto.setUsername(environment.getProperty("authentication.username"));
		dto.setPassword(environment.getProperty("authentication.password"));
		dto.setGrantType(environment.getProperty("authentication.grant_type"));
		dto.setClientId(environment.getProperty("authentication.client_id"));
		dto.setClientSecret(environment.getProperty("authentication.client_secret"));
		CountryRepositoryTest.tokenString = tokenGeneratorService.getTokenString(dto).getAuthToken();
		CountryRepositoryTest.tokenString=environment.getProperty("auth");
}

	
	
	@Test
	public void testDto() throws Exception
	{
		Map<Class<?>,Object> classNameMap = new HashMap<>();
		
		//Max 15
		classNameMap.put(CountryDTO.class,new CountryDTO());
		
		testObject(classNameMap);
	}

	
	private void testObject(Map<Class<?>,Object> classNameMap)
	{
		if(classNameMap!=null && !classNameMap.isEmpty())
		{
			Object object = null;
			try
			{
				for(Map.Entry<Class<?>,Object> entry : classNameMap.entrySet())
				{

					Class<?> dtoClassNm = entry.getKey();
					object = entry.getValue();

					Method[] methods = dtoClassNm.getDeclaredMethods();

					//Run the setter and then the getter
					for(Method method : methods)
					{
						//For Setter
						if(StringUtils.startsWith(method.getName(), "set")) 
						{
							Class<?> parameterClass =  method.getParameterTypes()[0];
							if(parameterClass == String.class){ method.invoke(object, "stringVal");}
							if(parameterClass == Long.class) { method.invoke(object, 1l);}
							if(parameterClass == Double.class){ method.invoke(object, 0.00);}
							if(parameterClass == Float.class){ method.invoke(object, 0.0f);}
							if(parameterClass == Integer.class){ method.invoke(object, 0);}
							if(parameterClass == Date.class){ method.invoke(object, new Date());}
							if(parameterClass == Boolean.class){ method.invoke(object, Boolean.TRUE);}
							//if(parameterClass.getClass().equals(Boolean.class) ){ method.invoke(object, Boolean.TRUE);}
						}
					}

					for(Method method : methods)
					{
						//For Getter
						if(StringUtils.startsWith(method.getName(), "get")) 
						{
							method.invoke(object);
						}
					}

				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}


		
	}
	
        @Test //1//repository
       public void testFindAll() {
              // when countries fetched
              List<Country> countriesFound = countryRepository.findAll();

              // then assert country list size should be more than 0
              assertThat(countriesFound.size()).isGreaterThan(0);
       }
       
       @Test
       public void testgetAllOlamCountriesRep() {
              // when countries fetched
              List<Country> countriesFound = countryRepository.getAllOlamCountries(2);

              // then assert country list size should be more than 0
              assertThat(countriesFound.size()).isGreaterThan(0);
       }
       
       @Test//2
       public void testgetCountryListForCountryIDListRep() {
              // when countries fetched
    	   List<Long> countryIdList = new ArrayList<Long>();
    	   countryIdList.add(1L);
    	   countryIdList.add(2L);
    	   countryIdList.add(3L);
    	   countryIdList.add(4L);
              List<CountryDTO> countriesFound = countryService.getCountryListForCountryIDList(countryIdList);

              // then assert country list size should be more than 0
              assertThat(countriesFound.size()).isGreaterThan(0);
       }
       
       @Test//3//fail
   		public void testGetCountryByCountryCode() throws Exception{
   		String countryCode="CI";
   		ResponseEntity<CountryDTO> countryDTO = countryController.getCountryByCountryCode(countryCode);
   		assertThat(countryDTO).isNotNull();
   		
   	}
       
       @Test
       public void testfindByDeletedFlagFalseRep() {
              // when countries fetched
    	           List<Country> countriesFound = countryRepository.findByDeletedFlagFalseOrderByCountryName();

              // then assert country list size should be more than 0
              assertThat(countriesFound.size()).isGreaterThan(0);
       }
       
       @Test
   	public void testgetAllCountryController() throws Exception {
   		this.mockMvc
   				.perform(get("/master/getAllCountry").header("app-id",OT_APP_ID).header("Authorization","Bearer "
   						+ 
   						tokenString)).andExpect(status().isOk());

   	}
       
  	 @Test
    	public void testgetAllOlamCountryController() throws Exception {
    		this.mockMvc
    				.perform(get("/master/getAllOlamCountry").header("app-id",OT_APP_ID).header("Authorization","Bearer "
    						+ 
    						tokenString)).andExpect(status().isOk());
    	}
  	 
  	@Test
   	public void testgetCountryByCountryIDListController() throws Exception {
   		this.mockMvc
   				.perform(get("/master/getCountryByCountryIDList?countryIDList=1").header("app-id",OT_APP_ID).header("Authorization","Bearer "
   						+ 
   						tokenString)).andExpect(status().isOk());
   	}
  	
  	 @Test
    	public void testgetCountryListForCountryCodeListController() throws Exception {
    		this.mockMvc
    				.perform(get("/master/getCountryByCountryCodeList?countryCodeList=IN").header("app-id",OT_APP_ID).header("Authorization","Bearer "
    						+ 
    						tokenString)).andExpect(status().isOk());

    	}
  	 
  	@Test
	public void testgetCountryByGeographyListController() throws Exception {
  		
  		GeographyDTO geographyDTO =new GeographyDTO();
  		geographyDTO.setCountryId(1l);
  		List<GeographyDTO> geographyDTOList=new ArrayList();
  		geographyDTOList.add(geographyDTO);
		
		final String GeographyJson = jsonTester2.write(geographyDTOList).getJson();
  		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/master/getCountryByGeographyList").content(GeographyJson).contentType(APPLICATION_JSON_VALUE).header("app-id",OT_APP_ID).header("Authorization","Bearer "
						+ 
						tokenString);
		
		mockMvc.perform(requestBuilder).andExpect(status().isOk());
}
  	 
  	@Test
	public void testgetCountryForCountryCodeController() throws Exception {
		this.mockMvc
				.perform(get("/master/unsecure/getCountryByCountryCode?countryCode=IN").header("app-id",OT_APP_ID).header("Authorization","Bearer "
						+ 
						tokenString)).andExpect(status().isOk());

	}
  	
  	
  	@Test
	public void testgetCountryByIdController() throws Exception {
		this.mockMvc
				.perform(get("/master/getCountryById?countryId=1").header("app-id",OT_APP_ID).header("Authorization","Bearer "
						+ 
						tokenString)).andExpect(status().isOk());

	}
  	
  	@Test
	public void testgetCountryByNameController() throws Exception {
		this.mockMvc
				.perform(get("/master/getCountryByName?countryName=India").header("app-id",OT_APP_ID).header("Authorization","Bearer "
						+ 
						tokenString)).andExpect(status().isOk());

	}
  	
  	@Test
	public void testsaveCountryController() throws Exception {
  		
  		CountryDTO countryDTO =new CountryDTO();
		countryDTO.setCountryId(2L);
		countryDTO.setCountryName("Pakistan");
		countryDTO.setCountryCode("PK");
		countryDTO.setDiallingCode("+91");
		countryDTO.setOlamCountry(true);
		countryDTO.setCentreLat(50.00);
		countryDTO.setCentreLng(60.00);
		countryDTO.setSmsOriginatorId("OLAM");
		countryDTO.setActive(true);
		
		final String countryDTOJson = jsonTester.write(countryDTO).getJson();
  		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/master/country").content(countryDTOJson).contentType(APPLICATION_JSON_VALUE).header("app-id",OT_APP_ID).header("Authorization","Bearer "
						+ 
						tokenString);
		
		mockMvc.perform(requestBuilder).andExpect(status().isOk());
}
  	
  	
	@Test
	public void testUpdtaeCountryController() throws Exception {
  		
  		CountryDTO countryDTO =new CountryDTO();
		countryDTO.setCountryId(1L);
		countryDTO.setCountryName("Pakistan");
		countryDTO.setCountryCode("PK");
		countryDTO.setDiallingCode("+92");
		countryDTO.setOlamCountry(true);
		countryDTO.setCentreLat(50.00);
		countryDTO.setCentreLng(60.00);
		countryDTO.setSmsOriginatorId("OLAM");
		countryDTO.setActive(true);
		
		final String countryDTOJson = jsonTester.write(countryDTO).getJson();
  		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/master/country").content(countryDTOJson).contentType(APPLICATION_JSON_VALUE).header("app-id",OT_APP_ID).header("Authorization","Bearer "
						+ 
						tokenString);
		
		mockMvc.perform(requestBuilder).andExpect(status().isOk());
}
  	
  	
  	
  	
  	
  	//negative test cases of controller where we are not passing appId
  	
  	 @Test
    	public void testgetAllCountryControllerNegative() throws Exception {
    		this.mockMvc
    				.perform(get("/master/getAllCountry").header("Authorization","Bearer "
    						+ 
    						tokenString)).andExpect(status().isBadRequest());

    	}
        
   	 @Test
     	public void testgetAllOlamCountrNegativeyController() throws Exception {
     		this.mockMvc
     				.perform(get("/master/getAllOlamCountry").header("Authorization","Bearer "
     						+ 
     						tokenString)).andExpect(status().isBadRequest());
     	}
   	 
   	@Test
    	public void testgetCountryByCountryIDNegativeListController() throws Exception {
    		this.mockMvc
    				.perform(get("/master/getCountryByCountryIDList?countryIDList=1").header("Authorization","Bearer "
    						+ 
    						tokenString)).andExpect(status().isBadRequest());
    	}
   	
   	 @Test
     	public void testgetCountryListForCountryCodeListNegativeController() throws Exception {
     		this.mockMvc
     				.perform(get("/master/getCountryByCountryCodeList?countryCodeList=IN").header("Authorization","Bearer "
     						+ 
     						tokenString)).andExpect(status().isBadRequest());

     	}
   	 
   	@Test
	public void testgetCountryByGeographyListNegativeController() throws Exception {
  		
  		GeographyDTO geographyDTO =new GeographyDTO();
  		geographyDTO.setCountryId(1l);
  		List<GeographyDTO> geographyDTOList=new ArrayList();
  		geographyDTOList.add(geographyDTO);
		
		final String GeographyJson = jsonTester2.write(geographyDTOList).getJson();
  		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/master/getCountryByGeographyList").content(GeographyJson).contentType(APPLICATION_JSON_VALUE).header("Authorization","Bearer "
						+ 
						tokenString);
		
		mockMvc.perform(requestBuilder).andExpect(status().isBadRequest());
}
   	 
   	@Test
 	public void testgetCountryForCountryCodeNegativeController() throws Exception {
 		this.mockMvc
 				.perform(get("/master/unsecure/getCountryByCountryCode?countryCode=IN").header("Authorization","Bearer "
 						+ 
 						tokenString)).andExpect(status().isBadRequest());

 	}
   	
   	
   	@Test
 	public void testgetCountryByIdNegativeController() throws Exception {
 		this.mockMvc
 				.perform(get("/master/getCountryById?countryId=1").header("Authorization","Bearer "
 						+ 
 						tokenString)).andExpect(status().isBadRequest());

 	}
   	
   	@Test
 	public void testgetCountryByNameNegativeController() throws Exception {
 		this.mockMvc
 				.perform(get("/master/getCountryByName?countryName=India").header("Authorization","Bearer "
 						+ 
 						tokenString)).andExpect(status().isBadRequest());

 	}
   	
   	@Test
 	public void testsaveCountryNegativeController() throws Exception {
   		
   		CountryDTO countryDTO =new CountryDTO();
 		countryDTO.setCountryId(2L);
 		countryDTO.setCountryName("Pakistan");
 		countryDTO.setCountryCode("PK");
 		countryDTO.setDiallingCode("+91");
 		countryDTO.setOlamCountry(true);
 		countryDTO.setCentreLat(50.00);
 		countryDTO.setCentreLng(60.00);
 		countryDTO.setSmsOriginatorId("OLAM");
 		countryDTO.setActive(true);
 		
 		final String countryDTOJson = jsonTester.write(countryDTO).getJson();
   		
 		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/master/country").content(countryDTOJson).contentType(APPLICATION_JSON_VALUE).header("Authorization","Bearer "
 						+ 
 						tokenString);
 		
 		mockMvc.perform(requestBuilder).andExpect(status().isBadRequest());
 }
  	
   	
   	
  	
  	 
   	@Test
   	public void testgetCountryByGeographyList() throws Exception {
   	 List<GeographyDTO> geographies=new ArrayList<>();
   		ResponseEntity<List<CountryDTO>> countryList = countryController.getCountryByGeographyList(geographies);
   		assertThat(countryList).isNotNull();
   		

   	}
  	
	@Test
   	public void testgetCountryByName() throws Exception {
		ResponseEntity<CountryDTO> countryDTO = countryController.getCountryByName("COTE D'IVOIRE");
		assertThat(countryDTO).isNotNull();

   	}
   	
  	 
  	@Test
	public void testSaveCountry() throws Exception {
		CountryDTO countryDTO =new CountryDTO();
		countryDTO.setCountryId(1L);
		countryDTO.setCountryName("India");
		countryDTO.setDeletedFlag(false);
		countryDTO.setActive(true);
		countryDTO.setDiallingCode("+91");
		countryDTO.setOlamCountry(false);
		 MessageDTO messageDTO= countryService.saveCountry(countryDTO,user);		 
		 assertThat(messageDTO.getMessage().equals("Success")).isTrue();
		
	}
  	 
  	//Service Test
		@Test
		public void testgetAllCountriesService() throws Exception {
			ResponseEntity<List<CountryDTO>> countriesFound = countryController.getAllCountry();
			assertThat(countriesFound).isNotNull();
			
		}
		//Service Test
				@Test
				public void testgetAllOlamCountryService() throws Exception {
					List<CountryDTO> countriesFound = countryService.getAllOlamCountries();
					assertThat(countriesFound.size()).isGreaterThan(0);
					
				}
				//Service Test
				@Test
				public void testgetCountryListForCountryIDListService() throws Exception {
					List<Long> countryIdList = new ArrayList<Long>();
			    	   countryIdList.add(1L);
			    	   countryIdList.add(2L);
			    	   countryIdList.add(3L);
			    	   countryIdList.add(4L);
					List<CountryDTO> countriesFound = countryService.getCountryListForCountryIDList(countryIdList);
					assertThat(countriesFound.size()).isGreaterThan(0);
					
				}
				
				//Service Test
				@Test
				public void testgetCountryByIdService() throws Exception {
					CountryDTO countriesFound = countryService.getCountryById(1L);
					assertThat(countriesFound.getCountryId()).hasNoNullFieldsOrProperties();					
				}
  	}*/