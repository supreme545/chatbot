document.addEventListener('DOMContentLoaded', function() {
    // Form validation
    const forms = document.querySelectorAll('form');
    forms.forEach(form => {
        form.addEventListener('submit', function(event) {
            event.preventDefault();
            if (!form.checkValidity()) {
                event.stopPropagation();
            } else {
                handleFormSubmit(form);
            }
            form.classList.add('was-validated');
        });
    });

    // Password strength validation
    const passwordInput = document.getElementById('password');
    if (passwordInput) {
        passwordInput.addEventListener('input', validatePassword);
    }

    // Password confirmation validation
    const confirmPasswordInput = document.getElementById('confirmPassword');
    if (confirmPasswordInput) {
        confirmPasswordInput.addEventListener('input', validatePasswordMatch);
    }

    // Profile picture upload
    const profilePicInput = document.getElementById('profilePicture');
    if (profilePicInput) {
        profilePicInput.addEventListener('change', handleProfilePicUpload);
    }

    // Logout functionality
    const logoutBtn = document.getElementById('logoutBtn');
    if (logoutBtn) {
        logoutBtn.addEventListener('click', handleLogout);
    }
});

// Password validation
function validatePassword() {
    const password = document.getElementById('password');
    const regex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;
    
    if (!regex.test(password.value)) {
        password.setCustomValidity('Password must be at least 8 characters long and contain letters, numbers, and special characters.');
    } else {
        password.setCustomValidity('');
    }
}

// Password match validation
function validatePasswordMatch() {
    const password = document.getElementById('password');
    const confirmPassword = document.getElementById('confirmPassword');
    
    if (password.value !== confirmPassword.value) {
        confirmPassword.setCustomValidity('Passwords do not match');
    } else {
        confirmPassword.setCustomValidity('');
    }
}

// Handle form submissions
function handleFormSubmit(form) {
    const formId = form.id;
    const formData = new FormData(form);
    const data = Object.fromEntries(formData.entries());

    // Add appropriate endpoints based on form type
    let endpoint = '';
    switch(formId) {
        case 'loginForm':
            endpoint = '/api/auth/login';
            break;
        case 'registerForm':
            endpoint = '/api/auth/register';
            break;
        case 'profileForm':
            endpoint = '/api/user/profile';
            break;
    }

    // Send form data to backend
    fetch(endpoint, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data)
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            // Handle successful submission
            handleSuccess(formId);
        } else {
            // Handle errors
            showError(data.message);
        }
    })
    .catch(error => {
        console.error('Error:', error);
        showError('An error occurred. Please try again.');
    });
}

// Handle profile picture upload
function handleProfilePicUpload(event) {
    const file = event.target.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            const img = document.querySelector('.avatar-wrapper img');
            img.src = e.target.result;
        };
        reader.readAsDataURL(file);
    }
}

// Handle logout
function handleLogout(event) {
    event.preventDefault();
    fetch('/api/auth/logout', {
        method: 'POST',
        credentials: 'include'
    })
    .then(() => {
        window.location.href = '/login.html';
    })
    .catch(error => {
        console.error('Error:', error);
        showError('Logout failed. Please try again.');
    });
}

// Success handler
function handleSuccess(formId) {
    switch(formId) {
        case 'loginForm':
            window.location.href = '/index.html';
            break;
        case 'registerForm':
            window.location.href = '/login.html';
            break;
        case 'profileForm':
            showSuccess('Profile updated successfully!');
            break;
    }
}

// Error display
function showError(message) {
    // Implement error toast or alert
    alert(message);
}

// Success message display
function showSuccess(message) {
    // Implement success toast or alert
    alert(message);
}
