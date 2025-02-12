package co.edu.eci.arep;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class HttpServer {
    private static final int PORT = 30000;
    private static final String STATIC_FILES_PATH = "src/main/resources/web/web";

    public static void main(String[] args) {
        try {
            if (args.length > 0) {
                Class<?> controllerClass = Class.forName(args[0]);
                MicroServer.registerController(controllerClass);
            } else {
                System.out.println("No se proporcionó clase de controlador. Ejecutando sin controladores REST.");
            }
            startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor iniciado en el puerto:" + PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                handleRequest(clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<String, String> parseQueryParams(String path) {
        Map<String, String> params = new HashMap<>();
        if (path.contains("?")) {
            String[] parts = path.split("\\?");
            if (parts.length > 1) {
                for (String param : parts[1].split("&")) {
                    String[] keyValue = param.split("=");
                    if (keyValue.length == 2) {
                        params.put(keyValue[0], keyValue[1]);
                    }
                }
            }
        }
        return params;
    }

    // Maneja cada conexión de cliente.
    private static void handleRequest(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             OutputStream out = clientSocket.getOutputStream()) {

            String requestLine = in.readLine();
            if (requestLine == null) return;
            System.out.println("Solicitud recibida: " + requestLine);

            String[] requestParts = requestLine.split(" ");
            if (requestParts.length < 2) return;

            String method = requestParts[0];
            String fullPath = requestParts[1];
            String path = fullPath.split("\\?")[0];
            Map<String, String> queryParams = parseQueryParams(fullPath);

            if (method.equals("GET")) {
                // Si la ruta comienza con "/App/rests", se asume que es una llamada a un servicio REST
                if (path.startsWith("/App/rests")) {
                    // Se remueve el prefijo para obtener la ruta configurada en la anotación @GetMapping
                    String route = path.replaceFirst("/App/rests", "");
                    Object response = MicroServer.handle(route, queryParams);
                    sendResponse(out, "200 OK", "application/json", response.toString().getBytes());
                } else {
                    // Se atiende la solicitud de archivo estático
                    serveStaticFile(out, path);
                }
            } else {
                // Respuesta para método no permitido en JSON
                String jsonError = "{\"message\": \"Método no permitido\"}";
                sendResponse(out, "405 Method Not Allowed", "application/json", jsonError.getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void serveStaticFile(OutputStream out, String path) throws IOException {
        if (path.equals("/App/") || path.equals("/App/index.html")) {
            path = "/index.html";
        } else {
            path = path.replaceFirst("/App", "");
        }

        File file = new File(STATIC_FILES_PATH + path);
        if (file.exists() && file.isFile()) {
            byte[] fileBytes = Files.readAllBytes(file.toPath());
            String contentType = Files.probeContentType(file.toPath());
            if (contentType == null) {
                contentType = "text/plain";
            }
            sendResponse(out, "200 OK", contentType, fileBytes);
        } else {
            String jsonNotFound = "{\"message\": \"404 Not Found\"}";
            sendResponse(out, "404 Not Found", "application/json", jsonNotFound.getBytes());
        }
    }



    private static void sendResponse(OutputStream out, String status, String contentType, byte[] content) throws IOException {
        PrintWriter writer = new PrintWriter(out, true);
        writer.println("HTTP/1.1 " + status);
        writer.println("Content-Type: " + contentType);
        writer.println("Content-Length: " + content.length);
        writer.println();
        out.write(content);
        out.flush();
    }

    private static class SocketOutputStreamWrapper extends OutputStream {
        private final PrintWriter out;
        public SocketOutputStreamWrapper(PrintWriter out) {
            this.out = out;
        }
        @Override
        public void write(int b) throws IOException {
            out.write(b);
        }
    }
}