
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

START_BUTTON.addEventListener('click', function(){
	
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

	getQuestion();
}

async function getQuestion(){
		
	const res = await fetch(`/retrieve/questionaire/${QUESTIONAIRE_ID}?type=${EXAM_TYPE}`);
	
	if(!res.ok){
		console.log("Error in fetching a question!");
	}
	
	const DATA = await res.json();
	
	const QUESTION = DATA.randomQuestion;
	
	console.log(DATA);
	
	const QUESTION_CONTAINER = document.createElement('div');
	QUESTION_CONTAINER.classList.add('question-container');
	QUESTION_CONTAINER.innerHTML = `<div class="question">
										<label>${QUESTION.question}</label>									
									</div>
									<div class="answers">
									</div>
									<button class="action-btn skip-btn">SKIP</button>`;
									
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
			console.log("ASSIGNED CORRECT");
		}
		
		const ANSWER_CONTAINER = document.createElement('div');
		ANSWER_CONTAINER.classList.add('answer');
		ANSWER_CONTAINER.setAttribute('id', answer.id);
		ANSWER_CONTAINER.innerHTML = `<label>${answer.answer}</label>`;
			
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

