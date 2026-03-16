/**
 * 
 */
const dateFilterTrigger = document.getElementById("dateFilterTrigger");
const dateFilterText    = document.getElementById("dateFilterText");
const datePickerPanel   = document.getElementById("datePickerPanel");
const datePickerClose   = document.getElementById("datePickerClose");
const dayGrid           = document.getElementById("dayGrid");
const monthTitle        = document.getElementById("monthTitle");
const selectedRange     = document.getElementById("selectedRange");
const prevMonth         = document.getElementById("prevMonth");
const nextMonth         = document.getElementById("nextMonth");
const resetDate         = document.getElementById("resetDate");
const applyDate         = document.getElementById("applyDate");
const quickButtons      = document.querySelectorAll(".quick-btn");

let currentDate = new Date();
let visibleYear = currentDate.getFullYear();
let visibleMonth = currentDate.getMonth();

let startDate = null;
let endDate = null;
let tempMode = "all";

function formatDate(date) {
  const y = date.getFullYear();
  const m = String(date.getMonth() + 1).padStart(2, "0");
  const d = String(date.getDate()).padStart(2, "0");
  return `${y}.${m}.${d}`;
}

function sameDate(a, b) {
  return (
    a &&
    b &&
    a.getFullYear() === b.getFullYear() &&
    a.getMonth() === b.getMonth() &&
    a.getDate() === b.getDate()
  );
}

function isBetween(target, start, end) {
  if (!start || !end) return false;
  const t = new Date(target.getFullYear(), target.getMonth(), target.getDate()).getTime();
  const s = new Date(start.getFullYear(), start.getMonth(), start.getDate()).getTime();
  const e = new Date(end.getFullYear(), end.getMonth(), end.getDate()).getTime();
  return t > s && t < e;
}

function setQuickActive(type) {
  quickButtons.forEach((btn) => {
    btn.classList.toggle("active", btn.dataset.range === type);
  });
}

function updateSelectedRangeText() {
  if (tempMode === "all" || (!startDate && !endDate)) {
    selectedRange.textContent = "전체 기간";
    return;
  }

  if (startDate && endDate) {
    selectedRange.textContent = `${formatDate(startDate)} ~ ${formatDate(endDate)}`;
    return;
  }

  if (startDate) {
    selectedRange.textContent = `${formatDate(startDate)} ~ 종료일 선택`;
  }
}

function renderCalendar(year, month) {
  dayGrid.innerHTML = "";
  monthTitle.textContent = `${year}년 ${month + 1}월`;

  const firstDay = new Date(year, month, 1);
  const lastDate = new Date(year, month + 1, 0).getDate();
  const startWeekday = firstDay.getDay();

  for (let i = 0; i < startWeekday; i++) {
    const empty = document.createElement("button");
    empty.className = "day-cell muted";
    empty.textContent = "";
    dayGrid.appendChild(empty);
  }

  for (let day = 1; day <= lastDate; day++) {
    const cell = document.createElement("button");
    const thisDate = new Date(year, month, day);

    cell.type = "button";
    cell.className = "day-cell";
    cell.textContent = day;

    if (sameDate(thisDate, new Date())) {
      cell.classList.add("today");
    }

    if (sameDate(thisDate, startDate)) {
      cell.classList.add("selected-start");
    }

    if (sameDate(thisDate, endDate)) {
      cell.classList.add("selected-end");
    }

    if (isBetween(thisDate, startDate, endDate)) {
      cell.classList.add("in-range");
    }

    cell.addEventListener("click", () => {
      tempMode = "custom";
      setQuickActive("");

      if (!startDate || (startDate && endDate)) {
        startDate = thisDate;
        endDate = null;
      } else if (thisDate < startDate) {
        endDate = startDate;
        startDate = thisDate;
      } else {
        endDate = thisDate;
      }

      updateSelectedRangeText();
      renderCalendar(visibleYear, visibleMonth);
    });

    dayGrid.appendChild(cell);
  }
}

function applyQuickRange(type) {
  const today = new Date();

  if (type === "all") {
    tempMode = "all";
    startDate = null;
    endDate = null;
  }

  if (type === "today") {
    tempMode = "today";
    startDate = new Date(today.getFullYear(), today.getMonth(), today.getDate());
    endDate = new Date(today.getFullYear(), today.getMonth(), today.getDate());
  }

  if (type === "week") {
    tempMode = "week";
    const day = today.getDay();
    const weekStart = new Date(today);
    const weekEnd = new Date(today);

    weekStart.setDate(today.getDate() - day);
    weekEnd.setDate(today.getDate() + (6 - day));

    startDate = new Date(weekStart.getFullYear(), weekStart.getMonth(), weekStart.getDate());
    endDate = new Date(weekEnd.getFullYear(), weekEnd.getMonth(), weekEnd.getDate());
  }

  if (type === "month") {
    tempMode = "month";
    startDate = new Date(today.getFullYear(), today.getMonth(), 1);
    endDate = new Date(today.getFullYear(), today.getMonth() + 1, 0);
  }

  setQuickActive(type);
  updateSelectedRangeText();
  renderCalendar(visibleYear, visibleMonth);
}

dateFilterTrigger.addEventListener("click", () => {
  datePickerPanel.classList.toggle("open");
  dateFilterTrigger.classList.toggle("active");
});

datePickerClose.addEventListener("click", () => {
  datePickerPanel.classList.remove("open");
  dateFilterTrigger.classList.remove("active");
});

prevMonth.addEventListener("click", () => {
  visibleMonth--;
  if (visibleMonth < 0) {
    visibleMonth = 11;
    visibleYear--;
  }
  renderCalendar(visibleYear, visibleMonth);
});

nextMonth.addEventListener("click", () => {
  visibleMonth++;
  if (visibleMonth > 11) {
    visibleMonth = 0;
    visibleYear++;
  }
  renderCalendar(visibleYear, visibleMonth);
});

quickButtons.forEach((btn) => {
  btn.addEventListener("click", () => {
    applyQuickRange(btn.dataset.range);
  });
});

resetDate.addEventListener("click", () => {
  applyQuickRange("all");
});

applyDate.addEventListener("click", () => {
  if (tempMode === "all" || (!startDate && !endDate)) {
    dateFilterText.textContent = "기간 선택 · 전체";
    dateFilterText.classList.remove("selected");
  } else if (startDate && endDate) {
    dateFilterText.textContent = `${formatDate(startDate)} ~ ${formatDate(endDate)}`;
    dateFilterText.classList.add("selected");
  } else if (startDate) {
    dateFilterText.textContent = `${formatDate(startDate)} ~`;
    dateFilterText.classList.add("selected");
  }

  datePickerPanel.classList.remove("open");
  dateFilterTrigger.classList.remove("active");
});

applyQuickRange("all");
renderCalendar(visibleYear, visibleMonth);