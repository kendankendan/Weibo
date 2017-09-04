/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : weibo

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-08-15 10:51:28
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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of cmspermission
-- ----------------------------
INSERT INTO `cmspermission` VALUES ('1', '管理后台系统的全部信息', '系统管理', 'system:manage', null);
INSERT INTO `cmspermission` VALUES ('2', '系统管理员信息权限管理', '用户管理', 'system:user', '1');
INSERT INTO `cmspermission` VALUES ('3', '系统角色管理', '角色管理', 'system:role', '1');
INSERT INTO `cmspermission` VALUES ('4', '管理权限', '权限管理', 'system:permission', '1');
INSERT INTO `cmspermission` VALUES ('5', '管理用户与角色的关系', '用户角色管理', 'system:userrole', '1');
INSERT INTO `cmspermission` VALUES ('6', '管理会员信息', '会员管理', 'account:manage', null);
INSERT INTO `cmspermission` VALUES ('7', '管理会员信息', '会员信息管理', 'account:info', '6');
INSERT INTO `cmspermission` VALUES ('8', '管理微博信息', '微博管理', 'account:weibo', '6');

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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of cmsrolepms
-- ----------------------------
INSERT INTO `cmsrolepms` VALUES ('1', '1', '2');
INSERT INTO `cmsrolepms` VALUES ('2', '1', '3');
INSERT INTO `cmsrolepms` VALUES ('3', '1', '4');
INSERT INTO `cmsrolepms` VALUES ('4', '1', '5');
INSERT INTO `cmsrolepms` VALUES ('5', '1', '7');
INSERT INTO `cmsrolepms` VALUES ('6', '1', '8');

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

