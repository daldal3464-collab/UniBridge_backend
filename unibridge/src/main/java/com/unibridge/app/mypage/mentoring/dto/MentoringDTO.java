package com.unibridge.app.mypage.mentoring.dto;

public class MentoringDTO {
    // 필드명 변경: internalId -> mentoringNumber
    private long mentoringNumber; 
    private long mentorNumber;
    private int subjectNumber;
    private String mentoringTitle;
    private String mentoringGoal;
    private String mentoringDetail;
    private Long fileNumber; // NULL 허용을 위해 Wrapper 클래스 권장
    private String fileName;
    private String createdAt; // DB 컬럼명 CREATED_AT과 일치 유도
    private String updatedAt;

    // 기본 생성자
    public MentoringDTO() {}

    // Getter & Setter
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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

    @Override
    public String toString() {
        return "MentoringDTO [mentoringNumber=" + mentoringNumber + ", mentoringTitle=" + mentoringTitle + "]";
    }
}