// API endpoint lấy danh sách movie từ backend Spring Boot
const MOVIE_API_URL = "/api/movies";

// Lấy các element cần thao tác từ HTML
const movieListElement = document.getElementById("movie-list");
const messageElement = document.getElementById("message");
const movieCountElement = document.getElementById("movie-count");
const reloadButton = document.getElementById("reload-btn");

// Hàm hiển thị message cho user
function showMessage(text, type = "") {
    messageElement.textContent = text;
    messageElement.className = "message";

    if (type !== "") {
        messageElement.classList.add(type);
    }
}

// Hàm gọi API backend để lấy danh sách movie
async function fetchMovies() {
    try {
        showMessage("Loading movies...");

        const response = await fetch(MOVIE_API_URL);

        if (!response.ok) {
            throw new Error("Failed to fetch movies. Status: " + response.status);
        }

        const movies = await response.json();

        renderMovies(movies);
    } catch (error) {
        console.error("Error loading movies:", error);

        movieListElement.innerHTML = "";
        movieCountElement.textContent = "0 movie loaded";
        showMessage("Cannot load movies. Please check if Spring Boot backend is running.", "error");
    }
}

// Hàm render danh sách movie ra HTML
function renderMovies(movies) {
    movieListElement.innerHTML = "";

    if (!movies || movies.length === 0) {
        movieCountElement.textContent = "0 movie loaded";
        showMessage("No movies found in database.", "error");
        return;
    }

    movieCountElement.textContent = movies.length + " movie(s) loaded";
    showMessage("Movies loaded successfully.", "success");

    movies.forEach(function (movie) {
        const movieCard = document.createElement("article");
        movieCard.className = "movie-card";

        movieCard.innerHTML = `
            <h3 class="movie-title">${movie.title}</h3>
            <p class="movie-info"><strong>ID:</strong> ${movie.id}</p>
            <p class="movie-info"><strong>Duration:</strong> ${movie.duration} minutes</p>
            <p class="movie-info"><strong>Genre:</strong> ${movie.genre}</p>
            <span class="badge">Now Showing</span>
        `;

        movieListElement.appendChild(movieCard);
    });
}

// Khi bấm Reload, gọi lại API
reloadButton.addEventListener("click", function () {
    fetchMovies();
});

// Khi trang vừa mở, tự động load movie
fetchMovies();