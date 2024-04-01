<?php
require "index.php";

// Lấy thông báo lỗi từ kết nối
$error_message = mysqli_error($conn);

if (!empty($error_message)) {
    // Xử lý lỗi nếu có
    $response = array("status" => "error", "message" => $error_message);
} else {
    // Lấy dữ liệu từ request POST
    $name = isset($_POST['Coach_Name']) ? "'" . mysqli_real_escape_string($conn, $_POST['Coach_Name']) . "'" : "NULL";
    $phone = isset($_POST['Phone']) ? "'" . mysqli_real_escape_string($conn, $_POST['Phone']) . "'" : "NULL";
    $birthday = isset($_POST['Birthday']) ? "'" . date('Y-m-d', strtotime($_POST['Birthday'])) . "'" : "NULL"; // Chuyển đổi thành định dạng Y-m-d để lưu vào cơ sở dữ liệu
    $gender = isset($_POST['Gender']) ? "'" . mysqli_real_escape_string($conn, $_POST['Gender']) . "'" : "NULL"; // Lưu giới tính

    // Kiểm tra số điện thoại đã tồn tại trong bảng users hay bảng coach chưa
    $phone_check_query_users = "SELECT * FROM users WHERE Phone = $phone";
    $phone_check_query_coach = "SELECT * FROM coach WHERE Phone = $phone";

    $phone_check_result_users = mysqli_query($conn, $phone_check_query_users);
    $phone_check_result_coach = mysqli_query($conn, $phone_check_query_coach);
    
    if ($phone_check_result_users && $phone_check_result_coach) {
        // Kiểm tra số dòng kết quả trả về từ cả hai bảng
        if (mysqli_num_rows($phone_check_result_users) > 0 || mysqli_num_rows($phone_check_result_coach) > 0) {
            // Số điện thoại đã tồn tại trong ít nhất một trong hai bảng
            $response = array("status" => "error", "message" => "Số điện thoại đã tồn tại trong hệ thống.");
        } else {
            // Số điện thoại không tồn tại trong cả hai bảng, tiến hành thêm dữ liệu vào bảng coach
            $query = "INSERT INTO coach (Coach_Name, Phone, Birthday, Gender)
                      VALUES ($name, $phone, $birthday, $gender)";
            
            if (mysqli_query($conn, $query)) {
                $response = array("status" => "success");
            } else {
                $response = array("status" => "error", "message" => mysqli_error($conn));
            }
        }
    } else {
        // Lỗi trong việc thực thi truy vấn kiểm tra số điện thoại
        $response = array("status" => "error", "message" => "Lỗi trong việc kiểm tra số điện thoại.");
    }
}

echo json_encode($response);
?>
