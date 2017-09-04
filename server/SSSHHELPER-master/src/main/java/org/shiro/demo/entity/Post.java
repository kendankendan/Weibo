package org.shiro.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 微博实体
 * Created by luowz on 2017/8/3.
 */

@Entity
@Table(name = "weibo_post")

public class Post {
    @Id
    @Column(name = "weiboId")
    private String weiboId;//微博编号

    @Column(name="userId")
    private Integer userId;//用户编号

    @Column(name="weiboAlive")
    private Integer weiboAlive;//微博是否删除

    @Column(name="weiboRootId")
    private String weiboRootId;//weiboRootId

    @Column(name="weiboParentId")
    private String weiboParentId;//weiboParentId

    @Column(name="weiboCollect")
    private Integer weiboCollect;//收藏次数

    @Column(name="weiboForward")
    private Integer weiboForward;//转发次数

    @Column(name="weiboComment")
    private Integer weiboComment;//评论次数

    @Column(name="weiboLike")
    private Integer weiboLike;//赞的次数

    @Column(name="weiboTime")
    private Long weiboTime;//发表时间

    @Column(name="weiboFwContent")
    private String weiboFwContent;//转发内容

    @Column(name="weiboContent")
    private String weiboContent;//微博内容

    @Column(name="weiboPhoto")
    private String weiboPhoto;//图片链接地址

    @Column(name="weiboAt")
    private String weiboAt;//微博中@的人，用@进行分割

    @Column(name="weiboTopic")
    private String weiboTopic;//微博中主题

    private String userName;//用户昵称

    private boolean weiboisLike;//微博是否被赞过

    private boolean weiboisCollect;//微博是否被收藏

    private String[] weiboAts;//回传前端需要

    private String userHead;//回传前端需要

    public Post() {
    }

    public Post(Integer userId, Integer weiboAlive, String weiboRootId, String weiboParentId, Integer weiboCollect, Integer weiboForward, Integer weiboComment, Integer weiboLike, Long weiboTime, String weiboFwContent, String weiboContent, String weiboPhoto) {
        this.userId = userId;
        this.weiboAlive = weiboAlive;
        this.weiboRootId = weiboRootId;
        this.weiboParentId = weiboParentId;
        this.weiboCollect = weiboCollect;
        this.weiboForward = weiboForward;
        this.weiboComment = weiboComment;
        this.weiboLike = weiboLike;
        this.weiboTime = weiboTime;
        this.weiboFwContent = weiboFwContent;
        this.weiboContent = weiboContent;
        this.weiboPhoto = weiboPhoto;
    }

    public Post(String weiboId,Integer userId, Long weiboTime, String weiboContent, String weiboPhoto, Integer weiboAlive, Integer weiboCollect, Integer weiboForward, Integer weiboComment, Integer weiboLike, String weiboAt, String weiboTopic, boolean weiboisLike, boolean weiboisCollect, String[] weiboAts) {
        this.weiboId = weiboId;
        this.userId = userId;
        this.weiboTime = weiboTime;
        this.weiboContent = weiboContent;
        this.weiboPhoto = weiboPhoto;
        this.weiboAlive = weiboAlive;
        this.weiboCollect = weiboCollect;
        this.weiboForward = weiboForward;
        this.weiboComment = weiboComment;
        this.weiboLike = weiboLike;
        this.weiboAt = weiboAt;
        this.weiboTopic = weiboTopic;
        this.weiboisLike = weiboisLike;
        this.weiboisCollect = weiboisCollect;
        this.weiboAts = weiboAts;
    }
    public Post(String weiboId,Integer userId, Long weiboTime, String weiboContent, String weiboPhoto, Integer weiboAlive, Integer weiboCollect, Integer weiboForward, Integer weiboComment, Integer weiboLike, String weiboAt, String weiboTopic, String userName, String weiboRootId, String weiboParentId, boolean weiboisLike, boolean weiboisCollect) {
        this.weiboId = weiboId;
        this.userId = userId;
        this.weiboTime = weiboTime;
        this.weiboContent = weiboContent;
        this.weiboPhoto = weiboPhoto;
        this.weiboAlive = weiboAlive;
        this.weiboCollect = weiboCollect;
        this.weiboForward = weiboForward;
        this.weiboComment = weiboComment;
        this.weiboLike = weiboLike;
        this.weiboAt = weiboAt;
        this.weiboTopic = weiboTopic;
        this.userName = userName;
        this.weiboisLike = weiboisLike;
        this.weiboisCollect = weiboisCollect;
        this.weiboRootId = weiboRootId;
        this.weiboParentId = weiboParentId;
    }
    public String getWeiboId() {
        return weiboId;
    }

    public void setWeiboId(String weiboId) {
        this.weiboId = weiboId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getWeiboAlive() {
        return weiboAlive;
    }

    public void setWeiboAlive(Integer weiboAlive) {
        this.weiboAlive = weiboAlive;
    }

    public String getWeiboRootId() {
        return weiboRootId;
    }

    public void setWeiboRootId(String weiboRootId) {
        this.weiboRootId = weiboRootId;
    }

    public String getWeiboParentId() {
        return weiboParentId;
    }

    public void setWeiboParentId(String weiboParentId) {
        this.weiboParentId = weiboParentId;
    }

    public Integer getWeiboCollect() {
        return weiboCollect;
    }

    public void setWeiboCollect(Integer weiboCollect) {
        this.weiboCollect = weiboCollect;
    }

    public Integer getWeiboForward() {
        return weiboForward;
    }

    public void setWeiboForward(Integer weiboForward) {
        this.weiboForward = weiboForward;
    }

    public Integer getWeiboComment() {
        return weiboComment;
    }

    public void setWeiboComment(Integer weiboComment) {
        this.weiboComment = weiboComment;
    }

    public Integer getWeiboLike() {
        return weiboLike;
    }

    public void setWeiboLike(Integer weiboLike) {
        this.weiboLike = weiboLike;
    }

    public Long getWeiboTime() {
        return weiboTime;
    }

    public void setWeiboTime(Long weiboTime) {
        this.weiboTime = weiboTime;
    }

    public String getWeiboFwContent() {
        return weiboFwContent;
    }

    public void setWeiboFwContent(String weiboFwContent) {
        this.weiboFwContent = weiboFwContent;
    }

    public String getWeiboContent() {
        return weiboContent;
    }

    public void setWeiboContent(String weiboContent) {
        this.weiboContent = weiboContent;
    }

    public String getWeiboPhoto() {
        return weiboPhoto;
    }

    public void setWeiboPhoto(String weiboPhoto) {
        this.weiboPhoto = weiboPhoto;
    }

    public String getWeiboAt() {
        return weiboAt;
    }

    public void setWeiboAt(String weiboAt) {
        this.weiboAt = weiboAt;
    }

    public String getWeiboTopic() {
        return weiboTopic;
    }

    public void setWeiboTopic(String weiboTopic) {
        this.weiboTopic = weiboTopic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isWeiboisLike() {
        return weiboisLike;
    }

    public void setWeiboisLike(boolean weiboisLike) {
        this.weiboisLike = weiboisLike;
    }

    public boolean isWeiboisCollect() {
        return weiboisCollect;
    }

    public void setWeiboisCollect(boolean weiboisCollect) {
        this.weiboisCollect = weiboisCollect;
    }

    public String[] getWeiboAts() {
        return weiboAts;
    }

    public void setWeiboAts(String[] weiboAts) {
        this.weiboAts = weiboAts;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }
}
