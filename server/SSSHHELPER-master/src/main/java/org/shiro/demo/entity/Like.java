package org.shiro.demo.entity;

import javax.persistence.*;

/**
 * 点赞实体
 * Created by yetz on 2017/8/4.
 */
@Entity
@Table(name = "weibo_like")
public class Like {
    public void setLikeId(String likeId) {
        this.likeId = likeId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setWeiboId(String weiboId) {
        this.weiboId = weiboId;
    }

    public void setLikeTime(Long likeTime) {
        this.likeTime = likeTime;
    }

    @Id
    @Column(name = "likeId")
    private String likeId;//点赞编号

    @Column(name="userId")
    private Integer userId;//用户编号

    @Column(name = "weiboId")
    private String weiboId;//微博编号

    @Column(name="likeTime")
    private Long likeTime;//发表时间

    public Like(String likeId, Integer userId, String weiboId, Long likeTime) {
        this.likeId = likeId;
        this.userId = userId;
        this.weiboId = weiboId;
        this.likeTime = likeTime;
    }
    public Like() {
        super();
    }
    public String getLikeId() {
        return likeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getWeiboId() {
        return weiboId;
    }

    public Long getLikeTime() {
        return likeTime;
    }
}
