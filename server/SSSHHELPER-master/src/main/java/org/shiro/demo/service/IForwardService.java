package org.shiro.demo.service;

import org.shiro.demo.entity.Post;
import org.shiro.demo.vo.PostVO;
import org.shiro.demo.vo.PreForwardVO;

/**
 * Created by yetz on 2017/8/8.
 */
public interface IForwardService {
    /**
     * 获取转发前的信息
     * @param pWeibo 被转发的微博
     * @return PreForwardVO
     */
    public PreForwardVO getPreForward(Post pWeibo);

    /**
     * 提交转发信息
     * @param forwardWeibo
     * @return PostVO
     */
    public PostVO addForward(Post forwardWeibo);

}
