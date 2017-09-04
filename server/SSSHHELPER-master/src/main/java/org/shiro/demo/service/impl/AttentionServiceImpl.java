package org.shiro.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.shiro.demo.dao.util.QueryCondition;
import org.shiro.demo.entity.Attention;
import org.shiro.demo.service.IAttentionService;
import org.shiro.demo.service.IBaseService;
import org.springframework.stereotype.Service;
@Service("attentionService")
public class AttentionServiceImpl extends DefultBaseService implements IAttentionService{
	
	@Resource(name="baseService")
	private IBaseService baseService;
	/**添加关注功能
	 * @param attention
	 * @return
	 */
	@Override
	public boolean insertAttention(Attention attention) {
		boolean flag = false;
		try {
			baseService.save(attention);
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
	/**验证是否已关注功能
	 * @param userId
	 * @param attentionUserId
	 * @return
	 */
	@Override
	public boolean attentionExist(int userId, int attentionUserId) {
		boolean flag = false;
		List<QueryCondition> queryConditions = new ArrayList<>();
		QueryCondition queryCondition = new QueryCondition("attentionUserId",QueryCondition.EQ,attentionUserId);
		queryConditions.add(queryCondition);
		List<Attention> attentions = baseService.get(Attention.class, queryConditions);
		for (Attention attention : attentions) {
			if (attention.getUserId() == userId) {
				flag = true;
			}
		}
		return flag;
	}
	/**获取粉丝id
	 * @param attentionUserId
	 * @return
	 */
	@Override
	public List<Attention> getFans(int attentionUserId) {
		List<QueryCondition> queryConditions = new ArrayList<>();
		QueryCondition queryCondition = new QueryCondition("attentionUserId",QueryCondition.EQ,attentionUserId);
		queryConditions.add(queryCondition);
		List<Attention> list = baseService.get(Attention.class, queryConditions);
		return list;
	}
	/**获取已关注用户id
	 * @param userId
	 * @return
	 */
	@Override
	public List<Attention> getFollow(int userId) {
		List<QueryCondition> queryConditions = new ArrayList<>();
		QueryCondition queryCondition = new QueryCondition("userId",QueryCondition.EQ,userId);
		queryConditions.add(queryCondition);
		List<Attention> list = baseService.get(Attention.class, queryConditions);
		return list;
	}
	/* 
	 * 取消关注功能
	 */
	@Override
	public boolean deleteAttention(int userId, int attentionUserId) {
		boolean flag = false;
		int result = baseService.executeBySQL("delete from weibo_attention where userId = ? and attentionUserId = ?", userId,attentionUserId);
		if (result == 1) {
			flag = true;
		}
		return flag;
	}

}
