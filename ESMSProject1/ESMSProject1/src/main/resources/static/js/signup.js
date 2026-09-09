// Rules show karo - jab password field click oh

function showRules() {
    document.getElementById('passwordRules').style.display = 'block';
}

// Rules Hide karo - jab bahar click ho
function hideRules() {
    const input = document.getElementById('signupPassword');
    //Agar password empty hai toh hide karo
    if (input.value.length === 0) {
        document.getElementById('passwordRules').style.display = 'none';
    }
}
function togglePassword() {
    const input = document.getElementById('signupPassword');
    const eye = document.querySelector('.eye-icon');
    if (input.type === "password") {
        input.type = "text";
        eye.textContent = "🙈";
    } else {
        input.type = "password";
        eye.textContent = "👁️";
    }
}
//Live Password Rules Check
function checkPassword(value) {
    const check = (id, condition, text) => {
        const el = document.getElementById(id);
        el.className = condition ? 'valid' : '';
        el.textContent = (condition ? '✓ ' : '✗ ') + text;
    };

    check('rule-length', value.length >= 8,           'Min 8 characters');
    check('rule-upper', /[A-Z]/.test(value),          'One uppercase letter (A-Z)');
    check('rule-lower', /[a-z]/.test(value),          'One lowercase letter (a-z)');
    check('rule-number', /[0-9]/.test(value),         'One number (0-9)');
    check('rule-special', /[!@#$%^&*]/.test(value),   'One special character (!@#$%^&*)');
}

// Eye Icon Toggle
/*function togglePassword(inputId, eyeIcon) {
    const input = document.getElementById(inputId);
    if (input.type === "password") {
        input.type = "text";
        eyeIcon.textContent = "🙈";
    } else {
        input.type = "password";
        eyeIcon.textContent = "👁️";
    }
}
// Password Rules Live Check
function checkPassword(value) {
    document.getElementById('rule-length').className = 'rule' + (value.length >= 8 ? 'valid' : '');
    document.getElementById('rule-upper').className = 'rule' + (/[A-Z]/.test(value) ? 'valid' : '');
    document.getElementById('rule-lower').className = 'rule' + (/[a-z]/.test(value) ? 'valid' : '');
    document.getElementById('rule-number').className = 'rule' + (/[0-9]/.test(value) ? 'valid' : '');
    document.getElementById('rule-special').className = 'rule' + (/[!@#$%^&*]/.test(value) ? 'valid' : '');
}*/