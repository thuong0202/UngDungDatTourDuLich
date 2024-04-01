<?php
require "index.php";

// Lấy thông tin từ request
$fullName = isset($_POST['FullName']) ? "'" . mysqli_real_escape_string($conn, $_POST['FullName']) . "'" : "NULL";
$gender = isset($_POST['Gender']) ? "'" . mysqli_real_escape_string($conn, $_POST['Gender']) . "'" : "NULL";
$age = isset($_POST['Age']) ? "'" . mysqli_real_escape_string($conn, $_POST['Age']) . "'" : "NULL";
$phone = isset($_POST['Phone']) ? "'" . mysqli_real_escape_string($conn, $_POST['Phone']) . "'" : "NULL";
$orderId = isset($_POST['Or_id']) ? "'" . mysqli_real_escape_string($conn, $_POST['Or_id']) . "'" : "NULL";

// Tiến hành thêm dữ liệu vào bảng
$query = "INSERT INTO order_detail (FullName, Gender, Age, Phone, Or_id)
          VALUES ($fullName, $gender, $age, $phone, $orderId)";

if (mysqli_query($conn, $query)) {
    $response = array("status" => "success");
} else {
    $response = array("status" => "error", "message" => mysqli_error($conn));
}

echo json_encode($response);
?>
