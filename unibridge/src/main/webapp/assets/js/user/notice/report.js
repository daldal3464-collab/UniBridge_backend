/**
 * 
 */
function createLearningReportGroup(data) {
  return data.map((value) => createLearningReportWeek(value)).join("\n");
}

function createLearningReportWeek(learningReport) {
  const reportsHtml = learningReport.reports.map((value) => createLearningReport(value)).join("\n");
  const nowDate = new Date();
  const firstDay = new Date(nowDate.getFullYear(), nowDate.getMonth(), 1);
  const dayOfMonth = nowDate.getDate();

  const curMonth = (nowDate).getMonth() + 1;
  const curWeek  = Math.ceil((dayOfMonth + firstDay.getDay()) / 7);
  const isCurrentWeek = (curMonth === learningReport.month) && 
    (learningReport.weekOfMonth === curWeek);

  const html = `
    <li class="list-content">
      <div class="list-content__title">
        <div class="list-content__date">
          <span>${learningReport.month}월 ${learningReport.weekOfMonth}주차</span>
        </div>
        <div class="list-content__subtitle">
          <span class="date">${learningReport.startDate} ~ ${learningReport.endDate}</span>
          <span class="desc">${isCurrentWeek ? "이번" : "저번"} 주에 작성된 학습보고서</span>
        </div>
      </div>
      <div class="list-content__body">
        <div class="list-content__body-title">
          <span class="write-date">작성일</span>
          <span class="report-name">학습일지명</span>
          <span class="mentor-mentee-name">멘토/멘티</span>
          <span class="subject-name">과목명</span>
          <span class="week">주차</span>
          <span class="session">회차</span>
          <span class="actions">관리</span>
        </div>
        <ul class="list-content__body-content">${reportsHtml}</ul>
      </div>
    </li>
  `;

  return html;
}

function createLearningReport(report) {
  const html = `
    <li class="list-learning-content rid-${report.reportId}">
      <div class="date">${report.writtenAt}</div>
      <div class="report-name">
        <div class="title">${report.reportTitle}</div>
        <div class="subtitle">${report.reportSubtitle}</div>
      </div>
      <div class="mentor-mentee-name">
        <div class="mentor">${report.mentor.name}</div>
        <div class="mentee">${report.mentee.name}</div>
      </div>
      <div class="subject-name">${report.subjectName}</div>
      <div class="week">${report.studyWeekLabel}주차</div>
      <div class="session">${report.sessionLabel}회차</div>
      <div class="actions">
        <div class="detail">상세보기</div>
        ${report.actions.canEdit ? 
          '<div class="modify">수정</div>' : ""}
      </div>
    </li>
  `;

  return html;
}


