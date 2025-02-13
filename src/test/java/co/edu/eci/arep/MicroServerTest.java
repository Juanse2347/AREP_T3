package co.edu.eci.arep;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import co.edu.eci.arep.GreetingController;
import co.edu.eci.arep.MicroServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MicroServerTest {

    @BeforeEach
    public void setUp() throws Exception {
        // Si tu MicroServer no tiene un método para limpiar las rutas,
        // podrías resetear los mapas vía reflexión o ejecutar los tests en un entorno aislado.
        // Para este ejemplo asumiremos que no hay contaminación entre tests.
        MicroServer.registerController(GreetingController.class);
    }

    @Test
    public void testGreetingWithName() throws Exception {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("name", "Juan");

        // Se espera que la ruta "greeting" retorne el mensaje con el nombre "Juan"
        String response = (String) MicroServer.handle("greeting", queryParams);
        assertEquals("{\"message\": \"Hola, Juan\"}", response);
    }

    @Test
    public void testGreetingDefault() throws Exception {
        Map<String, String> queryParams = new HashMap<>();
        // No se provee el parámetro "name": se debe usar el valor por defecto "Mundo"
        String response = (String) MicroServer.handle("greeting", queryParams);
        assertEquals("{\"message\": \"Hola, Mundo\"}", response);
    }

    @Test
    public void testNotFoundRoute() throws Exception {
        Map<String, String> queryParams = new HashMap<>();
        // Se busca una ruta que no existe en el registro
        String response = (String) MicroServer.handle("nonexistent", queryParams);
        assertEquals("{\"message\": \"404 Not Found\"}", response);
    }
}
