/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50151
Source Host           : localhost:3306
Source Database       : yydb

Target Server Type    : MYSQL
Target Server Version : 50151
File Encoding         : 65001

Date: 2017-02-17 16:59:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for cmspermission
-- ----------------------------
DROP TABLE IF EXISTS `cmspermission`;
CREATE TABLE `cmspermission` (
  `pmsid` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `name` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `permission` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `parentid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`pmsid`),
  KEY `FK_dl60a0nqnys11h5b1xahciren` (`parentid`),
  CONSTRAINT `cmspermission_ibfk_1` FOREIGN KEY (`parentid`) REFERENCES `cmspermission` (`pmsid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of cmspermission
-- ----------------------------
INSERT INTO `cmspermission` VALUES ('1', '管理后台系统的全部信息', '系统管理', 'system:manage', null);
INSERT INTO `cmspermission` VALUES ('2', '系统管理员信息权限管理', '用户管理', 'system:user', '1');
INSERT INTO `cmspermission` VALUES ('3', '系统角色管理', '角色管理', 'system:role', '1');
INSERT INTO `cmspermission` VALUES ('4', '第二菜单', '第二菜单', 'test', null);
INSERT INTO `cmspermission` VALUES ('5', '第二菜单第一子项', '第一子项', 'testone', '4');

-- ----------------------------
-- Table structure for cmsrole
-- ----------------------------
DROP TABLE IF EXISTS `cmsrole`;
CREATE TABLE `cmsrole` (
  `roleid` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `name` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`roleid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of cmsrole
-- ----------------------------
INSERT INTO `cmsrole` VALUES ('1', '拥有最高权限的超级管理员', '超级管理员');
INSERT INTO `cmsrole` VALUES ('2', '权限为空', '游客');

-- ----------------------------
-- Table structure for cmsrolepms
-- ----------------------------
DROP TABLE IF EXISTS `cmsrolepms`;
CREATE TABLE `cmsrolepms` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleid` bigint(20) NOT NULL,
  `pmsid` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_iiaf860xrkux4d333n06gqdap` (`pmsid`),
  KEY `FK_6sphp8ycu59cwo68b71u2jcye` (`roleid`),
  CONSTRAINT `cmsrolepms_ibfk_1` FOREIGN KEY (`roleid`) REFERENCES `cmsrole` (`roleid`),
  CONSTRAINT `cmsrolepms_ibfk_2` FOREIGN KEY (`pmsid`) REFERENCES `cmspermission` (`pmsid`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of cmsrolepms
-- ----------------------------
INSERT INTO `cmsrolepms` VALUES ('7', '1', '2');
INSERT INTO `cmsrolepms` VALUES ('8', '1', '5');

-- ----------------------------
-- Table structure for cmsuser
-- ----------------------------
DROP TABLE IF EXISTS `cmsuser`;
CREATE TABLE `cmsuser` (
  `userid` bigint(20) NOT NULL AUTO_INCREMENT,
  `account` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `nickname` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `password` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`userid`),
  UNIQUE KEY `UK_726c0ppn7oftcawwsq6n4u6gn` (`account`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of cmsuser
-- ----------------------------
INSERT INTO `cmsuser` VALUES ('1', 'admin', '管理员', '$2a$10$RtCDFfOKE82JwsO4XOkSk.dyG9098Q.91PLsO8OiqlVtEGeUoo0mC');
INSERT INTO `cmsuser` VALUES ('2', 'admin1', '123', '$2a$10$RtCDFfOKE82JwsO4XOkSk.dyG9098Q.91PLsO8OiqlVtEGeUoo0mC');
INSERT INTO `cmsuser` VALUES ('5', 'user1', 'user12', '$2a$10$ug4/Xg5V6hZU8qOpJks/8ec73.k4IVmm6IKyE8kW0RoMCGlSoDSdC');

-- ----------------------------
-- Table structure for cmsuserrole
-- ----------------------------
DROP TABLE IF EXISTS `cmsuserrole`;
CREATE TABLE `cmsuserrole` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userid` bigint(20) NOT NULL,
  `roleid` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_7txs9153uvcm24p3n34pa1c8p` (`roleid`),
  KEY `FK_h8hq3ed5gko0yq3jforlirm9i` (`userid`),
  CONSTRAINT `cmsuserrole_ibfk_1` FOREIGN KEY (`roleid`) REFERENCES `cmsrole` (`roleid`),
  CONSTRAINT `cmsuserrole_ibfk_2` FOREIGN KEY (`userid`) REFERENCES `cmsuser` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of cmsuserrole
-- ----------------------------
INSERT INTO `cmsuserrole` VALUES ('1', '1', '1');
INSERT INTO `cmsuserrole` VALUES ('2', '2', '2');
INSERT INTO `cmsuserrole` VALUES ('3', '1', '2');
