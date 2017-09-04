package org.shiro.demo.service.impl;

import org.shiro.demo.controller.front.interfa.returnDataInterface;
import org.shiro.demo.dao.util.Pagination;
import org.shiro.demo.dao.util.QueryCondition;
import org.shiro.demo.entity.Account;
import org.shiro.demo.entity.Like;
import org.shiro.demo.entity.Message;
import org.shiro.demo.entity.Post;
import org.shiro.demo.service.*;
import org.shiro.demo.vo.PostVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by yetz on 2017/8/4.
 */

@Service("likeService")
public class LikeServiceImpl extends DefultBaseService implements ILikeService{
    @Resource(name="baseService")
    private IBaseService baseService;


    @Resource(name="postService")
    private IPostService postService;

    @Resource(name="messageService")
    private IMessageService messageService;

    @Resource(name="accountService")
    private IAccountService accountService;

    public boolean isLikeExist(Integer userId, String weiboId) {
        Like likeObj = (Like) baseService.getUniqueResultByJpql("from Like as o where o.userId=? and o.weiboId=?", userId , weiboId);
        return (likeObj != null);
    }

    public boolean isWeiboExist(String weiboId) {
        Post postObj = (Post) baseService.getUniqueResultByJpql("from Post as o where o.weiboId=?", weiboId);
        return (postObj != null);
    }

    public returnDataInterface addLike(Integer userId, String weiboId) {
        returnDataInterface res = null;

        try{
            long creatTime = System.currentTimeMillis() / 1000; //时间精度为秒
            String uuid = UUID.randomUUID().toString().replaceAll("-","");

            Like likeObj = new Like(uuid, userId,weiboId, creatTime);

            baseService.save(likeObj);


            Post postObj = (Post) baseService.getUniqueResultByJpql("from Post as o where o.weiboId=?", weiboId);
            postObj.setWeiboLike(postObj.getWeiboLike() + 1);
            baseService.save(postObj);

            //通知点赞消息
            Account account = accountService.getAccountByUserid(likeObj.getUserId());
            Post pWeibo = postService.getOneWeibo(likeObj.getWeiboId());
            String msgContent = "用户@" + account.getUserName() + " 赞了您的微博";
            Message msg = new Message(pWeibo.getUserId(),likeObj.getUserId(), msgContent, likeObj.getWeiboId());
            messageService.sendMessage(msg);


            res = new returnDataInterface(200, "成功", "");;


        }catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    @Override
    public returnDataInterface removeLike(Integer userId, String weiboId) {
        returnDataInterface res = null;

        try{

            Like likeObj = (Like) baseService.getUniqueResultByJpql("from Like as o where o.userId=? and o.weiboId=?", userId , weiboId);
            baseService.delete(Like.class, likeObj.getLikeId());
            Post postObj = (Post) baseService.getUniqueResultByJpql("from Post as o where o.weiboId=?", weiboId);
            postObj.setWeiboLike(postObj.getWeiboLike() - 1);
            baseService.update(postObj);
            res = new returnDataInterface(200, "取消点赞", "");;
        }catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    @Override
    public List<PostVO> getAllLikesByPage(Integer userId, Integer pageNum, Integer pageSize) {
        List<PostVO> likeWeiboList = new ArrayList<PostVO>();
        List<QueryCondition> queryConditions = new ArrayList<>();
        QueryCondition queryCondition = new QueryCondition("userId",QueryCondition.EQ,userId);
        queryConditions.add(queryCondition);

        Pagination<Like> likePages = baseService.getPagination(Like.class, queryConditions, "order by likeTime DESC", pageNum, pageSize);

        List<Like> likes = likePages.getRecordList();
        for(Like like:likes) {
            Post post = postService.getOneWeibo(like.getWeiboId());
            if(post.getWeiboAlive() == 1 ) {

                PostVO postvo = new PostVO(post);


                if (postvo.getWeiboRootId() != null) {
                    Post rootWeibo = postService.getOneWeibo(postvo.getWeiboRootId());
                    Account rootAccount = accountService.getAccountByUserid(rootWeibo.getUserId());
                    rootWeibo.setUserHead(rootAccount.getUserHead());
                    rootWeibo.setUserName(rootAccount.getUserName());
                    postvo.setRootWeibo(rootWeibo);
                }
                //
              /*  Post rootWeibo = postService.getOneWeibo(postvo.getWeiboId());
                Account rootAccount = accountService.getAccountByUserid(rootWeibo.getUserId());
                rootWeibo.setUserHead(rootAccount.getUserHead());
                rootWeibo.setUserName(rootAccount.getUserName());
                postvo.setRootWeibo(rootWeibo);*/
                //
                postvo.setWeiboisLike(true);
                Account account = accountService.getAccountByUserid(postvo.getUserId());
                postvo.setUserHead(account.getUserHead());
                postvo.setUserName(account.getUserName());
                likeWeiboList.add(postvo);
            }

        }
        return likeWeiboList;
    }


}
