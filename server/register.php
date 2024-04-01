<?php
require "index.php";
$error_message = mysqli_error($conn); // Gán thông báo lỗi cho biến $error_message

// Kiểm tra xem có lỗi nào xảy ra hay không
if (!empty($error_message)) {
    // Xử lý lỗi nếu có
    $response = array("status" => "error", "message" => $error_message);
} else {
    // Lấy dữ liệu POST và làm sạch đầu vào
    $phone = mysqli_real_escape_string($conn, $_POST['Phone']);
    $pass = mysqli_real_escape_string($conn, $_POST['Passwords']);
    $role = mysqli_real_escape_string($conn, $_POST['Role_user']); 

    // Kiểm tra xem tài khoản đã tồn tại hay chưa
    $check_query = "SELECT * FROM users WHERE phone = '$phone'";
    $result = mysqli_query($conn, $check_query);
    if (mysqli_num_rows($result) > 0) {
        // Nếu tài khoản đã tồn tại, trả về thông báo JSON
        $response = array("status" => "error", "message" => "Tài khoản đã tồn tại");
    } else {
        // Kiểm tra các trường khác và thiết lập giá trị mặc định là NULL nếu chúng không được gửi từ ứng dụng
        $name = isset($_POST['FullName']) ? "'" . mysqli_real_escape_string($conn, $_POST['FullName']) . "'" : "NULL";
        $gender = isset($_POST['Gender']) ? "'" . mysqli_real_escape_string($conn, $_POST['Gender']) . "'" : "NULL";
		$birthday = isset($_POST['Birthday']) ? "'" . date('Y-m-d', strtotime(str_replace('/', '-', mysqli_real_escape_string($conn, $_POST['Birthday'])))) . "'" : "NULL";

        // Chuẩn bị và thực thi câu truy vấn
        $query = "INSERT INTO users
                  VALUES ('$phone', $name, $gender, $birthday, '$pass', '$role')";

        if (mysqli_query($conn, $query)) {
            $response = array("status" => "success");
        } else {
            $response = array("status" => "error", "message" => mysqli_error($conn));
        }
    }
}

echo json_encode($response);
?>
