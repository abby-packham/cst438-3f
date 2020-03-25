package cst438hw2.service;
 
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.BDDMockito.given;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.ArgumentMatchers.anyString;

import cst438hw2.domain.*;
 
@SpringBootTest
public class CityServiceTest {

	@MockBean
	private WeatherService weatherService;
	
	@Autowired
	private CityService cityService;
	
	@MockBean
	private CityRepository cityRepository;
	
	@MockBean
	private CountryRepository countryRepository;

	
	@Test
	public void contextLoads() {
	}


	@Test
	public void testCityFound() throws Exception {
		
		int timeZone = -25200;
		int td = 1584479424;
		OffsetDateTime time = OffsetDateTime.now(ZoneId.of("Etc/UTC")).plusSeconds(timeZone);
		String strTime = time.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
		long cityId = 3799;
		String cityName = "Glendora";
		String countryCode = "USA";
		String countryName = "United States";
		String district = "California";
		int population = 52400;
		List<City> cities = 
                Arrays.asList(
                		new City(cityId, cityName, countryCode, district, population));
		Country country = new Country(countryCode, countryName);
		CityInfo expected = new CityInfo(cityId, cityName, countryCode, countryName, district, population,
				15.0, strTime);
		given(cityRepository.findByName(cityName)).willReturn(cities);
		given(countryRepository.findByCode(countryCode)).willReturn(country);
		given(weatherService.getTempAndTime(cityName)).willReturn(new TempAndTime(15.0, td, timeZone));
		CityInfo actual =  cityService.getCityInfo("Glendora");
		assertEquals(expected, actual);
	}
	
	
	@Test 
	public void  testCityNotFound() {
		List<City> emptyList = Arrays.asList();
		String badName = anyString();
		
		given(cityRepository.findByName(badName)).willReturn(emptyList);
		assertNull(cityService.getCityInfo(badName));

	}
	
	@Test 
	public void  testCityMultiple() {
		
		int timeZone = -25200;
		int td = 1584483381;
		OffsetDateTime time = OffsetDateTime.now(ZoneId.of("Etc/UTC")).plusSeconds(timeZone);
		String strTime = time.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
		long cityId = 947547;
		int population = 489000;
		String cityName = "Kansas City", countryCode = "USA", countryName = "United States", district = "Missouri";
		List<City> cities = 
                Arrays.asList(
                		new City(cityId, cityName, countryCode, district, population),
                		new City(12766, cityName, countryCode, "Kansas", 102832 ));
		Country country = new Country(countryCode, countryName);
		CityInfo expected = new CityInfo(cityId, cityName, countryCode, countryName, district, population,
				15.0, strTime);
		
		given(cityRepository.findByName(cityName)).willReturn(cities);
		given(countryRepository.findByCode(countryCode)).willReturn(country);
		given(weatherService.getTempAndTime(cityName)).willReturn(new TempAndTime(15.0, td, timeZone));
		CityInfo actual =  cityService.getCityInfo("kansas City");
		assertEquals(expected, actual);
	}

}
