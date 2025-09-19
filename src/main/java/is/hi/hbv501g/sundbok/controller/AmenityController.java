package is.hi.hbv501g.sundbok.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AmenityController {
    @GetMapping("/api/amenity")
    public String index() {
        return "Greetings from Amenity Controller!";
    }
}
