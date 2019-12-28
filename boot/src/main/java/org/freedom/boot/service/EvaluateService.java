package org.freedom.boot.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.freedom.boot.bean.Collect;
import org.freedom.boot.bean.EvaluateExample;
import org.freedom.boot.bean.Evaluate;
import org.freedom.boot.bean.EvaluateExample;
import org.freedom.boot.bean.EvaluateExample.Criteria;
import org.freedom.boot.bean.EvaluateLike;
import org.freedom.boot.bean.EvaluateLikeExample;
import org.freedom.boot.dao.EvaluateLikeMapper;
import org.freedom.boot.dao.EvaluateMapper;
import org.freedom.boot.service.AdminEvaluateService.EvaluateWithReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class EvaluateService {

	@Autowired
	EvaluateMapper evaluateMapper;

	@Autowired
	EvaluateLikeMapper evaluateLikeMapper;

	// 带有回复的评价
	class EvaluateWithReply {
		Evaluate userEvaluate;
		List<Evaluate> reply;
		List<String> imgList;

		public EvaluateWithReply() {
			super();
			// TODO Auto-generated constructor stub
		}

		public EvaluateWithReply(Evaluate userEvaluate, List<Evaluate> reply, List<String> imgList) {
			super();
			this.userEvaluate = userEvaluate;
			this.reply = reply;
			this.imgList = imgList;
		}

		public List<String> getImgList() {
			return imgList;
		}

		public void setImgList(List<String> imgList) {
			this.imgList = imgList;
		}

		public Evaluate getUserEvaluate() {
			return userEvaluate;
		}

		public void setUserEvaluate(Evaluate userEvaluate) {
			this.userEvaluate = userEvaluate;
		}

		public List<Evaluate> getReply() {
			return reply;
		}

		public void setReply(List<Evaluate> reply) {
			this.reply = reply;
		}

	}

	/**
	 * 根据书本id获取评价列表(redis缓存)
	 * 
	 * @param bookId
	 * @return
	 */
	@Cacheable
	public List<EvaluateWithReply> getEvaluateList(Integer bookId) {
		EvaluateExample evaluateExample = new EvaluateExample();
		Criteria criteria = evaluateExample.createCriteria();
		criteria.andBookIdEqualTo(bookId);
		criteria.andToEvaluateIdIsNull();
		evaluateExample.setOrderByClause("evaluate_time ASC");
		List<Evaluate> evaluates = evaluateMapper.selectByExampleWithUser(evaluateExample);
		List<EvaluateWithReply> evaluateWithReplies = new ArrayList<EvaluateWithReply>();
		for (Evaluate evaluate : evaluates) {
			EvaluateExample evaluateExample2 = new EvaluateExample();
			Criteria criteria2 = evaluateExample2.createCriteria();
			criteria2.andToEvaluateIdEqualTo(evaluate.getEvaluateId());
			criteria2.andAdminIdIsNotNull();
			evaluateExample2.setOrderByClause("evaluate_time ASC");
			String imgString = evaluate.getImg();
			List<String> imgs = new ArrayList<String>();
			if (imgString != null && imgString != "") {
				if (imgString.contains(";")) {
					imgs = Arrays.asList(imgString.split(";"));
				} else {
					imgs.add(imgString);
				}
			}

			evaluateWithReplies
					.add(new EvaluateWithReply(evaluate, evaluateMapper.selectByExample(evaluateExample2), imgs));

		}

		return evaluateWithReplies;
	}

	/**
	 * 添加评论
	 * 
	 * @param evaluate
	 * @return
	 */
	public int addEvaluateReply(Integer orderItemId, Integer evaluateId, Integer userId, String content, Date time) {
		Evaluate newEvaluate = new Evaluate();
		newEvaluate.setOrderItemId(orderItemId);
		newEvaluate.setToEvaluateId(evaluateId);
		newEvaluate.setUserId(userId);
		newEvaluate.setContent(content);
		newEvaluate.setEvaluateTime(time);

		return evaluateMapper.insertSelective(newEvaluate);
	}

	/**
	 * 删除评论
	 * 
	 * @param id
	 * @return
	 */
	public int deleteEvaluateReply(Integer id) {

		return evaluateMapper.deleteByPrimaryKey(id);

	}

	/**
	 * 删除评价
	 * 
	 * @param id
	 * @return
	 */
	public int deleteEvaluate(Integer id) {
		System.out.println(id);
		if (evaluateMapper.deleteByPrimaryKey(id) == 1) {
			//删除所有评论
			EvaluateExample example = new EvaluateExample();
			Criteria criteria = example.createCriteria();
			criteria.andToEvaluateIdEqualTo(id);
			return evaluateMapper.deleteByExample(example);
		} else {
			return 0;
		}
	}

	/**
	 * 点赞
	 * 
	 * @param userId
	 * @param evaluateId
	 * @return
	 */
	public int evaluateLike(Integer userId, Integer evaluateId) {
		EvaluateLikeExample example = new EvaluateLikeExample();
		EvaluateLikeExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		criteria.andEvaluateIdEqualTo(evaluateId);
		// 取消点赞
		if (evaluateLikeMapper.selectByExample(example) != null) {
			return 1;
		}
		// 添加点赞
		else {
			EvaluateLike evaluateLike = new EvaluateLike();
			evaluateLike.setUserId(userId);
			evaluateLike.setEvaluateId(evaluateId);
			return evaluateLikeMapper.insertSelective(evaluateLike);
		}

	}

}
