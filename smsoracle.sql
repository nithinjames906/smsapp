-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 28, 2021 at 07:16 PM
-- Server version: 10.4.6-MariaDB
-- PHP Version: 7.1.32

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `smsoracle`
--

-- --------------------------------------------------------

--
-- Table structure for table `question`
--

CREATE TABLE `question` (
  `id` bigint(20) NOT NULL,
  `body` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `multi_select` bit(1) DEFAULT NULL,
  `options` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `survey_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `question`
--

INSERT INTO `question` (`id`, `body`, `created_date`, `multi_select`, `options`, `type`, `survey_id`) VALUES
(15, 'Enter EmpId', NULL, b'0', '', 'FREETEXT', 4),
(16, 'Enter Designation', NULL, b'0', '', 'FREETEXT', 4),
(17, 'Is it a great place to work?', NULL, b'0', 'Disagree,Strongly Disagree,Agree,Strongly Agree,Neutral', 'CHOICE', 4),
(21, 'Enter EmpId', NULL, b'0', '', 'FREETEXT', 5),
(22, 'Enter Designation', NULL, b'0', '', 'FREETEXT', 5),
(23, 'Is it a great place to work?', NULL, b'1', 'Disagree,Strongly Disagree,Agree,Strongly Agree,Neutral', 'CHOICE', 5),
(24, 'Are you satisfied with the remuneration?', NULL, b'0', 'Yes,No', 'CHOICE', 5),
(25, 'Enter EmpId', NULL, b'0', '', 'FREETEXT', 6),
(26, 'Enter Designation', NULL, b'0', '', 'FREETEXT', 6),
(27, 'Is it a great place to work?', NULL, b'0', 'Disagree,Strongly Disagree,Agree,Strongly Agree,Neutral', 'CHOICE', 6),
(28, 'Are you satisfied with the remuneration?', NULL, b'0', 'Yes,No', 'CHOICE', 6),
(29, 'What are the pros', NULL, b'0', '', 'FREETEXT', 7),
(30, 'What are the cons', NULL, b'0', '', 'FREETEXT', 7),
(31, 'Are you satisfied?', NULL, b'0', 'Yes,No', 'CHOICE', 7),
(32, 'Select colors needed', NULL, b'0', 'Red,Green,Yellow,Blue', 'CHOICE', 7),
(33, 'What are the pros', NULL, b'0', '', 'FREETEXT', 8),
(34, 'What are the cons', NULL, b'0', '', 'FREETEXT', 8),
(35, 'Are you satisfied?', NULL, b'0', 'Yes,No', 'CHOICE', 8),
(36, 'Select colors needed', NULL, b'1', 'Red,Green,Yellow,Blue', 'CHOICE', 8),
(37, 'What are the pros', NULL, b'0', '', 'FREETEXT', 9),
(38, 'What are the cons', NULL, b'0', '', 'FREETEXT', 9),
(39, 'Are you satisfied?', NULL, b'0', 'Yes,No', 'CHOICE', 9),
(40, 'Select colors needed', NULL, b'1', 'Red,Green,Yellow,Blue', 'CHOICE', 9),
(41, 'Test Question', NULL, b'0', '', 'FREETEXT', 10),
(42, 'What are the pros', NULL, b'0', '', 'FREETEXT', 11),
(43, 'What are the cons', NULL, b'0', '', 'FREETEXT', 11),
(44, 'Are you satisfied?', NULL, b'0', 'Yes,No', 'CHOICE', 11),
(45, 'Select colors needed?', NULL, b'1', 'Red,Green,Yellow,Blue', 'CHOICE', 11);

-- --------------------------------------------------------

--
-- Table structure for table `smsuser`
--

CREATE TABLE `smsuser` (
  `id` bigint(20) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  `user_role` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `smsuser`
--

INSERT INTO `smsuser` (`id`, `user_name`, `user_role`, `password`) VALUES
(9, 'Nithin', 'ADMIN', '202cb962ac59075b964b07152d234b70'),
(10, 'Soniya', 'CUSTOMER', '202cb962ac59075b964b07152d234b70'),
(11, 'Midhun', 'ANALYTIC', '202cb962ac59075b964b07152d234b70');

-- --------------------------------------------------------

--
-- Table structure for table `survey`
--

CREATE TABLE `survey` (
  `id` bigint(20) NOT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `parent_survey_id` bigint(20) DEFAULT NULL,
  `root_survey_id` bigint(20) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `updated_date` datetime DEFAULT NULL,
  `version` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `survey`
--

INSERT INTO `survey` (`id`, `created_by`, `created_date`, `is_active`, `parent_survey_id`, `root_survey_id`, `status`, `title`, `updated_date`, `version`) VALUES
(4, 1, '2021-05-24 13:25:05', b'0', NULL, NULL, 'PUBLISHED', 'GPR Survey New', '2021-05-24 13:33:49', 1),
(5, 1, '2021-05-24 13:25:05', b'0', 4, 4, 'PUBLISHED', 'GPR Survey Edited', '2021-05-24 15:10:27', 2),
(6, 1, '2021-05-24 13:25:05', b'1', 5, 4, 'PUBLISHED', 'GPR Survey Edited', '2021-05-24 15:38:29', 3),
(7, 1, '2021-05-25 13:03:09', b'0', NULL, NULL, 'PUBLISHED', 'Emplyee Engagement Survey', NULL, 1),
(8, 1, '2021-05-25 13:03:09', b'0', 7, 7, 'PUBLISHED', 'Emplyee Engagement Survey', '2021-05-25 13:04:10', 2),
(9, 1, '2021-05-25 13:03:09', b'0', 8, 7, 'PUBLISHED', 'Employee Engagement Survey', '2021-05-25 13:04:52', 3),
(10, 1, '2021-05-26 19:29:22', b'1', NULL, NULL, 'PUBLISHED', 'New Survey', NULL, 1),
(11, 1, '2021-05-25 13:03:09', b'1', 9, 8, 'PUBLISHED', 'Employee Engagement Survey', '2021-05-26 19:34:05', 4);

-- --------------------------------------------------------

--
-- Table structure for table `user_survey`
--

CREATE TABLE `user_survey` (
  `survey_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `status` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `user_survey_response`
--

CREATE TABLE `user_survey_response` (
  `id` bigint(20) NOT NULL,
  `question_id` bigint(20) DEFAULT NULL,
  `response` varchar(255) DEFAULT NULL,
  `survey_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `question`
--
ALTER TABLE `question`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK65ro96jafjvulbqu8ia0pb254` (`survey_id`);

--
-- Indexes for table `smsuser`
--
ALTER TABLE `smsuser`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `survey`
--
ALTER TABLE `survey`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_survey`
--
ALTER TABLE `user_survey`
  ADD PRIMARY KEY (`survey_id`,`user_id`);

--
-- Indexes for table `user_survey_response`
--
ALTER TABLE `user_survey_response`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK643m0ybog07bjfwiriwcgan7l` (`survey_id`,`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `question`
--
ALTER TABLE `question`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=46;

--
-- AUTO_INCREMENT for table `smsuser`
--
ALTER TABLE `smsuser`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `survey`
--
ALTER TABLE `survey`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `user_survey_response`
--
ALTER TABLE `user_survey_response`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=107;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `question`
--
ALTER TABLE `question`
  ADD CONSTRAINT `FK65ro96jafjvulbqu8ia0pb254` FOREIGN KEY (`survey_id`) REFERENCES `survey` (`id`);

--
-- Constraints for table `user_survey_response`
--
ALTER TABLE `user_survey_response`
  ADD CONSTRAINT `FK643m0ybog07bjfwiriwcgan7l` FOREIGN KEY (`survey_id`,`user_id`) REFERENCES `user_survey` (`survey_id`, `user_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
