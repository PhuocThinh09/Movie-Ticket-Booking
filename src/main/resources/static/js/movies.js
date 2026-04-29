/*
 * Day 12 - Movie Frontend
 * Nhiệm vụ:
 * 1. Gọi API GET /api/movies
 * 2. Nhận dữ liệu JSON từ backend
 * 3. Render danh sách phim ra HTML
 * 4. Xử lý loading, empty, error
 */

// API endpoint lấy danh sách phim từ Spring Boot backend
const MOVIE_API_URL = "/api/movies";

// Lấy các element từ HTML
const loadingMessage = document.getElementById("loadingMessage");
const errorMessage = document.getElementById("errorMessage");
const emptyMessage = document.getElementById("emptyMessage");
const movieList = document.getElementById("movieList");

/*
 * DOMContentLoaded chạy khi HTML đã load xong.
 * Nếu gọi loadMovies() quá sớm, JS có thể không tìm thấy các element như movieList.
 */
document.addEventListener("DOMContentLoaded", function () {
    loadMovies();
});

/*
 * Hàm chính:
 * Gọi API lấy danh sách phim.
 */
async function loadMovies() {
    showLoading();

    try {
        /*
         * fetch() gửi HTTP request đến backend.
         * Vì frontend đang chạy cùng server Spring Boot localhost:8080,
         * ta có thể dùng đường dẫn tương đối "/api/movies".
         */
        const response = await fetch(MOVIE_API_URL);

        /*
         * response.ok = true nếu status nằm trong khoảng 200-299.
         * Ví dụ:
         * 200 OK
         * 201 Created
         *
         * response.ok = false nếu:
         * 400 Bad Request
         * 404 Not Found
         * 500 Internal Server Error
         */
        if (!response.ok) {
            throw new Error("Failed to load movies. HTTP status: " + response.status);
        }

        /*
         * Chuyển JSON response thành JavaScript object/array.
         */
        const data = await response.json();

        /*
         * Hiện tại API của bạn có thể trả về:
         * [
         *   { id: 1, title: "Avengers", ... }
         * ]
         *
         * Sau này khi làm pagination, API có thể trả:
         * {
         *   content: [
         *     { id: 1, title: "Avengers", ... }
         *   ],
         *   totalElements: 1
         * }
         *
         * Đoạn này hỗ trợ cả 2 kiểu.
         */
        const movies = Array.isArray(data) ? data : data.content;

        /*
         * Nếu không có phim thì hiện empty state.
         */
        if (!movies || movies.length === 0) {
            showEmpty();
            return;
        }

        /*
         * Nếu có dữ liệu thì render ra HTML.
         */
        renderMovies(movies);

    } catch (error) {
        /*
         * Nếu API lỗi, server tắt, endpoint sai, JSON lỗi...
         * thì hiện thông báo lỗi.
         */
        showError(error.message);
    }
}

/*
 * Render toàn bộ danh sách phim.
 */
function renderMovies(movies) {
    hideAllMessages();

    // Xóa nội dung cũ trước khi render lại
    movieList.innerHTML = "";

    // Duyệt từng movie và tạo card
    movies.forEach(function (movie) {
        const movieCard = createMovieCard(movie);
        movieList.appendChild(movieCard);
    });
}

/*
 * Tạo HTML card cho 1 movie.
 */
function createMovieCard(movie) {
    const card = document.createElement("article");
    card.className = "movie-card";

    /*
     * Nếu posterUrl rỗng/null thì dùng ảnh placeholder.
     */
    const posterUrl = movie.posterUrl && movie.posterUrl.trim() !== ""
        ? movie.posterUrl
        : "https://placehold.co/400x600?text=No+Poster";

    /*
     * Dùng template string để tạo HTML.
     * escapeHtml() giúp tránh lỗi nếu dữ liệu có ký tự đặc biệt.
     */
    card.innerHTML = `
        <img 
            class="movie-poster" 
            src="${escapeHtml(posterUrl)}" 
            alt="${escapeHtml(movie.title || "Movie poster")}"
        >

        <div class="movie-content">
            <h3 class="movie-title">
                ${escapeHtml(movie.title || "Untitled Movie")}
            </h3>

            <div class="movie-meta">
                Duration: ${movie.duration || "N/A"} minutes
            </div>

            <div class="movie-meta">
                Rating: ${escapeHtml(movie.rating || "N/A")}
            </div>

            <div class="movie-meta">
                Language: ${escapeHtml(movie.language || "N/A")}
            </div>

            <div class="movie-meta">
                Release Date: ${escapeHtml(movie.releaseDate || "N/A")}
            </div>

            <span class="badge">
                ${escapeHtml(movie.status || "UNKNOWN")}
            </span>

            <p class="movie-description">
                ${escapeHtml(movie.description || "No description available.")}
            </p>
        </div>
    `;

    return card;
}

/*
 * Hiển thị loading state.
 */
function showLoading() {
    loadingMessage.classList.remove("hidden");
    errorMessage.classList.add("hidden");
    emptyMessage.classList.add("hidden");

    movieList.innerHTML = "";
}

/*
 * Hiển thị empty state khi API trả về danh sách rỗng.
 */
function showEmpty() {
    loadingMessage.classList.add("hidden");
    errorMessage.classList.add("hidden");
    emptyMessage.classList.remove("hidden");

    movieList.innerHTML = "";
}

/*
 * Hiển thị error state khi API lỗi.
 */
function showError(message) {
    loadingMessage.classList.add("hidden");
    emptyMessage.classList.add("hidden");

    errorMessage.textContent = message;
    errorMessage.classList.remove("hidden");

    movieList.innerHTML = "";
}

/*
 * Ẩn loading, empty, error.
 */
function hideAllMessages() {
    loadingMessage.classList.add("hidden");
    errorMessage.classList.add("hidden");
    emptyMessage.classList.add("hidden");
}

/*
 * Escape HTML để tránh browser hiểu dữ liệu từ DB là HTML thật.
 *
 * Ví dụ nếu title trong DB là:
 * <script>alert("hack")</script>
 *
 * Nếu render trực tiếp thì nguy hiểm.
 * escapeHtml sẽ biến thành text an toàn.
 */
function escapeHtml(value) {
    return String(value)
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll('"', "&quot;")
        .replaceAll("'", "&#039;");
}