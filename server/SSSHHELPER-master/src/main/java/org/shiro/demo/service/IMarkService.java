package org.shiro.demo.service;

import org.shiro.demo.controller.front.interfa.returnDataInterface;
import org.shiro.demo.vo.PostVO;

import java.util.List;

/**
 * Created by yetz on 2017/8/7.
 */
public interface IMarkService {
    /**
     * 验证用户是否已经收藏
     * @param userId
     * @param weiboId
     * @return 0:不存在 1 存在
     */

    public boolean isMarkExist(Integer userId, String weiboId);

    /**
     * 验证微博是否存在
     * @param weiboId
     * @return 0:不存在 1 存在
     */

    public boolean isWeiboExist(String weiboId);


    /**
     * 添加收藏
     * 要求被收藏的weiboId存在 且该用户还未收藏
     * @param userId
     * @param weiboId
     * @return 见接口文档
     */
    public returnDataInterface addMark(Integer userId, String weiboId);

    /**
     * 移除收藏
     * 要求被收藏的weiboId存在 且该用户还未收藏
     * @param userId
     * @param weiboId
     * @return 见接口文档
     */
    public returnDataInterface removeMark(Integer userId, String weiboId);


    /**
     *
     * 分页查询一个用户的所有收藏微博
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return List<PostVO>
     */
    public List<PostVO> getAllMarksByPage(Integer userId, Integer pageNum, Integer pageSize);
}
