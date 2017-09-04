package org.shiro.demo.controller.front;

import net.sf.json.JSONObject;
import org.shiro.demo.controller.front.interfa.returnDataInterface;
import org.shiro.demo.entity.Account;
import org.shiro.demo.entity.Post;
import org.shiro.demo.service.*;
import org.shiro.demo.vo.PostVO;
import org.shiro.demo.vo.PreForwardVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;


/**
 * Created by yetz on 2017/8/8.
 */

@Controller
@RequestMapping(value="/front")
public class ForwardController {
    @Autowired
    private IForwardService forwardService;

    @Autowired
    private IPostService postService;


    @Autowired
    private ILikeService likeService;




    @Autowired
    private IAccountService accountService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/preforward",method= RequestMethod.GET,produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getPreForWard(@RequestParam(value="userId")Integer userId, @RequestParam(value="weiboId")String weiboId) {
        String resStr = "";
        returnDataInterface res = null;
        Account account = accountService.getAccountByUserid(userId);

        if(null==userId || "".equals(weiboId) || null == weiboId){
            res = new returnDataInterface(100, "userId 和 weiboId不能为空", "");
        }
        else if (null ==account) {
            res = new returnDataInterface(100, "用户不存在", "");
        }
        else if(account.getUserSpeak() == Account.USER_NO_SPEAK) {
            res = new returnDataInterface(100, "用户已被禁言", "");
        }
        else {

            boolean weiboExist = likeService.isWeiboExist(weiboId);
            if(!weiboExist) {
                res = new returnDataInterface(100, "待转发的微博不存在", "");
            }
            else {
                Post weibo = postService.getOneWeibo(weiboId);
                PreForwardVO preForward = forwardService.getPreForward(weibo);
                res = new returnDataInterface(200, "成功", preForward);

            }
        }
        System.out.println(account.getUserSpeak()+":"+Account.USER_NO_SPEAK);
        JSONObject resObj =  JSONObject.fromObject(res);
        resStr = resObj.toString();

        return resStr;

    }

    @RequestMapping(value = "/forward",method= RequestMethod.GET,produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String postPreForWard(@RequestParam(value="userId")Integer userId, @RequestParam(value="weiboRootId")String weiboRootId,
                                @RequestParam(value="weiboParentId")String weiboParentId, @RequestParam(value="weiboAt") String weiboAt,
                                @RequestParam(value="weiboTopic") String weiboTopic, @RequestParam(value="weiboFwContent") String weiboFwContent) {
        String returnData = "";
        returnDataInterface returnDataInterface = null;
        try {
            //获得系统的时间，单位为毫秒,转换为秒
            long totalMilliSeconds = System.currentTimeMillis();
            long weiboTime = totalMilliSeconds / 1000;
            //uuid生成
            String uuid = UUID.randomUUID().toString().replaceAll("-","");

            boolean isValidUserName = true;
            boolean hasWeiboAt = false;
            String usernames[] = null;
            //weiboAt分割，根据用户名查用户表看存不存在
            if(weiboAt != null && !weiboAt.isEmpty()) {
                usernames = weiboAt.split("@");
                hasWeiboAt = true;
                isValidUserName = postService.validateAccount(usernames);
            }
            boolean pWeiboExist = likeService.isWeiboExist(weiboParentId);
            boolean rWeiboExist = likeService.isWeiboExist(weiboRootId);


            if(!isValidUserName ){
                returnDataInterface = new returnDataInterface(100, "@的用户名不存在", "");
            }
            else if (!pWeiboExist || !rWeiboExist)
            {
                returnDataInterface = new returnDataInterface(100, "原微博不存在", "");
            }
            else{
                //一些默认值
                Integer weiboAlive = 1;
                Integer weiboCollect = 0;
                Integer weiboForward = 0;
                Integer	weiboComment = 0;
                Integer weiboLike = 0;

                //转发的微博没有正文
                String weiboContent = "";
                String weiboPhoto = "";
                //得到account
                Account account = accountService.getAccountByUserid(userId);
                if(account == null) {
                    returnDataInterface = new returnDataInterface(100, "被转发的用户不存在", "");

                }
                else if(account.getUserSpeak() == Account.USER_NO_SPEAK) {
                    returnDataInterface = new returnDataInterface(100, "已被禁言", "");

                }
                else {

                    Post postWeibo = new Post(uuid, userId, weiboTime, weiboContent, weiboPhoto, weiboAlive, weiboCollect, weiboForward, weiboComment, weiboLike, weiboAt, weiboTopic, account.getUserName(), weiboRootId, weiboParentId, false, false);
                    postWeibo.setWeiboFwContent(weiboFwContent);
                    PostVO postVO = forwardService.addForward(postWeibo);
                    Post rootWeibo = postService.getOneWeibo(postVO.getWeiboRootId());
                    Account rootAccount = accountService.getAccountByUserid(rootWeibo.getUserId());

                    rootWeibo.setUserHead(rootAccount.getUserHead());
                    rootWeibo.setUserName(rootAccount.getUserName());
                    postVO.setRootWeibo(rootWeibo);
                    if(hasWeiboAt) {
                        postVO.setWeiboAts(usernames);
                    }


                    if (postVO != null) {
                        returnDataInterface = new returnDataInterface(200, "发表成功", postVO);
                    } else {
                        returnDataInterface = new returnDataInterface(100, "发表失败", "");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        returnData = JSONObject.fromObject(returnDataInterface).toString();
        return returnData;
    }

}
