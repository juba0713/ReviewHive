
let hasStart = false;

const CONTENT = document.querySelector('.content');

const START_BUTTON = document.querySelector('.start-btn');

const BUTTONS = document.querySelector('.buttons');

const EXAM_TYPE = document.getElementById('examType').value;

const QUESTIONAIRE_ID = document.getElementById('questionaireId').value;

let totalQuestion = 0;

let totalChecked = 0;

let totalWronged = 0;

let totalSkipped = 0;

let correctAnswer = 0;

let hasAnswered = false;

const totalQuestionLabel = document.querySelector('.total-question');

const totalCheckedLabel = document.querySelector('.total-checked');

const totalWrongedLabel = document.querySelector('.total-wronged');

const totalSkippedLabel = document.querySelector('.total-skipped');

let totalSeconds = 0;

let timerInterval;
		
START_BUTTON.addEventListener('click', function(){
	
	if(dto.examType == 1){
		startTimer(hour, minute, second);
	}
	
	addStopButton();
	
	this.remove();
});

function addStopButton(){
	const STOP_BUTTON = document.createElement('button'); 
    STOP_BUTTON.textContent = "STOP";
	STOP_BUTTON.style.background = "#DC3545";
	STOP_BUTTON.classList.add('action-btn');
	
	BUTTONS.innerHTML = '';
		
	BUTTONS.append(STOP_BUTTON);
	
	STOP_BUTTON.addEventListener('click', function(){
		exitConfirmation();
	});
	
	if(EXAM_TYPE == 1){
		getOneHundredQuestion();
	}else if(EXAM_TYPE == 2){
		getQuestion();
	}
}

let realAnswers = new Map();
let userAnswers = new Map();

async function getOneHundredQuestion(){
	
	const res = await fetch(`/retrieve/questionaire/hundred/${QUESTIONAIRE_ID}?type=${EXAM_TYPE}`);
		
	if(!res.ok){
		console.log("Error in fetching a question!");
	}
	
	const DATA = await res.json();
	
	const QUESTIONS = DATA.oneHundredQuestions;
	
	console.log(QUESTIONS);
	
	const SCROLLABLE = document.createElement("div");
	SCROLLABLE.classList.add("scrollable");
	
	for(let question of QUESTIONS){
		
		userAnswers.set(question.id, 0);
		
		const QUESTION_CONTAINER = document.createElement('div');
		QUESTION_CONTAINER.classList.add('question-containers');
		QUESTION_CONTAINER.innerHTML = `<div class="questions">
											<label>${question.question}</label>									
										</div>
										<div class="answers"></div>`;
										
		if(question.questionImage != null && question.questionImage != ''){
			const QUESTION_IMAGE = document.createElement('img');
			QUESTION_IMAGE.src = `/view/image/${question.id}/${question.questionImage}`;
			QUESTION_CONTAINER.querySelector('.questions').append(QUESTION_IMAGE);
		}
																		
		const ANSWERS_CONTAINER = QUESTION_CONTAINER.querySelector('.answers');

		for(let answer of question.answers){
			
			if(answer.isCorrect){
				correctAnswer = answer.id;
				realAnswers.set(question.id, answer.id);
			}
			
			const ANSWER_CONTAINER = document.createElement('div');
			ANSWER_CONTAINER.classList.add('answer');
			ANSWER_CONTAINER.setAttribute('question-id', question.id);
			ANSWER_CONTAINER.setAttribute('id', answer.id);
			ANSWER_CONTAINER.innerHTML = `<label>${answer.answer}</label>`;
			
			if(answer.answerImage != null && answer.answerImage != ''){
				const ANSWER_IMAGE = document.createElement('img');
				ANSWER_IMAGE.src = `/view/image/${answer.questionId}/${answer.answerImage}`;
				ANSWER_CONTAINER.append(ANSWER_IMAGE);
			}
				
			ANSWER_CONTAINER.addEventListener('click', function(){
								
				this.parentElement.querySelectorAll('.answer').forEach(answer => answer.classList.remove('answered'));
				
				this.classList.toggle('answered');	
				
				userAnswers.set(Number(this.getAttribute('question-id')), Number(this.getAttribute('id')));
				
				console.log(realAnswers);
				console.log(userAnswers);
			});
			
			ANSWERS_CONTAINER.append(ANSWER_CONTAINER);
		}
		
		const HR = document.createElement('hr');
		HR.setAttribute('size', 5);
		HR.setAttribute('color', "#000");
		
		const HR1 = document.createElement('hr');
		HR1.setAttribute('size', 5);
		HR1.setAttribute('color', "#000");
		
		const QUESTIONLABEL = document.createElement('div');
		QUESTIONLABEL.innerHTML = "QUESTION";
		
		SCROLLABLE.append(HR);
		SCROLLABLE.append(QUESTIONLABEL);
		SCROLLABLE.append(HR1);
		
		SCROLLABLE.append(QUESTION_CONTAINER);
	}
	
	CONTENT.append(SCROLLABLE);

}

