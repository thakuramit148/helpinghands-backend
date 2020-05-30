-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 29, 2020 at 06:09 PM
-- Server version: 10.3.16-MariaDB
-- PHP Version: 7.3.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `helping_hand`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `username`, `password`) VALUES
(1, 'admin123', '123123'),
(5, 'admin2', '111111'),
(9, 'admin12', '123123');

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `employee` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `full_name` varchar(100) NOT NULL,
  `address` varchar(300) NOT NULL,
  `email` varchar(200) NOT NULL,
  `phone` varchar(10) NOT NULL,
  `is_active` bit(1) NOT NULL,
  `org_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`id`, `username`, `password`, `full_name`, `address`, `email`, `phone`, `is_active`, `org_id`) VALUES
(1, 'emp101', '123123', 'ramesh kalal', 'new city - park shop<>Gujarat--Navsari', 'emp101@gmail.com', '9876547897', b'1', 1),
(27, 'emp102', '123123', 'emp two', 'askdj jkasdj adsdah d<>Gujarat--Ahmedabad', 'emp102@gmail.com', '1231231231', b'1', 1),
(31, 'emp103', '123123', 'asdha aasd', 'sdfj skjfnsk dsdf sf s<>Gujarat--Ahmedabad', 'asd@adsd.sads', '1231231230', b'0', 1),
(32, 'emp104', '123123', 'amiasda', '2sdfhsuis iusdfui dfg<>Gujarat--Surat', 'asd@dfsad.com', '9876543210', b'0', 1),
(34, 'emp301', '123123', 'kamlesh bhai', 'party park, new town<>Gujarat--Surat', 'kamlesh@ad.com', '9879876548', b'1', 3),
(37, 'emp105', '123123', 'asdhk dn', 'asdhuak aksjd asjdasd a<>Gujarat--Surat', 'sfds@sf.sfd', '1231231238', b'0', 1),
(38, 'skdfhks df', '123123123s.nfsklnd f', 'slkdnfksdf', 'dkfnsdkjfnskjnf sfn ksjfd df<>Uttar Pradesh--Agra', 'dkfh@sfj.sdf', '1231231272', b'0', 1),
(39, 'emp111', '123123', 'emporg', 'ad ajdk aksjd ka jdka sd<>Chandigarh (UT)--Chandigarh', 'emp@asda.com', '9879878878', b'1', 23),
(40, 'emp101org103', '123123', 'kamlesh bhai', 'navsari highway, grid road<>Gujarat--Navsari', 'kamlesh@gmail.com', '9879879877', b'1', 3);

-- --------------------------------------------------------

--
-- Table structure for table `employee_pickup`
--

CREATE TABLE `employee_pickup` (
  `id` int(11) NOT NULL,
  `emp_id` int(11) NOT NULL,
  `donation_id` int(11) NOT NULL,
  `status` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employee_pickup`
--

INSERT INTO `employee_pickup` (`id`, `emp_id`, `donation_id`, `status`) VALUES
(15, 1, 15, 'Dropped'),
(16, 1, 19, 'In progress'),
(17, 1, 15, 'Dropped'),
(18, 1, 15, 'Completed'),
(19, 39, 21, 'Dropped'),
(20, 39, 21, 'Completed'),
(21, 39, 22, 'Dropped'),
(22, 39, 22, 'Completed'),
(23, 34, 23, 'Dropped'),
(24, 34, 23, 'Completed'),
(25, 1, 24, 'Dropped'),
(26, 1, 24, 'Completed');

-- --------------------------------------------------------

--
-- Table structure for table `event`
--

