/*
Navicat MySQL Data Transfer

Source Server         : root
Source Server Version : 80017
Source Host           : 127.0.0.1:3306
Source Database       : bbs

Target Server Type    : MYSQL
Target Server Version : 80017
File Encoding         : 65001

Date: 2019-10-29 10:54:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_admin
-- ----------------------------
DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '管理员编号',
  `name` varchar(255) NOT NULL COMMENT '管理员名称',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '头像',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_approve
-- ----------------------------
DROP TABLE IF EXISTS `t_approve`;
CREATE TABLE `t_approve` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '点赞编号',
  `user_id` int(11) NOT NULL COMMENT '用户编号',
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '点赞时间',
  `post_id` int(11) NOT NULL COMMENT '帖子编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_blacklist
-- ----------------------------
DROP TABLE IF EXISTS `t_blacklist`;
CREATE TABLE `t_blacklist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户编号',
  `permission` int(11) NOT NULL COMMENT '权限编号；0禁评论，1禁发帖',
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_collect
-- ----------------------------
DROP TABLE IF EXISTS `t_collect`;
CREATE TABLE `t_collect` (
  `user_id` int(11) NOT NULL COMMENT '收藏人用户编号',
  `post_id` int(11) NOT NULL COMMENT '收藏的帖子编号',
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_comment
-- ----------------------------
DROP TABLE IF EXISTS `t_comment`;
CREATE TABLE `t_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `reply_id` int(11) NOT NULL COMMENT '主回复编号',
  `to_username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '回复对象的用户名，如果不是楼中楼则不用添加',
  `user_id` int(11) NOT NULL COMMENT '回复者的编号',
  `comment_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '回复时间',
  `comment` varchar(255) NOT NULL COMMENT '评论内容',
  `to_user_id` int(11) DEFAULT NULL COMMENT '回复对象的用户编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for t_follow
-- ----------------------------
DROP TABLE IF EXISTS `t_follow`;
CREATE TABLE `t_follow` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '关注编号',
  `follow_id` int(11) NOT NULL COMMENT '关注人用户编号',
  `followed_id` int(11) NOT NULL COMMENT '被关注人用户编号',
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_message
-- ----------------------------
DROP TABLE IF EXISTS `t_message`;
CREATE TABLE `t_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '信息编号',
  `content` varchar(255) NOT NULL COMMENT '内容',
  `send_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '信息发送时间',
  `receive_id` int(11) NOT NULL COMMENT '接收人用户编号',
  `send_id` int(11) NOT NULL COMMENT '发起人用户编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_plate
-- ----------------------------
DROP TABLE IF EXISTS `t_plate`;
CREATE TABLE `t_plate` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '板块编号',
  `name` varchar(255) NOT NULL COMMENT '板块名',
  `user_id` int(11) NOT NULL COMMENT '创建人编号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `describes` varchar(255) DEFAULT NULL COMMENT '相关信息',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '0禁用1启动',
  `view_num` int(11) NOT NULL DEFAULT '0' COMMENT '浏览人数',
  `post_num` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_post
-- ----------------------------
DROP TABLE IF EXISTS `t_post`;
CREATE TABLE `t_post` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '帖子编号',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '帖名',
  `user_id` int(11) NOT NULL COMMENT '发帖人编号',
  `section_id` int(11) DEFAULT NULL COMMENT '分区编号',
  `reply_num` int(11) NOT NULL DEFAULT '0' COMMENT '回复数',
  `view_num` int(11) NOT NULL DEFAULT '0' COMMENT '查看数',
  `approve_num` int(11) NOT NULL DEFAULT '0' COMMENT '点赞数',
  `collect_num` int(11) NOT NULL DEFAULT '0' COMMENT '收藏数',
  `content` varchar(255) NOT NULL COMMENT '内容',
  `post_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发帖时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  `video` varchar(255) DEFAULT NULL COMMENT '视频',
  `type` int(11) NOT NULL COMMENT '帖子类型0文字图片，1视频',
  `status` int(11) unsigned NOT NULL DEFAULT '3' COMMENT '0普通1置顶2公告3审核4禁用',
  `plate_id` int(11) DEFAULT '0' COMMENT '板块编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_post_image
-- ----------------------------
DROP TABLE IF EXISTS `t_post_image`;
CREATE TABLE `t_post_image` (
  `image` varchar(255) NOT NULL COMMENT '图片地址',
  `post_id` int(11) NOT NULL COMMENT '帖子编号',
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for t_reply
-- ----------------------------
DROP TABLE IF EXISTS `t_reply`;
CREATE TABLE `t_reply` (
  `reply_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '回复时间',
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '回复编号',
  `user_id` int(11) NOT NULL COMMENT '回复人编号',
  `post_id` int(11) NOT NULL COMMENT '回复的帖子编号',
  `content` varchar(255) NOT NULL COMMENT '回复内容',
  `comment_num` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色编号',
  `plate_id` int(11) NOT NULL COMMENT '板块编号',
  `user_id` int(11) NOT NULL COMMENT '用户编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_section
-- ----------------------------
DROP TABLE IF EXISTS `t_section`;
CREATE TABLE `t_section` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '分区编号',
  `name` varchar(255) NOT NULL COMMENT '分区名',
  `plate_id` int(11) NOT NULL COMMENT '板块编号',
  `post_num` int(11) DEFAULT '0' COMMENT '帖子数',
  `user_id` int(11) NOT NULL COMMENT '用户编号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `status` int(11) DEFAULT '1' COMMENT '0禁用1启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '账号',
  `username` varchar(255) NOT NULL COMMENT '昵称',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `register_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `introduce` varchar(255) DEFAULT NULL COMMENT '介绍',
  `image` varchar(255) DEFAULT NULL COMMENT '头像',
  `post_num` int(11) DEFAULT '0' COMMENT '帖子数',
  `reply_num` int(11) DEFAULT '0' COMMENT '评论数',
  `collect_num` int(11) DEFAULT '0' COMMENT '收藏数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=115 DEFAULT CHARSET=utf8;
