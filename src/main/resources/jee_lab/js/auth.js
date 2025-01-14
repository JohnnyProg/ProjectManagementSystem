document.getElementById('loginForm').addEventListener('submit', function(event) {
  event.preventDefault();
  const username = document.getElementById('loginUsername').value;
  const password = document.getElementById('loginPassword').value;

  const apiUrl = `${window.APP_API_URL}/api/v1`;

  fetch(`${apiUrl}/auth/login`, {
      method: 'POST',
      headers: {
          'Content-Type': 'application/json'
      },
      body: JSON.stringify({
          username: username,
          password: password
      })
  })
  .then(response => response.text())
  .then(data => {
      if (data) {
          localStorage.setItem('token', data);
          alert('Logged in successfully!');
          window.location.href = 'index.html';
      } else {
          alert('Login failed. Please check your credentials.');
      }
  })
  .catch(error => {
      console.error('Error during login:', error);
      alert('Login failed. Please try again.');
  });
});

document.getElementById('registerForm').addEventListener('submit', function(event) {
  event.preventDefault();
  const username = document.getElementById('registerUsername').value;
  const password = document.getElementById('registerPassword').value;
  const email = document.getElementById('registerEmail').value;
  const apiUrl = `${window.APP_API_URL}/api/v1`;
  fetch(`${apiUrl}/auth/register`, {
      method: 'POST',
      headers: {
          'Content-Type': 'application/json'
      },
      body: JSON.stringify({
          username: username,
          password: password,
          email: email
      })
  })
  .then(response => {
    // Check if the response is OK (status 2xx)
    if (response.ok) {
        return response.text(); // Parse the response as text for a string body
    } else {
        throw new Error(`Error: ${response.status}`);
    }
})
.then(data => {
    alert('Registration successful! You can now log in.');
    console.log('Server response:', data); // Log server message for debugging
    // Optionally redirect to the login form or homepage
})
.catch(error => {
    console.error('Registration failed:', error);
    alert('Registration failed. Please try again.');
});
});