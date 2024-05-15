# Order Service

An incomplete reference project for a small-ish Java 21 + Spring Boot 3.2.5 backend service.

## Running the app & tests
This being a Spring Boot app with maven, you can run it locally with

`mvn spring-boot:run`

After running the command above, the service should be running at
http://localhost:9090 with a `/api` context path / suffix.

The integration & unit tests can be run with

`mvn test`

## API documentation

While the service is running, the swagger documentation for the available endpoints can be found at
http://localhost:9090/api/swagger-ui/index.html, assuming that the default port has not been changed.

The service also offers an info endpoint at http://localhost:9090/api/info

And a health endpoint at http://localhost:9090/api/health

## Configuration

There are a couple of configuration related files check at `/src/main/resources/`

- `application.yml` for server configurations such as which port to run on.
- `app-info.yml` for declaring what information of the service should be through the info endpoint.