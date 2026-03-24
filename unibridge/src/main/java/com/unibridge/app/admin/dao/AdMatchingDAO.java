package com.unibridge.app.admin.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.unibridge.app.admin.dto.AdMatchingCancelDTO;
import com.unibridge.app.admin.dto.AdMatchingListDTO;
import com.unibridge.config.MyBatisConfig;

public class AdMatchingDAO {
	public SqlSession sqlSession;

	public AdMatchingDAO() {
		sqlSession = MyBatisConfig.getSqlSessionFactory().openSession(true);
	}

	//매칭 취소 갯수 조회
	public int getCancelTotal() {
		return sqlSession.selectOne("admin.matchingCancelTotal");
	}
	
	//매칭 취소 전체 조회
	public List<AdMatchingListDTO> getCancelSelect() {
		List<AdMatchingListDTO> list= sqlSession.selectList("admin.matchingCancelSelect");
		return list;
	}
	
	//매칭 취소 상세 조회
	public AdMatchingCancelDTO getCancelDetail(int matchingNumber) {
		return sqlSession.selectOne("admin.matchingCancelSelectDetail", matchingNumber);
	}
	
	//매칭 삭제
	public void delete(int matchingNumber) {
		System.out.println("매칭 삭제 - matchingDelete 실행");
		sqlSession.delete("admin.matchingDelete",matchingNumber);
	}
	
	
	
}
