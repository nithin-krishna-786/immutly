# Immutly

Application has an API for CRUD Operations on Products

## Development

Update your local database connection in `application.properties` 
Lombok must be supported by your IDE. For IntelliJ install the Lombok plugin and enable annotation processing -
After starting the application it is accessible under `localhost:8080

Rest Endpoints are accessible at
http://localhost:8080/swagger-ui/index.html

## Build

The application can be built using the following command:
```
mvn clean install
```

Design Decisions: 

Created REST Controller /products for managing all the CRUD operations in a Single Controller

GET /products - Fetch Products based on Query Parameters minPrice,maxPrice,productName


GET /products/{id} - Fetch Product by ID


POST /products - Create Product


PUT /products/{id} - Updating a Product


DELETE /products/{id} - Deleting a Product