CREATE TABLE `event` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `start` date NOT NULL,
  `end` date NOT NULL,
  `description` varchar(300) NOT NULL,
  `org_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `event`
--

INSERT INTO `event` (`id`, `name`, `start`, `end`, `description`, `org_id`) VALUES
(1, 'planting day', '2020-05-01', '2020-05-02', 'planting trees in the urban areas with the locals', 1),
(8, 'asdadasd', '2020-05-28', '2020-05-31', 'asdasd ada dasd', 1),
(9, 'ad asda', '2020-05-28', '2020-05-30', 'ad ad asd asd asd asdasd asd asdad asd ', 1),
(10, 'adkfuiadun', '2020-05-28', '2020-05-29', 'a kdjn kajf kaj fkja fkjadf', 23),
(12, 'farmer event', '2020-05-30', '2020-05-31', 'local farmers get together', 3),
(13, 'food giveaway', '2020-06-01', '2020-06-04', 'giving donated food to the local poor peoples', 1);

-- --------------------------------------------------------

--
-- Table structure for table `organization`
--

CREATE TABLE `organization` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `full_name` varchar(100) NOT NULL,
  `email` varchar(200) NOT NULL,
  `phone` varchar(10) NOT NULL,
  `description` varchar(300) NOT NULL,
  `is_active` bit(1) NOT NULL,
  `is_verified` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `organization`
--

INSERT INTO `organization` (`id`, `username`, `password`, `full_name`, `email`, `phone`, `description`, `is_active`, `is_verified`) VALUES
(1, 'org101', '123123', 'Childcare Ngo', 'org101@gmail.com', '9638313428', 'we work for the poor children who are unfortunate to even receive food, our goal is to help as many children as we can help. join us in our mission.', b'1', b'1'),
(3, 'org103', '123123', 'Kisan care Ngo', 'org103@gmail.com', '9638313400', 'Farmer in india are the hardest working people, yet they don\'t get enough income for their hardwork, and their families are struggling every day. we want to help them by providing anything that can make their day better', b'1', b'1'),
(9, 'org104', '123123', 'BharatShala', 'org104@gmail.com', '1231231231', 'our goal is to provide education to every children in india. whatever it takes, we are willing to do every thing  ', b'1', b'1'),
(11, 'org105', '123123', 'Medi care Ngo', 'org105@gmail.com', '9638313333', 'In India, many poor people die, because they can\'t afford the expensive medicines, we want to help such peoples and provide them with cheaper medicines ', b'1', b'1'),
(14, 'org106', '123123', 'Plant India Ngo  ', 'org106@gmail.com', '8838313428', 'we care about the your mother earth. join us, in saving her. we plant about 100+ plants across the city in a week.', b'1', b'1'),
(20, 'org107', '123123', 'Garib seva', 'org107@gmail.com', '1231231230', 'India has huge ratio difference between poor and rich people, we are work on reducing that ratio. we provide food, clothes, shelter to poor people and we even give them a reasonable job. So they can feed the family. help us by donating what you can.\r\n', b'1', b'1'),
(23, 'org102', '123123', 'Just smile fund', 'org102@gmail.com', '1312312312', 'there is no better job than spreading happiness, a simple help to a needy person and seeing SMILE on that persons face makes our day. we experience this every day, join us and spread smile everywhere and make this place a better place. ', b'1', b'1'),
(24, 'org111', '123123', 'Lifeline ngo', 'lifeline@gmail.com', '8732412342', 'we aim to provide cheapest priced medicines in india', b'1', b'1');

-- --------------------------------------------------------

--
-- Table structure for table `org_address`
--

CREATE TABLE `org_address` (
  `id` int(11) NOT NULL,
  `org_id` int(11) NOT NULL,
  `area` varchar(100) NOT NULL,
  `city` varchar(50) NOT NULL,
  `state` varchar(50) NOT NULL,
  `is_active` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `org_address`
--

INSERT INTO `org_address` (`id`, `org_id`, `area`, `city`, `state`, `is_active`) VALUES
(1, 1, 'shayam vatika, parvat patiya', 'Surat', 'Gujarat', b'1'),
(13, 3, 'parth tower, dudhiya talab, station ', 'Navsari', 'Gujarat', b'1'),
(14, 23, 'tirupati temple, akhar chowk', 'Barpeta', 'Assam', b'1'),
(15, 23, 'lkjndlfkg dlglskg ls kfgs', 'West Kameng', 'Arunachal Pradesh', b'1'),
(16, 3, 'parvat patiya, magob', 'Surat', 'Gujarat', b'1'),
(17, 1, 'station road, near unai', 'Navsari', 'Gujarat', b'1');

-- --------------------------------------------------------

--
-- Table structure for table `org_category`
--

CREATE TABLE `org_category` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `org_category`
--

INSERT INTO `org_category` (`id`, `name`) VALUES
(1, 'Food'),
(2, 'Clothes'),
(3, 'Medicine'),
(4, 'Books'),
(5, 'Agriculture'),
(6, 'Other');

-- --------------------------------------------------------

--
-- Table structure for table `org_category_reference`
--

CREATE TABLE `org_category_reference` (
  `id` int(11) NOT NULL,
  `org_category_id` int(11) NOT NULL,
  `org_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `org_category_reference`
