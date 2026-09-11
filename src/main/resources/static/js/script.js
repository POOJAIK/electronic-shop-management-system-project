<script>
const pw = document.getElementById('newPassword');
const confirm = document.getElementById('confirmPassword');

function check(id, test) {
    const el = document.getElementById(id);
    el.textContent = (test ? '✓ ' : '✗ ') + el.textContent.slice(2);
    el.style.color = test ? '#22c55e' : '#6d5fa0';
}

pw.addEventListener('input', function() {
    const v = this.value;
    check('r-upper',   /[A-Z]/.test(v));
    check('r-lower',   /[a-z]/.test(v));
    check('r-number',  /[0-9]/.test(v));
    check('r-special', /[!@#$%^&*()_+\-=\[\]{}|;:,<>?]/.test(v));
    check('r-length',  v.length >= 8);
});

confirm.addEventListener('input', function() {
    const msg = document.getElementById('matchMsg');
    if (this.value === pw.value) {
    msg.textContent = '✓ Passwords match';
    msg.style.color = '#22c55e';
    } else {
         msg.textContent = '✗ Passwords do not match';
         msg.style.color = '#ef4444';
    }
});

document.getElementById('resetForm').addEventListener('submit', function(e) {
    if (pw.value !== confirm.value) {
        e.preventDefault();
        alert('Password do not match!');
    }
});
</script>