async function getQuestion(){
		
	const res = await fetch(`/retrieve/questionaire/${QUESTIONAIRE_ID}?type=${EXAM_TYPE}`);
	
	if(!res.ok){
		console.log("Error in fetching a question!");
	}
	
	const DATA = await res.json();
	
	const QUESTION = DATA.randomQuestion;
	
	const QUESTION_CONTAINER = document.createElement('div');
	QUESTION_CONTAINER.classList.add('question-container');
	QUESTION_CONTAINER.innerHTML = `<div class="question">
										<label>${QUESTION.question}</label>									
									</div>
									<div class="answers">
									</div>
									<button class="action-btn skip-btn">SKIP</button>`;
									
	if(QUESTION.questionImage != null && QUESTION.questionImage != ''){
		const QUESTION_IMAGE = document.createElement('img');
		QUESTION_IMAGE.src = `/view/image/${QUESTION.id}/${QUESTION.questionImage}`;
		QUESTION_CONTAINER.querySelector('.question').append(QUESTION_IMAGE);
	}
									
	QUESTION_CONTAINER.querySelector('.skip-btn').addEventListener('click', function(){
		if(this.classList.contains('next-btn')){
			
			getQuestion();
			
			hasAnswered = false;
			
		}else{
			
			totalSkipped++;
			
			totalSkippedLabel.innerHTML = totalSkipped;
			
			getQuestion();
		}
	});
									
	const ANSWERS_CONTAINER = QUESTION_CONTAINER.querySelector('.answers');

	for(let answer of QUESTION.answers){
		
		if(answer.isCorrect){
			correctAnswer = answer.id;
		}
		
		const ANSWER_CONTAINER = document.createElement('div');
		ANSWER_CONTAINER.classList.add('answer');
		ANSWER_CONTAINER.setAttribute('id', answer.id);
		ANSWER_CONTAINER.innerHTML = `<label>${answer.answer}</label>`;
		
		if(answer.answerImage != null && answer.answerImage != ''){
			const ANSWER_IMAGE = document.createElement('img');
			ANSWER_IMAGE.src = `/view/image/${answer.questionId}/${answer.answerImage}`;
			ANSWER_CONTAINER.append(ANSWER_IMAGE);
		}
			
		ANSWER_CONTAINER.addEventListener('click', function(){
			
			if(hasAnswered){
				return;
			}
			
			hasAnswered = true;

				
			if(this.id == correctAnswer){
				totalChecked++;
				
				this.style.background = '#4CAF50';
				
				totalCheckedLabel.innerHTML = totalChecked;
				
				toggleConfetti();
			}else{
				totalWronged++;
				
				this.style.background = '#FF4C4C';
				
				document.getElementById(correctAnswer).style.background = '#4CAF50';
				
				totalWrongedLabel.innerHTML = totalWronged;
			}
			
			const SKIP_BTN = document.querySelector('.skip-btn');
			SKIP_BTN.classList.add('next-btn');
			SKIP_BTN.innerHTML = 'NEXT';		
			
		});
		
		ANSWERS_CONTAINER.append(ANSWER_CONTAINER);
	}
						
	
	CONTENT.innerHTML = '';
	CONTENT.append(QUESTION_CONTAINER);

	totalQuestion++;

	totalQuestionLabel.innerHTML = totalQuestion;		
	
}

