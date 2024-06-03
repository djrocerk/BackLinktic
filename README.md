# eCommerce API Documentation

This API provides endpoints to manage orders, order details, and products for an eCommerce platform.

## Available Routes

### Order Detail

- **GET /api-app/detail/{order_id}**: Retrieves the details of a specific order by its ID.

### Order

- **GET /api-app/order**: Lists all available orders.
- **GET /api-app/order/{id}**: Retrieves a specific order by its ID.
- **POST /api-app/order**: Creates a new order with the provided details.
- **DELETE /api-app/order/{id}**: Deletes a specific order by its ID.

### Product

- **GET /api-app/product**: Lists all available products.
- **GET /api-app/product/{id}**: Retrieves a specific product by its ID.
- **POST /api-app/product**: Adds a new product.
- **PUT /api-app/product**: Updates an existing product.
- **DELETE /api-app/product/{id}**: Deletes a specific product by its ID.

## Swagger UI Documentation

You can access the interactive documentation of the API via Swagger UI at the following link:

[http://localhost:8083/swagger-ui/index.html](http://localhost:8083/swagger-ui/index.html)
