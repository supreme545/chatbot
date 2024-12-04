document.addEventListener('DOMContentLoaded', function() {
    // Create animated background
    createAnimatedBackground();

    // Initialize 3D effects
    init3DEffects();

    // Navbar scroll effect
    const navbar = document.querySelector('.navbar');
    window.addEventListener('scroll', function() {
        if (window.scrollY > 50) {
            navbar.classList.add('shadow-sm');
        } else {
            navbar.classList.remove('shadow-sm');
        }
    });

    // Smooth scrolling for anchor links
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function(e) {
            e.preventDefault();
            const target = document.querySelector(this.getAttribute('href'));
            if (target) {
                target.scrollIntoView({
                    behavior: 'smooth',
                    block: 'start'
                });
            }
        });
    });

    // Add animation to cards on scroll
    const cards = document.querySelectorAll('.feature-card, .pricing-card, .step-card');
    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.style.opacity = '1';
                entry.target.style.transform = 'translateY(0) rotateX(10deg)';
            }
        });
    }, {
        threshold: 0.1
    });

    cards.forEach(card => {
        card.style.opacity = '0';
        card.style.transform = 'translateY(50px) rotateX(0deg)';
        card.style.transition = 'all 0.8s cubic-bezier(0.4, 0, 0.2, 1)';
        observer.observe(card);
    });

    // Mobile menu handling with 3D effect
    const navbarToggler = document.querySelector('.navbar-toggler');
    const navbarCollapse = document.querySelector('.navbar-collapse');

    if (navbarToggler && navbarCollapse) {
        navbarToggler.addEventListener('click', () => {
            navbarCollapse.classList.toggle('show');
            if (navbarCollapse.classList.contains('show')) {
                navbarCollapse.style.transform = 'translateZ(20px)';
            } else {
                navbarCollapse.style.transform = 'translateZ(0)';
            }
        });
    }
});

// Create animated background
function createAnimatedBackground() {
    const background = document.createElement('div');
    background.className = 'animated-background';
    
    const circles = document.createElement('ul');
    circles.className = 'circles';
    
    for (let i = 0; i < 10; i++) {
        const li = document.createElement('li');
        li.style.left = `${Math.random() * 100}%`;
        li.style.width = li.style.height = `${Math.random() * 40 + 10}px`;
        li.style.animationDelay = `${Math.random() * 5}s`;
        li.style.animationDuration = `${Math.random() * 10 + 15}s`;
        circles.appendChild(li);
    }
    
    background.appendChild(circles);
    document.body.prepend(background);
}

// Initialize 3D effects
function init3DEffects() {
    // 3D card effect
    const cards = document.querySelectorAll('.feature-card, .pricing-card');
    
    cards.forEach(card => {
        card.addEventListener('mousemove', (e) => {
            const rect = card.getBoundingClientRect();
            const x = e.clientX - rect.left;
            const y = e.clientY - rect.top;
            
            const centerX = rect.width / 2;
            const centerY = rect.height / 2;
            
            const rotateX = (y - centerY) / 10;
            const rotateY = -(x - centerX) / 10;
            
            card.style.transform = `perspective(1000px) rotateX(${rotateX}deg) rotateY(${rotateY}deg) translateZ(20px)`;
        });
        
        card.addEventListener('mouseleave', () => {
            card.style.transform = 'perspective(1000px) rotateX(0) rotateY(0) translateZ(0)';
        });
    });

    // Parallax effect for hero section
    const heroContent = document.querySelector('.hero-content');
    const heroImage = document.querySelector('.hero-image');

    if (heroContent && heroImage) {
        window.addEventListener('mousemove', (e) => {
            const mouseX = e.clientX / window.innerWidth;
            const mouseY = e.clientY / window.innerHeight;

            heroContent.style.transform = `translate3d(${mouseX * 20}px, ${mouseY * 20}px, 30px)`;
            heroImage.style.transform = `translate3d(${mouseX * 40}px, ${mouseY * 40}px, 50px)`;
        });
    }

    // Add hover effect to buttons
    const buttons = document.querySelectorAll('.btn');
    buttons.forEach(btn => {
        btn.addEventListener('mousemove', (e) => {
            const rect = btn.getBoundingClientRect();
            const x = e.clientX - rect.left;
            const y = e.clientY - rect.top;
            
            btn.style.setProperty('--x', `${x}px`);
            btn.style.setProperty('--y', `${y}px`);
        });
    });
}

// Add ripple effect to buttons
document.addEventListener('click', function(e) {
    if (e.target.classList.contains('btn')) {
        const btn = e.target;
        const rect = btn.getBoundingClientRect();
        const x = e.clientX - rect.left;
        const y = e.clientY - rect.top;

        const ripple = document.createElement('span');
        ripple.className = 'ripple';
        ripple.style.left = `${x}px`;
        ripple.style.top = `${y}px`;

        btn.appendChild(ripple);

        setTimeout(() => {
            ripple.remove();
        }, 1000);
    }
});
