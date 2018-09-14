/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50723
Source Host           : 127.0.0.1:3306
Source Database       : school

Target Server Type    : MYSQL
Target Server Version : 50723
File Encoding         : 65001

Date: 2018-09-10 22:19:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `supplement_info`
-- ----------------------------
DROP TABLE IF EXISTS `supplement_info`;
CREATE TABLE `supplement_info` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '补单金额',
  `express_type` int(1) NOT NULL COMMENT '0，寄件；1，收件',
  `express_id` int(20) NOT NULL,
  `type` int(1) NOT NULL COMMENT '1，补运费；2，补服务费',
  `is_pay` int(1) NOT NULL DEFAULT '0' COMMENT '0，未支付；1，已支付',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_deleted` int(1) NOT NULL DEFAULT '0' COMMENT '0，未删除；1，已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of supplement_info
-- ----------------------------
