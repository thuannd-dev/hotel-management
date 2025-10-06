
document.addEventListener('DOMContentLoaded', function () {

    /*--- 1. Navbar Scroll Effect ---*/
    const navbar = document.querySelector('.navbar');
    if (navbar) {
        window.addEventListener('scroll', () => {
            if (window.scrollY > 50) {
                navbar.classList.add('scrolled');
            } else {
                navbar.classList.remove('scrolled');
            }
        });
    }

    /*--- 2. Booking Form Date Validation ---*/
    const arrivalDateInput = document.getElementById('arrivalDate');
    const departureDateInput = document.getElementById('departureDate');
    if (arrivalDateInput && departureDateInput) {
        const today = new Date().toISOString().split('T')[0];
        arrivalDateInput.setAttribute('min', today);
        arrivalDateInput.addEventListener('change', function () {
            if (this.value) {
                const arrivalDate = new Date(this.value);
                arrivalDate.setDate(arrivalDate.getDate() + 1);
                const nextDay = arrivalDate.toISOString().split('T')[0];
                departureDateInput.setAttribute('min', nextDay);
                if (departureDateInput.value && departureDateInput.value < nextDay) {
                    departureDateInput.value = '';
                }
            }
        });
    }

    /*--- 3. Scroll Animation ---*/
    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.classList.add('is-visible');
            }
        });
    }, {
        threshold: 0.1
    });
    const elementsToAnimate = document.querySelectorAll('.scroll-animate');
    elementsToAnimate.forEach(el => observer.observe(el));

    /*--- 5. Register Form Validation ---*/
    const registerForm = document.getElementById('registerForm');
    if (registerForm) {
        registerForm.addEventListener('submit', function (event) {
            event.preventDefault();
            event.stopPropagation();

            let isValid = true;

            this.querySelectorAll('.is-invalid').forEach(el => el.classList.remove('is-invalid'));
            this.querySelectorAll('.is-valid').forEach(el => el.classList.remove('is-valid'));

            const fullName = document.getElementById('signup-fullname');
            const username = document.getElementById('signup-username');
            const idNumber = document.getElementById('signup-idnumber');
            const dob = document.getElementById('signup-dob');
            const email = document.getElementById('signup-email');
            const phone = document.getElementById('signup-phone');
            const address = document.getElementById('signup-address');
            const password = document.getElementById('signup-password');
            const confirmPassword = document.getElementById('signup-confirm-password');

            function validate(field, condition) {
                if (condition) {
                    field.classList.add('is-valid');
                    return true;
                } else {
                    field.classList.add('is-invalid');
                    isValid = false;
                    return false;
                }
            }

            validate(fullName, fullName.value.trim() !== '');
            validate(username, username.value.length >= 6);
            validate(idNumber, /^[0-9]{9,12}$/.test(idNumber.value));
            validate(dob, dob.value !== '');
            validate(email, /^\S+@\S+\.\S+$/.test(email.value));
            validate(phone, /(0[3|5|7|8|9])+([0-9]{8})\b/.test(phone.value));
            validate(address, address.value.trim() !== '');
            validate(password, password.value.length >= 8);

            const passwordMatchErrorDiv = document.getElementById('password-match-error');
            if (password.value !== confirmPassword.value) {
                confirmPassword.classList.add('is-invalid');
                passwordMatchErrorDiv.innerText = "Passwords do not match.";
                isValid = false;
            } else {
                validate(confirmPassword, confirmPassword.value !== '');
                passwordMatchErrorDiv.innerText = "Please confirm your password.";
            }

            if (isValid) {
                this.submit();
            }
        });
    }
});