--

INSERT INTO `org_category_reference` (`id`, `org_category_id`, `org_id`) VALUES
(1, 1, 1),
(2, 3, 1),
(5, 4, 1),
(7, 5, 3),
(15, 5, 1),
(16, 6, 1),
(17, 2, 1),
(18, 1, 23),
(19, 2, 23),
(20, 6, 23),
(21, 6, 3),
(22, 4, 23),
(23, 4, 3);

-- --------------------------------------------------------

--
-- Table structure for table `review`
--

CREATE TABLE `review` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `org_id` int(11) NOT NULL,
  `ratings` decimal(10,1) NOT NULL,
  `review` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `review`
--

INSERT INTO `review` (`id`, `user_id`, `org_id`, `ratings`, `review`) VALUES
(1, 1, 1, '9.0', 'great ngo'),
(2, 2, 3, '9.0', 'best Ngo'),
(3, 51, 1, '8.0', 'best Ngo');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `full_name` varchar(100) NOT NULL,
  `address` varchar(300) NOT NULL,
  `email` varchar(200) NOT NULL,
  `phone` varchar(10) NOT NULL,
  `is_active` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `password`, `full_name`, `address`, `email`, `phone`, `is_active`) VALUES
(1, 'amit111', '111111', 'hakimsingh', '223surat<>Arunachal Pradesh--East Kameng', 'asd@gmail.com', '1238313428', b'1'),
(2, 'amit123', '123123', 'thakur amit hakimsingh', '28/201 priyanka intercity<>Gujarat--Valsad', 'thakuramit148@gmail.com', '9638313428', b'1'),
(37, 'amit1231', '123123', 'asdadas', 'adf asdasd 22 sds<>Arunachal Pradesh--East Kameng', 'as2SD2sf@gdfg', '1231123123', b'1'),
(46, 'amit12311', '123123', 'sdkfjs shq', 'adda asd a1sd<>Arunachal Pradesh--East Kameng', 'th@nsd.coa', '1231231231', b'1'),
(47, 'john wick', '123123', 'legendary john wick', 'magob - parvat patiya<>Gujarat--Surat', 'johnwick148@gmail.com', '9191231921', b'1'),
(51, 'john123', '123123', 'legendary john wick', 'magob - parvat patiya<>Arunachal Pradesh--East Kameng', 'john123@gmail.com', '9191441920', b'1'),
(52, 'vicky123', '111111', 'vicky shah', 'station road, unai, vansda<>Gujarat--Navsari', 'vicky@gmail.com', '8978998878', b'1'),
(53, 'asdadsas', '123123', 'asdad ada', 'a kjfak jdkaj dkj asdk jasd<>Assam--Barpeta', 'ada@dfsdf.com', '9834441231', b'1');

-- --------------------------------------------------------

--
-- Table structure for table `user_donation`
--

