# 🛒 Shopping Cart API

A modern, production-ready RESTful API for e-commerce shopping cart functionality built with **Spring Boot 4.0** and **Java 21**.

## ✨ Features

- **Category Management** - Full CRUD operations for product categories
- **Product Management** - Complete product lifecycle management with search capabilities
- **Image Upload** - AWS S3 integration for product image storage
- **Pagination & Sorting** - Built-in support for paginated responses
- **Validation** - Request validation using Jakarta Bean Validation
- **Exception Handling** - Comprehensive custom exception handling

---

## 🛠️ Tech Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 21 | Programming Language |
| Spring Boot | 4.0.1 | Application Framework |
| Spring Data JPA | - | Database Access |
| PostgreSQL | - | Database |
| AWS SDK | 2.41.2 | S3 Image Storage |
| ModelMapper | 3.2.6 | DTO Mapping |
| Lombok | - | Boilerplate Reduction |
| Gradle | - | Build Tool |

---

## 📁 Project Structure

```
src/main/java/com/shoppingcart/
├── config/          # Configuration classes
├── controller/      # REST API controllers
├── dto/             # Data Transfer Objects
├── entity/          # JPA entities
├── exception/       # Custom exceptions & handlers
├── repository/      # Data repositories
├── service/         # Business logic
│   └── impl/        # Service implementations
└── ShoppingCartApplication.java
```

---

## 🚀 Getting Started

### Prerequisites

- Java 21+
- PostgreSQL database
- AWS S3 bucket (for image storage)

### Environment Setup

Create a `.env` file in the project root with the following variables:

```env
SPRING_APPLICATION_NAME=ShoppingCart
DATASOURCE_URL=jdbc:postgresql://localhost:5432/shopping_cart
USERNAME=your_db_username
PASSWORD=your_db_password
HIBERNATE_DDL_AUTO=update

# AWS Configuration
AWS_ACCESS_KEY=your_aws_access_key
AWS_SECRET_KEY=your_aws_secret_key
AWS_REGION=your_aws_region
AWS_BUCKET_NAME=your_bucket_name
```

### Running the Application

```bash
# Build the project
./gradlew build

# Run the application
./gradlew bootRun
```

The API will be available at `http://localhost:8080`

---

## 📚 API Endpoints

### Categories

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| `GET` | `/api/v1/public/categories` | Get all categories (paginated) | Public |
| `POST` | `/api/v1/admin/categories` | Create a category | Admin |
| `PUT` | `/api/v1/admin/categories/{id}` | Update a category | Admin |
| `DELETE` | `/api/v1/admin/categories/{id}` | Delete a category | Admin |

### Products

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| `GET` | `/api/v1/public/products` | Get all products (paginated) | Public |
| `GET` | `/api/v1/public/categories/{categoryId}/products` | Get products by category | Public |
| `GET` | `/api/v1/public/products/keyword/{keyword}` | Search products by keyword | Public |
| `POST` | `/api/v1/admin/categories/{categoryId}/product` | Create a product | Admin |
| `PUT` | `/api/v1/admin/products/{id}` | Update a product | Admin |
| `DELETE` | `/api/v1/admin/products/{id}` | Delete a product | Admin |
| `PUT` | `/api/v1/products/{id}/image` | Upload product image | - |

### 📄 Pagination & Sorting

All list endpoints support pagination and sorting through query parameters.

#### Common Parameters

| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| `pageNumber` | Integer | `0` | Page number (0-indexed) |
| `pageSize` | Integer | `10` | Number of items per page |
| `sortOrder` | String | `asc` | Sort direction: `asc` or `desc` |

#### Sort Fields by Resource

| Resource | Parameter | Default `sortBy` | Available Fields |
|----------|-----------|------------------|------------------|
| Categories | `sortBy` | `categoryId` | `categoryId`, `categoryName` |
| Products | `sortBy` | `productId` | `productId`, `productName`, `price`, `quantity` |

#### Example Request

```http
GET /api/v1/public/products?pageNumber=0&pageSize=5&sortBy=price&sortOrder=desc
```

#### Paginated Response Structure

```json
{
  "contents": [...],
  "pageNumber": 0,
  "pageSize": 5,
  "totalElements": 50,
  "totalPages": 10,
  "last": false
}
```

---

## 📦 Request/Response Examples

### Create Category

**Request:**
```http
POST /api/v1/admin/categories
Content-Type: application/json

{
  "categoryName": "Electronics"
}
```

**Response:**
```json
{
  "categoryId": 1,
  "categoryName": "Electronics"
}
```

### Create Product

**Request:**
```http
POST /api/v1/admin/categories/1/product
Content-Type: application/json

{
  "productName": "iPhone 15",
  "productDescription": "Latest Apple smartphone",
  "quantity": 100,
  "price": 999.99,
  "discount": 10
}
```

**Response:**
```json
{
  "productId": 1,
  "productName": "iPhone 15",
  "image": "default.png",
  "productDescription": "Latest Apple smartphone",
  "quantity": 100,
  "price": 999.99,
  "discount": 10,
  "specialPrice": 899.99
}
```

### Upload Product Image

**Request:**
```http
PUT /api/v1/products/1/image
Content-Type: multipart/form-data

image: [file]
```

---

## ⚙️ Configuration

### AWS S3 Setup

1. Create an S3 bucket in your AWS account
2. Enable ACLs in bucket settings (Object Ownership → ACLs enabled)
3. Configure Block Public Access to allow public ACLs
4. Add your credentials to the `.env` file

---

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

