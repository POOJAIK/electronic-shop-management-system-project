// Jab bhi user type kare — products filter ho

const searchInput = document.getElementById('searchInput');

searchInput.addEventListener('input', function() {
// User ne jo type kiya usse lowercase mein lo
    const searchText = this.value.toLowerCase();

// Saare product cards lo
    const cards = document.querySelectorAll('.product-card');

    cards.forEach(card => {
// Har card ka product naam lo
    const productName = card.getAttribute('data-name').toLowerCase();

// Naam mein search text hai?
    if (productName.includes(searchText)) {
        card.style.display = 'block';        //dikhao
    } else {
            card.style.display = 'none';      // chhupaao
    }
    });
});

// ===== FILTER FUNCTIONALITY =====
// Filter button click hone par products filter karo
function filterProducts(category) {
    // Sabse pehle active class update karo
    document.querySelectorAll('.filter-btn').forEach(btn => {
        btn.classList.remove('active');
    });
    event.target.classList.add('active');

    // Saare cards lo
    const cards = document.querySelectorAll('.product-card');

    cards.forEach(card => {
        const productName = card.getAttribute('data-name').toLowerCase();

        // 'all' select kiya — sab dikhao
        if (category == 'all') {
            card.style.display = 'block';
        }
        // Category match karo product naam mein
        else if (productName.includes(category)) {
            card.style.display = 'block';
        }
        else {
            card.style.display = 'none';
        }
});
}

// ===== SCROLL ANIMATIONS =====
// Cards scroll karne par fade in hon
const animateOnScroll = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
        if(entry.isIntersecting) {
            entry.target.style.opacity = '1';
            entry.target.style.transform = 'translateY(0)';
        }
    });
} { threshold: 0.1});

//Har card pe animation lagao
document.querySelectorAll('.product-card').forEach((card, index) => {
    card.style.opacity = '0';
    card.style.transform = 'translateY(30px)';
    card.style.transition = 'opacity 0.5 ease ${index * 0.1}s,transform 0.5s ease ${index * 0.1}s';'

    animateOnScroll.observer(card);
});