package com.unibridge.app.admin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.unibridge.app.admin.dto.AdMenteeBoardListDTO;
import com.unibridge.config.MyBatisConfig;

public class AdMenteeBoardDAO {
	
	   public SqlSession sqlSession;

	   public AdMenteeBoardDAO() {
	      sqlSession = MyBatisConfig.getSqlSessionFactory().openSession(true);
	   }

		public int getTotal() {
			System.out.println("게시글 총 개수 조회 - getTotal 메소드 실행");
			return sqlSession.selectOne("admin.getTotal");
		}
	   
	   //멘티 게시판 목록 확인
		public  List<AdMenteeBoardListDTO> selectFilter(Map<String, Integer> pagefilter){
			System.out.println("모든 게시글 조회하기 - selectAll 메소드 실행 : " + pagefilter);
			List<AdMenteeBoardListDTO> list = sqlSession.selectList("admin.selectFilter", pagefilter);
			System.out.println("조회 결과 : " + list);
			return list;
		}
}
