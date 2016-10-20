/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50521
Source Host           : localhost:3306
Source Database       : groupdining

Target Server Type    : MYSQL
Target Server Version : 50521
File Encoding         : 65001

Date: 2015-12-17 14:02:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `g_admin`
-- ----------------------------
DROP TABLE IF EXISTS `g_admin`;
CREATE TABLE `g_admin` (
  `id` varchar(32) NOT NULL,
  `name` varchar(32) DEFAULT NULL,
  `email` varchar(32) DEFAULT NULL,
  `real_name` varchar(32) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `style` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of g_admin
-- ----------------------------
INSERT INTO `g_admin` VALUES ('2c28819651ad7c160151ad80bbd70000', 'admin', 'asfdasf', null, 'a66abb5684c45962d887564f08346e8d', null, null, '2015-12-17 09:14:33', '2015-12-17 09:14:33', 'asfasfaf');

-- ----------------------------
-- Table structure for `g_cart`
-- ----------------------------
DROP TABLE IF EXISTS `g_cart`;
CREATE TABLE `g_cart` (
  `id` varchar(32) NOT NULL,
  `menu_id` text,
  `create_time` datetime DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of g_cart
-- ----------------------------

-- ----------------------------
-- Table structure for `g_dish`
-- ----------------------------
DROP TABLE IF EXISTS `g_dish`;
CREATE TABLE `g_dish` (
  `id` varchar(32) NOT NULL,
  `name` varchar(32) DEFAULT NULL,
  `pictrue` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `attribute` varchar(255) DEFAULT NULL,
  `amount` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of g_dish
-- ----------------------------
INSERT INTO `g_dish` VALUES ('2c28819651ae73fe0151ae7a843b0001', 'aaaaaa', '/uploadFile/image2.jpg', 'some Description', '12', '2015-12-17 13:47:23', '2015-12-17 13:47:23', null, '0');
INSERT INTO `g_dish` VALUES ('2c28819651ae73fe0151ae7b83950002', 'bbbbb', '/uploadFile/image1.jpg', 'some Description', '23', '2015-12-17 13:48:28', '2015-12-17 13:48:28', null, '0');

-- ----------------------------
-- Table structure for `g_menu`
-- ----------------------------
DROP TABLE IF EXISTS `g_menu`;
CREATE TABLE `g_menu` (
  `id` varchar(32) NOT NULL,
  `name` varchar(32) DEFAULT NULL,
  `style` varchar(32) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `discount` double DEFAULT NULL,
  `pictrue` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `dish_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of g_menu
-- ----------------------------
INSERT INTO `g_menu` VALUES ('2c28819651ae73fe0151ae7cba1a0003', 'm1', 'sdfasf', 'some Description', '120', '0.8', '/uploadFile/iblgvi02071172.jpg', '2015-12-17 13:49:48', '2015-12-17 13:49:48', '2c28819651ae73fe0151ae7a843b0001');
INSERT INTO `g_menu` VALUES ('2c28819651ae73fe0151ae7d95c00004', 'm2', 'asfasf', 'some Description', '150', '0.5', '/uploadFile/483-4222.jpg', '2015-12-17 13:50:44', '2015-12-17 13:50:44', '2c28819651ae73fe0151ae7b83950002');

-- ----------------------------
-- Table structure for `g_order`
-- ----------------------------
DROP TABLE IF EXISTS `g_order`;
CREATE TABLE `g_order` (
  `id` varchar(32) NOT NULL,
  `price` double DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of g_order
-- ----------------------------
INSERT INTO `g_order` VALUES ('2c28819651ae73fe0151ae7e3a390006', '0', '订单', '2015-12-17 13:51:26', '2015-12-17 13:51:26');
INSERT INTO `g_order` VALUES ('2c28819651ae816d0151ae829f2c0000', '0', '订单', '2015-12-17 13:56:14', '2015-12-17 13:56:14');
INSERT INTO `g_order` VALUES ('2c28819651ae816d0151ae86898b0001', '0', '订单', '2015-12-17 14:00:29', '2015-12-17 14:00:30');

-- ----------------------------
-- Table structure for `g_user`
-- ----------------------------
DROP TABLE IF EXISTS `g_user`;
CREATE TABLE `g_user` (
  `id` varchar(32) NOT NULL,
  `name` varchar(32) DEFAULT NULL,
  `email` varchar(32) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `real_name` varchar(32) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of g_user
-- ----------------------------
INSERT INTO `g_user` VALUES ('2c28819651ad98710151adb02e640006', 'user', 'sadfaf', '4da49c16db42ca04538d629ef0533fe8', null, null, '2015-12-17 10:06:23', null);
