package org.freedom.boot.service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.freedom.boot.bean.BookWithBLOBs;
import org.freedom.boot.bean.Evaluate;
import org.freedom.boot.bean.EvaluateExample;
import org.freedom.boot.bean.EvaluateExample.Criteria;
import org.freedom.boot.dao.EvaluateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AdminEvaluateService {

	@Autowired
	EvaluateMapper evaluateMapper;

	// 带有管理者回复的评价
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
	 * 根据书本id获取评价列表
	 * 
	 * @param bookId
	 * @return
	 */
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
			if (imgString != null&&imgString!="") {
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
	 * @param evaluate
	 * @return
	 */
	public int addEvaluate(Evaluate evaluate,Integer ifHyperAdmin)
	{
		Evaluate newEvaluate = evaluate;
		if(ifHyperAdmin==1)
		{
			newEvaluate.setAdminId(0);
		}
		else {
			newEvaluate.setAdminId(1);
		}
		
		newEvaluate.setEvaluateTime(new Date());
		 
		evaluateMapper.insertSelective(newEvaluate);
		int evaluateId = newEvaluate.getEvaluateId();
		return evaluateId;
	}

}