function exitConfirmation(){
	
	const exitConfirmatonHTML = document.createElement('div');
	exitConfirmatonHTML.classList.add('modal');
	exitConfirmatonHTML.innerHTML = `	<div class="modal-content">
												<div class="modal-header">EXIT?</div>
												<div>Are you sure you want to exit?</div>
												<div class="modal-buttons">
													<button class="action-btn no-btn">NO</button>
													<button class="action-btn yes-btn">YES</button>
												</div>
											</div>`;
											
	exitConfirmatonHTML.querySelector('.no-btn').addEventListener('click', function(){
		this.closest('.modal').remove();
	});
	
	exitConfirmatonHTML.querySelector('.yes-btn').addEventListener('click', function(){
		window.location.href=`/questionaire?id=${QUESTIONAIRE_ID}`;
	});
									
	document.getElementsByTagName('body')[0].append(exitConfirmatonHTML);
}

function finalScoring(totalCorrect){
	
	const finalScoringHTML = document.createElement('div');
	finalScoringHTML.classList.add('modal');
	finalScoringHTML.innerHTML = `	<div class="modal-content">
												<div class="modal-header">RESULT - <span class="${totalCorrect > 60 ? 'passed' : 'failed'}">${totalCorrect > 60 ? 'PASSED' : 'FAILED'}</span></div>
												<div>Score: ${totalCorrect} / 100</div>
												<div class="modal-buttons">
													<button class="action-btn no-btn">EXIT</button>
													<button class="action-btn yes-btn">TRY AGAIN</button>
												</div>
											</div>`;
											
	finalScoringHTML.querySelector('.no-btn').addEventListener('click', function(){
		window.location.href=`/questionaire?id=${QUESTIONAIRE_ID}`;
	});
	
	finalScoringHTML.querySelector('.yes-btn').addEventListener('click', function(){
		getOneHundredQuestion();
		realAnswers.clear();
		userAnswers.clear();
		CONTENT.innerHTML = '';
		startTimer(hour, minute, second);
		this.closest('.modal').remove();
	});
									
	document.getElementsByTagName('body')[0].append(finalScoringHTML);

}

function startTimer(hours, minutes, seconds) {
   if (!timerInterval) { // Prevent multiple intervals
       totalSeconds = hours * 3600 + minutes * 60 + seconds;
       updateTimer();
       timerInterval = setInterval(updateTimer, 1000);
   }
}
	
function updateTimer() {
    if (totalSeconds < 0) {
        clearInterval(timerInterval);
        timerInterval = null;
        return;
    }

    if (totalSeconds == 0) {    
        clearInterval(timerInterval); // Stop the timer before running finalScoring
        timerInterval = null;

        // Calculate total correct answers
        let correctCount = 0;
        realAnswers.forEach((correctValue, key) => {
            if (userAnswers.has(key) && userAnswers.get(key) === correctValue) {
                correctCount++;
            }
        });

        finalScoring(correctCount); // Now it will run only once
        return;
    }

    let hrs = Math.floor(totalSeconds / 3600);
    let mins = Math.floor((totalSeconds % 3600) / 60);
    let secs = totalSeconds % 60;

    document.querySelector('.time').textContent = 
        `${String(hrs).padStart(2, '0')} : ${String(mins).padStart(2, '0')} : ${String(secs).padStart(2, '0')}`;

    totalSeconds--;
}


function stopDemoExam(){
	
}


function toggleConfetti(){
	const count = 200,
	  defaults = {
	    origin: { y: 0.7 },
	  };

	function fire(particleRatio, opts) {
	  confetti(
	    Object.assign({}, defaults, opts, {
	      particleCount: Math.floor(count * particleRatio),
	    })
	  );
	}

	fire(0.25, {
	  spread: 26,
	  startVelocity: 55,
	});

	fire(0.2, {
	  spread: 60,
	});

	fire(0.35, {
	  spread: 100,
	  decay: 0.91,
	  scalar: 0.8,
	});

	fire(0.1, {
	  spread: 120,
	  startVelocity: 25,
	  decay: 0.92,
	  scalar: 1.2,
	});

	fire(0.1, {
	  spread: 120,
	  startVelocity: 45,
	});
}
