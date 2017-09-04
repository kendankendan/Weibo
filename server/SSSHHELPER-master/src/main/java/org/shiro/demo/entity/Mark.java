package org.shiro.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 收藏的记录
 * Created by yetz on 2017/8/7.
 */
@Entity
@Table(name = "weibo_collect")
public class Mark {
    public String getMarkId() {
        return markId;
    }

    public void setMarkId(String markId) {
        this.markId = markId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getWeiboId() {
        return weiboId;
    }

    public void setWeiboId(String weiboId) {
        this.weiboId = weiboId;
    }

    public Long getMarkTime() {
        return markTime;
    }

    public void setMarkTime(Long markTime) {
        this.markTime = markTime;
    }

    @Id
    @Column(name = "collectId")
    private String markId;//点赞编号

    @Column(name="userId")
    private Integer userId;//用户编号

    @Column(name = "weiboId")
    private String weiboId;//微博编号

    @Column(name="collectTime")
    private Long markTime;//发表时间

    public Mark(){}

    public Mark(String markId, Integer userId, String weiboId, Long markTime) {
        this.markId = markId;
        this.userId = userId;
        this.weiboId = weiboId;
        this.markTime = markTime;
    }
}
