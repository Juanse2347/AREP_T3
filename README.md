# Arquitecturas de Servidores de Aplicaciones, Meta protocolos de objetos, Patrón IoC, Reflexión

Este taller los estudiantes deberán construir un servidor Web (tipo Apache) en Java. El servidor debe ser capaz de entregar páginas html e imágenes tipo PNG. Igualmente el servidor debe proveer un framework IoC para la construcción de aplicaciones web a partir de POJOS. Usando el servidor se debe construir una aplicación Web de ejemplo. El servidor debe atender múltiples solicitudes no concurrentes.


## 📌 Características
Implementar un mecanismo de inyección de dependencias basado en anotaciones como 

```bash
@RestController, @GetMapping y @RequestParam.
```

- Desarrollo de un mecanismo para extraer valores de consulta de las solicitudes entrantes.
  
```bash
get("/hello", (req, res) -> "hello " + req.getValues("name"));
```
 
- Implementación del método staticfiles() para definir la carpeta donde se encuentran los archivos estáticos.
  
```bash
staticfiles("webroot/public");
```

## 🛠️ Requisitos
- Java 11 o superior
- Git
- Maven

## 🚀 Instalación y Ejecución
### 1️⃣ Clonar el repositorio
```bash
git clone https://github.com/Juanse2347/AREP_T3
cd AREP_T3
```

### 2️⃣ Compilar el proyecto con Maven
```bash
mvn clean install
```

![Image](https://github.com/user-attachments/assets/8889b4a2-c7a5-41ee-822c-e244bc836c8c)


### 3️⃣ Ejecutar el servidor

```bash
java -cp target/classes co.edu.eci.arep.HttpServer co.edu.eci.arep.GreetingController
```

![Image](https://github.com/user-attachments/assets/47f5a509-cbfd-4e83-902a-a29067b8f366)


### 4️⃣ Probar con un navegador o `curl`
```bash
curl http://localhost:30000/
```

## 🔍 Pruebas

Puedes ejecutar pruebas con JUnit:
```bash
mvn test
```

![Image](https://github.com/user-attachments/assets/c848bb42-f95b-4660-a23c-948d179853a4)


## 🔍 Pruebas de extremo a extremo ##

Pruebas del navegador 

Probamos que nuestro servicio este funcionando correctamente

```bash
http://localhost:30000/
```

## 🔍 Pruebas de Estilo de Codificacion ##

Con el siguiente comando realizamos las pruebas de estilo de codificación son aquellas que verifican que el código sigue las convenciones y buenas prácticas del equipo o la comunidad

```bash
mvn checkstyle:check
```

![Image](https://github.com/user-attachments/assets/6c5a4c16-9c71-463d-9629-59f5c976213a)

## 🔍 Probando el REST ##

Vamos a comprobar los endpoints de nuestra API

```bash
curl -X GET "http://localhost:30000/app/hello?name=Sebas"
```

Esperamos lo siguiente:

![Image](https://github.com/user-attachments/assets/b8ba46e0-740b-4d87-9d95-c7be7d270987)


Prueba el endpoint que devuelve PI

```bash
curl -X GET "http://localhost:30000/App/pi"
```

Esperamos lo siguiente:

![Image](https://github.com/user-attachments/assets/19025246-5536-489e-8780-92d8b1bfa35b)


## 🔍 Pruebas automatizadas ##

Este proyecto incluye pruebas automatizadas para garantizar su correcto funcionamiento. Ejecuta los tests con:

```bash
cd src
javac -cp .:/path/to/junit-4.12.jar co/edu/eci/arep/HttpServerTest.java
```

## :office: Desplieqgue ##

Vamos a ejecutar el servidor como un proceso en segundo plano o configurar un servicio systemd, de la siguiente manera:

```bash
mvn exec:java -Dexec.mainClass="co.edu.eci.arep.RunApp"
```

## :cd: Construido con ## 

 - Java - Lenguaje principal utilizado
 - Maven - Para la gestión de dependencias y automatización
 - JUnit - Para pruebas automatizadas

## :busts_in_silhouette: Contribuciones ##

Lea [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) para obtener detalles sobre nuestro código de conducta y el proceso para enviarnos solicitudes de extracción.

## :school_satchel: Control de Versiones ##

Usamos [SemVer](http://semver.org/) para controlar las versiones.

## :bust_in_silhouette: Autor ##

* **Juan Sebastian Sanchez** - *Trabajo Inicial* - [Juanse2347](https://github.com/Juanse2347)


## 📄 Licencia
Este proyecto está bajo la licencia [LICENSE](LICENSE). ¡Siéntete libre de contribuir! 😊


## :wave: Expresiones de Gratitud ##

- Inspiracion
- Compromiso

