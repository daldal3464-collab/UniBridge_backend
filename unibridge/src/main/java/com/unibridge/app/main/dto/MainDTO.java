package com.unibridge.app.main.dto;

import java.util.Date;

public class MainDTO {

	// =============================================
	// 1. 진행중인 대회 정보 (UB_CONTEST)
	// =============================================
	// CREATE TABLE UB_CONTEST (
	// contest_number NUMBER NOT NULL,
	// title VARCHAR2(255) NOT NULL,
	// detail VARCHAR2(1024) NOT NULL,
	// start_date DATE,
	// end_date DATE NOT NULL,
	// host_company VARCHAR2(100) NOT NULL,
	// tech_stack VARCHAR2(500),
	// status VARCHAR2(20) NOT NULL,
	// CONSTRAINT pk_ub_contest PRIMARY KEY (contest_number)
	// );
	public static class ContestDTO {
		private int contestNumber;
		private String title;
		private String detail;
		private Date startDate;
		private Date endDate;
		private String hostCompany;
		private String techStack;
		private String status;

		public ContestDTO() {
		}

		public int getContestNumber() {
			return contestNumber;
		}

		public void setContestNumber(int v) {
			this.contestNumber = v;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String v) {
			this.title = v;
		}

		public String getDetail() {
			return detail;
		}

		public void setDetail(String v) {
			this.detail = v;
		}

		public Date getStartDate() {
			return startDate;
		}

		public void setStartDate(Date v) {
			this.startDate = v;
		}

		public Date getEndDate() {
			return endDate;
		}

		public void setEndDate(Date v) {
			this.endDate = v;
		}

		public String getHostCompany() {
			return hostCompany;
		}

		public void setHostCompany(String v) {
			this.hostCompany = v;
		}

		public String getTechStack() {
			return techStack;
		}

		public void setTechStack(String v) {
			this.techStack = v;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String v) {
			this.status = v;
		}

		@Override
		public String toString() {
			return "ContestDTO [contestNumber=" + contestNumber + ", title=" + title + ", status=" + status
					+ ", endDate=" + endDate + "]";
		}
	}

	// =============================================
	// 2. 추천 멘토 정보 (UB_MENTORING + UB_MEMBER + UB_SUBJECT 조인)
	// =============================================
	// UB_MENTORING: INTERNAL_ID, MENTOR_NUMBER, SUBJECT_NUMBER,
	// MENTORING_TITLE, MENTORING_GOAL, MENTORING_DETAIL,
	// MENTORING_FILE, CREATED_AT, UPDATED_AT
	// UB_MEMBER: member_number, member_nickname, member_profile(파일번호)
	// UB_SUBJECT: subject_name
	// 2. 추천 멘토 정보 (수정됨)
	public static class MentorCardDTO {
		// [수정] internalId -> mentoringNumber (타입도 long 권장)
		private long mentoringNumber;
		private long mentorNumber;
		private String mentoringTitle;
		private String mentoringGoal;
		private String mentoringDetail;
		private String mentoringFile;
		private int subjectNumber;
		private String subjectName;
		private String memberNickname;
		private int memberProfile;

		public MentorCardDTO() {
		}

		// [수정] Getter/Setter 명칭 변경
		public long getMentoringNumber() {
			return mentoringNumber;
		}

		public void setMentoringNumber(long v) {
			this.mentoringNumber = v;
		}

		public long getMentorNumber() {
			return mentorNumber;
		}

		public void setMentorNumber(long v) {
			this.mentorNumber = v;
		}

		public String getMentoringTitle() {
			return mentoringTitle;
		}

		public void setMentoringTitle(String v) {
			this.mentoringTitle = v;
		}

		public String getMentoringGoal() {
			return mentoringGoal;
		}

		public void setMentoringGoal(String v) {
			this.mentoringGoal = v;
		}

		public String getMentoringDetail() {
			return mentoringDetail;
		}

		public void setMentoringDetail(String v) {
			this.mentoringDetail = v;
		}

		public String getMentoringFile() {
			return mentoringFile;
		}

		public void setMentoringFile(String v) {
			this.mentoringFile = v;
		}

		public int getSubjectNumber() {
			return subjectNumber;
		}

		public void setSubjectNumber(int v) {
			this.subjectNumber = v;
		}

		public String getSubjectName() {
			return subjectName;
		}

		public void setSubjectName(String v) {
			this.subjectName = v;
		}

		public String getMemberNickname() {
			return memberNickname;
		}

		public void setMemberNickname(String v) {
			this.memberNickname = v;
		}

		public int getMemberProfile() {
			return memberProfile;
		}

		public void setMemberProfile(int v) {
			this.memberProfile = v;
		}

		@Override
		public String toString() {
			return "MentorCardDTO [mentoringNumber=" + mentoringNumber + ", memberNickname=" + memberNickname
					+ ", subjectName=" + subjectName + ", mentoringTitle=" + mentoringTitle + "]";
		}
	}
}
