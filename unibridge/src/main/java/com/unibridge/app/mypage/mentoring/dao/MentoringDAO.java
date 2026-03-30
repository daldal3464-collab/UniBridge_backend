package com.unibridge.app.mypage.mentoring.dao;

import org.apache.ibatis.session.SqlSession;
import java.util.List;
import com.unibridge.app.mypage.mentoring.dto.MentoringDTO;
import com.unibridge.config.MyBatisConfig;

public class MentoringDAO {
	public SqlSession sqlSession;

	public MentoringDAO() {
        try {
            // true 설정을 통해 오토 커밋 활성화
            sqlSession = MyBatisConfig.getSqlSessionFactory().openSession(true);
            System.out.println("[DAO] SqlSession 생성 성공");
        } catch (Exception e) {
            System.out.println("[DAO] SqlSession 생성 실패: " + e.getMessage());
            e.printStackTrace();
        }
    }

	// 멘토링 등록 (UB_FILE과 UB_MENTORING 동시 처리)
    public void insert(MentoringDTO mentoringDTO) {
        try {
            // Mapper의 insert 태그를 실행. selectKey에 의해 fileNumber가 DTO에 자동으로 채워짐
            sqlSession.insert("mentoring.insert", mentoringDTO);
            System.out.println("[DAO] insert 성공 - 생성된 파일 번호: " + mentoringDTO.getFileNumber());
        } catch (Exception e) {
            System.out.println("[DAO] insert 중 오류 발생!");
            e.printStackTrace();
            throw e;
        }
    }

	// 멘토링 상세 조회 (매개변수명 및 타입 변경: int -> long)
	public MentoringDTO select(long mentoringNumber) {
		System.out.println("[DAO] select 호출됨 - 번호: " + mentoringNumber);
		MentoringDTO dto = null;
		try {
			dto = sqlSession.selectOne("mentoring.select", mentoringNumber);
			if (dto != null) {
				System.out.println("[DAO] select 성공 - 조회된 제목: " + dto.getMentoringTitle());
			} else {
				System.out.println("[DAO] select 결과 없음 - 해당 번호: " + mentoringNumber);
			}
		} catch (Exception e) {
			System.out.println("[DAO] select 중 오류 발생!");
			e.printStackTrace();
		}
		return dto;
	}

	// 멘토링 수정 (DTO의 필드명 변경에 따른 호출 수정)
	public void update(MentoringDTO mentoringDTO) {
		System.out.println("[DAO] update 호출됨 - 수정 대상 번호: " + mentoringDTO.getMentoringNumber());
		try {
			int result = sqlSession.update("mentoring.update", mentoringDTO);
			System.out.println("[DAO] update 성공 여부 (영향받은 행 수): " + result);
		} catch (Exception e) {
			System.out.println("[DAO] update 중 오류 발생!");
			e.printStackTrace();
		}
	}

	// 멘토링 삭제 (매개변수명 및 타입 변경)
	public void delete(long mentoringNumber) {
		System.out.println("[DAO] delete 호출됨 - 삭제 대상 번호: " + mentoringNumber);
		try {
			int result = sqlSession.delete("mentoring.delete", mentoringNumber);
			System.out.println("[DAO] delete 성공 여부 (영향받은 행 수): " + result);
		} catch (Exception e) {
			System.out.println("[DAO] delete 중 오류 발생!");
			e.printStackTrace();
		}
	}

	// 멘토링 중복 체크 (파라미터 타입 일관성 유지: long)
	public int checkAlreadyExists(long mentorNumber) {
	    return sqlSession.selectOne("mentoring.checkAlreadyExists", mentorNumber);
	}

	// 멘토링 있을시 가져오기
	public Long getMentoringNumberByMentor(long mentorNumber) {
	    // Mapper의 id="getMentoringNumberByMentor"와 일치해야 함 (이 부분이 없어서 에러남)
	    return sqlSession.selectOne("mentoring.getMentoringNumberByMentor", mentorNumber);
	}
	
	
}