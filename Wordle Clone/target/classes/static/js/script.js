document.addEventListener("DOMContentLoaded", function () {
    const guessInput = document.getElementById("guessInput");
    const guessButton = document.getElementById("guessButton");
    const newGameButton = document.getElementById("newGameButton");
    const grid = document.getElementById("grid");
    const message = document.getElementById("message");

    let attempts = 0;

    guessButton.addEventListener("click", makeGuess);
    newGameButton.addEventListener("click", startNewGame);

    function makeGuess() {
        const guess = guessInput.value.toLowerCase();
        if (guess.length !== 5) {
            alert("Please enter a 5-letter word.");
            return;
        }

        fetch("/guess", {
            method: "POST",
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: `playerGuess=${guess}`
        })
        .then(response => response.json())
        .then(data => {
            displayFeedback(data);
            guessInput.value = "";
            attempts++;

            if (data.gameWon) {
                message.textContent = "Congratulations! You guessed the word!";
                guessButton.disabled = true;
            } else if (data.gameOver) {
                message.textContent = "Game Over! You've used all attempts.";
                guessButton.disabled = true;
            }
        });
    }

    function displayFeedback(data) {
        const feedbackRow = document.createElement("div");
        feedbackRow.classList.add("feedback-row");

        for (let i = 0; i < data.guess.length; i++) {
            const cell = document.createElement("div");
            cell.classList.add("cell");

            cell.textContent = data.guess.charAt(i).toUpperCase();
            switch (data.feedback.charAt(i)) {
                case 'G':
                    cell.classList.add("correct");
                    break;
                case 'Y':
                    cell.classList.add("present");
                    break;
                default:
                    cell.classList.add("absent");
            }
            feedbackRow.appendChild(cell);
        }

        grid.appendChild(feedbackRow);
    }

    function startNewGame() {
        fetch("/new-game", {
            method: "POST"
        })
        .then(() => {
            grid.innerHTML = "";
            message.textContent = "";
            guessButton.disabled = false;
            attempts = 0;
        });
    }
});
