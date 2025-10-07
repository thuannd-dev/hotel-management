/**
 * Login Form JavaScript
 * Handles password toggle and form validation for hotel management login
 */

// Password toggle functionality
function togglePassword() {
    const passwordInput = document.getElementById("password");
    const eyeIcon = document.getElementById("eye-icon");

    if (passwordInput.type === "password") {
        passwordInput.type = "text";
        eyeIcon.innerHTML = `
      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13.875 18.825A10.05 10.05 0 0112 19c-4.478 0-8.268-2.943-9.543-7a9.97 9.97 0 011.563-3.029m5.858.908a3 3 0 114.243 4.243M9.878 9.878l4.242 4.242M9.878 9.878L3 3m6.878 6.878L21 21"></path>
    `;
    } else {
        passwordInput.type = "password";
        eyeIcon.innerHTML = `
      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"></path>
      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z"></path>
    `;
    }
}

// Form validation
document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form");
    const errorMessage = document.getElementById("error-message");

    if (form && errorMessage) {
        form.addEventListener("submit", function (e) {
            const username = document.getElementById("username").value.trim();
            const password = document.getElementById("password").value.trim();

            // Clear any previous error messages
            errorMessage.classList.add("hidden");

            // Check if fields are empty
            if (!username || !password) {
                e.preventDefault();
                showError("Please fill in all required fields.");
                return;
            }

            // Check username length
            if (username.length < 3) {
                e.preventDefault();
                showError("Username must be at least 3 characters long.");
                return;
            }

            // Check password length
            if (password.length < 6) {
                e.preventDefault();
                showError("Password must be at least 6 characters long.");
                return;
            }

            // If all validations pass, hide error message
            errorMessage.classList.add("hidden");
        });
    }

    // Function to show error messages
    function showError(message) {
        if (errorMessage) {
            errorMessage.querySelector("div").innerHTML = `
        <svg class="w-4 h-4 mr-2" fill="currentColor" viewBox="0 0 20 20">
          <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z" clip-rule="evenodd"></path>
        </svg>
        ${message}
      `;
            errorMessage.classList.remove("hidden");

            // Auto-hide error message after 5 seconds
            setTimeout(function () {
                errorMessage.classList.add("hidden");
            }, 5000);
        }
    }
});

// Enhanced form interactions
document.addEventListener("DOMContentLoaded", function () {
    // Add focus/blur effects to inputs
    const inputs = document.querySelectorAll(".login-input");

    inputs.forEach(function (input) {
        input.addEventListener("focus", function () {
            this.parentElement.classList.add("input-focused");
        });

        input.addEventListener("blur", function () {
            this.parentElement.classList.remove("input-focused");
        });
    });

    // Add loading state to login button
    const loginButton = document.querySelector(".login-button");
    const form = document.querySelector("form");

    if (form && loginButton) {
        form.addEventListener("submit", function (e) {
            // If form validation passes, show loading state
            const username = document.getElementById("username").value.trim();
            const password = document.getElementById("password").value.trim();

            if (
                username &&
                password &&
                username.length >= 3 &&
                password.length >= 6
            ) {
                loginButton.innerHTML = `
          <svg class="animate-spin -ml-1 mr-3 h-5 w-5 text-white inline" fill="none" viewBox="0 0 24 24">
            <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
            <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
          </svg>
          Signing In...
        `;
                loginButton.disabled = true;
            }
        });
    }
});