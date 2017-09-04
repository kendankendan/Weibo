package org.shiro.demo.controller.front;

import net.sf.json.JSONObject;
import org.shiro.demo.controller.front.interfa.returnDataInterface;
import org.shiro.demo.service.ILikeService;
import org.shiro.demo.util.FastJsonTool;
import org.shiro.demo.vo.PostVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 处理点赞相关的HTTP服务
 * Created by yetz on 2017/8/4.
 */

@Controller
@RequestMapping(value="/front")
public class LikeController {

    @Autowired
    private ILikeService likeService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/addlike",method= RequestMethod.GET,produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String addLike(@RequestParam(value="userId")Integer userId, @RequestParam(value="weiboId")String weiboId) {
        String resStr = "";
        returnDataInterface res = null;
        if(null==userId || "".equals(weiboId) || null == weiboId){
            res = new returnDataInterface(100, "userId 和 weiboId不能为空", "");
        }
        else {
            boolean likeExist = likeService.isLikeExist(userId, weiboId);
            boolean weiboExist = likeService.isWeiboExist(weiboId);
            if (likeExist) {
                res = new returnDataInterface(100, "点赞已存在", "");
            }
            else if(!weiboExist) {
                res = new returnDataInterface(100, "微博不存在", "");
            }
            else {
                res = likeService.addLike(userId, weiboId);
            }
        }

        resStr = FastJsonTool.createJsonString(res);
        return resStr;

    }

    @RequestMapping(value = "/removelike",method= RequestMethod.GET,produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String removeLike(@RequestParam(value="userId")Integer userId, @RequestParam(value="weiboId")String weiboId) {
        String resStr = "";
        returnDataInterface res = null;
        if(null==userId || "".equals(weiboId) || null == weiboId){
            res = new returnDataInterface(100, "userId 和 weiboId不能为空", "");
        }
        else {
            boolean likeExist = likeService.isLikeExist(userId, weiboId);
            boolean weiboExist = likeService.isWeiboExist(weiboId);
            if (!likeExist) {
                res = new returnDataInterface(100, "还未点赞", "");
            }
            else if(!weiboExist) {
                res = new returnDataInterface(100, "微博不存在", "");
            }
            else {
                res = likeService.removeLike(userId, weiboId);
            }
        }

        resStr = JSONObject.fromObject(res).toString();
        return resStr;

    }

    @RequestMapping(value = "/alllikes",method= RequestMethod.GET,produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getAllLikes(@RequestParam(value="userId")Integer userId, @RequestParam(value="pageSize")Integer pageSize, @RequestParam(value="pageNum")Integer pageNum) {
        String resStr = "";
        returnDataInterface res = null;
        if(null==userId ){
            res = new returnDataInterface(100, "非法的userID", "");
        }
        else {
            List<PostVO> likeWeiboList = likeService.getAllLikesByPage(userId, pageNum, pageSize);
            if(likeWeiboList == null){
                res = new returnDataInterface(100, "查询错误", "");
            }
            else {
                res = new returnDataInterface(200, "成功", likeWeiboList);
            }
        }

        resStr = JSONObject.fromObject(res).toString();

        return resStr;
    }
}
