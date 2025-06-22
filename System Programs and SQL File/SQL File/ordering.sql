-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 07, 2020 at 10:33 AM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.4.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ordering`
--

-- --------------------------------------------------------

--
-- Table structure for table `activity`
--

CREATE TABLE `activity` (
  `ACTV_ID` int(11) NOT NULL,
  `REF_Code` varchar(30) NOT NULL,
  `ITEM_Code` varchar(30) NOT NULL,
  `QUANTITY` int(11) NOT NULL,
  `ACTIVITY` varchar(50) NOT NULL,
  `DATE` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `Remarks` varchar(30) DEFAULT 'None'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `activity`
--

INSERT INTO `activity` (`ACTV_ID`, `REF_Code`, `ITEM_Code`, `QUANTITY`, `ACTIVITY`, `DATE`, `Remarks`) VALUES
(1, '', 'WOCSQD', 10, 'Deducted', '2020-05-05 17:37:58', 'Short'),
(2, '001', 'BOTT350', 20, 'Restock', '2020-05-07 07:14:33', 'None'),
(5, '002', 'WOCSQD', 20, 'Restock', '2020-05-07 07:14:57', 'None'),
(6, '003', 'BOTT350', 10, 'Restock', '2020-05-07 07:15:29', 'None'),
(7, '004', 'CND', 10, 'Restock', '2020-05-07 07:16:02', 'None'),
(8, '005', 'CND', 10, 'Restock', '2020-05-07 07:16:08', 'None'),
(9, '006', 'CUPS350', 4, 'Restock', '2020-05-07 07:16:22', 'None'),
(10, '007', 'CUPS500', 10, 'Restock', '2020-05-07 07:16:27', 'None'),
(11, '008', 'CUPS700', 20, 'Restock', '2020-05-07 07:16:49', 'None'),
(12, '', 'BOTT350', 2, 'Deducted', '2020-05-05 11:34:52', 'Short'),
(13, '', 'BOTT350', 1, 'Deducted', '2020-05-05 11:34:21', 'Short'),
(17, '', 'CND', 1, 'Deducted', '2020-05-05 12:27:01', 'Short'),
(18, '', 'CND', 1, 'Deducted', '2020-05-05 12:28:02', 'Short'),
(19, '', 'BOTT350', 1, 'Deducted', '2020-05-05 12:30:16', 'Short'),
(21, '', 'CUPS700', 100, 'Deducted', '2020-05-05 13:07:29', 'Short'),
(22, '', 'CUPS350', 71, 'Deducted', '2020-05-05 13:23:24', 'Short'),
(23, '', 'WOCSQD', 109, 'Deducted', '2020-05-05 13:23:42', 'Short'),
(25, '009', 'WOCSQD', 100, 'Restock', '2020-05-07 07:17:15', 'None'),
(26, '', 'WOCSOL', 96, 'Deducted', '2020-05-05 13:53:18', 'Short'),
(27, '010', 'WOCSOL', 100, 'Restock', '2020-05-07 07:17:22', 'None'),
(28, '', 'FRSOL', 5, 'Deducted', '2020-05-05 13:54:17', 'Short'),
(31, '011', 'BOTT350', 1, 'Restock', '2020-05-07 07:18:43', 'None'),
(32, '012', 'BOTT350', 1, 'Restock', '2020-05-07 07:18:46', 'None'),
(33, '013', 'BOTT350', 9, 'Restock', '2020-05-07 07:18:51', 'None'),
(34, '014', 'CUPS350', 51, 'Restock', '2020-05-07 07:18:54', 'None'),
(35, '015', 'CUPS700', 50, 'Restock', '2020-05-07 07:22:42', 'None'),
(36, '', 'BOTT350', 3, 'Deducted', '2020-05-05 17:37:01', 'Short'),
(37, '', 'CUPS700', 1, 'Deducted', '2020-05-05 21:25:20', 'Short'),
(38, '016', 'CUPS350', 100, 'Restock', '2020-05-07 07:22:47', 'None'),
(39, '', 'CUPS350', 89, 'Deducted', '2020-05-05 21:33:34', 'Short'),
(40, '017', 'CUPS700', 30, 'Restock', '2020-05-07 07:22:50', 'None'),
(41, '018', 'CUPS350', 89, 'Restock', '2020-05-07 07:22:53', 'None'),
(42, '', 'BOTT350', 1, 'Deducted', '2020-05-06 08:04:04', 'Short'),
(43, '', 'FRSOL', 1, 'Deducted', '2020-05-06 08:04:45', 'Short'),
(44, '019', 'FRSOL', 6, 'Restock', '2020-05-07 07:22:59', 'None'),
(45, '020', 'BOTT350', 100, 'Restock', '2020-05-07 07:23:02', 'None'),
(46, '021', 'CUPS700', 12, 'Restock', '2020-05-07 07:23:07', 'None'),
(47, '022', 'WOCSQD', 6, 'Restock', '2020-05-07 07:24:04', 'None'),
(48, '', 'CUPS500', 2, 'Deducted', '2020-05-06 21:34:08', 'Short'),
(49, '', 'FRFRIEND', 1, 'Deducted', '2020-05-06 21:37:12', 'Short'),
(50, '', 'FRFRIEND', 1, 'Deducted', '2020-05-06 21:57:10', 'Short'),
(51, '023', 'FRFRIEND', 1, 'Restock', '2020-05-07 07:24:14', 'None'),
(52, '024', 'CUPS500', 2, 'Restock', '2020-05-07 07:24:18', 'None'),
(53, '025', 'WOCSQD', 2, 'Restock', '2020-05-07 07:24:22', 'None'),
(54, '', 'CUPS700', 93, 'Deducted', '2020-05-07 05:26:37', 'Short'),
(55, '026', 'CUPS700', 2, 'Restock', '2020-05-07 07:34:26', 'None');

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `Record_num` int(11) NOT NULL,
  `Customer` varchar(30) NOT NULL DEFAULT 'John Doe',
  `Qty` int(11) NOT NULL DEFAULT 0,
  `Prod_Code` varchar(20) NOT NULL,
  `Item_Code` varchar(30) NOT NULL,
  `Prod_Name` varchar(30) NOT NULL,
  `Date` varchar(20) NOT NULL,
  `Amt` float NOT NULL DEFAULT 0,
  `Discount` varchar(20) NOT NULL DEFAULT 'none',
  `Instructions` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `items`
--

CREATE TABLE `items` (
  `Item_Codes` varchar(30) NOT NULL,
  `Qty` int(20) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `items`
--

INSERT INTO `items` (`Item_Codes`, `Qty`) VALUES
('BOTT350', 0),
('CND', 0),
('CUPS350', 0),
('CUPS500', 0),
('CUPS700', 2),
('FRFRIEND', 0),
('FRSOL', 0),
('WOCSOL', 0),
('WOCSQD', 2);

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `Record_no` int(11) NOT NULL,
  `Customer_Name` varchar(30) NOT NULL,
  `Quantity` int(11) NOT NULL DEFAULT 0,
  `Product_code` varchar(20) NOT NULL,
  `Item_CodeNo` varchar(30) NOT NULL,
  `Product_Name` varchar(30) NOT NULL,
  `Date_of_Order` varchar(20) NOT NULL,
  `Amount` float NOT NULL DEFAULT 0,
  `Applied_Discount` varchar(20) NOT NULL,
  `Special_Instructions` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`Record_no`, `Customer_Name`, `Quantity`, `Product_code`, `Item_CodeNo`, `Product_Name`, `Date_of_Order`, `Amount`, `Applied_Discount`, `Special_Instructions`) VALUES
(416, 'Irene Bae', 2, 'MAXIVELVET700', 'CUPS700', 'Red Velvet (700 ml)', '06/05/2020', 228, 'Senior Citizen', 'Less Sugar'),
(417, 'Irene Bae', 2, 'SNACKWOCLSQAD', 'WOCSQD', 'Wing O’ Clock (Squad)', '06/05/2020', 598, 'Senior Citizen', 'More Sauce');

-- --------------------------------------------------------

--
-- Table structure for table `supplies`
--

CREATE TABLE `supplies` (
  `SUPPLIES_ID` int(30) NOT NULL,
  `ITEM_CODE` varchar(20) NOT NULL,
  `ITEM_NAME` varchar(40) NOT NULL,
  `ITEM_QTY` int(11) NOT NULL,
  `ITEM_STATUS` varchar(30) NOT NULL,
  `ITEM_CATEGORY` varchar(20) NOT NULL,
  `STOCK_LEVEL` int(11) NOT NULL,
  `DATE_ADDED` timestamp NOT NULL DEFAULT current_timestamp(),
  `DATE_UPDATED` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `supplies`
--

INSERT INTO `supplies` (`SUPPLIES_ID`, `ITEM_CODE`, `ITEM_NAME`, `ITEM_QTY`, `ITEM_STATUS`, `ITEM_CATEGORY`, `STOCK_LEVEL`, `DATE_ADDED`, `DATE_UPDATED`) VALUES
(1, 'BOTT350', 'Bottle 350ml', 100, 'In Stock', 'Drinks', 30, '2020-04-27 13:40:42', '2020-05-06 16:09:29'),
(2, 'CND', 'Chic n Dip', 100, 'In Stock', 'Snacks', 30, '2020-04-27 14:26:22', '2020-04-29 10:43:14'),
(3, 'CUPS350', 'Cups 350 ml', 100, 'In Stock', 'Drinks', 30, '2020-04-27 14:28:02', '2020-05-06 08:03:24'),
(4, 'CUPS500', 'Cups 500 ml', 100, 'In Stock', 'Drinks', 30, '2020-04-27 14:28:02', '2020-05-06 22:04:05'),
(5, 'CUPS700', 'Cups 700 ml', 100, 'In Stock', 'Drinks', 30, '2020-04-27 14:29:15', '2020-05-07 07:34:27'),
(6, 'FRFRIEND', 'Fries (Friends)', 100, 'In Stock', 'Snacks', 30, '2020-04-27 14:29:15', '2020-05-06 22:02:23'),
(7, 'FRSOL', 'Fries (Solo)', 100, 'In Stock', 'Snacks', 30, '2020-04-20 14:30:14', '2020-05-06 08:05:08'),
(8, 'WOCSOL', 'WING o Clock (Solo)', 100, 'In Stock', 'Snacks', 30, '2020-04-20 16:00:00', '2020-05-05 13:53:44'),
(9, 'WOCSQD', 'WING o Clock (Squad)', 100, 'In Stock', 'Snacks', 30, '2020-04-27 14:30:35', '2020-05-06 22:07:24'),
(22, 'PB', 'Plastic Bags for Drinks', 100, 'In Stock', 'Drinks', 30, '2020-05-06 20:42:19', '2020-05-06 20:42:19');

-- --------------------------------------------------------

--
-- Table structure for table `verify`
--

CREATE TABLE `verify` (
  `COMPANY_ROLE` varchar(30) NOT NULL,
  `PASSWORD` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `verify`
--

INSERT INTO `verify` (`COMPANY_ROLE`, `PASSWORD`) VALUES
('Staff', 'ilovesuntea'),
('Manager', 'ilovesunteaph');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `activity`
--
ALTER TABLE `activity`
  ADD PRIMARY KEY (`ACTV_ID`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`Record_num`);

--
-- Indexes for table `items`
--
ALTER TABLE `items`
  ADD PRIMARY KEY (`Item_Codes`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`Record_no`);

--
-- Indexes for table `supplies`
--
ALTER TABLE `supplies`
  ADD PRIMARY KEY (`SUPPLIES_ID`);

--
-- Indexes for table `verify`
--
ALTER TABLE `verify`
  ADD PRIMARY KEY (`PASSWORD`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `activity`
--
ALTER TABLE `activity`
  MODIFY `ACTV_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=56;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `Record_no` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=418;

--
-- AUTO_INCREMENT for table `supplies`
--
ALTER TABLE `supplies`
  MODIFY `SUPPLIES_ID` int(30) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
