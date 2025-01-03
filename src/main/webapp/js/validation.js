// Form validation and interactivity

document.addEventListener('DOMContentLoaded', function() {
    // Get forms
    const loginForm = document.getElementById('loginForm');
    const registerForm = document.getElementById('registerForm');

    // Validate login form
    if (loginForm) {
        loginForm.addEventListener('submit', function(e) {
            if (!validateLoginForm()) {
                e.preventDefault();
            }
        });
    }

    // Validate registration form
    if (registerForm) {
        registerForm.addEventListener('submit', function(e) {
            if (!validateRegistrationForm()) {
                e.preventDefault();
            }
        });

        // Add password strength meter
        const passwordInput = document.getElementById('password');
        if (passwordInput) {
            passwordInput.addEventListener('input', updatePasswordStrength);
        }

        // Add password confirmation validation
        const confirmPasswordInput = document.getElementById('confirmPassword');
        if (confirmPasswordInput) {
            confirmPasswordInput.addEventListener('input', validatePasswordMatch);
        }
    }
});

// Login form validation
function validateLoginForm() {
    let isValid = true;
    const username = document.getElementById('username');
    const password = document.getElementById('password');

    // Username validation
    if (username.value.trim() === '') {
        setInvalid(username, 'Username is required');
        isValid = false;
    } else {
        setValid(username);
    }

    // Password validation
    if (password.value.trim() === '') {
        setInvalid(password, 'Password is required');
        isValid = false;
    } else {
        setValid(password);
    }

    return isValid;
}

// Registration form validation
function validateRegistrationForm() {
    let isValid = true;
    const username = document.getElementById('username');
    const email = document.getElementById('email');
    const password = document.getElementById('password');
    const confirmPassword = document.getElementById('confirmPassword');

    // Username validation
    if (username.value.trim().length < 3) {
        setInvalid(username, 'Username must be at least 3 characters long');
        isValid = false;
    } else {
        setValid(username);
    }

    // Email validation
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email.value.trim())) {
        setInvalid(email, 'Please enter a valid email address');
        isValid = false;
    } else {
        setValid(email);
    }

    // Password validation
    if (!validatePassword(password.value)) {
        setInvalid(password, 'Password must be at least 8 characters long and include numbers and letters');
        isValid = false;
    } else {
        setValid(password);
    }

    // Confirm password validation
    if (password.value !== confirmPassword.value) {
        setInvalid(confirmPassword, 'Passwords do not match');
        isValid = false;
    } else {
        setValid(confirmPassword);
    }

    return isValid;
}

// Password strength checker
function updatePasswordStrength() {
    const password = document.getElementById('password').value;
    const strengthMeter = document.createElement('div');
    strengthMeter.className = 'password-strength';
    
    // Remove existing strength meter
    const existingMeter = document.querySelector('.password-strength');
    if (existingMeter) {
        existingMeter.remove();
    }

    // Calculate password strength
    let strength = 0;
    if (password.length >= 8) strength++;
    if (password.match(/[a-z]/)) strength++;
    if (password.match(/[A-Z]/)) strength++;
    if (password.match(/[0-9]/)) strength++;
    if (password.match(/[^a-zA-Z0-9]/)) strength++;

    // Set strength meter color
    switch(true) {
        case (strength <= 2):
            strengthMeter.classList.add('strength-weak');
            break;
        case (strength <= 4):
            strengthMeter.classList.add('strength-medium');
            break;
        default:
            strengthMeter.classList.add('strength-strong');
    }

    // Add strength meter to DOM
    document.getElementById('password').parentNode.appendChild(strengthMeter);
}

// Password validation
function validatePassword(password) {
    return password.length >= 8 && 
           password.match(/[a-zA-Z]/) && 
           password.match(/[0-9]/);
}

// Password match validation
function validatePasswordMatch() {
    const password = document.getElementById('password');
    const confirmPassword = document.getElementById('confirmPassword');
    
    if (password.value !== confirmPassword.value) {
        setInvalid(confirmPassword, 'Passwords do not match');
    } else {
        setValid(confirmPassword);
    }
}

// Set invalid input
function setInvalid(input, message) {
    input.classList.add('is-invalid');
    input.classList.remove('is-valid');
    const feedback = input.nextElementSibling;
    if (feedback && feedback.classList.contains('invalid-feedback')) {
        feedback.textContent = message;
    }
}

// Set valid input
function setValid(input) {
    input.classList.remove('is-invalid');
    input.classList.add('is-valid');
}
