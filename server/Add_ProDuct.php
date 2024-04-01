<?php
require "index.php";

// Khởi tạo một mảng kết quả
$result = array();

// Lấy thông báo lỗi từ kết nối
$error_message = mysqli_error($conn);

if (!empty($error_message)) {
    // Nếu có lỗi, đặt trạng thái là lỗi và đặt thông báo lỗi vào mảng kết quả
    $result["status"] = "error";
    $result["message"] = $error_message;
} else {
    // Nếu không có lỗi
    $name = isset($_POST['Pro_Name']) ? "'" . mysqli_real_escape_string($conn, $_POST['Pro_Name']) . "'" : "NULL";
    $price = isset($_POST['Price']) ? mysqli_real_escape_string($conn, $_POST['Price']) : "NULL"; // Không cần đặt trong dấu nháy đơn
    $location = isset($_POST['Location']) ? "'" . mysqli_real_escape_string($conn, $_POST['Location']) . "'" : "NULL";
    $schedule = isset($_POST['Schedule_id']) ? intval($_POST['Schedule_id']) : "NULL";
    $description = isset($_POST['Desciption']) ? "'" . mysqli_real_escape_string($conn, $_POST['Desciption']) . "'" : "NULL";
    $quantity = isset($_POST['Quantity']) ? intval($_POST['Quantity']) : "NULL";
    $coach = isset($_POST['Coach_id']) ? intval($_POST['Coach_id']) : "NULL";
    $number_of_people = isset($_POST['Number_Of_People_On_Tour']) ? intval($_POST['Number_Of_People_On_Tour']) : "NULL";
    $cate = isset($_POST['Cate_id']) ? intval($_POST['Cate_id']) : "NULL";
    $ac = isset($_POST['Ac_id']) ? intval($_POST['Ac_id']) : "NULL";
    $path = isset($_POST['Pro_path']) ? "'" . mysqli_real_escape_string($conn, $_POST['Pro_path']) . "'" : "NULL";

    $query = "INSERT INTO product(Pro_Name, Price, Location, Schedule_id, Desciption, Quantity, Coach_id, Number_Of_People_On_Tour, Cate_id, Ac_id, Pro_path)
            VALUES ($name, $price,$location, $schedule, $description, $quantity, $coach, $number_of_people, $cate, $ac, $path)";

    if (mysqli_query($conn, $query)) {
        // Nếu truy vấn thành công, đặt trạng thái là thành công
        $result["status"] = "success";
    } else {
        // Nếu truy vấn thất bại, đặt trạng thái là lỗi và đặt thông báo lỗi vào mảng kết quả
        $result["status"] = "error";
        $result["message"] = mysqli_error($conn);
    }
}
// Chuyển đổi mảng kết quả thành chuỗi JSON và trả về
echo json_encode($result);
?>
