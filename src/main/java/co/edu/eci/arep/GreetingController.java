package co.edu.eci.arep;

import co.edu.eci.arep.annotation.GetMapping;
import co.edu.eci.arep.annotation.RequestParam;
import co.edu.eci.arep.annotation.RestController;

@RestController
public class GreetingController {

    @GetMapping("/greeting")
    public String greet(@RequestParam(value = "name", defaultValue = "Mundo") String name) {
        return "{\"message\": \"Hola, " + name + "\"}";
    }
}
