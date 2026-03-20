/**
 * 멘토 페이지네이션 클래스
 */
class MentorPagination {
    constructor({ totalMentors, mentorsPerPage = 10, pagesPerGroup = 5 }) {
        this.updateTotal(totalMentors, mentorsPerPage, pagesPerGroup);
        this.pageNumberContainer = document.querySelector('#pageNumber ul');
        this._init();
    }

    updateTotal(totalMentors, mentorsPerPage = 10, pagesPerGroup = 5) {
        this.totalMentors = totalMentors;
        this.mentorsPerPage = mentorsPerPage;
        this.pagesPerGroup = pagesPerGroup;
        this.totalPages = Math.ceil(totalMentors / mentorsPerPage) || 1;
        this.currentPage = 1;
        this.currentGroup = 1;
    }

    _init() {
        if (this.pageNumberContainer) this._render();
    }

    _groupRange() {
        const start = (this.currentGroup - 1) * this.pagesPerGroup + 1;
        const end = Math.min(start + this.pagesPerGroup - 1, this.totalPages);
        return { start, end };
    }

    get totalGroups() {
        return Math.ceil(this.totalPages / this.pagesPerGroup);
    }

    _render() {
        const { start, end } = this._groupRange();
        if(!this.pageNumberContainer) return;
        this.pageNumberContainer.innerHTML = '';

        const leftEl = this._buildArrow('left', '&lt;');
        leftEl.style.visibility = this.currentGroup === 1 ? 'hidden' : 'visible';
        this.pageNumberContainer.appendChild(leftEl);

        for (let i = start; i <= end; i++) {
            const li = document.createElement('li');
            li.textContent = i;
            if (i === this.currentPage) li.id = 'nowPage';
            li.addEventListener('click', () => this._goTo(i));
            this.pageNumberContainer.appendChild(li);
        }

        const rightEl = this._buildArrow('right', '&gt;');
        rightEl.style.visibility = this.currentGroup >= this.totalGroups ? 'hidden' : 'visible';
        this.pageNumberContainer.appendChild(rightEl);

        this._bindArrows(leftEl, rightEl);

        if (typeof this.onPageChange === 'function') {
            this.onPageChange(this.currentPage);
        }
    }

    _buildArrow(id, html) {
        const li = document.createElement('li');
        li.id = id;
        li.innerHTML = html;
        return li;
    }

    _bindArrows(left, right) {
        left.onclick = () => this._prevGroup();
        right.onclick = () => this._nextGroup();
    }

    _goTo(page) {
        if (page < 1 || page > this.totalPages) return;
        this.currentPage = page;
        this.currentGroup = Math.ceil(page / this.pagesPerGroup);
        this._render();
    }

    _prevGroup() {
        if (this.currentGroup <= 1) return;
        this.currentGroup--;
        this.currentPage = (this.currentGroup - 1) * this.pagesPerGroup + this.pagesPerGroup;
        this._render();
    }

    _nextGroup() {
        if (this.currentGroup >= this.totalGroups) return;
        this.currentGroup++;
        this.currentPage = (this.currentGroup - 1) * this.pagesPerGroup + 1;
        this._render();
    }
}

/**
 * 멘토 카드 생성
 */
function createMentoCard(mentor) {
    const cp = (typeof globalContextPath !== 'undefined') ? globalContextPath : '';
    // JSP 데이터 구조에 맞춰 id 또는 memberNumber를 가져옵니다.
    const mNum = mentor.id || mentor.memberNumber; 

    var html = '<div class="mentoInfo">';
    html += '  <div class="mentoName">' + mentor.name + ' 멘토님</div>';
    html += '  <div class="mentoCard">';
    html += '    <div class="mentoCardHead">';
    html += '      <div class="mentoSubject">' + (mentor.subject || '전공미정') + '</div>';
    html += '      <div class="mentoCardMain">';
    html += '        <div class="mentoFront">';
    html += '          <img src="' + cp + '/upload/' + (mentor.img || 'default.png') + '" alt="사진">';
    // 매칭하기 버튼에 멘토 번호를 data-id로 심어줍니다.
    html += '          <button type="button" class="matching" data-id="' + mNum + '">매칭하기</button>';
    html += '        </div>';
    html += '        <div class="mentoBack">';
    html += '          <div class="mentoringPurpose">' + (mentor.purpose || '소개 없음') + '</div>';
    html += '          <div class="mentoUniSchool">학교: ' + mentor.university + '</div>';
    html += '          <div class="mentoMajor">학과: ' + mentor.major + '</div>';
    html += '          <div class="mentoringDay">' + (mentor.date || '최근 가입') + '</div>';
    html += '        </div>';
    html += '      </div>';
    html += '    </div>';
    html += '  </div>';
    html += '</div>';
    return html;
}

