package org.shiro.demo.vo;

import org.shiro.demo.dao.util.Pagination;
import org.shiro.demo.entity.Post;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 微博显示类
 * Created by luowz on 2017/8/3.
 */
public class PostVO {

    private String weiboId;//微博编号

    private Integer userId;//用户编号

    private Integer weiboAlive;//微博是否删除

    private String weiboRootId;//weiboRootId

    private String weiboParentId;//weiboParentId

    private Integer weiboCollect;//收藏次数

    private Integer weiboForward;//转发次数

    private Integer weiboComment;//评论次数

    private Integer weiboLike;//赞的次数

    private String weiboTime;//发表时间

    private String weiboFwContent;//转发内容

    private String weiboContent;//微博内容

    private String weiboPhoto;//图片链接地址

    private String weiboAt;//微博中@的人，用@进行分割

    private String weiboTopic;//微博中主题

    private String userName; //用户昵称

    private boolean weiboisLike;//微博是否被赞过

    private boolean weiboisCollect;//微博是否被收藏

    private String open;//前端需要

    private String [] weiboAts;//传数组回前端

    private Post rootWeibo;//根微博 转发用

    private String userHead;//用户头像 回传前端用

    public PostVO() {
        super();
    }

    public PostVO(Post post) {
        super();
        this.weiboId = post.getWeiboId();
        this.userId = post.getUserId();
        this.weiboAlive = post.getWeiboAlive();
        this.weiboRootId = post.getWeiboRootId();
        this.weiboParentId = post.getWeiboParentId();
        this.weiboCollect = post.getWeiboCollect();
        this.weiboForward = post.getWeiboForward();
        this.weiboComment = post.getWeiboComment();
        this.weiboLike = post.getWeiboLike();
        this.weiboTime = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(post.getWeiboTime()*1000));
        this.weiboFwContent = post.getWeiboFwContent();
        this.weiboContent = post.getWeiboContent();
        this.weiboPhoto = post.getWeiboPhoto();
        this.weiboAt = post.getWeiboAt();
        this.weiboTopic = post.getWeiboTopic();
        this.userName = post.getUserName();
        this.weiboisLike = post.isWeiboisLike();
        this.weiboisCollect = post.isWeiboisCollect();
        this.open =  "false";
        this.weiboAts = post.getWeiboAts();
        this.userHead = post.getUserHead();
    }

    public PostVO(String weiboId, Integer weiboCollect, Integer weiboForward, Integer weiboComment, Integer weiboLike, String weiboTime, String weiboFwContent, String weiboContent, String weiboPhoto) {
        this.weiboId = weiboId;
        this.weiboCollect = weiboCollect;
        this.weiboForward = weiboForward;
        this.weiboComment = weiboComment;
        this.weiboLike = weiboLike;
        this.weiboTime = weiboTime;
        this.weiboFwContent = weiboFwContent;
        this.weiboContent = weiboContent;
        this.weiboPhoto = weiboPhoto;
    }

    public PostVO(String weiboId, Integer userId, Integer weiboAlive, String weiboRootId, String weiboParentId, Integer weiboCollect, Integer weiboForward, Integer weiboComment, Integer weiboLike, String weiboTime, String weiboFwContent, String weiboContent, String weiboPhoto, String weiboAt, String weiboTopic) {
        this.weiboId = weiboId;
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
        this.weiboAt = weiboAt;
        this.weiboTopic = weiboTopic;
    }

    public String getWeiboId() {
        return weiboId;
    }

    public void setWeiboId(String weiboId) {
        this.weiboId = weiboId;
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

    public String getWeiboTime() {
        return weiboTime;
    }

    public void setWeiboTime(String weiboTime) {
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

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String[] getWeiboAts() {
        return weiboAts;
    }

    public void setWeiboAts(String[] weiboAts) {
        this.weiboAts = weiboAts;
    }

    public Post getRootWeibo() {
        return rootWeibo;
    }

    public void setRootWeibo(Post rootWeibo) {
        this.rootWeibo = rootWeibo;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    /**
     * 将实体类转换成显示层实体类
     * @param pagination 分页数据
     * @return
     */
    public static Map<String, Object> changePost2PostVO(Pagination<Post> pagination){
        List<Post> recordList = pagination.getRecordList();
        List<PostVO> VOList = new ArrayList<PostVO>();
        Map<String, Object> map = new HashMap<String, Object>();
        for(Post item : recordList){
            VOList.add(new PostVO(item));
        }
        map.put("rows", VOList);
        map.put("total", pagination.getRecordCount());
        return map;
    }

    /**
     * 将实体类转换成显示层实体类
     * @param
     * @return
     */
    public static List<PostVO> changeCustomer2CustomerVO(List<Post> recordList){
        List<PostVO> roleVOList = new ArrayList<PostVO>();
        Map<String, Object> map = new HashMap<String, Object>();
        for(Post item : recordList){
            roleVOList.add(new PostVO(item));
        }
        return roleVOList;
    }
}
