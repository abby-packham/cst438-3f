package cst438hw2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


import cst438hw2.service.CityService;

@Controller
public class CityController {
	
	@Autowired
	private CityService cityService;
	
	@GetMapping("/cities/{city}")
	public String getWeather(@PathVariable("city") String cityName, Model model) {

		CityInfo cityInfo = cityService.getCityInfo(cityName);
		model.addAttribute("cityInfo", cityInfo);
		return "cities";
	}
	
	@PostMapping("/cities/reservation")
    public String createReservation(
            @RequestParam("level") String level, 
            @RequestParam("city") String cityName, 
            @RequestParam("email") String email, 
            Model model) {
        
        model.addAttribute("city", cityName);
        model.addAttribute("level", level);
        model.addAttribute("email", email);
        cityService.requestReservation(cityName, level, email);
        return "request_reservation";
    }
	
	
	
}