-- Enable UUID generation
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Users table
CREATE TABLE "users" (
    "user_id" UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    "username" VARCHAR(80) UNIQUE NOT NULL,
    "password" VARCHAR(255) NOT NULL
);

-- Books table
CREATE TABLE "books" ( 
    "book_id" UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    "title" VARCHAR(255) NOT NULL, 
    "author" VARCHAR(120)
);

-- Readings table
CREATE TABLE "readings" ( 
    "reading_id" UUID PRIMARY KEY DEFAULT gen_random_uuid(),  
    "book_id" UUID, 
    "reading_number" INTEGER NOT NULL,
    "start_date" DATE NOT NULL,
    "end_date" DATE NOT NULL,
    CONSTRAINT fk_book FOREIGN KEY ("book_id") REFERENCES "books" ("book_id")
); 

-- Recommendations table
CREATE TABLE "recommendations" (
    "book_id" UUID NOT NULL,
    "reading_id" UUID NOT NULL,
    PRIMARY KEY ("book_id", "reading_id"),
    CONSTRAINT fk_recommendations_book FOREIGN KEY ("book_id") REFERENCES "books" ("book_id") ON DELETE CASCADE,
    CONSTRAINT fk_recommendations_reading FOREIGN KEY ("reading_id") REFERENCES "readings" ("reading_id") ON DELETE CASCADE
);

-- Roles table
CREATE TABLE "roles" (
    "role_id" UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    "name" VARCHAR(255) NOT NULL
);

-- Users_roles table
CREATE TABLE "users_roles" (
    "user_id" UUID NOT NULL REFERENCES "users"("user_id") ON DELETE CASCADE,
    "role_id" UUID NOT NULL REFERENCES "roles"("role_id") ON DELETE CASCADE,
    PRIMARY KEY ("user_id", "role_id")
);

-- Roles inserts
INSERT INTO "roles" ("name") VALUES ('ROLE_USER');
INSERT INTO "roles" ("name") VALUES ('ROLE_ADMIN');
