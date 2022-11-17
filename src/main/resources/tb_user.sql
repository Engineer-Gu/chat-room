/*
 Navicat Premium Data Transfer

 Source Server         : 本地MySQL
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : localhost:3306
 Source Schema         : chat-room

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 17/11/2022 10:30:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `username` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `email` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('bingge', '123456', '1437594522@qq.com');
INSERT INTO `tb_user` VALUES ('haorange', '123456', '1437594522@qq.com');
INSERT INTO `tb_user` VALUES ('laogu', 'laogu666', '1437594522@qq.com');
INSERT INTO `tb_user` VALUES ('laoii', 'laogu666', '1437594522@qq.com');
INSERT INTO `tb_user` VALUES ('laokkk', '123456', '1437594522@qq.com');
INSERT INTO `tb_user` VALUES ('laoming', '123456', '1437594522@qq.com');
INSERT INTO `tb_user` VALUES ('laopp', '123456', '1437594522@qq.com');
INSERT INTO `tb_user` VALUES ('laottt', '123456', '1437594522@qq.com');
INSERT INTO `tb_user` VALUES ('zengge', 'zengge66', '1437594522@qq.com');

SET FOREIGN_KEY_CHECKS = 1;
