package com.unibridge.app.mypage.mentoring.dto;

public class MentoringDTO {
	private long mentoringNumber;
	private long mentorNumber;
	private int subjectNumber;
	private String mentoringTitle;
	private String mentoringGoal;
	private String mentoringDetail;
	private Long fileNumber;
	private String fileOriginalName;
	private String fileExtension;
	private long fileSize;
	private String createdAt;
	private String updatedAt;

	public long getMentoringNumber() {
		return mentoringNumber;
	}

	public void setMentoringNumber(long mentoringNumber) {
		this.mentoringNumber = mentoringNumber;
	}

	public long getMentorNumber() {
		return mentorNumber;
	}

	public void setMentorNumber(long mentorNumber) {
		this.mentorNumber = mentorNumber;
	}

	public int getSubjectNumber() {
		return subjectNumber;
	}

	public void setSubjectNumber(int subjectNumber) {
		this.subjectNumber = subjectNumber;
	}

	public String getMentoringTitle() {
		return mentoringTitle;
	}

	public void setMentoringTitle(String mentoringTitle) {
		this.mentoringTitle = mentoringTitle;
	}

	public String getMentoringGoal() {
		return mentoringGoal;
	}

	public void setMentoringGoal(String mentoringGoal) {
		this.mentoringGoal = mentoringGoal;
	}

	public String getMentoringDetail() {
		return mentoringDetail;
	}

	public void setMentoringDetail(String mentoringDetail) {
		this.mentoringDetail = mentoringDetail;
	}

	public Long getFileNumber() {
		return fileNumber;
	}

	public void setFileNumber(Long fileNumber) {
		this.fileNumber = fileNumber;
	}

	public String getFileOriginalName() {
		return fileOriginalName;
	}

	public void setFileOriginalName(String fileOriginalName) {
		this.fileOriginalName = fileOriginalName;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

}