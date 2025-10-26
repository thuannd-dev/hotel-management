# Checkout Payment Feature Documentation

## Overview
This feature allows guests to checkout from their bookings and make payments. The system calculates room charges, service charges, applies taxes, and creates invoices and payment records.

## Feature Components

### 1. Database Entities
- **Invoice**: Stores invoice details for bookings
- **Payment**: Records payment transactions
- **TaxConfig**: Manages tax configuration (rate, effective dates)

### 2. Business Logic

#### Checkout Process Flow:
1. Guest selects a checked-in booking
2. System calculates:
   - Room charges (based on number of nights and room type price)
   - Service charges (sum of completed services)
   - Tax amount (based on active tax configuration)
   - Final amount
3. Guest selects payment method (Cash, Credit Card, Debit Card, or Online)
4. System creates:
   - Invoice record with all charges
   - Payment record with transaction details
5. System updates booking status to "Checked-out" and payment status to "Paid"

### 3. User Interface

#### Guest Checkout Pages:

**Page 1: Checkout Booking Selection** (`/guest/checkout`)
- Lists all bookings with status "Checked-in"
- Shows booking details (room, dates, guests)
- "Proceed to Checkout" button for each booking

**Page 2: Checkout Summary** (`/guest/checkout?action=summary&bookingId=X`)
- Displays booking information
- Shows detailed charge breakdown:
  - Room charges
  - Service charges
  - Subtotal
  - Tax (if applicable)
  - Discount (if applicable)
  - Final amount
- Payment method selection (radio buttons)
- "Complete Checkout" button

**Page 3: Confirmation**
- Redirects to "My Bookings" page with success message

## Technical Implementation

### Backend Components

#### Enums Created:
- `InvoiceStatus`: Unpaid, Paid, Canceled
- `PaymentMethod`: Cash, Credit Card, Debit Card, Online
- `PaymentTransactionStatus`: Pending, Completed, Failed

#### DAOs Created:
- `InvoiceDAO`: CRUD operations for invoices
- `PaymentDAO`: CRUD operations for payments
- `TaxConfigDAO`: Manages tax configurations
- `CheckoutDAO`: Calculates room and service charges
- `CheckoutBookingDAO`: Fetches bookings available for checkout

#### Services Created:
- `CheckoutService`: Main business logic for checkout process
  - `getCheckedInBookingsForGuest(guestId)`: Get bookings available for checkout
  - `calculateCheckoutSummary(bookingId)`: Calculate all charges
  - `processCheckout(bookingId, paymentMethod)`: Complete checkout transaction

#### Controllers Created:
- `CheckoutController`: Handles HTTP requests
  - GET `/guest/checkout`: Show booking selection
  - GET `/guest/checkout?action=summary&bookingId=X`: Show checkout summary
  - POST `/guest/checkout`: Process payment and complete checkout

### Frontend Components

#### JSP Views Created:
- `checkout-bookings.jsp`: Booking selection page
- `checkout-summary.jsp`: Checkout summary and payment page


## Business Rules

### Checkout Restrictions:
1. Only bookings with status "Checked-in" can be checked out
2. Only completed services are included in service charges
3. Tax is applied based on active tax configuration
4. Each booking can only have one invoice

### Validation:
- Booking must exist and be in "Checked-in" status
- Payment method must be selected
- No duplicate invoices allowed

### Status Updates:
- Booking status: "Checked-in" → "Checked-out"
- Booking payment status: Updated to "Paid"
- Invoice status: Set to "Paid"
- Payment status: Set to "Completed"



## Testing Scenarios

### Test Case 1: Normal Checkout
1. Create a booking with status "Checked-in"
2. Add completed services to the booking
3. Navigate to checkout
4. Verify charges are calculated correctly
5. Select payment method and complete checkout
6. Verify invoice and payment records are created
7. Verify booking status is updated to "Checked-out"

### Test Case 2: Checkout Without Services
1. Create a booking with status "Checked-in" but no services
2. Navigate to checkout
3. Verify only room charges are shown
4. Complete checkout
5. Verify service charges = 0

### Test Case 3: Invalid Booking Status
1. Try to checkout a booking with status "Reserved"
2. System should reject with error message
3. Try to checkout a booking already "Checked-out"
4. System should reject with error message

### Test Case 4: Duplicate Checkout Prevention
1. Complete checkout for a booking
2. Try to checkout the same booking again
3. System should prevent duplicate invoice creation




