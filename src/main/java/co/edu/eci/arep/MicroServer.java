package co.edu.eci.arep;

import co.edu.eci.arep.annotation.GetMapping;
import co.edu.eci.arep.annotation.RestController;
import co.edu.eci.arep.annotation.RequestParam;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

public class MicroServer {

    private static final Map<Class<?>, Object> controllerInstances = new HashMap<>();

    private static Map<String, Method> routeMapping = new HashMap<>();

    public static void registerController(Class<?> controllerClass) throws Exception {
        Object controllerInstance = controllerClass.getDeclaredConstructor().newInstance();
        controllerInstances.put(controllerClass, controllerInstance);

        for (Method method : controllerClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(GetMapping.class)) {
                String route = method.getAnnotation(GetMapping.class).value();
                // Remover la barra inicial para que la clave sea "greeting" en lugar de "/greeting"
                if(route.startsWith("/")) {
                    route = route.substring(1);
                }
                routeMapping.put(route, method);
            }
        }
    }




    public static Object handle(String route, Map<String, String> queryParams) throws Exception {
        Method method = routeMapping.get(route);
        if (method == null) {
            return "{\"message\": \"404 Not Found\"}";  // Si no se encuentra el método
        }
        Object controller = controllerInstances.get(method.getDeclaringClass());
        Parameter[] parameters = method.getParameters();
        Object[] args = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            RequestParam rp = parameters[i].getAnnotation(RequestParam.class);
            if (rp != null) {
                String value = queryParams.getOrDefault(rp.value(), rp.defaultValue());
                args[i] = value;
            }
        }

        Object result = method.invoke(controller, args);

        if (result instanceof String) {
            return (String) result;
        } else if (result instanceof byte[]) {
            return new String((byte[]) result);
        } else {
            return "{\"message\": \"Internal Server Error\"}";
        }
    }
}