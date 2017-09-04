package org.shiro.demo.vo;

import org.shiro.demo.entity.Post;

/**
 * Created by yetz on 2017/8/8.
 */
public class PreForwardVO {



    private String weiboRootId;//weiboRootId

    private String weiboParentId;//weiboParentId

    private String pWeiboAt;//微博中@的人，用@进行分割

    private String pWeiboTopic;//微博中主题

    private String pUsername; //用户昵称

    private String pContent; //被转发的内容内的评论


    public PreForwardVO() {
        super();
    }

    public PreForwardVO(Post post) {
        super();
        boolean isFirstForward = (post.getWeiboRootId() == null);
        String pID = post.getWeiboId();
        this.setWeiboParentId(pID);
        if(isFirstForward) {
            this.setWeiboRootId(pID);
        }
        else {
            this.setWeiboRootId(post.getWeiboRootId());
        }

        this.setpWeiboAt(post.getWeiboAt());
        this.setpWeiboTopic(post.getWeiboTopic());
        this.setpUsername(post.getUserName());
        if(isFirstForward)
            this.setpContent("");
        else
            this.setpContent(post.getWeiboFwContent());


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

    public String getpWeiboAt() {
        return pWeiboAt;
    }

    public void setpWeiboAt(String pWeiboAt) {
        this.pWeiboAt = pWeiboAt;
    }

    public String getpWeiboTopic() {
        return pWeiboTopic;
    }

    public void setpWeiboTopic(String pWeiboTopic) {
        this.pWeiboTopic = pWeiboTopic;
    }

    public String getpUsername() {
        return pUsername;
    }

    public void setpUsername(String pUsername) {
        this.pUsername = pUsername;
    }

    public String getpContent() {
        return pContent;
    }

    public void setpContent(String pContent) {
        this.pContent = pContent;
    }
}
