-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:7906
-- Generation Time: Oct 03, 2024 at 07:44 AM
-- Server version: 8.3.0
-- PHP Version: 8.2.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pet_spa`
--

-- --------------------------------------------------------

--
-- Table structure for table `account`
--

CREATE TABLE `account` (
  `id` bigint NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `roles` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `deleted` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `account`
--

INSERT INTO `account` (`id`, `created_at`, `email`, `name`, `password`, `roles`, `updated_at`, `deleted`) VALUES
(1, NULL, 'okthd111@gmail.com', 'Okthd111', '$2a$10$6byz3sfd/lG75Rso7JZFH.N.FKq5VJpcJx6ExecQgiXWmCIrtjd6G', 'ROLE_USER', '2024-09-29 02:04:33.225980', b'0'),
(6, NULL, 'svpv.hotrogh@gmail.com', 'Admin', '$2a$10$o0lXoeeJLenuO8j3aMdLf.A.bAlJCX2FOfVUILXvaFNcOTTI6sa/2', 'ROLE_USER', '2024-09-29 03:44:33.547233', b'0'),
(8, NULL, 'string', 'string', '$2a$10$9BCyYqUB6/sCa4NZpl5fmON9s4ZAkQFRPP6B626vCc3aTfZatRJl.', 'ROLE_USER', '2024-09-29 18:54:05.035490', b'0'),
(9, NULL, 'user@gmail.com', 'test dang ky', '$2a$10$gXlh6rcM2zmSm9.zgIZOfeETo5JvFFObmjJaRHRL6dRpewtn5cqVS', 'ROLE_USER', '2024-10-03 09:59:06.142358', b'0'),
(10, NULL, 'user@gmail.comm', 'test dang ky', '$2a$10$cpR5nDqiI2CocX9NEL3XOejAOUt8x.nc71tP/dw/uDM2FfgMK6zHS', 'ROLE_USER', '2024-10-03 10:00:02.863388', b'0'),
(11, NULL, 'user@gmail.comu', 'test dang ky', '$2a$10$dHAvfHsEHRPcWi76RLygSOS.Rl9DYOPgcDUflwXjBqQz029LxHXza', 'ROLE_USER', '2024-10-03 10:01:45.222993', b'0');

-- --------------------------------------------------------

--
-- Table structure for table `address_books`
--

CREATE TABLE `address_books` (
  `id` bigint NOT NULL,
  `city` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `country` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `postal_code` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `state` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `street` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `account_id` bigint NOT NULL,
  `deleted` bit(1) NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `full_name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `phone_number` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `address_books`
--

INSERT INTO `address_books` (`id`, `city`, `country`, `created_at`, `postal_code`, `state`, `street`, `updated_at`, `account_id`, `deleted`, `email`, `full_name`, `phone_number`) VALUES
(5, 'Ho Chi Minh', 'Vietnam', '2024-10-02 17:28:38.020323', '700000', 'Quan 8', '1931 Pham The Hien', '2024-10-02 17:28:38.020323', 6, b'0', 'okthd111@gmail.com', 'Le Viet Hai Duong', '0393067818'),
(6, 'Ho chi mih', 'Vietnam', '2024-10-03 01:53:42.693537', '700000', 'quan 8', '1932 pham the hien', '2024-10-03 01:53:42.693537', 6, b'0', 'okthd111@gmail.com', 'Le Viet Hai Duong', '0393067818'),
(7, 'Ho Chi Minh', 'Vietnam', '2024-10-03 01:58:09.054284', '700000', 'Quan 8', '1922 Pham the hien', '2024-10-03 01:58:09.054284', 6, b'0', 'svpv.hotrogh@gmail.com', 'Le Viet Hai Duong', '0393067818');

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `id` bigint NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `deleted` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`id`, `created_at`, `description`, `name`, `updated_at`, `deleted`) VALUES
(1, '2024-10-01 16:31:16.036837', 'Description for Food', 'Food', '2024-10-01 16:31:16.036837', b'0'),
(2, '2024-10-01 16:31:16.079223', 'Description for Toy', 'Toy', '2024-10-01 16:31:16.079223', b'0'),
(3, '2024-10-01 16:31:16.137035', 'Description for Accessory', 'Accessory', '2024-10-01 16:31:16.137035', b'0'),
(4, '2024-10-01 16:31:16.218025', 'Description for Clothes', 'Clothes', '2024-10-01 16:31:16.218025', b'0');

-- --------------------------------------------------------

--
-- Table structure for table `hotel_bookings`
--

