-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 12, 2023 at 02:51 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `car_service`
--

-- --------------------------------------------------------

--
-- Table structure for table `cars`
--

CREATE TABLE `cars` (
  `id` int(11) NOT NULL,
  `year` int(11) DEFAULT NULL,
  `idModel` int(11) DEFAULT NULL,
  `idUser` int(11) DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `models`
--

CREATE TABLE `models` (
  `idModel` int(11) NOT NULL,
  `modelName` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `models`
--

INSERT INTO `models` (`idModel`, `modelName`) VALUES
(1, 'Model C'),
(2, 'Model E'),
(3, 'Model S');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `idOrder` int(11) NOT NULL,
  `city` varchar(70) DEFAULT NULL,
  `email` varchar(70) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `quantity` int(11) NOT NULL,
  `idPart` int(11) DEFAULT NULL,
  `isSent` tinyint(1) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`idOrder`, `city`, `email`, `phone`, `quantity`, `idPart`, `isSent`, `timestamp`) VALUES
(1, 'Svilajnac', 'marioferk@gmail.com', '062534202', 2, 1, 1, '2023-08-12 00:26:56'),
(2, 'Marko', 'marko@gmail.com', '061231231', 1, 13, 0, '2023-08-12 00:27:31');

-- --------------------------------------------------------

--
-- Table structure for table `parts`
--

CREATE TABLE `parts` (
  `idPart` int(11) NOT NULL,
  `name` varchar(30) DEFAULT NULL,
  `serial` varchar(50) NOT NULL,
  `from_year` int(11) DEFAULT NULL,
  `to_year` int(11) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `idModel` int(11) DEFAULT NULL,
  `idType` int(11) DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `parts`
--

INSERT INTO `parts` (`idPart`, `name`, `serial`, `from_year`, `to_year`, `price`, `description`, `idModel`, `idType`, `timestamp`) VALUES
(1, 'VRIBBED BELT SET ', '132312512', 2001, 2008, 149.99, 'VRIBBED BELT SET FOR MERCEDES-BENZ C-CLASS/T-Model OM 626.951 1.6L 4cyl C-CLASS (Fits: Mercedes-Benz C-Class)', 1, 1, '2023-08-08 11:12:12'),
(2, 'VRIBBED BELT SET', '12315637', 2009, 2015, 191.11, 'VRIBBED BELT SET FOR MERCEDES-BENZ C-CLASS/T-Model/Sedan E-CLASS 2.1L 4cyl 2.7L (Fits: Mercedes-Benz C-Class)', 1, 1, '2023-08-08 11:12:12'),
(3, 'SWAG 10 93 9269', '678475', 2010, 2015, 191.11, 'SWAG 10 93 9269 Timing Chain for MERCEDES-BENZ,MERCEDES-BENZ (BBDC) (Fits: Mercedes-Benz C-Class)', 1, 1, '2023-08-08 11:12:12'),
(4, 'VRIBBED BELT SET', '345323', 2002, 2009, 119.68, 'VRIBBED BELT SET FOR MERCEDES-BENZ C-CLASS/T-Model/Sedan SLK E-CLASS 1.8L 4cyl', 2, 1, '2023-08-08 11:12:12'),
(5, 'VRIBBED BELT SET', '31253476', 2010, 2016, 259.11, 'VRIBBED BELT SET FOR MERCEDES-BENZ E-CLASS/Convertible/T-Model/All-Terrain 2.0L', 2, 1, '2023-08-08 11:12:12'),
(6, 'NEW V-RIBBED BELT SET', '563472', 2017, 2023, 82.64, 'NEW V-RIBBED BELT SET FOR MERCEDES BENZ E CLASS W211 OM 646 951 OM 648 961 INA', 2, 1, '2023-08-08 11:12:12'),
(7, 'VRIBBED BELT SET', '423737', 2003, 2010, 152.44, 'VRIBBED BELT SET FOR MERCEDES-BENZ S-CLASS/Sedan E-CLASS/T-Model GLK-CLASS 2.1L (Fits: Mercedes-Benz S-Class)', 3, 1, '2023-08-08 11:12:12'),
(8, 'VRIBBED BELT SET', '214584', 2011, 2017, 84.55, 'VRIBBED BELT SET FOR MERCEDES-BENZ E-CLASS/T-Model C-CLASS/Sportcoupe S-CLASS (Fits: Mercedes-Benz S-Class)', 2, 1, '2023-08-08 11:12:12'),
(9, 'tire spanner', '2630634', 2002, 2012, 12.5, 'Spanner mercedes S class w221 tire spanner ribbed SMALL', 3, 3, '2023-08-08 11:12:12'),
(10, 'new tire spanner ', '345121772', 2013, 2018, 15.25, 'mercedes S class w521 tire spanner ribbed LARGE', 3, 3, '2023-08-08 11:12:12'),
(11, 'A6510500800 Spanner', '45749567', 2001, 2011, 186.11, 'Original Mercedes-Benz A6510500800 Spanner Timing OM651 C KLASSE', 1, 3, '2023-08-08 11:12:12'),
(12, 'A6510500822 Spanner', '634563578', 2012, 2019, 192.56, 'Original Mercedes-Benz A6510500822 Spanner Timing OM651 C KLASSE', 1, 3, '2023-08-08 11:12:12'),
(13, 'W202 Fan Belt Spanner', '237697', 2001, 2010, 65.25, 'Original Mercedes E W202 Fan Belt Spanner Vibration Damper', 2, 3, '2023-08-08 11:12:12'),
(14, 'W305 Fan Belt Spanner', '4684935', 2011, 2018, 81, 'Original Mercedes E W305 Fan Belt Spanner Vibration Damper', 2, 3, '2023-08-08 11:12:12'),
(15, 'WATER PUMP PARKING HEATER', '55234236', 2001, 2008, 72.55, 'WATER PUMP PARKING HEATER FOR MERCEDES-BENZ C-CLASS/T-Model/Sedan/Sportcoupe (Fits: Mercedes-Benz C-Class)', 1, 4, '2023-08-08 11:12:12'),
(16, 'WATER PUMP FOR MERCEDES-BENZ C', '42374567', 2009, 2016, 301.21, 'WATER PUMP FOR MERCEDES-BENZ C-CLASS/T-Model/Convertible M 274.920 2.0L 4cyl (Fits: Mercedes-Benz C-Class)', 1, 4, '2023-08-08 11:12:12'),
(17, 'VEMO Original Quality', '13123564', 2010, 2018, 58.55, 'VEMO Original Quality Air Flow Sensor V20-72-0005', 1, 10, '2023-08-11 11:35:51'),
(18, 'STARK Air Flow Sensor', '45875956', 2015, 2020, 65.05, 'STARK Air Flow Sensor SKAS-0150051', 1, 10, '2023-08-11 11:35:51'),
(19, 'KS TOOLS', '364570', 2002, 2010, 10.27, 'KS TOOLS 515.3361 Oil', 2, 5, '2023-08-11 11:41:23'),
(20, 'HAZET', '543756860', 2003, 2011, 13.08, 'HAZET 9400-100 Oil', 2, 5, '2023-08-11 11:41:23'),
(21, 'HAZET', '068985', 2004, 2012, 19.01, 'HAZET 9400-1000 Oil', 2, 5, '2023-08-11 11:41:23');

-- --------------------------------------------------------

--
-- Table structure for table `services`
--

CREATE TABLE `services` (
  `idService` int(11) NOT NULL,
  `name` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `services`
--

INSERT INTO `services` (`idService`, `name`) VALUES
(1, 'big_service'),
(2, 'small_service'),
(3, 'sensors');

-- --------------------------------------------------------

--
-- Table structure for table `types`
--

CREATE TABLE `types` (
  `idType` int(11) NOT NULL,
  `typeName` varchar(30) DEFAULT NULL,
  `idService` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `types`
--

INSERT INTO `types` (`idType`, `typeName`, `idService`) VALUES
(1, 'Timing belt', 1),
(2, 'Guide roller', 1),
(3, 'Spanner', 1),
(4, 'Water pump', 1),
(5, 'Oil', 2),
(6, 'Cabin air filter', 2),
(7, 'Engine air filter', 2),
(8, 'Fuel filter', 2),
(9, 'Turbine pressure sensor', 3),
(10, 'O2 sensor', 3),
(11, 'MAF sensor', 3),
(12, 'MAP sensor', 3);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `idUser` int(11) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `password` varchar(300) DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`idUser`, `email`, `password`, `timestamp`) VALUES
(1, 'mario@gmail.com', 'mario99', '2023-08-08 10:00:07'),
(2, 'marko@gmail.com', 'marko123', '2023-08-08 10:35:05'),
(3, 'test', 'test', '2023-08-09 12:54:22'),
(7, 'test1', 'bcrypt+sha512$e783c92b169cacac55e356ddf6e6901d$12$06d1a0b8e1f378cab108ccc6d2390dfbd187659f086743e3', '2023-08-09 15:00:21'),
(8, 'test2', 'bcrypt+sha512$44fe80056686ed88e7e31b5e49209c1a$12$d7cfd5386c6eb0b8aa0fb54557c6afcec544415fbca3c048', '2023-08-10 11:17:23'),
(9, 'test3', 'bcrypt+sha512$62248327c54c43d76f9de76374a4ce70$12$87b1b66baadbdf131a8532289669cd50da683d7331e6f1d8', '2023-08-10 12:39:16');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cars`
--
ALTER TABLE `cars`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idModel` (`idModel`),
  ADD KEY `idUser` (`idUser`);

--
-- Indexes for table `models`
--
ALTER TABLE `models`
  ADD PRIMARY KEY (`idModel`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`idOrder`),
  ADD KEY `idPart` (`idPart`);

--
-- Indexes for table `parts`
--
ALTER TABLE `parts`
  ADD PRIMARY KEY (`idPart`),
  ADD KEY `idModel` (`idModel`),
  ADD KEY `idType` (`idType`);

--
-- Indexes for table `services`
--
ALTER TABLE `services`
  ADD PRIMARY KEY (`idService`);

--
-- Indexes for table `types`
--
ALTER TABLE `types`
  ADD PRIMARY KEY (`idType`),
  ADD KEY `idService` (`idService`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`idUser`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cars`
--
ALTER TABLE `cars`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `models`
--
ALTER TABLE `models`
  MODIFY `idModel` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `idOrder` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `parts`
--
ALTER TABLE `parts`
  MODIFY `idPart` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT for table `services`
--
ALTER TABLE `services`
  MODIFY `idService` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `types`
--
ALTER TABLE `types`
  MODIFY `idType` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `idUser` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `cars`
--
ALTER TABLE `cars`
  ADD CONSTRAINT `cars_ibfk_1` FOREIGN KEY (`idModel`) REFERENCES `models` (`idModel`),
  ADD CONSTRAINT `cars_ibfk_2` FOREIGN KEY (`idUser`) REFERENCES `users` (`idUser`);

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`idPart`) REFERENCES `parts` (`idPart`);

--
-- Constraints for table `parts`
--
ALTER TABLE `parts`
  ADD CONSTRAINT `parts_ibfk_1` FOREIGN KEY (`idModel`) REFERENCES `models` (`idModel`),
  ADD CONSTRAINT `parts_ibfk_2` FOREIGN KEY (`idType`) REFERENCES `types` (`idType`);

--
-- Constraints for table `types`
--
ALTER TABLE `types`
  ADD CONSTRAINT `types_ibfk_1` FOREIGN KEY (`idService`) REFERENCES `services` (`idService`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
