package org.HotelInfoSearch.controller;

import org.HotelInfoSearch.domain.Hotel;
import org.HotelInfoSearch.domain.HotelSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class ApiController {
    private HotelSearchService hotelSerachService;

    public ApiController(HotelSearchService hotelSerachService) {
        this.hotelSerachService = hotelSerachService;
    }

    @ResponseBody
    @RequestMapping(method=RequestMethod.GET, path="/hotels/{hotelId}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable("hotelId") Long hotelId) {
        Hotel hotel = hotelSerachService.getHotelById(hotelId);
        return ResponseEntity.ok(hotel);
    }

}
