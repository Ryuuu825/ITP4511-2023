-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- 主機： 127.0.0.1
-- 產生時間： 2023-04-11 09:08:14
-- 伺服器版本： 10.4.24-MariaDB
-- PHP 版本： 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 資料庫： `itp4511_asm_db`
--

-- --------------------------------------------------------

--
-- 資料表結構 `guest`
--

CREATE TABLE `guest` (
  `id` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- 傾印資料表的資料 `guest`
--

INSERT INTO `guest` (`id`, `userId`, `name`, `email`) VALUES
(1, 1, 'John Smith', 'john.smith@example.com'),
(2, 2, 'Jane Doe', 'jane.doe@example.com'),
(3, 3, 'David Johnson', 'david.johnson@example.com'),
(4, 4, 'Sarah Williams', 'sarah.williams@example.com'),
(5, 5, 'Michael Brown', 'michael.brown@example.com'),
(6, 6, 'Emily Wilson', 'emily.wilson@example.com'),
(7, 7, 'Daniel Lee', 'daniel.lee@example.com'),
(8, 8, 'Samantha Chen', 'samantha.chen@example.com'),
(9, 9, 'James Kim', 'james.kim@example.com'),
(10, 10, 'Jennifer Lee', 'jennifer.lee@example.com'),
(11, 1, 'Matthew Davis', 'matthew.davis@example.com'),
(12, 2, 'Olivia Wilson', 'olivia.wilson@example.com'),
(13, 3, 'Ethan Brown', 'ethan.brown@example.com'),
(14, 4, 'Isabella Chen', 'isabella.chen@example.com'),
(15, 5, 'Benjamin Kim', 'benjamin.kim@example.com'),
(16, 6, 'Victoria Lee', 'victoria.lee@example.com'),
(17, 7, 'Lucas Davis', 'lucas.davis@example.com'),
(18, 8, 'Chloe Wilson', 'chloe.wilson@example.com'),
(19, 9, 'Alexander Lee', 'alexander.lee@example.com'),
(20, 10, 'Ava Chen', 'ava.chen@example.com'),
(21, 1, 'William Kim', 'william.kim@example.com'),
(22, 2, 'Sophia Lee', 'sophia.lee@example.com'),
(23, 3, 'Daniel Davis', 'daniel.davis@example.com'),
(24, 4, 'Mia Wilson', 'mia.wilson@example.com'),
(25, 5, 'Matthew Brown', 'matthew.brown@example.com'),
(26, 6, 'Olivia Chen', 'olivia.chen@example.com'),
(27, 7, 'Ethan Kim', 'ethan.kim@example.com'),
(28, 8, 'Isabella Lee', 'isabella.lee@example.com'),
(29, 9, 'Benjamin Davis', 'benjamin.davis@example.com'),
(30, 10, 3, 5, 'Victoria Wilson', 'victoria.wilson@example.com'),
(31, 1, 4, 1, 'Lucas Brown', 'lucas.brown@example.com'),
(32, 2, 5, 2, 'Chloe Chen', 'chloe.chen@example.com'),
(33, 3, 6, 3, 'Alexander Kim', 'alexander.kim@example.com'),
(34, 4, 7, 4, 'Ava Lee', 'ava.lee@example.com'),
(35, 5, 8, 5, 'William Davis', 'william.davis@example.com'),
(36, 6, 9, 1, 'Sophia Chen', 'sophia.chen@example.com'),
(37, 7, 1, 2, 'Daniel Kim', 'daniel.kim@example.com'),
(38, 8, 2, 3, 'Mia Lee', 'mia.lee@example.com'),
(39, 9, 3, 4, 'Matthew Davis', 'matthew.davis@example.com'),
(40, 10, 4, 5, 'Sophia Chen', 'sophia.chen@example.com'),
(41, 1, 5, 1, 'Daniel Kim', 'daniel.kim@example.com'),
(42, 2, 6, 2, 'Mia Lee', 'mia.lee@example.com'),
(43, 3, 7, 3, 'Matthew Davis', 'matthew.davis@example.com'),
(44, 4, 8, 4, 'Olivia Wilson', 'olivia.wilson@example.com'),
(45, 5, 9, 5, 'Ethan Brown', 'ethan.brown@example.com'),
(46, 6, 1, 1, 'Isabella Chen', 'isabella.chen@example.com'),
(47, 7, 2, 2, 'Benjamin Kim', 'benjamin.kim@example.com'),
(48, 8, 3, 3, 'Victoria Lee', 'victoria.lee@example.com'),
(49, 9, 4, 4, 'Lucas Davis', 'lucas.davis@example.com'),
(50, 10, 5, 5, 'Chloe Wilson', 'chloe.wilson@example.com'),
(51, 1, 6, 1, 'Alexander Brown', 'alexander.brown@example.com'),
(52, 2, 7, 2, 'Ava Chen', 'ava.chen@example.com'),
(53, 3, 8, 3, 'William Kim', 'william.kim@example.com'),
(54, 4, 9, 4, 'Sophia Lee', 'sophia.lee@example.com'),
(55, 5, 1, 5, 'Daniel Davis', 'daniel.davis@example.com'),
(56, 6, 2, 1, 'Mia Wilson', 'mia.wilson@example.com'),
(57, 7, 3, 2, 'Matthew Brown', 'matthew.brown@example.com'),
(58, 8, 4, 3, 'Olivia Chen', 'olivia.chen@example.com'),
(59, 9, 5, 4, 'Ethan Kim', 'ethan.kim@example.com'),
(60, 10, 6, 5, 'Isabella Lee', 'isabella.lee@example.com'),
(61, 1, 7, 1, 'Benjamin Davis', 'benjamin.davis@example.com'),
(62, 2, 8, 2, 'Victoria Wilson', 'victoria.wilson@example.com'),
(63, 3, 9, 3, 'Lucas Brown', 'lucas.brown@example.com'),
(64, 4, 1, 4, 'Chloe Chen', 'chloe.chen@example.com'),
(65, 5, 2, 5, 'Alexander Kim', 'alexander.kim@example.com'),
(66, 6, 3, 1, 'Ava Lee', 'ava.lee@example.com'),
(67, 7, 4, 2, 'William Davis', 'william.davis@example.com'),
(68, 8, 5, 3, 'Sophia Chen', 'sophia.chen@example.com'),
(69, 9, 6, 4, 'Daniel Kim', 'daniel.kim@example.com'),
(70, 10, 7, 5, 'Mia Lee', 'mia.lee@example.com'),
(71, 1, 8, 1, 'Matthew Davis', 'matthew.davis@example.com'),
(72, 2, 9, 2, 'Olivia Wilson', 'olivia.wilson@example.com'),
(73, 3, 1, 3, 'Ethan Brown', 'ethan.brown@example.com'),
(74, 4, 2, 4, 'Isabella Chen', 'isabella.chen@example.com'),
(75, 5, 3, 5, 'Benjamin Kim', 'benjamin.kim@example.com'),
(76, 6, 4, 1, 'Victoria Lee', 'victoria.lee@example.com'),
(77, 7, 5, 2, 'Lucas Davis', 'lucas.davis@example.com'),
(78, 8, 6, 3, 'Chloe Wilson', 'chloe.wilson@example.com'),
(79, 9, 7, 4, 'Alexander Lee', 'alexander.lee@example.com'),
(80, 10, 8, 5, 'Ava Chen', 'ava.chen@example');

--
-- 已傾印資料表的索引
--

--
-- 資料表索引 `guest`
--
ALTER TABLE `guest`
  ADD PRIMARY KEY (`id`),
  ADD KEY `userId` (`userId`);

--
-- 在傾印的資料表使用自動遞增(AUTO_INCREMENT)
--

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `guest`
--
ALTER TABLE `guest`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=81;

--
-- 已傾印資料表的限制式
--

--
-- 資料表的限制式 `guest`
--
ALTER TABLE `guest`
  ADD CONSTRAINT `guest_ibfk_1` FOREIGN KEY (`bookingId`) REFERENCES `booking` (`id`),
  ADD CONSTRAINT `guest_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `guest_ibfk_3` FOREIGN KEY (`venueId`) REFERENCES `venue` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
