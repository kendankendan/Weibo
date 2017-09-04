package org.shiro.demo.controller.front;

import net.sf.json.JSONObject;
import org.shiro.demo.controller.front.interfa.returnDataInterface;
import org.shiro.demo.service.IMarkService;
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
 * 处理收藏相关的服务
 * Created by yetz on 2017/8/7.
 */

@Controller
@RequestMapping(value="/front")
public class MarkController {

    @Autowired
    private IMarkService markService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @RequestMapping(value = "/addmark",method= RequestMethod.GET,produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String addMark(@RequestParam(value="userId")Integer userId, @RequestParam(value="weiboId")String weiboId) {
        String resStr = "";
        returnDataInterface res = null;
        if(null==userId || "".equals(weiboId) || null == weiboId){
            res = new returnDataInterface(100, "userId 和 weiboId不能为空", "");
        }
        else {
            boolean markExist = markService.isMarkExist(userId, weiboId);
            boolean weiboExist = markService.isWeiboExist(weiboId);
            if (markExist) {
                res = new returnDataInterface(100, "已收藏", "");
            }
            else if(!weiboExist) {
                res = new returnDataInterface(100, "微博不存在", "");
            }
            else {
                res = markService.addMark(userId, weiboId);
            }
        }

        resStr = JSONObject.fromObject(res).toString();
        return resStr;
    }

    @RequestMapping(value = "/removemark",method= RequestMethod.GET,produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String removeLike(@RequestParam(value="userId")Integer userId, @RequestParam(value="weiboId")String weiboId) {
        String resStr = "";
        returnDataInterface res = null;
        if(null==userId || "".equals(weiboId) || null == weiboId){
            res = new returnDataInterface(100, "userId 和 weiboId不能为空", "");
        }
        else {
            boolean likeExist = markService.isMarkExist(userId, weiboId);
            boolean weiboExist = markService.isWeiboExist(weiboId);
            if (!likeExist) {
                res = new returnDataInterface(100, "还未点赞", "");
            }
            else if(!weiboExist) {
                res = new returnDataInterface(100, "微博不存在", "");
            }
            else {
                res = markService.removeMark(userId, weiboId);
            }
        }

        resStr = JSONObject.fromObject(res).toString();
        return resStr;

    }

    @RequestMapping(value = "/allmarks",method= RequestMethod.GET,produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getAllMarks(@RequestParam(value="userId")Integer userId, @RequestParam(value="pageSize")Integer pageSize, @RequestParam(value="pageNum")Integer pageNum) {
        String resStr = "";
        returnDataInterface res = null;
        if(null==userId ){
            res = new returnDataInterface(100, "非法的userID", "");
        }
        else {
            List<PostVO> markWeiboList = markService.getAllMarksByPage(userId, pageNum, pageSize);
            if(markWeiboList == null){
                res = new returnDataInterface(100, "查询错误", "");
            }
            else {
                res = new returnDataInterface(200, "成功", markWeiboList);
            }
        }

        resStr = JSONObject.fromObject(res).toString();
        return resStr;
    }
}
