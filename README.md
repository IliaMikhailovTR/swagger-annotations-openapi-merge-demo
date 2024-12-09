# Merge swagger annotations and openapi.yaml file to a single swagger page

The magic happens [here](src/main/java/com/mostdevwill/swagger/SwaggerResourceController.java)

It unites Swagger Annotations for the [UserController](src/main/java/com/mostdevwill/swagger/UserController.java) and
the [openapi-product.yaml](src/main/resources/static/openapi-product.yaml) file to a single swagger page.
Check http://localhost:8080/swagger-ui/index.html
