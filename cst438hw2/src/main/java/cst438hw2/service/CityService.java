package cst438hw2.service;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cst438hw2.domain.*;

@Service
public class CityService {
	
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(CityService.class);
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private CountryRepository countryRepository;
	
	@Autowired
	private WeatherService weatherService;
	
	public CityService(CityRepository cityRepository, CountryRepository countryRepository, WeatherService weatherService) {
		this.cityRepository = cityRepository;
		this.countryRepository = countryRepository;
		this.weatherService = weatherService;
	}
	
	public CityInfo getCityInfo(String cityName) {
	log.info("City to retrieve: "+cityName);
		List<City> cities = cityRepository.findByName(cityName);
		if(cities.isEmpty()){
			return null;
		}
		City city = cities.get(0); 
		String countryCode = city.getCountryCode();
		Country country = countryRepository.findByCode(countryCode);
		String strCountry = country.getName();
		TempAndTime cityTempAndTime = weatherService.getTempAndTime(cityName);
		int timeZone = cityTempAndTime.getTimezone();
		OffsetDateTime time = OffsetDateTime.now(ZoneId.of("Etc/UTC")).plusSeconds(timeZone);	
		String strTime = time.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
		return new CityInfo(city.getId(), city.getName(), countryCode, strCountry, city.getDistrict(), city.getPopulation(),
				cityTempAndTime.getTemp(), strTime);
		
     }
	
	public void requestReservation(String cityName, String level, String email) {
        String msg  = "{\"cityName\": \""+ cityName + 
                "\" \"level\": \""+level+
                "\" \"email\": \""+email+"\"}" ;
        
        System.out.println("Sending message:"+msg);
        
        rabbitTemplate.convertSendAndReceive(
                fanout.getName(), 
                "",   // routing key none.
                msg);

    }
	
}
	

