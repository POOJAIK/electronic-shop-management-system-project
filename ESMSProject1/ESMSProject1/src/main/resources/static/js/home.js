// ======   hero slider =====

let currentSlide = 0;
const sliders = document.querySelectorAll('.hero-slide');
const dots = document.querySelectorAll('.dot');
let autoSliderTimer;

function goToSlider(n) {
    sliders[currentSlide].classList.remove('active');
    dots[currentSlide].classList.remove('active');
    currentSlide = n;
    sliders[currentSlide].classList.add('active');
    dots[currentSlide].classList.add('active');
}

function nextSlide() {
    goToSlider((currentSlide + 1) % slides.length);
    resetTimer();
}

function prevSlide() {
     goToSlider((currentSlide - 1 + slides.length) % slides.length);
     resetTimer();
}

function resetTimer() {
    clearInterval(autoSliderTimer);
    autoSliderTimer = setInterval(nextSlide, 4000);
}

// auto slide start
autoSliderTimer = setInterval(nextSlide, 4000);

//======= counter animation ======
function animateCounter(el) {
    const traget = parseInt(el.getAttribute('data-target'));
    const duration = 2000;
    const step = target / (duration / 16);
    let current = 0;

    const timer = setInterval(() => {
    current += step;
    if (current >= target) {
        current = target;
        clearInterval(timer);
    }
    el.textContent = Math.floor(current) + '+';
    }, 16);
}

// Counter starts when visible on screen

const counters = document.querySelectorAll('.counter');
const counterObserver = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
        if(entry.isIntersecting) {
            animateCounter(entry.traget);
            counterObserver.unobserve(entry.target);
        }
    });
});
counters.forEach(counter => counterObserver.observe(counter));

// ===== scroll Animations ======
const animateOnScroll =  new IntersectionObserver((entries) => {
    entries.forEach(entry => {
        if (entry.isIntersecting) {
            animateCounter(entry.traget);
            counterObserver.unobserve(entry.target);
        }
    });
});
counters.forEach(counter => counterObserver.observe(counter));

// ===== scroll animations ======
const animateOnScroll = new IntersectionObserver((entries) => {
    entries.foEach(entry => {
        if (entry.isIntersecting) {
            entry.target.style.opacity = '1';
            entry.target.style.transform = 'translateY(0)';
        }
    });
}, { threshold: 0.1});

// Product cards, feature cards, stat cards animate on scroll
document.querySelectorAll('.product-card, feature-card, .stat-card').forEach((el, index) => {
    el.style.opacity = '0';
    el.style.transform = 'opacity 0.6s ease ${index * 0.1}s, transform 0.6s ease ${index * 0.1}s';
    animateOnScroll.observe(el);
});

//===== navbar scroll effect =====
window.addEventListener('scroll', () => {
    const navbar = document.querySelector('.navbar');
    if (navbar) {
        if(window.scrollY > 50) {
            navbar.style.background = 'rgba(10,10,15,0.98)';
            navbar.style.boxShadow = ' 0 2px 20px rgba(0,0,0,0.5)';
        } else {
            navbar.style.background = '';
            navbar.style.boxShadow = '';
        }
    }
});
























