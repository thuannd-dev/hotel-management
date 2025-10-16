
document.addEventListener('DOMContentLoaded', function () {

    //--- Navbar Scroll Effect ---
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

    //--- Booking Form Date Validation ---
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
});