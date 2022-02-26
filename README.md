# work-manager

### Prerequisites
1) JDK 11 installed
2) Maven installed
3) PostgreSQL database launched on localhost (either installed and launched as a separate process or by starting up a docker
container with command `docker-compose up -d` as `docker-compose.yml` file is added for convenience)
4) `workdb` database manually created

### Running the app

Use the command:

`mvn spring-boot:run`

### Documentation and using the API

API docs can be found at this url once backend is launched:

`http://localhost:8080/swagger-ui/index.html`

With these interactive docs it's also possible to execute public API commands from the same page interactively without 
the need to use curl or Postman client or have jsp pages to submit requests with json payload. Try it out.
