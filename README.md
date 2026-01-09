# 🛒 Shopping Cart API

A modern, **production-ready** RESTful e-commerce API built with **Spring Boot 4.0** and **Java 21**. Features secure JWT authentication, role-based access control, AWS S3 integration for image storage, and comprehensive product/category management.

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0.1-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![AWS S3](https://img.shields.io/badge/AWS_S3-FF9900?style=for-the-badge&logo=amazons3&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white)

---

## ✨ Features

### 🔐 Authentication & Authorization
- **JWT-based Authentication** - Secure, stateless authentication using JSON Web Tokens
- **HTTP-Only Cookie** - JWT stored in secure HTTP-only cookies for enhanced security
- **Role-Based Access Control** - Three roles: `USER`, `ADMIN`, `SELLER`
- **Automatic Role Seeding** - Roles are automatically initialized on application startup
- **BCrypt Password Encryption** - Industry-standard password hashing

### 📦 Product Management
- **Full CRUD Operations** - Create, read, update, and delete products
- **Category Association** - Products organized by categories
- **Image Upload** - AWS S3 integration for product image storage
- **Search by Keyword** - Find products by name
- **Automatic Special Price** - Calculates discounted price automatically

### 🏷️ Category Management
- **Category CRUD** - Complete category lifecycle management
- **Admin-Only Mutations** - Only admins can create/update/delete categories
- **Unique Constraint** - Prevents duplicate product names within categories

### 📊 Advanced Features
- **Pagination & Sorting** - Built-in support for all list endpoints
- **Request Validation** - Jakarta Bean Validation for all inputs
- **Global Exception Handling** - Consistent, user-friendly error responses
- **Seller Support** - Sellers can manage their own products

---

## 🛠️ Tech Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| **Java** | 21 | Programming Language |
| **Spring Boot** | 4.0.1 | Application Framework |
| **Spring Security** | - | Authentication & Authorization |
| **Spring Data JPA** | - | Database Access Layer |
| **PostgreSQL** | - | Relational Database |
| **JWT (jjwt)** | 0.13.0 | Token-based Authentication |
| **AWS SDK** | 2.41.2 | S3 Image Storage |
| **ModelMapper** | 3.2.6 | DTO Mapping |
| **Lombok** | - | Boilerplate Reduction |
| **Gradle** | - | Build Tool |

---

## 📁 Project Structure

```
src/main/java/com/shoppingcart/
├── config/                    # Configuration & Data Seeding
│   ├── AppConstants.java          # Pagination defaults
│   ├── AwsConfig.java             # AWS S3 configuration
│   ├── DataSeeder.java            # Automatic role seeding
│   └── ProjectConfig.java         # ModelMapper config
│
├── controller/                # REST API Controllers
│   ├── AuthController.java        # Authentication endpoints
│   ├── CategoryController.java    # Category management
│   └── ProductController.java     # Product management
│
├── dto/                       # Data Transfer Objects
│   ├── ApiResponse.java           # Generic API response
│   ├── ApiErrorResponse.java      # Error response format
│   ├── CategoryDto.java           # Category request/response
│   ├── CategoryResponseDto.java   # Paginated categories
│   ├── ProductDto.java            # Product request/response
│   └── ProductResponseDto.java    # Paginated products
│
├── entity/                    # JPA Entities
│   ├── Address.java               # User addresses
│   ├── Category.java              # Product categories
│   ├── Product.java               # Product details
│   ├── Role.java                  # User roles
│   ├── User.java                  # User accounts
│   └── enums/
│       └── UserRoles.java         # ROLE_USER, ROLE_ADMIN, ROLE_SELLER
│
├── exception/                 # Exception Handling
│   ├── APIException.java          # Generic API exception
│   ├── BadRequestException.java   # 400 errors
│   ├── DuplicateResourceException.java  # 409 conflicts
│   ├── EmptyResultException.java  # Empty results
│   ├── GlobalExceptionHandler.java # Central exception handler
│   └── ResourceNotFoundException.java  # 404 errors
│
├── repository/                # Data Repositories
│   ├── AddressRepository.java
│   ├── CategoryRepository.java
│   ├── ProductRepository.java
│   ├── RoleRepository.java
│   └── UserRepository.java
│
├── security/                  # Security Configuration
│   ├── SecurityConfig.java        # Spring Security setup
│   ├── constants/
│   │   └── SecurityConstants.java # Public URLs, auth headers
│   ├── jwt/
│   │   ├── JwtAuthEntryPoint.java # Unauthorized handler
│   │   ├── JwtAuthFilter.java     # JWT request filter
│   │   └── JwtUtils.java          # JWT utility methods
│   ├── request/
│   │   ├── LoginRequest.java      # Sign-in request DTO
│   │   └── SignupRequest.java     # Registration request DTO
│   ├── response/
│   │   ├── AuthenticationResult.java  # Login response
│   │   ├── MessageResponse.java       # Simple message
│   │   ├── SignupResponse.java        # Registration response
│   │   └── UserInfoResponse.java      # User details
│   ├── service/
│   │   ├── CustomUserDetailsImpl.java # UserDetails implementation
│   │   └── CustomUserDetailsService.java # Loads users
│   └── utils/
│       └── AuthUtil.java          # Auth utilities
│
├── service/                   # Business Logic
│   ├── AuthService.java           # Auth service interface
│   ├── CategoryService.java       # Category service interface
│   ├── ProductService.java        # Product service interface
│   └── impl/
│       ├── AuthServiceImpl.java       # Auth implementation
│       ├── CategoryServiceImpl.java   # Category implementation
│       └── ProductServiceImpl.java    # Product implementation
│
└── ShoppingCartApplication.java   # Main Application
```

---

## 🚀 Getting Started

### Prerequisites

- **Java 21** or higher
- **PostgreSQL** database
- **AWS S3 bucket** (for image storage)
- **Gradle** (or use included wrapper)

### Environment Setup

Create a `.env` file in the project root:

```env
# Application
SPRING_APPLICATION_NAME=ShoppingCart

# Database
DATASOURCE_URL=jdbc:postgresql://localhost:5432/shopping_cart
USERNAME=your_db_username
PASSWORD=your_db_password
HIBERNATE_DDL_AUTO=update

# AWS S3 Configuration
AWS_ACCESS_KEY=your_aws_access_key
AWS_SECRET_KEY=your_aws_secret_key
AWS_REGION=ap-south-1
AWS_BUCKET_NAME=your_bucket_name

# JWT Configuration
JWT_SECRET=your_256_bit_secret_key_here_make_it_long_and_secure
JWT_TIME=86400000
```

### Running the Application

```bash
# Clone the repository
git clone https://github.com/yourusername/shopping-cart-api.git
cd shopping-cart-api

# Build the project
./gradlew build

# Run the application
./gradlew bootRun
```

The API will be available at `http://localhost:8080`

---

## 📚 API Reference

### 🔐 Authentication

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| `POST` | `/api/v1/auth/signup` | Register a new user | Public |
| `POST` | `/api/v1/auth/signin` | Login and get JWT cookie | Public |
| `POST` | `/api/v1/auth/signout` | Logout (clears cookie) | Authenticated |
| `GET` | `/api/v1/auth/user` | Get current user details | Authenticated |
| `GET` | `/api/v1/auth/username` | Get current username | Authenticated |

### 🏷️ Categories

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| `GET` | `/api/v1/public/categories` | Get all categories (paginated) | Public |
| `POST` | `/api/v1/admin/categories` | Create a category | Admin |
| `PUT` | `/api/v1/admin/categories/{id}` | Update a category | Admin |
| `DELETE` | `/api/v1/admin/categories/{id}` | Delete a category | Admin |

### 📦 Products

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| `GET` | `/api/v1/public/products` | Get all products (paginated) | Public |
| `GET` | `/api/v1/public/categories/{categoryId}/products` | Get products by category | Public |
| `GET` | `/api/v1/public/products/keyword/{keyword}` | Search products by keyword | Public |
| `POST` | `/api/v1/admin/categories/{categoryId}/product` | Create a product | Admin, Seller |
| `PUT` | `/api/v1/admin/products/{id}` | Update a product | Admin, Seller |
| `DELETE` | `/api/v1/admin/products/{id}` | Delete a product | Admin |
| `PUT` | `/api/v1/products/{id}/image` | Upload product image | Admin, Seller |

---

## 📄 Pagination & Sorting

All list endpoints support pagination and sorting through query parameters.

### Query Parameters

| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| `pageNumber` | Integer | `0` | Page number (0-indexed) |
| `pageSize` | Integer | `10` | Items per page |
| `sortBy` | String | varies | Field to sort by |
| `sortOrder` | String | `asc` | Sort direction: `asc` or `desc` |

### Sort Fields

| Resource | Default `sortBy` | Available Fields |
|----------|------------------|------------------|
| Categories | `categoryId` | `categoryId`, `categoryName` |
| Products | `productId` | `productId`, `productName`, `price`, `quantity` |

### Example Request

```http
GET /api/v1/public/products?pageNumber=0&pageSize=5&sortBy=price&sortOrder=desc
```

### Paginated Response

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

### User Registration

**Request:**
```http
POST /api/v1/auth/signup
Content-Type: application/json

{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "securePassword123",
  "role": ["user"]
}
```

**Response:**
```json
{
  "message": "User registered successfully!",
  "username": "john_doe"
}
```

### User Login

**Request:**
```http
POST /api/v1/auth/signin
Content-Type: application/json

{
  "username": "john_doe",
  "password": "securePassword123"
}
```

**Response:**
```json
{
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "roles": ["ROLE_USER"],
  "jwtCookie": "JWT cookie set in response header"
}
```

### Create Category

**Request:**
```http
POST /api/v1/admin/categories
Content-Type: application/json
Cookie: jwtCookie=<your-jwt-token>

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
Cookie: jwtCookie=<your-jwt-token>

{
  "productName": "iPhone 15 Pro",
  "description": "Latest Apple smartphone with A17 Pro chip",
  "quantity": 100,
  "price": 999.99,
  "discount": 10
}
```

**Response:**
```json
{
  "productId": 1,
  "productName": "iPhone 15 Pro",
  "image": "default.png",
  "description": "Latest Apple smartphone with A17 Pro chip",
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
Cookie: jwtCookie=<your-jwt-token>

image: [file]
```

**Response:**
```json
{
  "productId": 1,
  "productName": "iPhone 15 Pro",
  "image": "https://your-bucket.s3.region.amazonaws.com/products/image.jpg",
  ...
}
```

---

## ⚠️ Error Responses

The API uses consistent error response format:

### Standard Error Response

```json
{
  "message": "Error description here",
  "success": false
}
```

### Validation Error Response

```json
{
  "username": "Username must be between 3 and 20 characters",
  "email": "Email must be valid",
  "password": "Password must be at least 6 characters"
}
```

### HTTP Status Codes

| Code | Description |
|------|-------------|
| `200` | Success |
| `201` | Created |
| `400` | Bad Request / Validation Error |
| `401` | Unauthorized (Invalid credentials) |
| `403` | Forbidden (Insufficient permissions) |
| `404` | Resource Not Found |
| `409` | Conflict (Duplicate resource) |
| `500` | Internal Server Error |

---

## 🔒 Security Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                      Client Request                         │
└──────────────────────────┬──────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────┐
│                   JwtAuthFilter                             │
│  • Extracts JWT from HTTP-Only Cookie                       │
│  • Validates token signature and expiration                 │
│  • Sets SecurityContext with user details                   │
└──────────────────────────┬──────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────┐
│               SecurityFilterChain                           │
│  • Public URLs: /api/v1/auth/**, /api/v1/public/**          │
│  • Stateless session management                             │
│  • CSRF disabled (using JWT)                                │
└──────────────────────────┬──────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────┐
│              @PreAuthorize Checks                           │
│  • hasRole('ADMIN') - Admin-only operations                 │
│  • hasAnyRole('ADMIN', 'SELLER') - Product management       │
└──────────────────────────┬──────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────┐
│                    Controller                               │
└─────────────────────────────────────────────────────────────┘
```

---

## ⚙️ AWS S3 Setup

1. **Create an S3 bucket** in your AWS account
2. **Enable ACLs** in bucket settings:
   - Go to Permissions → Object Ownership
   - Select "ACLs enabled"
3. **Configure Block Public Access**:
   - Allow public ACLs for product images
4. **Add IAM credentials** to your `.env` file
5. **Set CORS** (if needed for frontend):
   ```json
   [
     {
       "AllowedOrigins": ["*"],
       "AllowedMethods": ["GET", "PUT", "POST"],
       "AllowedHeaders": ["*"]
     }
   ]
   ```

---

## 🧪 Testing

```bash
# Run all tests
./gradlew test

# Run with test report
./gradlew test jacocoTestReport
```

---

## 🔧 Development

### Build Commands

```bash
# Clean build
./gradlew clean build

# Build without tests
./gradlew build -x test

# Run in development mode
./gradlew bootRun
```

### Database Schema

The application uses `hibernate.ddl-auto=update` by default. On first run, the following tables are created:

- `users` - User accounts
- `roles` - Available roles (auto-seeded)
- `user_roles` - User-role mapping
- `categories` - Product categories
- `products` - Product catalog
- `addresses` - User addresses

---

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 👨‍💻 Author

**Swadesh**

- GitHub: [@swadesh](https://github.com/swadesh)

---

<p align="center">
  Made with ❤️ using Spring Boot
</p>
