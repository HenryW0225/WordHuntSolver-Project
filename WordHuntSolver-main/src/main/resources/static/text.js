let pos = -1;
let showedword = document.getElementById('words-shown');
let gridContainer = document.getElementById('word-hunt-grid');
let form = document.getElementById('word-hunt-form');
let resultsDiv = document.getElementById('results');
let errorDiv = document.getElementById('error');

const GRID_SIZE = 4; // For a 4x4 grid
const examplePathway = [0, 1, 2, 3];
const movex = [-1, 1, 0, 0, 1, 1, -1, -1];
const movey = [0, 0, 1, -1, -1, 1, 1, -1];
class WordResponse {
    constructor(path, word, xpos, ypos) {
        this.path = path;
        this.word = word;
        this.xpos = xpos;
        this.ypos = ypos;
    }
}
// Function to create the grid
/*function createGrid(rows, cols) {
    for (let i = 0; i < rows * cols; i++) {
		//add lines too
        const cell = document.createElement('div');
        cell.classList.add('grid-cell');

        const input = document.createElement('input');
        input.setAttribute('type', 'text');
        input.setAttribute('maxlength', '1'); // Only one character
        input.setAttribute('data-index', i);
        input.addEventListener('input', (e) => {
            // Automatically move to next cell upon input
            const nextIndex = i + 1;
            const nextInput = document.querySelector(`input[data-index='${nextIndex}']`);
            if (nextInput && e.target.value) {
                nextInput.focus();
            }
        });

        cell.appendChild(input);
        gridContainer.appendChild(cell);
    }
}*/
   function createGrid(rows, cols) {
        gridContainer.style.gridTemplateColumns = `repeat(${cols}, 1fr)`;
        
        for (let i = 0; i < rows * cols; i++) {
            const cell = document.createElement('div');
            cell.classList.add('grid-cell');

            const input = document.createElement('input');
            input.setAttribute('type', 'text');
            input.setAttribute('maxlength', '1'); // Only one character
            input.setAttribute('data-index', i);
            input.addEventListener('input', (e) => {
                // Automatically move to next cell upon input
                const nextIndex = i + 1;
                const nextInput = document.querySelector(`input[data-index='${nextIndex}']`);
                if (nextInput && e.target.value) {
                    nextInput.focus();
                }
            });

            // Add arrow elements
            /*const arrowUp = document.createElement('div');
            arrowUp.classList.add('arrow', 'up');
            arrowUp.innerHTML = '▲';
            arrowUp.addEventListener('click', () => moveFocus(i, 'up'));

            const arrowDown = document.createElement('div');
            arrowDown.classList.add('arrow', 'down');
            arrowDown.innerHTML = '▼';
            arrowDown.addEventListener('click', () => moveFocus(i, 'down'));

            const arrowLeft = document.createElement('div');
            arrowLeft.classList.add('arrow', 'left');
            arrowLeft.innerHTML = '◄';
            arrowLeft.addEventListener('click', () => moveFocus(i, 'left'));

            const arrowRight = document.createElement('div');
            arrowRight.classList.add('arrow', 'right');
            arrowRight.innerHTML = '►';
            arrowRight.addEventListener('click', () => moveFocus(i, 'right'));

            cell.appendChild(arrowUp);
            cell.appendChild(arrowDown);
            cell.appendChild(arrowLeft);
            cell.appendChild(arrowRight);*/
            cell.appendChild(input);
            gridContainer.appendChild(cell);
        }
    }
    function highlightPathway(pathway) {
		const inputs = document.querySelector('.grid-cell input');
		inputs.forEach(input => {
        	input.classList.remove('highlight');
        	/*input.querySelectorAll('.arrow').forEach(arrow => {
                arrow.style.display = 'none';
            });*/
    	});
        // Highlight the pathway and show arrows
        for (let i = 0; i < pathway.length; i++) {
            const currIndex = pathway[i];
           	inputs[currIndex].classList.add('highlight');

            /*if (i < pathway.length - 1) {
                const nextIndex = pathway[i + 1];
                if (nextIndex === currentIndex - 1) {
                    inputs.querySelector('.arrow.left').style.display = 'block';
                } else if (nextIndex === currentIndex + 1) {
                    inputs.querySelector('.arrow.right').style.display = 'block';
                } else if (nextIndex === currentIndex + GRID_SIZE) {
                    inputs.querySelector('.arrow.up').style.display = 'block';
                } else if (nextIndex === currentIndex - GRID_SIZE) {
                    inputs.querySelector('.arrow.down').style.display = 'block';
                }
            }*/
        }
    }

