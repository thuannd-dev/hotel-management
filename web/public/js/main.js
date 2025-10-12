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

    /*--- 2. Booking Form Date Logic ---*/
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

    /*--- 4. Register Form Validation (PHẦN ĐƯỢC THÊM VÀO) ---*/
    const registerForm = document.getElementById('registerForm');
    if (registerForm) {
        registerForm.addEventListener('submit', function (event) {
            event.preventDefault(); // Ngăn form gửi đi để kiểm tra

            let isFormValid = true;

            // Xóa các lỗi cũ
            this.querySelectorAll('.is-invalid').forEach(el => el.classList.remove('is-invalid'));

            // Lấy các trường input
            const fullName = document.getElementById('signup-fullname');
            const username = document.getElementById('signup-username');
            const email = document.getElementById('signup-email');
            const phone = document.getElementById('signup-phone');
            const password = document.getElementById('signup-password');
            const confirmPassword = document.getElementById('signup-confirm-password');

            // Hàm hỗ trợ kiểm tra lỗi và cập nhật UI
            function validateField(field, condition) {
                if (condition) {
                    field.classList.remove('is-invalid');
                    return true;
                } else {
                    field.classList.add('is-invalid');
                    isFormValid = false;
                    return false;
                }
            }

            // Thực hiện kiểm tra
            validateField(fullName, fullName.value.trim() !== '');
            validateField(username, username.value.length >= 6);
            validateField(email, /^\S+@\S+\.\S+$/.test(email.value));
            validateField(phone, /(0[3|5|7|8|9])+([0-9]{8})\b/.test(phone.value));
            validateField(password, password.value.length >= 8);
            
            // Kiểm tra mật khẩu xác nhận
            if (password.value !== confirmPassword.value || confirmPassword.value === '') {
                confirmPassword.classList.add('is-invalid');
                // Cập nhật thông báo lỗi tương ứng
                const errorDiv = confirmPassword.nextElementSibling;
                if (errorDiv && errorDiv.classList.contains('invalid-feedback')) {
                    errorDiv.textContent = (password.value !== confirmPassword.value) 
                        ? 'Mật khẩu không khớp.' 
                        : 'Vui lòng xác nhận mật khẩu.';
                }
                isFormValid = false;
            } else {
                confirmPassword.classList.remove('is-invalid');
            }

            // Nếu tất cả hợp lệ, gửi form đi
            if (isFormValid) {
                console.log('Register form is valid, submitting...');
                this.submit();
            }
        });
    }


    /*--- 5. Booking Form Submission Validation ---*/
    const bookingForm = document.getElementById('bookingForm');
    if (bookingForm) {
        bookingForm.addEventListener('submit', function (event) {
            event.preventDefault();

            let isFormValid = true;
            
            // Lấy các trường input
            const arrivalDate = document.getElementById('arrivalDate');
            const departureDate = document.getElementById('departureDate');
            const roomType = document.getElementById('roomType');
            const adults = document.getElementById('adults');

            // Xóa lỗi cũ
            const fields = [arrivalDate, departureDate, roomType, adults];
            fields.forEach(field => field.classList.remove('is-invalid'));

            // Kiểm tra từng trường
            if (arrivalDate.value === '') {
                arrivalDate.classList.add('is-invalid');
                isFormValid = false;
            }
            if (departureDate.value === '') {
                departureDate.classList.add('is-invalid');
                isFormValid = false;
            }
            if (roomType.value === '') {
                roomType.classList.add('is-invalid');
                isFormValid = false;
            }
            if (adults.value < 1) {
                adults.classList.add('is-invalid');
                isFormValid = false;
            }

            // Nếu form hợp lệ, gửi đi
            if (isFormValid) {
                console.log('Booking form is valid, submitting...');
                this.submit();
            }
        });
    }
});