package org.shiro.demo.service.impl;

import org.shiro.demo.entity.Account;
import org.shiro.demo.entity.Message;
import org.shiro.demo.entity.Post;
import org.shiro.demo.service.*;
import org.shiro.demo.vo.PostVO;
import org.shiro.demo.vo.PreForwardVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yetz on 2017/8/8.
 */

@Service("forwardService")
public class ForwardServiceImpl implements IForwardService {
    @Resource(name="baseService")
    private IBaseService baseService;

    @Resource(name="postService")
    private IPostService postService;

    @Resource(name="messageService")
    private IMessageService messageService;

    @Resource(name="accountService")
    private IAccountService accountService;

    @Override
    public PreForwardVO getPreForward(Post pWeibo) {
        PreForwardVO forwardVO = new PreForwardVO(pWeibo);
        return forwardVO;
    }

    @Override
    public PostVO addForward(Post forwardWeibo) {
        PostVO res = null;
        try{
            baseService.save(forwardWeibo);
            String pID = forwardWeibo.getWeiboParentId();
            String rootID = forwardWeibo.getWeiboRootId();
            Post pWeibo = postService.getOneWeibo(pID);
            pWeibo.setWeiboForward(pWeibo.getWeiboForward()+1);
            baseService.update(pWeibo);

            String msgContent1 = "用户@" + forwardWeibo.getUserName() + " 转发了您的微博";
            Message msg1 = new Message(pWeibo.getUserId(),forwardWeibo.getUserId(),msgContent1,pID);
            messageService.sendMessage(msg1);

            if(!rootID.equals(pID)) {  //如果root 和 parent是同一条，只增加一次转发
                Post rootWeibo = postService.getOneWeibo(rootID);
                rootWeibo.setWeiboForward(rootWeibo.getWeiboForward() + 1);
                baseService.update(rootWeibo);

                String msgContent2 = "用户@" + forwardWeibo.getUserName() + " 转发了您的微博";
                Message msg2 = new Message(rootWeibo.getUserId(),forwardWeibo.getUserId(),msgContent2,rootID);
                messageService.sendMessage(msg2);
            }
            res = new PostVO(forwardWeibo);

            Post rootWeibo = postService.getOneWeibo(res.getWeiboRootId());
            Account rootAccount = accountService.getAccountByUserid(rootWeibo.getUserId());
            rootWeibo.setUserHead(rootAccount.getUserHead());

            Account account = accountService.getAccountByUserid(res.getUserId());
            res.setUserHead(account.getUserHead());
            res.setRootWeibo(rootWeibo);
        }catch (Exception e) {
            e.printStackTrace();
            res = null;
        }
        return res;
    }
}
