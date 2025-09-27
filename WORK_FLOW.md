# WorkFlow Team

Quy tắc làm việc với Git (`Git Flow`). Đây là một `git flow` cơ bản, tùy dự án sẽ có một số `branch` khác phục vụ những môi trường khác nhau như `release-qc` cho tester test, `release-beta` cho người dùng test.

---

## Các Nhánh Chính (Branches)

- `master`: Là nhánh `default`, đây là nơi chứa source chính thức khi `release` sản phẩm ra cộng đồng.
- `hotfixes`: Nhánh này được `checkout` từ `master`, để sửa nhanh những lỗi của nhánh `master`. Sau khi sửa xong thì sẽ `merge` lại vào `master`. Bởi vì chúng ta không nên thay đổi code trực tiếp trên nhánh `master`, rất nguy hiểm đến sản phẩm đang chạy `production`.
- `release`: Nhánh này là nơi các tính năng đã hoàn thành và đang trong quá trình `test`, sau khi `test` xong thì sẽ được `merge` vào `master`.
- `develop`: Được `checkout` từ `master`, nhánh này là nơi chứa tất cả lịch sử `commit` của dự án. Khi dev đã hoàn thành xong một chức năng trên nhánh `feature` thì sẽ được `merge` vào nhánh `develop`. Leader sẽ `review` và sẽ tiến hành `merge develop` vào `release` để cho tester `test` tính năng đó.
- `feature`: Được `checkout` từ `develop`, `feature` ở đây có thể là `feature/login`, `feature/register`, `feature/cart`. Sau khi hoàn thành một `feature` sẽ tiến hành `merge` vào `develop`.

---

## Những nguyên tắc để dùng Git an toàn

### 1. Merge branch nhỏ vào branch tổng hoặc tạo merge request

Ví dụ: `merge feature/login` vào `develop`.

- Giúp cho Leader và các thành viên trong team có thể `review code` bạn và comment những đoạn code không hợp lý.
- Lưu lại lịch sử `code` của bạn (bao gồm cả `commit`), phục vụ việc kiểm tra sau này nếu dự án có vấn đề.
- Là nơi đánh giá `code` của các thành viên trong team, mọi người có thể học hỏi cách `code` lẫn nhau. Sau này nếu công ty có đánh giá nhân viên thì có thể xem lại cái `merge request` này để xem chất lượng `code` của nhân viên đó.

### 2. Xử lý conflict code

- Để hạn chế `conflict` thì hãy **chia nhỏ file, chia nhỏ code ra.**
- Thường xuyên cập nhật `local branch` để mới nhất so với `remote branch` bằng cách sử dụng lệnh `git pull`.
    - Ví dụ: mình code ở `feature/login`, để hạn chế việc xử lý `conflict` khi mình tiến hành `merge feature/login` vào `develop` trong quá trình mình code, mình thường hay `git pull origin develop` để code của mình luôn mới.
- Khi xuất hiện `conflict` thì hãy lựa chọn các option một cách cẩn thận, nên xem xét kêu gọi những người liên quan đến `conflict` để cùng giải quyết.