CREATE TABLE `user_donation` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `org_id` int(11) NOT NULL,
  `drop_type` varchar(10) NOT NULL,
  `donation_date` date NOT NULL,
  `donation_received_date` date DEFAULT NULL,
  `status` varchar(20) NOT NULL,
  `description` varchar(300) NOT NULL,
  `is_donated` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_donation`
--

INSERT INTO `user_donation` (`id`, `user_id`, `org_id`, `drop_type`, `donation_date`, `donation_received_date`, `status`, `description`, `is_donated`) VALUES
(15, 2, 1, 'Pickup', '2020-05-25', '2020-05-26', 'Completed', 'I have some old toys in good condition, and 10kg of rice. hope it helps', b'0'),
(16, 1, 1, 'Pickup', '2020-05-25', '2020-05-29', 'Completed', 'Giving 12 coloring books, and some comic books as well ', b'0'),
(17, 2, 3, 'Self', '2020-05-25', '2020-05-27', 'Completed', '12 new pair of football shoes (size 8)', b'0'),
(18, 47, 1, 'Self', '2020-05-26', NULL, 'Completed', 'Giving 10 size 12 t-Shirts ', b'0'),
(19, 47, 1, 'Pickup', '2020-05-26', '2020-05-27', 'Completed', '20 M20 Surgical masks', b'0'),
(20, 1, 23, 'Pickup', '2020-05-28', NULL, 'Canceled', 'a football, and a pair of football shoes', b'0'),
(21, 1, 23, 'Pickup', '2020-05-28', '2020-05-28', 'Completed', 'new pair of cricket outfit size18, 1kg of almonds', b'0'),
(22, 2, 23, 'Pickup', '2020-05-28', '2020-05-28', 'Completed', 'adna dman dmna s,dm as,da', b'0'),
(23, 52, 3, 'Pickup', '2020-05-29', '2020-05-29', 'Completed', '20kg of sunflower seeds', b'0'),
(24, 52, 1, 'Pickup', '2020-05-29', '2020-05-29', 'Completed', '20 kg of rice and 5 kg of wheat, hope it helps', b'0'),
(25, 52, 1, 'Self', '2020-05-29', '2020-05-29', 'Completed', '2 kg of sunflower seeds', b'0'),
(26, 52, 3, 'Pickup', '2020-05-29', '2020-05-29', 'Completed', '2 liter pesticides for rice farming', b'0');

-- --------------------------------------------------------

--
-- Table structure for table `user_donation_category`
--

CREATE TABLE `user_donation_category` (
  `id` int(11) NOT NULL,
  `donation_id` int(11) NOT NULL,
  `donation_category_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_donation_category`
--

INSERT INTO `user_donation_category` (`id`, `donation_id`, `donation_category_id`) VALUES
(27, 15, 1),
(28, 15, 6),
(29, 16, 4),
(30, 17, 6),
(31, 18, 2),
(32, 19, 3),
(33, 20, 2),
(34, 20, 6),
(35, 21, 1),
(36, 21, 2),
(37, 22, 6),
(38, 23, 5),
(39, 24, 1),
(40, 25, 5),
(41, 26, 5);

-- --------------------------------------------------------

--
-- Table structure for table `volunteer`
--

CREATE TABLE `volunteer` (
  `id` int(11) NOT NULL,
  `org_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `is_approved` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `volunteer`
--

INSERT INTO `volunteer` (`id`, `org_id`, `user_id`, `is_approved`) VALUES
(1, 1, 1, 1),
(2, 1, 2, 1),
(3, 3, 1, 1),
(4, 23, 1, 1),
(5, 23, 2, 1),
(6, 3, 52, 1),
(7, 1, 52, 1),
(8, 9, 52, 1),
(9, 23, 52, 1),
(10, 3, 2, 1);

-- --------------------------------------------------------

--
-- Table structure for table `volunteer_pickup`
--

