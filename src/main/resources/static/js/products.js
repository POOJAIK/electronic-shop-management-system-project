// Jab bhi user type kare — products filter ho

const searchInput = document.getElementById('search-bar').addEventListener('input', function() {

     const searchText = this.value.toLowerCase();
     const cards = document.querySelectorAll('.product-card');

     cards.forEach(card => {
         const productName = card.getAttribute('data-name').toLowerCase(); // Har card ka product naam lo

         if (productName.includes(searchText)) {
            card.style.display = 'block';              //dikhao
         } else {
                card.style.display = 'none';           // chhupaao
         }
     });
});
//        document.querySelectorAll('.product-card').forEach(c => {
//            console.log(c.getAttribute('data-category'))
//        })

// ===== FILTER FUNCTIONALITY =====
// Filter button click hone par products filter karo
//function filterProducts(category, event) {
//    console.log("clicked:", category);

    // Sabse pehle active class update karo
    document.querySelectorAll('.filter-btn').forEach(btn => {
        btn.addEventListener('click', function() {

            document.querySelectorAll('.filter-btn').forEach(b => {
                b.classList.remove('active');
            });
            this.classList.add('active');

            const category = this.getAttribute('data-filter');
            console.log("clicked:", category);

            //search clear
            document.getElementById('search-bar').value = '';

            // cards filter
            const cards = document.querySelectorAll('.product-card');
            cards.forEach(card => {

                const productCategory = card.getAttribute('data-category');
                if (category === 'all' || productCategory === category) {
                    card.style.display = 'block';
                } else {
                    card.style.display = 'none';
                }
            });
        });
    });

// ===== search =====

//document.getElementById('search-bar').addEventListener('input', function () {
//    const searchText = this.value.toLowerCase();
//    const cards = document.querySelectorAll('.product-card');
//
//    cards.forEach(card => {
//        const productName = card.getAttribute('data-name').toLowerCase();
//        if (productName.includes(searchText)) {
//            card.style.display = 'block';
//        } else {
//            card.style.display = 'none';
//        }
//    });
//});

// ===== SCROLL ANIMATIONS =====
// Cards scroll karne par fade in hon
const animateOnScroll = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
        if(entry.isIntersecting) {
            entry.target.style.opacity = '1';
            entry.target.style.transform = 'translateY(0)';
        }
    });
}, { threshold: 0.1});

//Har card pe animation lagao
document.querySelectorAll('.product-card').forEach((card, index) => {
    card.style.opacity = '0';
    card.style.transform = 'translateY(30px)';

    card.style.transition = `opacity 0.5s ease ${index * 0.1}s, transform 0.5s ease ${index * 0.1}s`;

    animateOnScroll.observe(card);
});