CREATE TABLE `hotel_bookings` (
  `id` bigint NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `check_in_date` datetime(6) NOT NULL,
  `check_out_date` datetime(6) NOT NULL,
  `comment` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `note` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `payment_type` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `pick_up_address` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `pick_up_type` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `rating` int DEFAULT NULL,
  `return_address` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `return_type` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `status` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `total_price` decimal(38,2) NOT NULL,
  `account_id` bigint NOT NULL,
  `pet_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `hotel_bookings`
--

INSERT INTO `hotel_bookings` (`id`, `created_at`, `deleted`, `updated_at`, `check_in_date`, `check_out_date`, `comment`, `note`, `payment_type`, `pick_up_address`, `pick_up_type`, `rating`, `return_address`, `return_type`, `status`, `total_price`, `account_id`, `pet_id`) VALUES
(1, '2024-10-03 08:36:13.705283', b'0', '2024-10-03 08:36:13.706280', '2024-10-02 17:00:00.000000', '2024-10-03 17:00:00.000000', '', '', 'payment-at-store', 'Số 1931, Đường Phạm Thế Hiển, Phường 6, Quận 8, Hồ Chí Minh, Hồ Chí Minh, 73016', 'Self-drop-off', 0, 'Số 1931, Đường Phạm Thế Hiển, Phường 6, Quận 8, Hồ Chí Minh, Hồ Chí Minh, 73016', 'Self-return', 'Cancelled', '15.00', 6, 4),
(2, '2024-10-03 08:42:26.271336', b'0', '2024-10-03 08:42:26.271336', '2024-10-02 17:00:00.000000', '2024-10-03 17:00:00.000000', '', '', 'payment-at-store', 'Số 1931, Đường Phạm Thế Hiển, Phường 6, Quận 8, Hồ Chí Minh, Hồ Chí Minh, 73016', 'Self-drop-off', 0, 'Số 1931, Đường Phạm Thế Hiển, Phường 6, Quận 8, Hồ Chí Minh, Hồ Chí Minh, 73016', 'Self-return', 'Cancelled', '15.00', 6, 4),
(3, '2024-10-03 08:56:19.677030', b'0', '2024-10-03 08:56:19.677030', '2024-10-02 17:00:00.000000', '2024-10-03 17:00:00.000000', '', '', 'payment-at-store', '', 'Self-drop-off', 0, '', 'Self-return', 'Cancelled', '15.00', 6, 27),
(4, '2024-10-03 09:03:57.475227', b'0', '2024-10-03 09:03:57.475227', '2024-10-02 17:00:00.000000', '2024-10-03 17:00:00.000000', '', '', 'payment-at-store', '', 'Self-drop-off', 0, '', 'Self-return', 'Cancelled', '15.00', 6, 27),
(5, '2024-10-03 14:18:45.135393', b'0', '2024-10-03 14:18:45.135393', '2024-10-02 17:00:00.000000', '2024-10-04 17:00:00.000000', '', '', 'payment-at-store', '', 'Self-drop-off', 0, '', 'Return by staff', 'Pending', '30.00', 6, 27);

-- --------------------------------------------------------

--
-- Table structure for table `pets`
--

CREATE TABLE `pets` (
  `id` bigint NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `account_id` bigint NOT NULL,
  `avatar_url` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `description` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `height` double NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `weight` double NOT NULL,
  `pet_type_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pets`
--

INSERT INTO `pets` (`id`, `created_at`, `deleted`, `updated_at`, `account_id`, `avatar_url`, `description`, `height`, `name`, `weight`, `pet_type_id`) VALUES
(1, '2024-09-30 08:21:18.478741', b'1', '2024-09-30 08:21:18.478741', 1, 'http://localhost:8090/uploads/pets/1727659278467-c4a.jpg', '123', 213, 'pet 01', 32, 1),
(2, '2024-09-30 08:22:38.487396', b'0', '2024-10-01 00:56:42.738108', 1, 'http://localhost:8090/uploads/pets/1727718934364-c4a.jpg', '123', 123, 'pet - 2', 123, 2),
(3, '2024-09-30 08:22:41.799447', b'1', '2024-09-30 08:22:41.799447', 1, 'http://localhost:8090/uploads/pets/1727659361788-c4a.jpg', '123', 213, 'pet 03', 32, 1),
(4, '2024-09-30 08:22:47.330787', b'0', '2024-09-30 08:22:47.330787', 1, 'http://localhost:8090/uploads/pets/1727659367321-c4a.jpg', '123', 213, 'pet 04', 32, 1),
(5, '2024-09-30 08:22:50.803069', b'0', '2024-09-30 08:22:50.803069', 1, 'http://localhost:8090/uploads/pets/1727659370791-c4a.jpg', '123', 213, 'pet 05', 32, 1),
(6, '2024-09-30 22:29:57.091505', b'1', '2024-09-30 22:29:57.091505', 1, 'http://localhost:8090/uploads/pets/1727710197059-img-20240930-204151.jpg', '123', 123, 'test form fluter', 123, 2),
(7, '2024-09-30 22:30:04.829303', b'1', '2024-09-30 22:30:04.829303', 1, 'http://localhost:8090/uploads/pets/1727710204812-img-20240930-204151.jpg', '123', 123, 'test form fluter', 123, 2),
(8, '2024-09-30 22:30:10.319119', b'1', '2024-09-30 22:30:10.319119', 1, 'http://localhost:8090/uploads/pets/1727710210299-img-20240930-204151.jpg', '123', 123, 'test form fluter', 123, 2),
(9, '2024-09-30 22:43:10.085870', b'1', '2024-09-30 22:43:10.085870', 6, 'http://localhost:8090/uploads/pets/1727710990052-img-20240930-204142.jpg', '123', 123, 'Test', 123, 2),
(10, '2024-09-30 22:43:24.408806', b'1', '2024-09-30 22:43:24.408806', 6, 'http://localhost:8090/uploads/pets/1727711004393-img-20240930-204142.jpg', '123', 123, 'Test1', 123, 2),
(11, '2024-09-30 22:47:08.466172', b'1', '2024-09-30 22:47:08.466172', 6, 'http://localhost:8090/uploads/pets/1727711228450-img-20240930-204142.jpg', '123', 123, 'Test12', 123, 2),
(12, '2024-09-30 22:48:02.414325', b'1', '2024-09-30 22:48:02.414325', 6, 'http://localhost:8090/uploads/pets/1727711282401-img-20240930-204150.jpg', '123', 123, '1111', 3123, 2),
(13, '2024-09-30 22:58:59.822564', b'1', '2024-09-30 22:58:59.822564', 6, 'http://localhost:8090/uploads/pets/1727711939808-img-20240930-204142.jpg', '123', 123, '123', 123, 2),
(14, '2024-09-30 23:08:56.502027', b'1', '2024-09-30 23:08:56.502027', 6, 'http://localhost:8090/uploads/pets/1727712536490-img-20240930-204151.jpg', 'asd', 123, 'test add pet lan cuoi', 1231, 17),
(15, '2024-10-01 01:23:16.574874', b'1', '2024-10-01 01:23:16.574874', 6, 'http://localhost:8090/uploads/pets/1727720596561-065bde6e-6814-44c7-8cab-f616ab8c1e2c2266348019580613578.jpg', 'A lovely pet', 123, 'Dog 01', 123, 1),
(16, '2024-10-01 01:26:38.750688', b'1', '2024-10-01 01:34:39.077519', 6, 'http://localhost:8090/uploads/pets/1727721279048-img-20240930-204150.jpg', 'A lovely pet', 123, 'Dog 01', 123, 1),
(17, '2024-10-01 01:44:03.137199', b'1', '2024-10-01 01:46:44.086514', 6, 'http://localhost:8090/uploads/pets/1727722004072-img-20240930-204150.jpg', 'A lovely pet', 123, 'Dog 02', 123, 1),
(18, '2024-10-01 01:55:39.586624', b'1', '2024-10-01 01:55:39.586624', 6, 'http://localhost:8090/uploads/pets/1727722539568-img-20240930-204142.jpg', 'A lovely pet', 12, 'Dog 01', 12, 1),
(19, '2024-10-01 01:57:22.607283', b'1', '2024-10-01 02:30:02.514173', 6, 'http://localhost:8090/uploads/pets/1727724602491-1ec6ea88-1112-4479-b62b-f7f0225ed03b8894754482486735959.jpg', 'A lovely pet', 12, 'Dog 01', 12, 1),
(20, '2024-10-01 02:21:11.198892', b'1', '2024-10-01 02:21:11.198892', 6, 'http://localhost:8090/uploads/pets/1727724071150-img-20240930-204150.jpg', 'A lovely pet', 123, 'Dog 02', 123, 1),
(21, '2024-10-01 02:50:44.635898', b'1', '2024-10-01 02:53:00.417065', 6, 'http://localhost:8090/uploads/pets/1727725844620-c5e72ec9-c714-4341-872f-83c4493d04088258060466111541024.jpg', 'A lovely pet', 35, 'Dog 01', 23, 1),
(22, '2024-10-01 07:13:41.271343', b'1', '2024-10-01 07:13:41.271343', 6, 'http://localhost:8090/uploads/pets/1727741621185-e312d4e5-4a33-490a-adce-5fed2b1758c99101838631440341480.jpg', 'A lovely pet', 23, 'Cat 001', 6, 2),
(23, '2024-10-01 07:24:03.373521', b'1', '2024-10-01 07:24:03.373521', 6, 'http://localhost:8090/uploads/pets/1727742243348-5a7cb1d7-adcb-4063-85d7-cc87fd06b9ff3981789175075194801.jpg', 'A lovely pet', 25, 'Cat 02', 8, 2),
(24, '2024-10-01 07:24:46.757454', b'1', '2024-10-01 07:24:46.757454', 6, 'http://localhost:8090/uploads/pets/1727742286742-f18967c6-920b-472c-ad31-14f51428b1ee205329078824827826.jpg', 'A lovely pet', 21, 'Cat 03', 12, 2),
(25, '2024-10-01 15:56:43.033280', b'1', '2024-10-01 15:56:43.033280', 6, 'http://localhost:8090/uploads/pets/1727773002988-img-20240930-204142.jpg', 'A lovely pet', 12, 'Pet 01', 21, 1),
(26, '2024-10-01 19:16:26.877466', b'1', '2024-10-01 19:16:26.877466', 6, 'http://localhost:8090/uploads/pets/1727784986720-img-20240930-204142.jpg', 'A lovely pet', 13, 'Pet 01', 13, 1),
(27, '2024-10-01 19:18:14.912313', b'0', '2024-10-01 19:18:14.912313', 6, 'http://localhost:8090/uploads/pets/1727785094891-img-20240930-204142.jpg', 'A lovely pet', 12, 'pet01', 12, 1);

-- --------------------------------------------------------

--
-- Table structure for table `pet_photos`
--

CREATE TABLE `pet_photos` (
  `id` bigint NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `photo_url` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `uploaded_by` bigint NOT NULL,
  `pet_id` bigint NOT NULL,
  `photo_category_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pet_photos`
--

INSERT INTO `pet_photos` (`id`, `created_at`, `deleted`, `updated_at`, `photo_url`, `uploaded_by`, `pet_id`, `photo_category_id`) VALUES
(1, '2024-10-01 01:57:22.624986', b'0', '2024-10-01 01:57:22.624986', 'http://localhost:8090/uploads/pets/1727722642595-img-20240930-204142.jpg', 6, 19, 1),
(2, '2024-10-01 02:21:11.238935', b'0', '2024-10-01 02:21:11.238935', 'http://localhost:8090/uploads/pets/1727724071150-img-20240930-204150.jpg', 6, 20, 1),
(3, '2024-10-01 02:50:44.657920', b'0', '2024-10-01 02:50:44.657920', 'http://localhost:8090/uploads/pets/1727725844620-c5e72ec9-c714-4341-872f-83c4493d04088258060466111541024.jpg', 6, 21, 1),
(4, '2024-10-01 07:13:41.358953', b'0', '2024-10-01 07:13:41.358953', 'http://localhost:8090/uploads/pets/1727741621185-e312d4e5-4a33-490a-adce-5fed2b1758c99101838631440341480.jpg', 6, 22, 1),
(5, '2024-10-01 07:24:03.400598', b'0', '2024-10-01 07:24:03.400598', 'http://localhost:8090/uploads/pets/1727742243348-5a7cb1d7-adcb-4063-85d7-cc87fd06b9ff3981789175075194801.jpg', 6, 23, 1),
(6, '2024-10-01 07:24:46.777320', b'0', '2024-10-01 07:24:46.777320', 'http://localhost:8090/uploads/pets/1727742286742-f18967c6-920b-472c-ad31-14f51428b1ee205329078824827826.jpg', 6, 24, 1),
(7, '2024-10-01 15:56:43.169419', b'0', '2024-10-01 15:56:43.169419', 'http://localhost:8090/uploads/pets/1727773002988-img-20240930-204142.jpg', 6, 25, 1),
(8, '2024-10-01 19:16:27.077531', b'0', '2024-10-01 19:16:27.077531', 'http://localhost:8090/uploads/pets/1727784986720-img-20240930-204142.jpg', 6, 26, 1),
(9, '2024-10-01 19:18:14.941008', b'0', '2024-10-01 19:18:14.941008', 'http://localhost:8090/uploads/pets/1727785094891-img-20240930-204142.jpg', 6, 27, 1);

-- --------------------------------------------------------

--
-- Table structure for table `pet_photo_categories`
--

CREATE TABLE `pet_photo_categories` (
  `id` bigint NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pet_photo_categories`
--

INSERT INTO `pet_photo_categories` (`id`, `created_at`, `deleted`, `updated_at`, `description`, `name`) VALUES
(1, '2024-10-01 01:55:39.604117', b'0', '2024-10-01 01:55:39.604117', 'Avatar photo category', 'Avatar');

-- --------------------------------------------------------

--
-- Table structure for table `pet_tags`
--

CREATE TABLE `pet_tags` (
  `id` bigint NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `icon_url` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `price` decimal(38,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pet_tags`
--

INSERT INTO `pet_tags` (`id`, `created_at`, `deleted`, `updated_at`, `description`, `icon_url`, `name`, `price`) VALUES
(1, '2024-10-03 09:29:11.339685', b'0', '2024-10-03 09:29:11.339685', 'Chicken thigh tag', 'http://localhost:8090/uploads/pet-tags/1727922551319-61bch1ooiml-ac-sl1200.jpg', 'Chicken thigh tag', '123.00'),
(2, '2024-10-03 09:29:48.645392', b'0', '2024-10-03 09:29:48.645392', 'Fishbone tag', 'http://localhost:8090/uploads/pet-tags/1727922588635-61rzlxunxal-ac-sl1200.jpg', 'Fishbone tag', '123.00'),
(3, '2024-10-03 09:30:40.217370', b'0', '2024-10-03 09:30:40.217370', 'Bone tag', 'http://localhost:8090/uploads/pet-tags/1727922640212-61ubnp8d3el-ac-sl1200.jpg', 'Bone tag', '123.00'),
(4, '2024-10-03 09:31:53.239254', b'0', '2024-10-03 09:31:53.239254', 'Paw tag', 'http://localhost:8090/uploads/pet-tags/1727922713232-61bdf51cbdl-ac-sl1200.jpg', 'Paw tag', '13.00');

-- --------------------------------------------------------

--
-- Table structure for table `pet_tag_orders`
--

CREATE TABLE `pet_tag_orders` (
  `id` bigint NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `full_address` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `num` int NOT NULL,
  `receiver_address_id` bigint NOT NULL,
  `receiver_email` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `receiver_name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `receiver_phone` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `text_back` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `text_front` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `account_id` bigint NOT NULL,
  `pet_tag_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pet_tag_orders`
--

INSERT INTO `pet_tag_orders` (`id`, `created_at`, `deleted`, `updated_at`, `full_address`, `num`, `receiver_address_id`, `receiver_email`, `receiver_name`, `receiver_phone`, `text_back`, `text_front`, `account_id`, `pet_tag_id`) VALUES
(1, '2024-10-03 09:38:46.078594', b'0', '2024-10-03 09:38:46.078594', 'string', 1, 5, 'string', 'string', 'string', 'string back', 'string font', 6, 1),
(2, '2024-10-03 12:32:24.114072', b'0', '2024-10-03 12:32:24.114072', 'string', 0, 0, 'string', 'string', 'string', 'string', 'string', 1, 1),
(3, '2024-10-03 12:34:44.350968', b'0', '2024-10-03 12:34:44.350968', '1931 Pham The Hien, Ho Chi Minh, Quan 8, Vietnam, 700000', 100, 5, 'okthd111@gmail.com', 'Le Viet Hai Duong', '0393067818', 'Your custom text back', 'Your custom text front', 6, 1),
(4, '2024-10-03 14:24:10.568913', b'0', '2024-10-03 14:24:10.568913', '1931 Pham The Hien, Ho Chi Minh, Quan 8, Vietnam, 700000', 0, 5, 'okthd111@gmail.com', 'Le Viet Hai Duong', '0393067818', 'Contact me', 'Cho 01', 6, 1),
(5, '2024-10-03 14:24:42.726533', b'0', '2024-10-03 14:24:42.726533', '1931 Pham The Hien, Ho Chi Minh, Quan 8, Vietnam, 700000', 1, 5, 'okthd111@gmail.com', 'Le Viet Hai Duong', '0393067818', 'Your custom text back', 'Your custom text front', 6, 1);

-- --------------------------------------------------------

--
-- Table structure for table `pet_types`
--

CREATE TABLE `pet_types` (
  `id` bigint NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `icon_url` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pet_types`
--

INSERT INTO `pet_types` (`id`, `created_at`, `deleted`, `updated_at`, `description`, `icon_url`, `name`) VALUES
(1, '2024-09-30 06:30:03.111402', b'0', '2024-09-30 06:40:09.799566', 'An animal that wags its tail when it sees you come home', 'http://localhost:8090/uploads/pet-type/1727655286614-img-hero_dog.png', 'Dog'),
(2, '2024-09-30 06:38:06.161185', b'0', '2024-09-30 06:38:06.161185', 'Fish-eating creatures', 'http://localhost:8090/uploads/pet-types/1727656974983-c4a.jpg', 'Cat'),
(17, '2024-09-30 07:50:30.894887', b'1', '2024-09-30 07:51:28.819934', '123', 'http://localhost:8090/uploads/pet-types/1727657430886-img-hero-dog.png', 'tets 02');

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `id` bigint NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `image_url` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `price` decimal(38,2) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `category_id` bigint NOT NULL,
  `deleted` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`id`, `created_at`, `image_url`, `name`, `price`, `updated_at`, `category_id`, `deleted`) VALUES
(1, '2024-10-01 16:31:16.048002', 'https://via.placeholder.com/500?text=Dry Food 1', 'Dry Food 1', '100.00', '2024-10-01 16:31:16.048002', 1, b'0'),
(2, '2024-10-01 16:31:16.056588', 'https://via.placeholder.com/500?text=Cat Food 2', 'Cat Food 2', '110.00', '2024-10-01 16:31:16.056588', 1, b'0'),
(3, '2024-10-01 16:31:16.064657', 'https://via.placeholder.com/500?text=Cat Food 3', 'Cat Food 3', '120.00', '2024-10-01 16:31:16.064657', 1, b'0'),
(4, '2024-10-01 16:31:16.089092', 'https://via.placeholder.com/500?text=Tug Rope 1', 'Tug Rope 1', '100.00', '2024-10-01 16:31:16.089092', 2, b'0'),
(5, '2024-10-01 16:31:16.098233', 'https://via.placeholder.com/500?text=Chew Toy 2', 'Chew Toy 2', '110.00', '2024-10-01 16:31:16.098233', 2, b'0'),
(6, '2024-10-01 16:31:16.106772', 'https://via.placeholder.com/500?text=Tug Rope 3', 'Tug Rope 3', '120.00', '2024-10-01 16:31:16.106772', 2, b'0'),
(7, '2024-10-01 16:31:16.114682', 'https://via.placeholder.com/500?text=Toy Mouse 4', 'Toy Mouse 4', '130.00', '2024-10-01 16:31:16.114682', 2, b'0'),
(8, '2024-10-01 16:31:16.124866', 'https://via.placeholder.com/500?text=Sound Toy 5', 'Sound Toy 5', '140.00', '2024-10-01 16:31:16.124866', 2, b'0'),
(9, '2024-10-01 16:31:16.144945', 'https://via.placeholder.com/500?text=Leash 1', 'Leash 1', '100.00', '2024-10-01 16:31:16.144945', 3, b'0'),
(10, '2024-10-01 16:31:16.153768', 'https://via.placeholder.com/500?text=Collar 2', 'Collar 2', '110.00', '2024-10-01 16:31:16.153768', 3, b'0'),
(11, '2024-10-01 16:31:16.163028', 'https://via.placeholder.com/500?text=Collar 3', 'Collar 3', '120.00', '2024-10-01 16:31:16.163028', 3, b'0'),
(12, '2024-10-01 16:31:16.172501', 'https://via.placeholder.com/500?text=Harness 4', 'Harness 4', '130.00', '2024-10-01 16:31:16.172501', 3, b'0'),
(13, '2024-10-01 16:31:16.181274', 'https://via.placeholder.com/500?text=Leash 5', 'Leash 5', '140.00', '2024-10-01 16:31:16.181274', 3, b'0'),
(14, '2024-10-01 16:31:16.190618', 'https://via.placeholder.com/500?text=Harness 6', 'Harness 6', '150.00', '2024-10-01 16:31:16.192106', 3, b'0'),
(15, '2024-10-01 16:31:16.202710', 'https://via.placeholder.com/500?text=Leash 7', 'Leash 7', '160.00', '2024-10-01 16:31:16.202710', 3, b'0'),
(16, '2024-10-01 16:31:16.225436', 'https://via.placeholder.com/500?text=Dog T-Shirt 1', 'Dog T-Shirt 1', '100.00', '2024-10-01 16:31:16.225436', 4, b'0'),
(17, '2024-10-01 16:31:16.235516', 'https://via.placeholder.com/500?text=Cat T-Shirt 2', 'Cat T-Shirt 2', '110.00', '2024-10-01 16:31:16.235516', 4, b'0'),
(18, '2024-10-01 16:31:16.243553', 'https://via.placeholder.com/500?text=Dog Sweater 3', 'Dog Sweater 3', '120.00', '2024-10-01 16:31:16.243553', 4, b'0'),
(19, '2024-10-01 16:31:16.252272', 'https://via.placeholder.com/500?text=Cat Jacket 4', 'Cat Jacket 4', '130.00', '2024-10-01 16:31:16.252272', 4, b'0'),
(20, '2024-10-01 16:31:16.260781', 'https://via.placeholder.com/500?text=Cat Raincoat 5', 'Cat Raincoat 5', '140.00', '2024-10-01 16:31:16.260781', 4, b'0'),
(21, '2024-10-01 16:31:16.270223', 'https://via.placeholder.com/500?text=Cat Raincoat 6', 'Cat Raincoat 6', '150.00', '2024-10-01 16:31:16.270223', 4, b'0'),
(22, '2024-10-01 16:31:16.276431', 'https://via.placeholder.com/500?text=Dog T-Shirt 7', 'Dog T-Shirt 7', '160.00', '2024-10-01 16:31:16.276431', 4, b'0'),
(23, '2024-10-01 16:31:16.284699', 'https://via.placeholder.com/500?text=Dog Sweater 8', 'Dog Sweater 8', '170.00', '2024-10-01 16:31:16.284699', 4, b'0');

-- --------------------------------------------------------

--
-- Table structure for table `shop_orders`
--

CREATE TABLE `shop_orders` (
  `id` bigint NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `delivery_address` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `delivery_date` datetime(6) DEFAULT NULL,
  `payment_status` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `payment_type` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `status` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `total_price` decimal(38,2) NOT NULL,
  `account_id` bigint NOT NULL,
  `receiver_address_id` bigint NOT NULL,
  `receiver_email` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `receiver_name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `receiver_phone` varchar(255) COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `shop_orders`
--

INSERT INTO `shop_orders` (`id`, `created_at`, `deleted`, `updated_at`, `delivery_address`, `delivery_date`, `payment_status`, `payment_type`, `status`, `total_price`, `account_id`, `receiver_address_id`, `receiver_email`, `receiver_name`, `receiver_phone`) VALUES
(22, '2024-10-03 02:43:50.285088', b'0', '2024-10-03 02:43:50.285088', '1931 Pham The Hien, Ho Chi Minh, Vietnam', NULL, 'Unpaid', 'COD', 'Pending', '100.00', 6, 5, 'okthd111@gmail.com', 'Le Viet Hai Duong', '0393067818'),
(23, '2024-10-03 03:53:20.177823', b'0', '2024-10-03 03:53:20.177823', '1931 Pham The Hien, Ho Chi Minh, Quan 8, Vietnam, 700000', NULL, 'Unpaid', 'COD', 'Pending', '100.00', 6, 5, 'okthd111@gmail.com', 'Le Viet Hai Duong', '0393067818'),
(24, '2024-10-03 03:56:06.322416', b'0', '2024-10-03 03:56:06.322416', '1931 Pham The Hien, Ho Chi Minh, Quan 8, Vietnam, 700000', NULL, 'Unpaid', 'COD', 'Pending', '100.00', 6, 5, 'okthd111@gmail.com', 'Le Viet Hai Duong', '0393067818'),
(25, '2024-10-03 04:00:02.906081', b'0', '2024-10-03 04:00:02.906081', '1931 Pham The Hien, Ho Chi Minh, Quan 8, Vietnam, 700000', NULL, 'Unpaid', 'COD', 'Pending', '100.00', 6, 5, 'okthd111@gmail.com', 'Le Viet Hai Duong', '0393067818'),
(26, '2024-10-03 04:01:14.601762', b'0', '2024-10-03 04:49:46.022358', '1922 Pham the hien, Ho Chi Minh, Quan 8, Vietnam, 700000', NULL, 'Unpaid', 'COD', 'Pending', '100.00', 6, 7, 'svpv.hotrogh@gmail.com', 'Le Viet Hai Duong', '0393067818'),
(27, '2024-10-03 04:02:38.820683', b'0', '2024-10-03 04:02:38.820683', '1931 Pham The Hien, Ho Chi Minh, Quan 8, Vietnam, 700000', NULL, 'Unpaid', 'COD', 'Canceled by user', '110.00', 6, 5, 'okthd111@gmail.com', 'Le Viet Hai Duong', '0393067818'),
(28, '2024-10-03 04:06:27.458493', b'0', '2024-10-03 04:06:27.458493', '1931 Pham The Hien, Ho Chi Minh, Quan 8, Vietnam, 700000', NULL, 'Unpaid', 'COD', 'Canceled by user', '100.00', 6, 5, 'okthd111@gmail.com', 'Le Viet Hai Duong', '0393067818'),
(29, '2024-10-03 04:08:07.311590', b'0', '2024-10-03 04:08:07.311590', '1931 Pham The Hien, Ho Chi Minh, Quan 8, Vietnam, 700000', NULL, 'Unpaid', 'COD', 'Canceled by user', '100.00', 6, 5, 'okthd111@gmail.com', 'Le Viet Hai Duong', '0393067818'),
(30, '2024-10-03 04:10:14.166342', b'0', '2024-10-03 04:10:14.166342', '1931 Pham The Hien, Ho Chi Minh, Quan 8, Vietnam, 700000', NULL, 'Unpaid', 'COD', 'Canceled', '100.00', 6, 5, 'okthd111@gmail.com', 'Le Viet Hai Duong', '0393067818'),
(31, '2024-10-03 04:13:39.654499', b'1', '2024-10-03 04:13:39.654499', '1931 Pham The Hien, Ho Chi Minh, Quan 8, Vietnam, 700000', NULL, 'Unpaid', 'COD', 'Pending', '110.00', 6, 5, 'okthd111@gmail.com', 'Le Viet Hai Duong', '0393067818'),
(32, '2024-10-03 04:15:03.532392', b'1', '2024-10-03 04:15:03.532392', '1931 Pham The Hien, Ho Chi Minh, Quan 8, Vietnam, 700000', NULL, 'Unpaid', 'COD', 'Pending', '110.00', 6, 5, 'okthd111@gmail.com', 'Le Viet Hai Duong', '0393067818'),
(33, '2024-10-03 14:22:09.638211', b'0', '2024-10-03 14:22:09.638211', '1931 Pham The Hien, Ho Chi Minh, Quan 8, Vietnam, 700000', NULL, 'Unpaid', 'COD', 'Canceled by user', '410.00', 6, 5, 'okthd111@gmail.com', 'Le Viet Hai Duong', '0393067818');

-- --------------------------------------------------------

--
-- Table structure for table `shop_order_products`
--

CREATE TABLE `shop_order_products` (
  `shop_order_id` bigint NOT NULL,
  `quantity` int DEFAULT NULL,
  `product_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `shop_order_products`
--

INSERT INTO `shop_order_products` (`shop_order_id`, `quantity`, `product_id`) VALUES
(21, 1, 1),
(22, 1, 1),
(23, 1, 1),
(24, 1, 4),
(25, 1, 4),
(26, 1, 1),
(27, 1, 2),
(28, 1, 1),
(29, 1, 1),
(30, 1, 4),
(31, 1, 2),
(32, 1, 2),
(33, 3, 1),
(33, 1, 2);

-- --------------------------------------------------------

--
-- Table structure for table `spa_bookings`
--

CREATE TABLE `spa_bookings` (
  `id` bigint NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `booked_date` datetime(6) NOT NULL,
  `comment` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `note` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `payment_type` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `pick_up_address` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `pick_up_type` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `price` decimal(38,2) NOT NULL,
  `rating` int DEFAULT NULL,
  `return_address` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `return_type` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `status` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `used_date` datetime(6) NOT NULL,
  `used_time` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `account_id` bigint NOT NULL,
  `pet_id` bigint NOT NULL,
  `service_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `spa_bookings`
--

INSERT INTO `spa_bookings` (`id`, `created_at`, `deleted`, `updated_at`, `booked_date`, `comment`, `note`, `payment_type`, `pick_up_address`, `pick_up_type`, `price`, `rating`, `return_address`, `return_type`, `status`, `used_date`, `used_time`, `account_id`, `pet_id`, `service_id`) VALUES
(1, '2024-10-02 09:27:34.470799', b'0', '2024-10-03 07:28:25.367309', '2024-10-01 17:00:00.000000', NULL, '', 'payment-at-store', 'Self-drop-off', 'Self-drop-off', '30.00', NULL, 'Self-return', 'Self-return', 'Cancelled', '2024-10-01 17:00:00.000000', '8:59 AM', 6, 27, 1),
(2, '2024-10-02 09:28:42.380481', b'0', '2024-10-03 07:28:32.358172', '2024-10-01 17:00:00.000000', NULL, '', 'payment-at-store', 'Self-drop-off', 'Self-drop-off', '30.00', NULL, 'Self-return', 'Self-return', 'Cancelled', '2024-10-01 17:00:00.000000', '8:59 AM', 6, 27, 1),
(3, '2024-10-02 09:28:44.965681', b'0', '2024-10-03 07:28:55.056483', '2024-10-01 17:00:00.000000', NULL, '', 'payment-at-store', 'Self-drop-off', 'Self-drop-off', '30.00', NULL, 'Self-return', 'Self-return', 'Cancelled', '2024-10-01 17:00:00.000000', '8:59 AM', 6, 27, 1),
(4, '2024-10-02 09:29:48.236616', b'0', '2024-10-03 07:27:35.941941', '2024-10-01 17:00:00.000000', NULL, '1600 Amphitheatre Parkway, Mountain View, CA 94043', 'payment-at-store', '1600 Amphitheatre Parkway, Mountain View, CA 94043', 'Pick-up by staff', '30.00', NULL, 'Self-return', 'Self-return', 'Cancelled', '2024-10-01 17:00:00.000000', '8:59 AM', 6, 27, 1),
(5, '2024-10-02 09:30:08.191110', b'0', '2024-10-03 07:25:25.568449', '2024-10-01 17:00:00.000000', NULL, 'Số 1931, Đường Phạm Thế Hiển, Phường 6, Quận 8, Hồ Chí Minh, Hồ Chí Minh, 73016', 'payment-at-home', 'Số 1931, Đường Phạm Thế Hiển, Phường 6, Quận 8, Hồ Chí Minh, Hồ Chí Minh, 73016', 'Pick-up by staff', '30.00', NULL, 'Số 1931, Đường Phạm Thế Hiển, Phường 6, Quận 8, Hồ Chí Minh, Hồ Chí Minh, 73016', 'Return by staff', 'Cancelled', '2024-10-01 17:00:00.000000', '8:59 AM', 6, 27, 1),
(6, '2024-10-02 09:33:03.812688', b'0', '2024-10-03 07:24:25.240842', '2024-10-01 17:00:00.000000', NULL, 'note e2', 'payment-at-store', 'Số 1931, Đường Phạm Thế Hiển, Phường 6, Quận 8, Hồ Chí Minh, Hồ Chí Minh, 73016', 'Pick-up by staff', '30.00', NULL, 'Số 1931, Đường Phạm Thế Hiển, Phường 6, Quận 8, Hồ Chí Minh, Hồ Chí Minh, 73016', 'Return by staff', 'Cancelled', '2024-10-01 17:00:00.000000', '8:59 AM', 6, 27, 1),
(7, '2024-10-02 09:33:42.310103', b'0', '2024-10-03 07:01:01.464072', '2024-10-01 17:00:00.000000', NULL, 'note e2', 'payment-at-store', 'Số 1931, Đường Phạm Thế Hiển, Phường 6, Quận 8, Hồ Chí Minh, Hồ Chí Minh, 73016', 'Pick-up by staff', '30.00', NULL, 'Số 1931, Đường Phạm Thế Hiển, Phường 6, Quận 8, Hồ Chí Minh, Hồ Chí Minh, 73016', 'Return by staff', 'CANCELLED', '2024-10-01 17:00:00.000000', '8:59 AM', 6, 27, 1),
(8, '2024-10-03 06:22:37.050865', b'0', '2024-10-03 07:26:30.107032', '2024-10-02 17:00:00.000000', NULL, '', 'payment-at-home', 'Self-drop-off', 'Self-drop-off', '30.00', NULL, '', 'Return by staff', 'Cancelled', '2024-10-02 17:00:00.000000', '6:22 AM', 6, 27, 1),
(9, '2024-10-03 06:45:46.709768', b'0', '2024-10-03 07:28:51.106531', '2024-10-02 17:00:00.000000', NULL, '', 'payment-at-store', 'Self-drop-off', 'Self-drop-off', '30.00', NULL, 'Self-return', 'Self-return', 'Cancelled', '2024-10-02 17:00:00.000000', '6:45 AM', 6, 27, 1),
(10, '2024-10-03 07:33:31.380913', b'0', '2024-10-03 07:33:31.380913', '2024-10-02 17:00:00.000000', NULL, '', 'payment-at-store', 'Self-drop-off', 'Self-drop-off', '30.00', NULL, 'Self-return', 'Self-return', 'Pending', '2024-10-02 17:00:00.000000', '7:33 AM', 6, 27, 1),
(11, '2024-10-03 07:34:22.976857', b'0', '2024-10-03 07:34:22.976857', '2024-10-03 17:00:00.000000', NULL, '', 'payment-at-store', 'Self-drop-off', 'Self-drop-off', '30.00', NULL, 'Self-return', 'Self-return', 'Pending', '2024-10-03 17:00:00.000000', '7:34 AM', 6, 27, 1),
(12, '2024-10-03 07:40:25.681115', b'0', '2024-10-03 14:04:10.237488', '2024-10-02 17:00:00.000000', NULL, '', 'payment-at-store', 'Self-drop-off', 'Self-drop-off', '40.00', NULL, 'Self-return', 'Self-return', 'Cancelled', '2024-10-02 17:00:00.000000', '7:40 AM', 6, 27, 2);

-- --------------------------------------------------------

--
-- Table structure for table `spa_categories`
--

CREATE TABLE `spa_categories` (
  `id` bigint NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `image_url` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `spa_categories`
--

INSERT INTO `spa_categories` (`id`, `created_at`, `deleted`, `updated_at`, `description`, `name`, `image_url`) VALUES
(1, NULL, b'0', NULL, 'Comprehensive care for your pet\'s fur, ensuring it stays clean, healthy, and stylish', 'Grooming', 'https://i.imgur.com/YQjJJcn.jpeg'),
(2, NULL, b'0', NULL, 'Specialized treatments to keep your pet\'s skin in top condition, addressing common skin issues and improving comfort.', 'Skincare', 'https://i.imgur.com/T2cZMSo.png'),
(3, NULL, b'0', NULL, ': Focused care for paws and nails to keep them clean, healthy, and free of discomfort.', 'Pawdicure', 'https://i.imgur.com/AdXnXVT.png'),
(4, NULL, b'0', NULL, 'Essential dental services to maintain your pet\'s oral hygiene and prevent common issues like bad breath or gum disease.', 'Oral Care', 'https://i.imgur.com/SH9NIQB.png'),
(5, NULL, b'0', NULL, 'Soothing massages designed to relieve stress, improve circulation, and address specific health concerns.', 'Massage Therapy', 'https://i.imgur.com/hvbOAVk.png'),
(6, NULL, b'0', NULL, 'Artistic grooming services that give your pet a unique look and personality.', 'Pet Styling', 'https://i.imgur.com/R3ymVqU.jpeg'),
(7, NULL, b'0', NULL, 'Calming therapies that combine aromatherapy, herbal treatments, and deep cleansing baths to help your pet relax.', 'Relaxation', 'https://i.imgur.com/qjXG9vG.png');

-- --------------------------------------------------------

--
-- Table structure for table `spa_products`
--

CREATE TABLE `spa_products` (
  `id` bigint NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `image_url` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `price` decimal(38,2) NOT NULL,
  `category_id` bigint NOT NULL,
  `description` varchar(255) COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `spa_products`
--

INSERT INTO `spa_products` (`id`, `created_at`, `deleted`, `updated_at`, `image_url`, `name`, `price`, `category_id`, `description`) VALUES
(1, NULL, b'0', NULL, 'https://i.imgur.com/g9tGwUd.png', 'Bath & Coat Conditioning', '30.00', 1, 'Cleanse, massage, and condition pet\'s coat for smooth and shiny fur.'),
(2, NULL, b'0', NULL, 'https://i.imgur.com/ljvcCDu.png', 'Haircut & Trimming', '40.00', 1, 'Trim and style pet\'s fur to keep it neat and tidy.'),
(3, NULL, b'0', NULL, 'https://i.imgur.com/1vhgZwd.png', 'Special Coat Care', '50.00', 1, 'Detangle and groom for long or easily matted coats.'),
(4, NULL, b'0', NULL, 'https://i.imgur.com/YudDd0J.png', 'Herbal Bath', '35.00', 2, 'Soothe skin with natural herbs to reduce itching and irritation.'),
(5, NULL, b'0', NULL, 'https://i.imgur.com/WlwQGOd.png', 'Moisturizing Treatment', '30.00', 2, 'Hydrate dry skin, leaving it soft and healthy.'),
(6, NULL, b'0', NULL, 'https://i.imgur.com/zUgNdS6.png', 'Flea & Tick Treatment', '25.00', 2, 'Eliminate fleas and ticks with safe, effective products.'),
(7, NULL, b'0', NULL, 'https://i.imgur.com/oYavYsn.png', 'Nail Trimming', '15.00', 3, 'Gentle nail trimming to avoid discomfort or injury.'),
(8, NULL, b'0', NULL, 'https://i.imgur.com/2COEh9j.png', 'Paw & Nail Care', '20.00', 3, 'Moisturize and protect paws from dryness or cracking.'),
(9, NULL, b'0', NULL, 'https://i.imgur.com/6PSdxnQ.png', 'Paw Cleaning & Deodorizing', '10.00', 3, 'Clean and remove odors from paws.'),
(10, NULL, b'0', NULL, 'https://i.imgur.com/hL1VaSS.jpeg', 'Tooth Brushing', '20.00', 4, 'Clean pet’s teeth to prevent plaque and bad breath.'),
(11, NULL, b'0', NULL, 'https://i.imgur.com/yMatCBx.png', 'Plaque Removal', '40.00', 4, 'Remove plaque buildup and improve oral hygiene.'),
(12, NULL, b'0', NULL, 'https://i.imgur.com/Bx2pAqD.png', 'Breath Freshening', '15.00', 4, 'Keep your pet’s breath fresh with safe oral care.'),
(13, NULL, b'0', NULL, 'https://i.imgur.com/J6aePdt.png', 'Relaxation Massage', '45.00', 5, 'Relieve muscle tension and improve blood circulation.'),
(14, NULL, b'0', NULL, 'https://i.imgur.com/VSxIdIi.jpeg', 'Therapeutic Massage', '50.00', 5, 'Alleviate pain for older pets or those with joint issues.'),
(15, NULL, b'0', NULL, 'https://i.imgur.com/t8zGUcG.png', 'Stress Relief Massage', '40.00', 5, 'Calm anxious pets with a soothing massage.'),
(16, NULL, b'0', NULL, 'https://i.imgur.com/07zQ8BX.png', 'Artistic Fur Styling', '60.00', 6, 'Create unique and stylish looks for your pet.'),
(17, NULL, b'0', NULL, 'https://i.imgur.com/zl5feNU.png', 'Safe Fur Coloring', '50.00', 6, 'Apply safe, allergy-free pet dye for a fashionable look.'),
(18, NULL, b'0', NULL, 'https://i.imgur.com/CRDQip5.png', 'Light Makeup', '25.00', 6, 'Add subtle, cute accents for special occasions.'),
(19, NULL, b'0', NULL, 'https://i.imgur.com/YHIxxvc.png', 'Aromatherapy', '30.00', 7, 'Use safe essential oils to relax and calm your pet.'),
(20, NULL, b'0', NULL, 'https://i.imgur.com/rL6v2oU.png', 'Herbal Steam Bath', '35.00', 7, 'Deep cleanse with herbal steam for skin and coat.'),
(21, NULL, b'0', NULL, 'https://i.imgur.com/2Gu1lE4.jpeg', 'Mineral Mud Bath', '40.00', 7, 'Detoxify and deeply cleanse your pet\'s coat and skin.');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKq0uja26qgu1atulenwup9rxyr` (`email`);

--
-- Indexes for table `address_books`
--
ALTER TABLE `address_books`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKoinav8s6d5tbaj2smwedfx964` (`account_id`);

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `hotel_bookings`
--
ALTER TABLE `hotel_bookings`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKoi4baul8qwhljtwlwsilfj7rk` (`account_id`),
  ADD KEY `FK5nc7wlvps3slvrpitaqd763yg` (`pet_id`);

--
-- Indexes for table `pets`
--
ALTER TABLE `pets`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKp02i0a9uttd4xu2o4x5u5wayl` (`pet_type_id`);

--
-- Indexes for table `pet_photos`
--
ALTER TABLE `pet_photos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKfdp39phgvx1umgkmyevib6te8` (`pet_id`),
  ADD KEY `FKmdeju3ty7cqkkqro4gebck2o6` (`photo_category_id`);

--
-- Indexes for table `pet_photo_categories`
--
ALTER TABLE `pet_photo_categories`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pet_tags`
--
ALTER TABLE `pet_tags`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pet_tag_orders`
--
ALTER TABLE `pet_tag_orders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKguy2cq4cra061p7g3quj5klnc` (`account_id`),
  ADD KEY `FKjsh6mxkim5yyi303svob8drup` (`pet_tag_id`);

--
-- Indexes for table `pet_types`
--
ALTER TABLE `pet_types`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKog2rp4qthbtt2lfyhfo32lsw9` (`category_id`);

--
-- Indexes for table `shop_orders`
--
ALTER TABLE `shop_orders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK4iubqknq7ve0881uo3kakmj7h` (`account_id`);

--
-- Indexes for table `shop_order_products`
--
ALTER TABLE `shop_order_products`
  ADD PRIMARY KEY (`shop_order_id`,`product_id`),
  ADD KEY `FKtjkx9p9ghf265ksfgc0x75fe1` (`product_id`);

--
-- Indexes for table `spa_bookings`
--
ALTER TABLE `spa_bookings`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKqjbtmoenuijfdcm4htap142v7` (`account_id`),
  ADD KEY `FK84geij7fg49jvdt0gcfl5w1ne` (`pet_id`),
  ADD KEY `FK6vuvmm5scb7fxal8x6t3y74nc` (`service_id`);

--
-- Indexes for table `spa_categories`
--
ALTER TABLE `spa_categories`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `spa_products`
--
ALTER TABLE `spa_products`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKjqmihh11432hitqrea1gwa2s7` (`category_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `account`
--
ALTER TABLE `account`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `address_books`
--
ALTER TABLE `address_books`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `categories`
--
ALTER TABLE `categories`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `hotel_bookings`
--
ALTER TABLE `hotel_bookings`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `pets`
--
ALTER TABLE `pets`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT for table `pet_photos`
--
ALTER TABLE `pet_photos`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `pet_photo_categories`
--
ALTER TABLE `pet_photo_categories`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `pet_tags`
--
ALTER TABLE `pet_tags`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `pet_tag_orders`
--
ALTER TABLE `pet_tag_orders`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `pet_types`
--
ALTER TABLE `pet_types`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT for table `shop_orders`
--
ALTER TABLE `shop_orders`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT for table `spa_bookings`
--
ALTER TABLE `spa_bookings`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `spa_categories`
--
ALTER TABLE `spa_categories`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `spa_products`
--
ALTER TABLE `spa_products`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `address_books`
--
ALTER TABLE `address_books`
  ADD CONSTRAINT `FKoinav8s6d5tbaj2smwedfx964` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`);

--
-- Constraints for table `hotel_bookings`
--
ALTER TABLE `hotel_bookings`
  ADD CONSTRAINT `FK5nc7wlvps3slvrpitaqd763yg` FOREIGN KEY (`pet_id`) REFERENCES `pets` (`id`),
  ADD CONSTRAINT `FKoi4baul8qwhljtwlwsilfj7rk` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`);

--
-- Constraints for table `pets`
--
ALTER TABLE `pets`
  ADD CONSTRAINT `FKp02i0a9uttd4xu2o4x5u5wayl` FOREIGN KEY (`pet_type_id`) REFERENCES `pet_types` (`id`);

--
-- Constraints for table `pet_photos`
--
ALTER TABLE `pet_photos`
  ADD CONSTRAINT `FKfdp39phgvx1umgkmyevib6te8` FOREIGN KEY (`pet_id`) REFERENCES `pets` (`id`),
  ADD CONSTRAINT `FKmdeju3ty7cqkkqro4gebck2o6` FOREIGN KEY (`photo_category_id`) REFERENCES `pet_photo_categories` (`id`);

--
-- Constraints for table `pet_tag_orders`
--
ALTER TABLE `pet_tag_orders`
  ADD CONSTRAINT `FKguy2cq4cra061p7g3quj5klnc` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`),
  ADD CONSTRAINT `FKjsh6mxkim5yyi303svob8drup` FOREIGN KEY (`pet_tag_id`) REFERENCES `pet_tags` (`id`);

--
-- Constraints for table `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `FKog2rp4qthbtt2lfyhfo32lsw9` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`);

--
-- Constraints for table `shop_orders`
--
ALTER TABLE `shop_orders`
  ADD CONSTRAINT `FK4iubqknq7ve0881uo3kakmj7h` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`);

--
-- Constraints for table `shop_order_products`
--
ALTER TABLE `shop_order_products`
  ADD CONSTRAINT `FKa47qhm93yfjf1egkonjicvgey` FOREIGN KEY (`shop_order_id`) REFERENCES `shop_orders` (`id`),
  ADD CONSTRAINT `FKtjkx9p9ghf265ksfgc0x75fe1` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

--
-- Constraints for table `spa_bookings`
--
ALTER TABLE `spa_bookings`
  ADD CONSTRAINT `FK6vuvmm5scb7fxal8x6t3y74nc` FOREIGN KEY (`service_id`) REFERENCES `spa_products` (`id`),
  ADD CONSTRAINT `FK84geij7fg49jvdt0gcfl5w1ne` FOREIGN KEY (`pet_id`) REFERENCES `pets` (`id`),
  ADD CONSTRAINT `FKqjbtmoenuijfdcm4htap142v7` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`);

--
-- Constraints for table `spa_products`
--
ALTER TABLE `spa_products`
  ADD CONSTRAINT `FKjqmihh11432hitqrea1gwa2s7` FOREIGN KEY (`category_id`) REFERENCES `spa_categories` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