// Initialize the grid
createGrid(GRID_SIZE, GRID_SIZE);
//highlightPathway(examplePathway);
// Function to highlight a specific square in the grid
function highlightCells(coordinates) {
    // Clear existing highlights first
    const inputs = document.querySelectorAll('.grid-cell input');
    inputs.forEach(input => {
        input.classList.remove('highlight');
    });

    // Highlight the specified cells
    coordinates.forEach(([row, col]) => {
        const index = row * 4 + col; // Calculate the index based on rows and cols
        if (inputs[index]) {
            inputs[index].classList.add('highlight');
            inputs[index].classList.add('arrow.up');
        } else {
            console.error(`Input at (${row}, ${col}) not found.`);
        }
    });
}
// Handle form submission
form.addEventListener('submit', async (e) => {
    e.preventDefault();

    // Collect letters from the grid
    const inputs = document.querySelectorAll('.grid-cell input');
    const letters = Array.from(inputs).map(input => input.value.toUpperCase());
    const array4x4 = new Array(4).fill(null).map(() => new Array(4).fill(0));
     for(let i = 0; i < GRID_SIZE; i++) {
		for(let j = 0; j < GRID_SIZE; j++) {
			array4x4[i][j] = letters[4*i + j].charAt(0);
		}
	}
    // Validate inputs
    if (letters.some(letter => letter === '')) {
        errorDiv.textContent = 'Please fill in all the letters.';
        resultsDiv.innerHTML = '';
        return;
    }

    // Send letters to the backend API
    try {
        const response = await fetch('http://localhost:8080/api/solve', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(array4x4),
        });
       	possibleWords = await response.json();
       	//errorDiv.textContent = '' + Array.isArray(possibleWords);
       	arr = possibleWords.map(item => new WordResponse(item.path, item.word, item.xpos, item.ypos));
       	nextword();
    } catch (error) {
        console.error('Error:', error);
        errorDiv.textContent = 'Failed to fetch words. Please try again.' + error;
        resultsDiv.innerHTML = '';
    }
});
function nextword(){
	if(pos == arr.length - 1) {
		errorDiv.textContent ='You have reached the end of the list.'
	}
	else {
		pos++;
		let patharr = [];
		let posx = arr[pos].xpos;
		let posy = arr[pos].ypos;
		patharr.push([posx, posy]);
		for(let m = arr[pos].path.length - 1; m >= 0; m--) {
			posx-=movex[Number(arr[pos].path.charAt(m))];
			posy-=movey[Number(arr[pos].path.charAt(m))];
			patharr.push([posx, posy]);
		}
		highlightCells(patharr);
		if(pos == arr.length) {
			errorDiv.textContent ='You have reached the end of the list.'
		}
		showedword.textContent = arr[pos].word;
	}
}
function prevword() {
	if(pos == 0) {
		errorDiv.textContent ='You have reached the start of the list.'
	}
	else {
		pos--;
		let patharr = [];
		let posx = arr[pos].xpos;
		let posy = arr[pos].ypos;
		patharr.push([posx, posy]);
		for(let m = arr[pos].path.length - 1; m >= 0; m--) {
			posx-=movex[Number(arr[pos].path.charAt(m))];
			posy-=movey[Number(arr[pos].path.charAt(m))];
			patharr.push([posx, posy]);
		}
		highlightCells(patharr);
		showedword.textContent = arr[pos].word;
	}
}