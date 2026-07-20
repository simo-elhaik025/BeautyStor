CREATE TABLE IF NOT EXISTS `Category` (
	`id` BIGINT AUTO_INCREMENT UNIQUE,
	`name` VARCHAR,
	`slug` VARCHAR,
	`parent_id` BIGINT,
	`isActive` BOOLEAN,
	PRIMARY KEY(`id`)
);


CREATE TABLE IF NOT EXISTS `Brand` (
	`id` BIGINT AUTO_INCREMENT UNIQUE,
	`name` VARCHAR,
	`slug` VARCHAR,
	`logoUrl` VARCHAR,
	PRIMARY KEY(`id`)
);


CREATE TABLE IF NOT EXISTS `Product` (
	`id` BIGINT AUTO_INCREMENT UNIQUE,
	`name` VARCHAR,
	`slug` VARCHAR,
	`description` TEXT,
	`brand_id` BIGINT,
	`category_id` BIGINT,
	`basePrice` DECIMAL,
	`isAvailable` BOOLEAN,
	`attributes` JSON,
	PRIMARY KEY(`id`)
);


CREATE TABLE IF NOT EXISTS `ProductVariant` (
	`id` BIGINT AUTO_INCREMENT UNIQUE,
	`product_id` BIGINT,
	`sku` VARCHAR,
	`displayName` VARCHAR,
	`price` DECIMAL,
	`stockQuantity` INT,
	`attributes` JSON,
	PRIMARY KEY(`id`)
);


CREATE TABLE IF NOT EXISTS `ProductImage` (
	`id` BIGINT AUTO_INCREMENT UNIQUE,
	`product_id` BIGINT,
	`url` VARCHAR,
	`sortOrder` INT,
	`isPrimary` BOOLEAN,
	PRIMARY KEY(`id`)
);


CREATE TABLE IF NOT EXISTS `User` (
	`id` BIGINT AUTO_INCREMENT UNIQUE,
	`email` VARCHAR,
	`passwordHash` VARCHAR,
	`role` VARCHAR,
	`firstName` VARCHAR,
	`lastName` VARCHAR,
	`phone` VARCHAR,
	`isActive` BOOLEAN,
	PRIMARY KEY(`id`)
);


CREATE TABLE IF NOT EXISTS `Address` (
	`id` BIGINT AUTO_INCREMENT UNIQUE,
	`user_id` BIGINT,
	`fullAddress` TEXT,
	`city` VARCHAR,
	`postalCode` VARCHAR,
	`phone` VARCHAR,
	`isDefault` BOOLEAN,
	PRIMARY KEY(`id`)
);


CREATE TABLE IF NOT EXISTS `Cart` (
	`id` BIGINT AUTO_INCREMENT UNIQUE,
	`user_id` BIGINT,
	`session_id` VARCHAR,
	`status` VARCHAR,
	PRIMARY KEY(`id`)
);


CREATE TABLE IF NOT EXISTS `CartItem` (
	`id` BIGINT AUTO_INCREMENT UNIQUE,
	`cart_id` BIGINT,
	`product_variant_id` BIGINT,
	`quantity` INT,
	`snapshotPrice` DECIMAL,
	PRIMARY KEY(`id`)
);


CREATE TABLE IF NOT EXISTS `Order` (
	`id` BIGINT AUTO_INCREMENT UNIQUE,
	`orderNumber` VARCHAR,
	`user_id` BIGINT,
	`status` VARCHAR,
	`paymentMethod` VARCHAR,
	`shippingAddressSnapshot` JSON,
	`totalAmount` DECIMAL,
	`deliveryNotes` VARCHAR,
	`createdAt` TIMESTAMP,
	PRIMARY KEY(`id`)
);


CREATE TABLE IF NOT EXISTS `OrderItem` (
	`id` BIGINT AUTO_INCREMENT UNIQUE,
	`order_id` BIGINT,
	`product_variant_id` BIGINT,
	`productNameSnapshot` VARCHAR,
	`productVariantSku` VARCHAR,
	`quantity` INT,
	`priceAtPurchase` DECIMAL,
	PRIMARY KEY(`id`)
);


ALTER TABLE `Category`
ADD FOREIGN KEY(`parent_id`) REFERENCES `Category`(`id`)
ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE `Product`
ADD FOREIGN KEY(`category_id`) REFERENCES `Category`(`id`)
ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE `Product`
ADD FOREIGN KEY(`brand_id`) REFERENCES `Brand`(`id`)
ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE `ProductVariant`
ADD FOREIGN KEY(`product_id`) REFERENCES `Product`(`id`)
ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE `ProductImage`
ADD FOREIGN KEY(`product_id`) REFERENCES `Product`(`id`)
ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE `Address`
ADD FOREIGN KEY(`user_id`) REFERENCES `User`(`id`)
ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE `Cart`
ADD FOREIGN KEY(`user_id`) REFERENCES `User`(`id`)
ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE `Order`
ADD FOREIGN KEY(`user_id`) REFERENCES `User`(`id`)
ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE `CartItem`
ADD FOREIGN KEY(`cart_id`) REFERENCES `Cart`(`id`)
ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE `CartItem`
ADD FOREIGN KEY(`product_variant_id`) REFERENCES `ProductVariant`(`id`)
ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE `OrderItem`
ADD FOREIGN KEY(`order_id`) REFERENCES `Order`(`id`)
ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE `OrderItem`
ADD FOREIGN KEY(`product_variant_id`) REFERENCES `ProductVariant`(`id`)
ON UPDATE NO ACTION ON DELETE NO ACTION;