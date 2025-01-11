// You can put shared functions here if needed.
// For now, most of the specific logic is within each page's <script> tag.

// Example of a function to check if user is logged in (can be used in other pages)
function isLoggedIn() {
  return localStorage.getItem('token') !== null;
}

// Example of a function to redirect to login if not authenticated
function requireAuth() {
  if (!isLoggedIn()) {
      window.location.href = 'auth.html';
  }
}