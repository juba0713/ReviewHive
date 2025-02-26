const PREV_BUTTON = document.querySelector('.prev-btn');
const NEXT_BUTTON = document.querySelector('.next-btn');
const SELECT_TAG = document.querySelector(".select-tag");

const SEARCH_INPUTS = document.querySelectorAll('.search-input');

const INITIAL_PAGE = 1;

getData(INITIAL_PAGE);

const tableBody = document.querySelector("tbody");

let initial = true;

async function getData(page){
	try {
		//Fetch data from the given API
        var response = await fetch("/admin/api/questionaire/get-all?page=" + page);
        
        if(!response.ok){
			console.error("Error fetching category:", error);
			return;
		}
		
		const data = await response.json();
		
		console.log("Categories: ");
		console.log(data);
		
		const questionaires = data.questionaires;
		let totalPage = (questionaires && questionaires.length > 0) ? questionaires[0].totalPage : 1
		if(initial){
			updateButtonStates(INITIAL_PAGE, totalPage);
			updateSelectPage(totalPage);
			initial = false;
		}
		
		populateTable(questionaires);
		
		return {
			totalPage : totalPage
		}
        
    }catch(error){
		console.error("Error fetching category:", error);
	}
}

function populateTable(questionaires){
	
	tableBody.innerHTML = '';
	
	if(questionaires.length != 0){
		for(let questionaire of questionaires){
			const isChecked = questionaire.isOpen ? 'checked' : '';
			const categoryHTML = `<tr questionaireId="${questionaire.id}">
									<td>${questionaire.id}</td>
									<td>${questionaire.categoryName}</td>
									<td><span style="background: ${questionaire.hexColor}" class="abbreviation-color">${questionaire.abbreviation}</span></td>
									<td>${questionaire.questionaireName}</td>
									<td>${questionaire.questionTotal}</td>
									<td>
										<label class="switch">
										  <input type="checkbox" ${isChecked} class="status-toggle">
										  <span class="slider round"></span>
										</label>
									</td>
									<td>
										<a class="action-btn edit-btn" href="/admin/category-edit?id=${questionaire.id}">
											<img src="/icons/view.svg">
										</a>
										<a class="action-btn edit-btn" href="/admin/questionaire-edit?id=${questionaire.id}">
											<img src="/icons/edit.svg">
										</a>
										<button class="action-btn delete-btn" data-toggle="modal" data-target="#deleteModal">
											<img src="/icons/delete.svg">
										</button>
										<a class="action-btn add-btn" href="/admin/questionaire/questions?id=${questionaire.id}">
											<img src="/icons/add_question.svg">
										</a>
									</td>
								</tr>		`;
								
			tableBody.innerHTML += categoryHTML;	
		}
	}else{
		const categoryHTML = `<tr>
									<td colspan="7">No Data</td>									
								</tr>		`;
								
			tableBody.innerHTML += categoryHTML;
	}
	
	document.querySelectorAll('.status-toggle').forEach(toggle => toggle.addEventListener('change', function(){
		
	    const questionaireId = this.closest('tr').getAttribute('questionaireId');

	    const status = this.checked; 
	    
	    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
	    
	    fetch("/admin/api/questionaire/update-status", {
	        method: 'POST',
	        headers: {
	            'Content-Type': 'application/x-www-form-urlencoded', 
	            'X-CSRF-TOKEN': csrfToken 
	        },
	        body: new URLSearchParams({
	            id: questionaireId,
	            status: status
	        }),
	        credentials: 'same-origin'
	    })
	    .then(response => {
	        if (!response.ok) {
	            console.error('Error updating category status');
	        } else {
	            console.log('Category status updated successfully');
	        }
	    })
	    .catch(error => {
	        console.error('Error:', error);
	    });
	}));
	
	document.querySelectorAll('.delete-btn').forEach(btn => btn.addEventListener('click', function(){
		const categoryId = this.closest('tr').getAttribute('questionaireId');
		
		document.querySelector("#hiddenId").setAttribute('value', categoryId);
	}));
}

PREV_BUTTON.addEventListener('click', async function(){
			
	PREV_BUTTON.disabled = true;
    NEXT_BUTTON.disabled = true;
	
	let currPage = Number(SELECT_TAG.value);

	currPage--;
	SELECT_TAG.value = currPage;
	
	let { totalPage } = await getData(currPage);

	updateButtonStates(currPage, totalPage);

});

NEXT_BUTTON.addEventListener('click', async function(){
	
	PREV_BUTTON.disabled = true;
    NEXT_BUTTON.disabled = true;
	
	let currPage = Number(SELECT_TAG.value);
		
	currPage++;
	SELECT_TAG.value = currPage;
	
	let { totalPage } = await getData(currPage);

	updateButtonStates(currPage, totalPage);
	
});

SELECT_TAG.addEventListener('change', async function(){

    let currPage = Number(this.value) || 1;
    
    let { totalPage } = await getData(currPage);

    updateButtonStates(currPage, totalPage);
    
});

/*let debounceTimer;

SEARCH_INPUTS.forEach(select => select.addEventListener('input', function () {
	// Clear the previous timer
	clearTimeout(debounceTimer);

	// Set a new debounce timer
	debounceTimer = setTimeout(async () => {
	
			SELECT_TAG.value = INITIAL_PAGE;

			let { fullName, userId, empStatus, orgName, templateName, applDate, startDate, endDate, appliedDays,outsideRules } = getSearchValues();

			// Pass abort signal to the getData call
			let { totalPage } = await getData(fullName, userId, empStatus, orgName, applDate, templateName, startDate, endDate, appliedDays,outsideRules, INITIAL_PAGE, ORIGIN);

			// Update button states and select page
			updateButtonStates(INITIAL_PAGE, totalPage);
			updateSelectPage(totalPage);

		
	}, 300);
}));*/

function updateSelectPage(totalPage){

    SELECT_TAG.innerHTML = '';
    for (let page = 1; page <= totalPage; page++) {
        let optionHTML = `<option value="${page}">${page}</option>`;
        SELECT_TAG.innerHTML += optionHTML;
    }
}
 
function updateButtonStates(currPage, totalPage) {
    PREV_BUTTON.disabled = currPage === 1;
    NEXT_BUTTON.disabled = currPage === totalPage;
}