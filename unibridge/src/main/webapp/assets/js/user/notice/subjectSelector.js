/**
 * 
 */
const filterSubjectSelector = document.querySelector('.filter-subject-container');
filterSubjectSelector.addEventListener('click', function() {
  const filterSubjectDropdown = document.querySelector('.filter-subject-dropdown');
  const isDisabled = filterSubjectDropdown.classList.contains('disabled');

  if (isDisabled) {
    filterSubjectDropdown.classList.remove('disabled');
  } else {
    filterSubjectDropdown.classList.add('disabled');
  }
});

function createSubjectDropdown(data) {
  return data.map((value) => `<li class="filter-subject-item">${value}</li>`).join("\n");
}

function closeDropdownAndSetPlaceHolder(event, thisEl) {
  event.preventDefault();
  event.stopPropagation();

  const filterSubjectDropdown = document.querySelector('.filter-subject-dropdown');
  filterSubjectDropdown.classList.add('disabled');

  const filterSubjectPlaceholder = document.querySelector('.filter-subject-placeholder');
  filterSubjectPlaceholder.textContent = thisEl.textContent;
  filterSubjectPlaceholder.classList.add('selected');
}

// Dummy Data
const subjectNamesDummyResponse = {
  data : [
    "영어 독해와 작문", "수학 II", "통합과학", "영어 어휘", "국어", "수학 I", "영어 듣기", "사회"
  ]
};

const filterSubjectSelectorInner = filterSubjectSelector.querySelector(".filter-subject-dropdown__inner");
filterSubjectSelectorInner.innerHTML = createSubjectDropdown(subjectNamesDummyResponse.data);

const filterSubjectItems = document.querySelectorAll('.filter-subject-item');
filterSubjectItems.forEach(function(item) {
  item.addEventListener('click', function(event) {
    closeDropdownAndSetPlaceHolder(event, item);
  });
});