const learningReportDummyResponse = {
  data: [
    {
      month: 3,
      weekOfMonth: 2,
      startDate: "2026.03.08",
      endDate: "2026.03.15",
      reports: [
        {
          reportId: 1001,
          writtenAt: "2026.03.12",
          reportTitle: "영어 독해 학습일지",
          reportSubtitle: "문장 해석 및 어휘 정리",
          reportContent:
            "관계대명사와 분사구문이 포함된 독해 지문을 분석하고, 핵심 어휘의 뜻과 쓰임을 정리하였다.",
          mentor: {
            id: 201,
            name: "김민수"
          },
          mentee: {
            id: 301,
            name: "이서연"
          },
          subjectName: "영어 독해와 작문",
          studyWeekLabel: 4,
          sessionLabel: 7,
          actions: {
            canView: true,
            canEdit: true
          }
        },
        {
          reportId: 1002,
          writtenAt: "2026.03.10",
          reportTitle: "영어 독해 학습일지",
          reportSubtitle: "문장 해석 및 어휘 정리",
          reportContent:
            "지문의 문장 구조를 끊어 읽는 연습을 진행하고, 주요 표현 10개를 예문과 함께 복습하였다.",
          mentor: {
            id: 201,
            name: "김민수"
          },
          mentee: {
            id: 301,
            name: "이서연"
          },
          subjectName: "영어 독해와 작문",
          studyWeekLabel: 4,
          sessionLabel: 6,
          actions: {
            canView: true,
            canEdit: false
          }
        },
        {
          reportId: 1003,
          writtenAt: "2026.03.09",
          reportTitle: "수학 개념 복습일지",
          reportSubtitle: "삼각함수와 그래프 정리",
          reportContent:
            "삼각함수의 기본 성질과 그래프 변화 원리를 정리하고, 대표 유형 문제를 풀이하였다.",
          mentor: {
            id: 202,
            name: "박지훈"
          },
          mentee: {
            id: 302,
            name: "최유진"
          },
          subjectName: "수학 II",
          studyWeekLabel: 4,
          sessionLabel: 5,
          actions: {
            canView: true,
            canEdit: true
          }
        }
      ]
    },
    {
      month: 3,
      weekOfMonth: 1,
      startDate: "2026.03.01",
      endDate: "2026.03.07",
      reports: [
        {
          reportId: 1004,
          writtenAt: "2026.03.02",
          reportTitle: "영어 독해 학습일지",
          reportSubtitle: "문장 해석 및 어휘 정리",
          reportContent:
            "짧은 비문학 지문을 읽고 문장별 해석 연습을 수행했으며, 낯선 어휘를 별도로 정리하였다.",
          mentor: {
            id: 201,
            name: "김민수"
          },
          mentee: {
            id: 301,
            name: "이서연"
          },
          subjectName: "영어 독해와 작문",
          studyWeekLabel: 3,
          sessionLabel: 3,
          actions: {
            canView: true,
            canEdit: false
          }
        },
        {
          reportId: 1005,
          writtenAt: "2026.03.01",
          reportTitle: "수학 개념 복습일지",
          reportSubtitle: "함수, 그래프, 기초 연산",
          reportContent:
            "일차함수와 이차함수의 그래프 특징을 복습하고, 함수값 계산 및 기본 연산 문제를 해결하였다.",
          mentor: {
            id: 201,
            name: "김민수"
          },
          mentee: {
            id: 301,
            name: "이서연"
          },
          subjectName: "수학 II",
          studyWeekLabel: 3,
          sessionLabel: 4,
          actions: {
            canView: true,
            canEdit: false
          }
        },
        {
          reportId: 1006,
          writtenAt: "2026.03.03",
          reportTitle: "과학 탐구 학습일지",
          reportSubtitle: "힘과 운동 단원 정리",
          reportContent:
            "힘의 합성과 운동 법칙의 핵심 개념을 정리하고, 실생활 예시를 통해 적용 방법을 학습하였다.",
          mentor: {
            id: 203,
            name: "정다은"
          },
          mentee: {
            id: 303,
            name: "한지호"
          },
          subjectName: "통합과학",
          studyWeekLabel: 3,
          sessionLabel: 2,
          actions: {
            canView: true,
            canEdit: true
          }
        }
      ]
    },
    {
      month: 2,
      weekOfMonth: 4,
      startDate: "2026.02.22",
      endDate: "2026.02.28",
      reports: [
        {
          reportId: 1007,
          writtenAt: "2026.02.27",
          reportTitle: "영단어 암기 학습일지",
          reportSubtitle: "기출 어휘 50개 암기",
          reportContent:
            "기출 빈출 어휘 50개를 암기하고, 유의어와 반의어를 함께 정리하여 반복 학습하였다.",
          mentor: {
            id: 204,
            name: "서현우"
          },
          mentee: {
            id: 304,
            name: "김도윤"
          },
          subjectName: "영어 어휘",
          studyWeekLabel: 2,
          sessionLabel: 5,
          actions: {
            canView: true,
            canEdit: false
          }
        },
        {
          reportId: 1008,
          writtenAt: "2026.02.25",
          reportTitle: "국어 비문학 학습일지",
          reportSubtitle: "독서 지문 구조 분석",
          reportContent:
            "비문학 독서 지문의 중심 문장과 전개 방식을 파악하고, 문단별 핵심 내용을 요약하였다.",
          mentor: {
            id: 205,
            name: "이하린"
          },
          mentee: {
            id: 305,
            name: "박시우"
          },
          subjectName: "국어",
          studyWeekLabel: 2,
          sessionLabel: 4,
          actions: {
            canView: true,
            canEdit: true
          }
        },
        {
          reportId: 1009,
          writtenAt: "2026.02.24",
          reportTitle: "수학 문제풀이 학습일지",
          reportSubtitle: "수열 유형 문제 풀이",
          reportContent:
            "등차수열과 등비수열의 기본 공식을 복습하고, 유형별 문제 풀이 전략을 연습하였다.",
          mentor: {
            id: 202,
            name: "박지훈"
          },
          mentee: {
            id: 302,
            name: "최유진"
          },
          subjectName: "수학 I",
          studyWeekLabel: 2,
          sessionLabel: 3,
          actions: {
            canView: true,
            canEdit: false
          }
        }
      ]
    },
    {
      month: 2,
      weekOfMonth: 3,
      startDate: "2026.02.15",
      endDate: "2026.02.21",
      reports: [
        {
          reportId: 1010,
          writtenAt: "2026.02.20",
          reportTitle: "영어 듣기 학습일지",
          reportSubtitle: "듣기 유형별 문제 정리",
          reportContent:
            "대화형과 담화형 듣기 문제를 구분하여 풀이하고, 자주 등장하는 표현과 발음을 정리하였다.",
          mentor: {
            id: 204,
            name: "서현우"
          },
          mentee: {
            id: 306,
            name: "오예린"
          },
          subjectName: "영어 듣기",
          studyWeekLabel: 1,
          sessionLabel: 2,
          actions: {
            canView: true,
            canEdit: true
          }
        },
        {
          reportId: 1011,
          writtenAt: "2026.02.18",
          reportTitle: "사회 개념 학습일지",
          reportSubtitle: "시장 경제의 이해",
          reportContent:
            "시장 경제의 기본 원리와 수요·공급의 개념을 학습하고, 실제 사례를 통해 내용을 정리하였다.",
          mentor: {
            id: 206,
            name: "조민재"
          },
          mentee: {
            id: 307,
            name: "윤하은"
          },
          subjectName: "사회",
          studyWeekLabel: 1,
          sessionLabel: 1,
          actions: {
            canView: true,
            canEdit: false
          }
        }
      ]
    }
  ]
};

const listLrReportContainer = document.querySelector(
  ".list-container__inner  " +
  ".list-content-container"
);
listLrReportContainer.innerHTML = createLearningReportGroup(learningReportDummyResponse.data);


sessionStorage.setItem(
  "learningReportDummyResponse",
  JSON.stringify(learningReportDummyResponse)
);