-- ----------------------------
-- Table structure for user_account
-- ----------------------------
DROP TABLE IF EXISTS `user_account`;
CREATE TABLE `user_account` (
  `userId` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `userName` varchar(15) NOT NULL DEFAULT '' COMMENT '用户昵称',
  `userHead` varchar(255) DEFAULT NULL COMMENT '头像图片链接',
  `userLoginName` varchar(50) NOT NULL DEFAULT '' COMMENT '邮箱/手机（登录名）',
  `userPwd` varchar(255) NOT NULL COMMENT '密码',
  `userSex` bit(1) NOT NULL DEFAULT b'1' COMMENT '(1man/0women)',
  `userSpeak` bit(1) NOT NULL DEFAULT b'1' COMMENT '（1能说/0禁言）',
  `userAlive` bit(1) NOT NULL DEFAULT b'1' COMMENT '（1存在/0删除）',
  `userUnRead` int(11) NOT NULL DEFAULT '0' COMMENT '消息未读',
  `userRegisterDate` varchar(12) NOT NULL COMMENT '注册日期',
  `userIntroduce` varchar(255) DEFAULT NULL COMMENT '个人介绍',
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_account
-- ----------------------------
INSERT INTO `user_account` VALUES ('1', '1', null, '1111111223', 'admin', '', '', '', '0', '1502091677', null);
INSERT INTO `user_account` VALUES ('2', '12', null, '1', '$2a$10$vhRv149a77G9BwApCo.f0eyi4M.0ODLPVpXifzcajxXWAbyexLLIW', '', '', '', '0', '1502091677', null);
INSERT INTO `user_account` VALUES ('3', '13', null, '2', 'admin', '', '', '', '0', '1502091677', null);
INSERT INTO `user_account` VALUES ('4', '14', null, '3', '$2a$10$xU89aQAVWADM5fS0awYbOOp4K/OB.mhnxoi7kYq9cDmk4Y0Ho9uuW', '', '', '', '0', '1502091677', null);
INSERT INTO `user_account` VALUES ('7', 'mistake', '', 'lzb123@qq.com', '$2a$10$xwOf1uC.UxL7w0rXtEvED.f7QdPRa3ZzVWenZ82pQmNGg6vvpqquO', '\0', '', '', '0', '1502183242', '123');

-- ----------------------------
-- Table structure for weibo_attention
-- ----------------------------
DROP TABLE IF EXISTS `weibo_attention`;
CREATE TABLE `weibo_attention` (
  `attentionId` char(36) NOT NULL,
  `userId` int(10) unsigned NOT NULL COMMENT '关注人id',
  `attentionUserId` char(36) NOT NULL COMMENT '被关注人id',
  `attentionTime` varchar(12) NOT NULL,
  PRIMARY KEY (`attentionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of weibo_attention
-- ----------------------------

-- ----------------------------
-- Table structure for weibo_collect
-- ----------------------------
DROP TABLE IF EXISTS `weibo_collect`;
CREATE TABLE `weibo_collect` (
  `collectId` char(36) NOT NULL COMMENT '收藏id',
  `userId` int(10) unsigned NOT NULL COMMENT '用户id',
  `weiboId` char(36) NOT NULL COMMENT '微博id',
  `collectTime` varchar(12) NOT NULL COMMENT '收藏时间',
  PRIMARY KEY (`collectId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of weibo_collect
-- ----------------------------

-- ----------------------------
-- Table structure for weibo_comment
-- ----------------------------
DROP TABLE IF EXISTS `weibo_comment`;
CREATE TABLE `weibo_comment` (
  `commentId` char(36) NOT NULL COMMENT '评论id',
  `userId` int(10) unsigned NOT NULL COMMENT '用户id',
  `weiboId` char(36) NOT NULL COMMENT '微博id',
  `commentTime` varchar(12) NOT NULL COMMENT '评论时间',
  `commentContent` varchar(50) DEFAULT NULL COMMENT '评论内容',
  `userName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`commentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of weibo_comment
-- ----------------------------

-- ----------------------------
-- Table structure for weibo_like
-- ----------------------------
DROP TABLE IF EXISTS `weibo_like`;
CREATE TABLE `weibo_like` (
  `likeId` char(36) NOT NULL COMMENT '赞id',
  `userId` int(10) unsigned NOT NULL COMMENT '用户id',
  `weiboId` char(36) NOT NULL COMMENT '微博id',
  `likeTime` varchar(12) NOT NULL COMMENT '赞时间',
  PRIMARY KEY (`likeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of weibo_like
-- ----------------------------
INSERT INTO `weibo_like` VALUES ('1', '3', '1', '111');

-- ----------------------------
-- Table structure for weibo_message
-- ----------------------------
DROP TABLE IF EXISTS `weibo_message`;
CREATE TABLE `weibo_message` (
  `acceptUserId` int(10) unsigned NOT NULL COMMENT '接受用户id',
  `sendUserId` int(10) unsigned NOT NULL COMMENT '发送用户id',
  `messageContent` varchar(50) NOT NULL COMMENT '消息内容',
  `messageTime` varchar(12) NOT NULL COMMENT '消息时间',
  `messageId` varchar(255) NOT NULL,
  `messageUnRead` int(11) DEFAULT NULL,
  `weiboId` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of weibo_message
-- ----------------------------

-- ----------------------------
-- Table structure for weibo_post
-- ----------------------------
DROP TABLE IF EXISTS `weibo_post`;
CREATE TABLE `weibo_post` (
  `weiboId` char(36) NOT NULL COMMENT 'weibo id',
  `userId` int(10) unsigned NOT NULL COMMENT '用户id',
  `weiboAlive` bit(1) NOT NULL DEFAULT b'1' COMMENT '1存在/0删除',
  `weiboRootId` char(36) DEFAULT NULL COMMENT 'root id',
  `weiboParentId` char(36) DEFAULT NULL COMMENT 'parent id',
  `weiboCollect` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '收藏次数',
  `weiboForward` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '转发次数',
  `weiboComment` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '评论次数',
  `weiboLike` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '赞次数',
  `weiboTime` varchar(12) NOT NULL COMMENT '发表/转发时间',
  `weiboFwContent` varchar(100) DEFAULT NULL COMMENT '转发内容',
  `weiboContent` varchar(140) DEFAULT NULL COMMENT '微博内容',
  `weiboPhoto` varchar(255) DEFAULT NULL COMMENT '微博图片链接',
  `weiboAt` varchar(140) DEFAULT NULL COMMENT '微博中@的人，用@进行分割',
  `weiboTopic` varchar(15) DEFAULT NULL COMMENT '微博中的话题，只能一个',
  `weiboisLike` bit(1) NOT NULL DEFAULT b'0',
  `weiboAts` tinyblob,
  `username` varchar(255) DEFAULT NULL,
  `weiboisCollect` tinyint(1) NOT NULL,
  PRIMARY KEY (`weiboId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of weibo_post
-- ----------------------------
INSERT INTO `weibo_post` VALUES ('0b0decdd0764476e9581b6cf3e60dc78', '1', '', null, null, '0', '0', '0', '0', '1502091677', null, 'qqqqq', 'asdfgh', '@1@2', 'asdfgh', '\0', 0xACED0005757200135B4C6A6176612E6C616E672E537472696E673BADD256E7E91D7B470200007870000000027400013174000132, null, '0');
INSERT INTO `weibo_post` VALUES ('4741aa4f0abf49f89f2169cbca8f0b71', '1', '', null, null, '0', '0', '0', '0', '1502089982', null, 'qqqqq', 'asdfgh', '@1@2', 'asdfgh', '\0', 0xACED0005757200135B4C6A6176612E6C616E672E537472696E673BADD256E7E91D7B470200007870000000037400007400013174000132, null, '0');
INSERT INTO `weibo_post` VALUES ('4e4557d6d714471790ccc6e4e918f4d9', '1', '', null, null, '0', '0', '0', '0', '1502077346', null, 'qqqqq', 'asdfgh', '@1', 'asdfgh', '\0', null, null, '0');
INSERT INTO `weibo_post` VALUES ('8ff49e3167e24d02921ed632b50c4738', '1', '', null, null, '0', '0', '0', '0', '1502077361', null, 'qqqqq', 'asdfgh', '@1@2', 'asdfgh', '\0', null, null, '0');
INSERT INTO `weibo_post` VALUES ('9305dea066554871923117aabcd6a60e', '1', '\0', null, null, '0', '0', '0', '0', '1501816168', null, 'qqqqq', 'asdfgh', 'qq', 'qqq', '\0', null, null, '0');
INSERT INTO `weibo_post` VALUES ('d18f3e1a3270422d96fae0bab370643b', '1', '', null, null, '0', '0', '0', '0', '1502091321', null, 'qqqqq', 'asdfgh', '@1@2', 'asdfgh', '\0', 0xACED0005757200135B4C6A6176612E6C616E672E537472696E673BADD256E7E91D7B470200007870000000027400013174000132, null, '0');
INSERT INTO `weibo_post` VALUES ('fc71307466d44190bca3278bb6645bbe', '1', '', null, null, '0', '0', '0', '0', '1501814805', null, 'qqqqq', 'asdfgh', 'qq', 'qqq', '\0', null, null, '0');
INSERT INTO `weibo_post` VALUES ('ff62fcac24ea416c84c9b2696e32b107', '1', '', null, null, '0', '0', '0', '0', '1501816152', null, 'qqqqq', 'asdfgh', 'qq', 'qqq', '\0', null, null, '0');

-- ----------------------------
-- Table structure for weibo_topic
-- ----------------------------
DROP TABLE IF EXISTS `weibo_topic`;
CREATE TABLE `weibo_topic` (
  `topicId` char(36) NOT NULL COMMENT '话题id',
  `userId` int(10) unsigned NOT NULL COMMENT '用户id',
  `weiboId` char(36) NOT NULL COMMENT '微博id',
  `topicTime` varchar(12) NOT NULL COMMENT '话题时间',
  PRIMARY KEY (`topicId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of weibo_topic
-- ----------------------------
