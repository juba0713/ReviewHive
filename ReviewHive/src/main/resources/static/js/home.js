
const QUESTIONAIRE_LIST = document.querySelectorAll('.questionaire');

QUESTIONAIRE_LIST.forEach(questionaire => questionaire.addEventListener('click', function(){
	window.location.href=`/questionaire?id=${this.id}`;
}));