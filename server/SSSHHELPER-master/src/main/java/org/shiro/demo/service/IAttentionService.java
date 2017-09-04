package org.shiro.demo.service;

import java.util.List;

import org.shiro.demo.entity.Attention;

/**
 * @author chenyd
 *关注管理服务层
 */
public interface IAttentionService extends IBaseService{

	/**添加关注功能
	 * @param attention
	 * @return
	 */
	public boolean insertAttention(Attention attention);
	
	/**验证是否已关注功能
	 * @param userId
	 * @param attentionUserId
	 * @return
	 */
	public boolean attentionExist(int userId,int attentionUserId);
	
	/**获取粉丝id和数量
	 * @param attentionUserId
	 * @return
	 */
	public List<Attention> getFans(int attentionUserId);
	
	/**获取已关注用户id和数量
	 * @param userId
	 * @return
	 */
	public List<Attention> getFollow(int userId);
	
	/**取消关注
	 * @param userId
	 * @param attentionUserId
	 * @return
	 */
	public boolean deleteAttention(int userId,int attentionUserId);
}
