/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50711
 Source Host           : localhost
 Source Database       : plant

 Target Server Type    : MySQL
 Target Server Version : 50711
 File Encoding         : utf-8

 Date: 10/01/2016 20:54:54 PM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `comment`
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `commentip` bigint(11) DEFAULT NULL COMMENT '评论ip',
  `commentcity` varchar(50) DEFAULT NULL,
  `commentcontent` longtext COMMENT '评论内容',
  `commentdatetime` datetime DEFAULT NULL COMMENT '评论时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `liverecord`
-- ----------------------------
DROP TABLE IF EXISTS `liverecord`;
CREATE TABLE `liverecord` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `voteyes` int(11) DEFAULT NULL COMMENT '投yes的票数',
  `voteno` int(11) DEFAULT NULL COMMENT '投no的票数',
  `voteresult` smallint(3) DEFAULT NULL COMMENT '投票结果',
  `isexecute` smallint(3) DEFAULT NULL COMMENT '是否执行',
  `recorddatetime` datetime DEFAULT NULL COMMENT '日期时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL COMMENT '用户名',
  `password` varchar(45) DEFAULT NULL COMMENT '密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
--  Table structure for `vote`
-- ----------------------------
DROP TABLE IF EXISTS `vote`;
CREATE TABLE `vote` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `voteyesorno` tinyint(1) DEFAULT NULL COMMENT '投票结果,1-yes,0-no',
  `voteip` bigint(11) DEFAULT NULL COMMENT '投票IP',
  `votedate` date DEFAULT NULL COMMENT '投票日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='投票表';

SET FOREIGN_KEY_CHECKS = 1;
