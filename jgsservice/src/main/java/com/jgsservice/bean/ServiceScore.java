package com.jgsservice.bean;

import javax.persistence.Column;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.jgs.bean.BaseEntity;

@Table(name = ServiceScore.TABLE_NAME)
public class ServiceScore extends BaseEntity {
	
	public static final String SP_ID = "spId";
	public static final String USER_SCORE_TYPE = "userScoreType";
	public static final int USER_SCORE_GOOD = 1;
	public static final int USER_SCORE_MIDDLE = 2;
	public static final int USER_SCORE_BAD = 3;
	
	public static final String USER_SCORE_GOOD_LABEL = "user_score_good";
	public static final String USER_SCORE_MIDDLE_LAEBL = "user_score_middle";
	public static final String USER_SCORE_BAD_LABEL = "user_score_bad";
	
	


	public static final String USER_SCORE_COMMENT = "userScoreComment";

	public static final String USER_SCORE = "userScore";

	public static final String USER_ID = "userId";

	public static final String SO_ID = "soId";

	public static final String TABLE_NAME = "ServiceScore";

	@Column(name = SO_ID)
	@Expose
	public String soId;
	
	@Column(name = SP_ID)
	@Expose
	public String spId;

	@Column(name = USER_ID)
	@Expose
	public String userId;

	@Column(name = USER_SCORE)
	@Expose
	public Integer score;
	
	@Column(name = USER_SCORE_TYPE)
	@Expose
	public Integer userScoreType;

	@Column(name = USER_SCORE_COMMENT)
	@Expose
	public String userScoreComment;
	
	
	@Expose
	public String scoreComment;
	

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}



	public Integer getScore() {
		return score;
	}

	public void setScore(Integer userScore) {
		this.score = userScore;
	}

	public String getUserScoreComment() {
		return userScoreComment;
	}

	public void setUserScoreComment(String userScoreComment) {
		this.userScoreComment = userScoreComment;
	}

	public Integer getUserScoreType() {
		return userScoreType;
	}

	public void setUserScoreType(Integer userScoreType) {
		this.userScoreType = userScoreType;
	}

	public String getSpId() {
    	return spId;
    }

	public void setSpId(String spId) {
    	this.spId = spId;
    }

	public String getScoreComment() {
    	return scoreComment;
    }

	public void setScoreComment(String scoreComment) {
    	this.scoreComment = scoreComment;
    }
	
	
	
	

}
