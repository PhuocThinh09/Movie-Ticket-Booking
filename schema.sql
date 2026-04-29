CREATE TABLE `users` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `email` varchar(255) UNIQUE NOT NULL,
  `password` varchar(255) NOT NULL,
  `full_name` varchar(100) NOT NULL,
  `phone` varchar(20),
  `status` varchar(20) DEFAULT 'ACTIVE',
  `created_at` timestamp DEFAULT (now()),
  `updated_at` timestamp
);

CREATE TABLE `roles` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(50) UNIQUE NOT NULL
);

CREATE TABLE `user_roles` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`)
);

CREATE TABLE `movies` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `description` text,
  `duration` int NOT NULL,
  `rating` varchar(10),
  `release_date` date,
  `status` varchar(30) DEFAULT 'COMING_SOON',
  `poster_url` varchar(500),
  `language` varchar(50),
  `is_active` boolean DEFAULT true,
  `created_at` timestamp DEFAULT (now()),
  `updated_at` timestamp
);

CREATE TABLE `genres` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(100) UNIQUE NOT NULL
);

CREATE TABLE `movie_genres` (
  `movie_id` bigint NOT NULL,
  `genre_id` bigint NOT NULL,
  PRIMARY KEY (`movie_id`, `genre_id`)
);

CREATE TABLE `rooms` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `capacity` int NOT NULL,
  `status` varchar(20) DEFAULT 'ACTIVE',
  `created_at` timestamp DEFAULT (now()),
  `updated_at` timestamp
);

CREATE TABLE `seats` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `room_id` bigint NOT NULL,
  `row_label` varchar(5) NOT NULL,
  `seat_number` int NOT NULL,
  `type` varchar(20) DEFAULT 'STANDARD',
  `status` varchar(20) DEFAULT 'ACTIVE',
  `created_at` timestamp DEFAULT (now()),
  `updated_at` timestamp
);

CREATE TABLE `showtimes` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `movie_id` bigint NOT NULL,
  `room_id` bigint NOT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `status` varchar(20) DEFAULT 'ACTIVE',
  `created_at` timestamp DEFAULT (now()),
  `updated_at` timestamp
);

CREATE TABLE `bookings` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `booking_code` varchar(100) UNIQUE NOT NULL,
  `user_id` bigint NOT NULL,
  `showtime_id` bigint NOT NULL,
  `status` varchar(20) DEFAULT 'PENDING',
  `total_amount` decimal(10,2) NOT NULL,
  `expires_at` datetime,
  `created_at` timestamp DEFAULT (now()),
  `updated_at` timestamp
);

CREATE TABLE `booking_seats` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `booking_id` bigint NOT NULL,
  `seat_id` bigint NOT NULL,
  `showtime_id` bigint NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `status` varchar(20) NOT NULL DEFAULT 'HELD'
);

CREATE TABLE `payments` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `booking_id` bigint NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `method` varchar(50),
  `status` varchar(20) DEFAULT 'PENDING',
  `paid_at` datetime,
  `created_at` timestamp DEFAULT (now()),
  `updated_at` timestamp
);

CREATE TABLE `tickets` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `booking_seat_id` bigint UNIQUE NOT NULL,
  `ticket_code` varchar(100) UNIQUE NOT NULL,
  `qr_code_url` varchar(500),
  `status` varchar(20) DEFAULT 'VALID',
  `checked_in_at` datetime,
  `checked_in_by` bigint,
  `created_at` timestamp DEFAULT (now()),
  `updated_at` timestamp
);

CREATE UNIQUE INDEX `booking_seats_index_0` ON `booking_seats` (`showtime_id`, `seat_id`);

CREATE UNIQUE INDEX `payments_index_1` ON `payments` (`booking_id`);

ALTER TABLE `user_roles` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `user_roles` ADD FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`);

ALTER TABLE `movie_genres` ADD FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`);

ALTER TABLE `movie_genres` ADD FOREIGN KEY (`genre_id`) REFERENCES `genres` (`id`);

ALTER TABLE `seats` ADD FOREIGN KEY (`room_id`) REFERENCES `rooms` (`id`);

ALTER TABLE `showtimes` ADD FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`);

ALTER TABLE `showtimes` ADD FOREIGN KEY (`room_id`) REFERENCES `rooms` (`id`);

ALTER TABLE `bookings` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `bookings` ADD FOREIGN KEY (`showtime_id`) REFERENCES `showtimes` (`id`);

ALTER TABLE `booking_seats` ADD FOREIGN KEY (`booking_id`) REFERENCES `bookings` (`id`);

ALTER TABLE `booking_seats` ADD FOREIGN KEY (`seat_id`) REFERENCES `seats` (`id`);

ALTER TABLE `booking_seats` ADD FOREIGN KEY (`showtime_id`) REFERENCES `showtimes` (`id`);

ALTER TABLE `payments` ADD FOREIGN KEY (`booking_id`) REFERENCES `bookings` (`id`);

ALTER TABLE `tickets` ADD FOREIGN KEY (`booking_seat_id`) REFERENCES `booking_seats` (`id`);

ALTER TABLE `tickets` ADD FOREIGN KEY (`checked_in_by`) REFERENCES `users` (`id`);