/* ── 메인 로직 ── */

let filteredData = [];
let pagination = null;

function renderFilteredMentors(page, mentorsPerPage = 10) {
    console.log(`[Log] 화면 렌더링 시작 - 페이지: ${page}, 데이터 수: ${filteredData.length}`);
    const contentsEl = document.querySelector('.contents');
    if (!contentsEl) {
        console.error("[Error] .contents 요소를 찾을 수 없습니다.");
        return;
    }
    contentsEl.innerHTML = '';

    if (filteredData.length === 0) {
        contentsEl.innerHTML = '<div style="text-align:center; width:100%; padding:100px 0;">해당 카테고리의 멘토가 없습니다.</div>';
        return;
    }

    const start = (page - 1) * mentorsPerPage;
    const end = Math.min(start + mentorsPerPage, filteredData.length);
    const pageData = filteredData.slice(start, end);

    for (let i = 0; i < pageData.length; i += 2) {
        const row = document.createElement('div');
        row.className = 'mentoList';
        let html = createMentoCard(pageData[i]);
        if (pageData[i + 1]) html += createMentoCard(pageData[i + 1]);
        row.innerHTML = html;
        contentsEl.appendChild(row);
    }
}

document.addEventListener('DOMContentLoaded', () => {
    console.log("[Log] JS 로드 완료 및 실행 시작");

    // 1. 데이터 확인
    if (typeof REAL_MENTORS === 'undefined') {
        alert("REAL_MENTORS 데이터가 정의되지 않았습니다. JSP를 확인하세요.");
        return;
    }
    filteredData = [...REAL_MENTORS];

    // 2. 페이지네이션 초기화
    pagination = new MentorPagination({
        totalMentors: filteredData.length,
        mentorsPerPage: 10,
        pagesPerGroup: 5,
    });

    pagination.onPageChange = (page) => {
        renderFilteredMentors(page);
    };

    // 3. 첫 화면 렌더링
    renderFilteredMentors(1);

    // 4. 카테고리 버튼 이벤트 바인딩
    const categoryBtns = document.querySelectorAll('.category-btn');
    categoryBtns.forEach((btn) => {
        btn.addEventListener('click', function(e) {
            e.preventDefault();
            const selectedCategory = this.getAttribute('data-name');
            
            categoryBtns.forEach(b => b.removeAttribute('id'));
            this.id = 'nowCategory';

            if (selectedCategory === "전체") {
                filteredData = [...REAL_MENTORS];
            } else {
                filteredData = REAL_MENTORS.filter(m => m.subject === selectedCategory);
            }

            pagination.updateTotal(filteredData.length);
            pagination._render(); 
            renderFilteredMentors(1);
        });
    });

    // 5. [중요] 매칭하기 버튼 클릭 이벤트 (이벤트 위임)
    // 버튼이 새로 생성되어도 부모 요소인 document에서 클릭을 가로챕니다.
    document.addEventListener('click', function(e) {
        if (e.target && e.target.classList.contains('matching')) {
            const memberNumber = e.target.getAttribute('data-id');
            const cp = (typeof globalContextPath !== 'undefined') ? globalContextPath : '';
            
            if (!memberNumber || memberNumber === "undefined") {
                alert("멘토 정보를 불러오지 못했습니다.");
                return;
            }

            console.log("[상세페이지 이동] 멘토 번호:", memberNumber);
            location.href = cp + '/mentor/mentorDetailOk.sch?memberNumber=' + memberNumber;
        }
    });
});