CREATE TABLE `volunteer_pickup` (
  `id` int(11) NOT NULL,
  `vol_id` int(11) NOT NULL,
  `donation_id` int(11) NOT NULL,
  `status` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `volunteer_pickup`
--

INSERT INTO `volunteer_pickup` (`id`, `vol_id`, `donation_id`, `status`) VALUES
(1, 1, 19, 'Dropped'),
(2, 1, 19, 'Completed'),
(3, 5, 21, 'Dropped'),
(4, 7, 16, 'Dropped'),
(5, 7, 16, 'Completed'),
(6, 10, 26, 'Dropped'),
(7, 10, 26, 'Completed');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `phone` (`phone`),
  ADD KEY `org_id` (`org_id`);

--
-- Indexes for table `employee_pickup`
--
ALTER TABLE `employee_pickup`
  ADD PRIMARY KEY (`id`),
  ADD KEY `donation_id` (`donation_id`),
  ADD KEY `emp_id` (`emp_id`);

--
-- Indexes for table `event`
--
ALTER TABLE `event`
  ADD PRIMARY KEY (`id`),
  ADD KEY `org_id` (`org_id`);

--
-- Indexes for table `organization`
--
ALTER TABLE `organization`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `phone` (`phone`);

--
-- Indexes for table `org_address`
--
ALTER TABLE `org_address`
  ADD PRIMARY KEY (`id`),
  ADD KEY `org_id` (`org_id`);

--
-- Indexes for table `org_category`
--
ALTER TABLE `org_category`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `org_category_reference`
--
ALTER TABLE `org_category_reference`
  ADD PRIMARY KEY (`id`),
  ADD KEY `org_category_id` (`org_category_id`),
  ADD KEY `org_id` (`org_id`);

--
-- Indexes for table `review`
--
ALTER TABLE `review`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `org_id` (`org_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `phone` (`phone`);

--
-- Indexes for table `user_donation`
--
ALTER TABLE `user_donation`
  ADD PRIMARY KEY (`id`),
  ADD KEY `org_id` (`org_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `user_donation_category`
--
ALTER TABLE `user_donation_category`
  ADD PRIMARY KEY (`id`),
  ADD KEY `donation_id` (`donation_id`),
  ADD KEY `donation_category_id` (`donation_category_id`);

--
-- Indexes for table `volunteer`
--
ALTER TABLE `volunteer`
  ADD PRIMARY KEY (`id`),
  ADD KEY `org_id` (`org_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `volunteer_pickup`
--
ALTER TABLE `volunteer_pickup`
  ADD PRIMARY KEY (`id`),
  ADD KEY `donation_id` (`donation_id`),
  ADD KEY `vol_id` (`vol_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `employee`
--
ALTER TABLE `employee`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- AUTO_INCREMENT for table `employee_pickup`
--
ALTER TABLE `employee_pickup`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT for table `event`
--
ALTER TABLE `event`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `organization`
--
ALTER TABLE `organization`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT for table `org_address`
--
ALTER TABLE `org_address`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `org_category`
--
ALTER TABLE `org_category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `org_category_reference`
--
ALTER TABLE `org_category_reference`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT for table `review`
--
ALTER TABLE `review`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=54;

--
-- AUTO_INCREMENT for table `user_donation`
--
ALTER TABLE `user_donation`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT for table `user_donation_category`
--
ALTER TABLE `user_donation_category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=42;

--
-- AUTO_INCREMENT for table `volunteer`
--
ALTER TABLE `volunteer`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `volunteer_pickup`
--
ALTER TABLE `volunteer_pickup`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `employee`
--
ALTER TABLE `employee`
  ADD CONSTRAINT `employee_ibfk_1` FOREIGN KEY (`org_id`) REFERENCES `organization` (`id`);

--
-- Constraints for table `employee_pickup`
--
ALTER TABLE `employee_pickup`
  ADD CONSTRAINT `employee_pickup_ibfk_1` FOREIGN KEY (`donation_id`) REFERENCES `user_donation` (`id`),
  ADD CONSTRAINT `employee_pickup_ibfk_2` FOREIGN KEY (`emp_id`) REFERENCES `employee` (`id`);

--
-- Constraints for table `event`
--
ALTER TABLE `event`
  ADD CONSTRAINT `event_ibfk_1` FOREIGN KEY (`org_id`) REFERENCES `organization` (`id`);

--
-- Constraints for table `org_address`
--
ALTER TABLE `org_address`
  ADD CONSTRAINT `org_address_ibfk_1` FOREIGN KEY (`org_id`) REFERENCES `organization` (`id`);

--
-- Constraints for table `org_category_reference`
--
ALTER TABLE `org_category_reference`
  ADD CONSTRAINT `org_category_reference_ibfk_1` FOREIGN KEY (`org_category_id`) REFERENCES `org_category` (`id`),
  ADD CONSTRAINT `org_category_reference_ibfk_2` FOREIGN KEY (`org_id`) REFERENCES `organization` (`id`);

--
-- Constraints for table `review`
--
ALTER TABLE `review`
  ADD CONSTRAINT `review_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `review_ibfk_2` FOREIGN KEY (`org_id`) REFERENCES `organization` (`id`);

--
-- Constraints for table `user_donation`
--
ALTER TABLE `user_donation`
  ADD CONSTRAINT `user_donation_ibfk_1` FOREIGN KEY (`org_id`) REFERENCES `organization` (`id`),
  ADD CONSTRAINT `user_donation_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Constraints for table `user_donation_category`
--
ALTER TABLE `user_donation_category`
  ADD CONSTRAINT `user_donation_category_ibfk_1` FOREIGN KEY (`donation_id`) REFERENCES `user_donation` (`id`),
  ADD CONSTRAINT `user_donation_category_ibfk_2` FOREIGN KEY (`donation_category_id`) REFERENCES `org_category` (`id`);

--
-- Constraints for table `volunteer`
--
ALTER TABLE `volunteer`
  ADD CONSTRAINT `volunteer_ibfk_1` FOREIGN KEY (`org_id`) REFERENCES `organization` (`id`),
  ADD CONSTRAINT `volunteer_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Constraints for table `volunteer_pickup`
--
ALTER TABLE `volunteer_pickup`
  ADD CONSTRAINT `volunteer_pickup_ibfk_1` FOREIGN KEY (`donation_id`) REFERENCES `user_donation` (`id`),
  ADD CONSTRAINT `volunteer_pickup_ibfk_2` FOREIGN KEY (`vol_id`) REFERENCES `volunteer` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
