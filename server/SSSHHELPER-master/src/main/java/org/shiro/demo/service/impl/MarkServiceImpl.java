package org.shiro.demo.service.impl;

import org.shiro.demo.controller.front.interfa.returnDataInterface;
import org.shiro.demo.dao.util.Pagination;
import org.shiro.demo.dao.util.QueryCondition;
import org.shiro.demo.entity.Account;
import org.shiro.demo.entity.Mark;
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
 * Created by yetz on 2017/8/7.
 */

@Service("markService")
public class MarkServiceImpl implements IMarkService {

    @Resource(name="baseService")
    private IBaseService baseService;

    @Resource(name="likeService")
    private ILikeService likeService;

    @Resource(name="postService")
    private IPostService postService;

    @Resource(name="messageService")
    private IMessageService messageService;

    @Resource(name="accountService")
    private IAccountService accountService;

    public boolean isMarkExist(Integer userId, String weiboId) {
        Mark markObj = (Mark) baseService.getUniqueResultByJpql("from Mark as o where o.userId=? and o.weiboId=?", userId , weiboId);
        return (markObj != null);
    }

    public boolean isWeiboExist(String weiboId) {
        return likeService.isWeiboExist(weiboId);
    }

    public returnDataInterface addMark(Integer userId, String weiboId) {
        returnDataInterface res = null;

        try{
            long creatTime = System.currentTimeMillis() / 1000; //时间精度为秒
            String uuid = UUID.randomUUID().toString().replaceAll("-","");

            Mark markObj = new Mark(uuid, userId, weiboId, creatTime);


            baseService.save(markObj);


            Post postObj = (Post) baseService.getUniqueResultByJpql("from Post as o where o.weiboId=?", weiboId);
            postObj.setWeiboCollect(postObj.getWeiboCollect() + 1);
            baseService.save(postObj);


            //通知点赞消息
            Account account = accountService.getAccountByUserid(markObj.getUserId());
            Post pWeibo = postService.getOneWeibo(markObj.getWeiboId());
            String msgContent = "用户@" + account.getUserName() + " 收藏了您的微博";
            Message msg = new Message(pWeibo.getUserId(),markObj.getUserId(), msgContent, markObj.getWeiboId());
            messageService.sendMessage(msg);

            res = new returnDataInterface(200, "成功", "");;
        }catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    @Override
    public returnDataInterface removeMark(Integer userId, String weiboId) {
        returnDataInterface res = null;

        try{

            Mark markObj = (Mark) baseService.getUniqueResultByJpql("from Mark as o where o.userId=? and o.weiboId=?", userId , weiboId);
            baseService.delete(Mark.class, markObj.getMarkId());
            Post postObj = (Post) baseService.getUniqueResultByJpql("from Post as o where o.weiboId=?", weiboId);
            postObj.setWeiboCollect(postObj.getWeiboCollect() - 1);
            baseService.save(postObj);
            res = new returnDataInterface(200, "取消收藏", "");;
        }catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    @Override
    public List<PostVO> getAllMarksByPage(Integer userId, Integer pageNum, Integer pageSize) {
        List<PostVO> markWeiboList = new ArrayList<PostVO>();
        List<QueryCondition> queryConditions = new ArrayList<>();
        QueryCondition queryCondition = new QueryCondition("userId",QueryCondition.EQ,userId);
        queryConditions.add(queryCondition);

        Pagination<Mark> markPages = baseService.getPagination(Mark.class, queryConditions, "order by collectTime DESC", pageNum, pageSize);

        List<Mark> marks = markPages.getRecordList();
        for(Mark mark:marks) {
            Post post = postService.getOneWeibo(mark.getWeiboId());
            if(post.getWeiboAlive() == 1 ) {
                PostVO postvo = new PostVO(post);
                if (postvo.getWeiboRootId() != null) {
                    Post rootWeibo = postService.getOneWeibo(postvo.getWeiboRootId());
                    Account rootAccount = accountService.getAccountByUserid(rootWeibo.getUserId());
                    rootWeibo.setUserHead(rootAccount.getUserHead());
                    rootWeibo.setUserName(rootAccount.getUserName());

                    postvo.setRootWeibo(rootWeibo);
                }
                postvo.setWeiboisCollect(true);
                Account account = accountService.getAccountByUserid(postvo.getUserId());
                postvo.setUserHead(account.getUserHead());
                postvo.setUserName(account.getUserName());
                markWeiboList.add(postvo);
            }
        }
        return markWeiboList;
    }
}
