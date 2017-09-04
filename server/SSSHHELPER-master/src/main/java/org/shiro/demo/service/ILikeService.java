package org.shiro.demo.service;

import org.shiro.demo.controller.front.interfa.returnDataInterface;
import org.shiro.demo.vo.PostVO;

import java.util.List;

/**
 * Created by yetz on 2017/8/4.
 */
public interface ILikeService extends IBaseService{
    /**
     * 验证用户是否已经赞过
     * @param userId
     * @param weiboId
     * @return 0:不存在 1 存在
     */

    public boolean isLikeExist(Integer userId, String weiboId);

    /**
     * 验证微博是否存在
     * @param weiboId
     * @return 0:不存在 1 存在
     */

    public boolean isWeiboExist(String weiboId);


    /**
     * 添加赞
     * 要求被赞的weiboId存在 且点赞不存在
     * @param userId
     * @param weiboId
     * @return 见接口文档
     */
    public returnDataInterface addLike(Integer userId, String weiboId);

    /**
     * 取消赞
     * 要求被赞的weiboId存在 且点赞不存在
     * @param userId
     * @param weiboId
     * @return 见接口文档
     */
    public returnDataInterface removeLike(Integer userId, String weiboId);


    /**
     *
     * 分页查询一个用户的所有点赞微博
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return List<PostVO>
     */
    public List<PostVO> getAllLikesByPage(Integer userId, Integer pageNum, Integer pageSize);
}
