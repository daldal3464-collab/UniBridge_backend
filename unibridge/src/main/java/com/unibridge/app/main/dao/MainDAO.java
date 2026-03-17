package com.unibridge.app.main.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.unibridge.app.main.dto.MainDTO.ContestDTO;
import com.unibridge.app.main.dto.MainDTO.MentorCardDTO;
import com.unibridge.config.MyBatisConfig;

public class MainDAO {

    private SqlSession sqlSession;

    public MainDAO() {
        // true: auto-commit (SELECT만 사용하므로 문제없음)
        sqlSession = MyBatisConfig.getSqlSessionFactory().openSession(true);
    }

    /**
     * 메인 페이지에 표시할 진행중인 대회 목록을 조회합니다.
     * mainMapper.xml의 namespace="main", id="getContestList" 쿼리를 실행합니다.
     *
     * @return 진행중인 대회 목록 (status = '진행중' 기준)
     */
    public List<ContestDTO> getContestList() {
        return sqlSession.selectList("main.getContestList");
    }

    /**
     * 메인 페이지에 표시할 추천 멘토 목록을 조회합니다.
     * UB_MENTORING + UB_MEMBER + UB_SUBJECT 조인 쿼리를 실행합니다.
     * mainMapper.xml의 namespace="main", id="getMentorCardList" 쿼리를 실행합니다.
     *
     * @return 추천 멘토 카드 목록 (최신 등록순 7건)
     */
    public List<MentorCardDTO> getMentorCardList() {
        return sqlSession.selectList("main.getMentorCardList");
    }
}
