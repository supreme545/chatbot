// Dashboard JavaScript

document.addEventListener('DOMContentLoaded', function() {
    // Initialize Charts
    initializeCharts();

    // Initialize Sidebar Toggle
    initializeSidebar();

    // Initialize Dropdowns
    initializeDropdowns();
});

// Chart Initialization
function initializeCharts() {
    // Conversation Analytics Chart
    const conversationCtx = document.getElementById('conversationChart').getContext('2d');
    new Chart(conversationCtx, {
        type: 'line',
        data: {
            labels: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
            datasets: [{
                label: 'Conversations',
                data: [65, 59, 80, 81, 56, 55, 40],
                fill: true,
                borderColor: '#4f46e5',
                backgroundColor: 'rgba(79, 70, 229, 0.1)',
                tension: 0.4
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    display: false
                }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    grid: {
                        borderDash: [2, 4],
                        color: '#e5e7eb'
                    }
                },
                x: {
                    grid: {
                        display: false
                    }
                }
            }
        }
    });

    // User Distribution Chart
    const distributionCtx = document.getElementById('userDistributionChart').getContext('2d');
    new Chart(distributionCtx, {
        type: 'doughnut',
        data: {
            labels: ['Active', 'Inactive', 'New'],
            datasets: [{
                data: [65, 25, 10],
                backgroundColor: ['#4f46e5', '#6b7280', '#22c55e'],
                borderWidth: 0
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    position: 'bottom',
                    labels: {
                        usePointStyle: true,
                        padding: 20
                    }
                }
            },
            cutout: '75%'
        }
    });
}

// Sidebar Toggle Functionality
function initializeSidebar() {
    const toggleBtn = document.querySelector('.toggle-sidebar');
    const sidebar = document.querySelector('.sidebar');
    const mainContent = document.querySelector('.main-content');

    toggleBtn.addEventListener('click', () => {
        sidebar.classList.toggle('show');
        mainContent.classList.toggle('sidebar-hidden');
    });

    // Close sidebar on mobile when clicking outside
    document.addEventListener('click', (e) => {
        if (window.innerWidth <= 768 && 
            !sidebar.contains(e.target) && 
            !toggleBtn.contains(e.target)) {
            sidebar.classList.remove('show');
        }
    });
}

// Dropdown Initialization
function initializeDropdowns() {
    const userProfile = document.querySelector('.user-profile');
    const dropdowns = document.querySelectorAll('.dropdown');

    userProfile.addEventListener('click', (e) => {
        e.stopPropagation();
        userProfile.classList.toggle('show');
    });

    dropdowns.forEach(dropdown => {
        dropdown.addEventListener('click', (e) => {
            e.stopPropagation();
            dropdown.classList.toggle('show');
        });
    });

    // Close dropdowns when clicking outside
    document.addEventListener('click', () => {
        userProfile.classList.remove('show');
        dropdowns.forEach(dropdown => {
            dropdown.classList.remove('show');
        });
    });
}

// Update Stats Card Progress
function updateStatsProgress() {
    const progressElements = document.querySelectorAll('.stat-progress');
    progressElements.forEach(progress => {
        const value = parseFloat(progress.querySelector('span').textContent);
        if (value > 0) {
            progress.classList.add('positive');
            progress.classList.remove('negative');
        } else {
            progress.classList.add('negative');
            progress.classList.remove('positive');
        }
    });
}

// Handle Chart Period Selection
document.querySelectorAll('.chart-actions button').forEach(button => {
    button.addEventListener('click', function() {
        // Remove active class from all buttons
        this.parentElement.querySelectorAll('button').forEach(btn => {
            btn.classList.remove('active');
        });
        // Add active class to clicked button
        this.classList.add('active');
        
        // Update chart data based on selected period
        // This would typically involve an API call to get new data
        // For demo purposes, we'll just simulate a data update
        updateChartData(this.textContent.toLowerCase());
    });
});

// Simulate chart data update
function updateChartData(period) {
    // This would typically involve an API call
    // For demo purposes, we'll just log the period
    console.log(`Updating chart data for period: ${period}`);
}

// Initialize notifications
function initializeNotifications() {
    const notificationBell = document.querySelector('.nav-item i.bi-bell');
    notificationBell.addEventListener('click', () => {
        // This would typically involve showing a notification panel
        console.log('Notifications clicked');
    });
}

// Handle search functionality
const searchInput = document.querySelector('.search-box input');
searchInput.addEventListener('input', (e) => {
    const searchTerm = e.target.value.trim();
    if (searchTerm) {
        // This would typically involve searching through data
        console.log(`Searching for: ${searchTerm}`);
    }
});

// Add smooth transitions for all animations
document.documentElement.style.setProperty('--transition-speed', '0.3s');

// Initialize everything when the page loads
updateStatsProgress();
initializeNotifications();
