package org.shiro.demoTest.serviceTest;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.shiro.demo.entity.Attention;
import org.shiro.demo.service.IAttentionService;
import org.shiro.demo.service.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:applicationContext.xml"})
public class AttentionServiceTest {

	@Autowired
	private IAttentionService attentionService;
	@Autowired
	private IBaseService baseService;

	int userId = 1993771;
	int attentionUserId = 1993772;
	long attentionTime = 170000;
	Attention attention = new Attention(userId, attentionUserId, attentionTime);
	/**
	 * 期望关注成功测试
	 */
	@Test
	@Transactional
	@Rollback(true)//
	public void except_insertAttention_successTest() {
		if (attentionService.insertAttention(attention)) {
			assertEquals(attention, baseService.getById(Attention.class, attention.getAttentionId()));
		}
	}
	
	/**
	 * 期望已关注测试
	 */
	@Test
	@Transactional
	@Rollback(true)//
	public void except_AttentionExist_successTest() {
		if (attentionService.insertAttention(attention)) {
			assertEquals(true, attentionService.attentionExist(userId, attentionUserId));
		}
	}
	/**
	 * 期望取消关注成功测试
	 */
	@Test
	@Transactional
	@Rollback(true)//
	public void except_deleteAttention_successTest() {
		if (attentionService.insertAttention(attention)) {
			assertEquals(attention, baseService.getById(Attention.class, attention.getAttentionId()));
			assertEquals(true, attentionService.attentionExist(userId, attentionUserId));
			if (attentionService.deleteAttention(userId, attentionUserId)) {
			assertEquals(false, attentionService.attentionExist(userId, attentionUserId));
			}
		}
	}
}
