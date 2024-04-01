<?php
require "index.php";

// Lấy thông báo lỗi từ kết nối
$id = isset($_POST['SC_id']) ? "'" . mysqli_real_escape_string($conn, $_POST['SC_id']) . "'" : "NULL";
$location = isset($_POST['Location']) ? "'" . mysqli_real_escape_string($conn, $_POST['Location']) . "'" : "NULL";
$departure_dates = isset($_POST['Departure_Dates']) ? "'" . date('Y-m-d', strtotime(str_replace('/', '-', $_POST['Departure_Dates']))) . "'" : "NULL"; // Chuyển đổi thành định dạng Y-m-d để lưu vào cơ sở dữ liệu
$return_date = isset($_POST['Return_Date']) ? "'" . date('Y-m-d', strtotime(str_replace('/', '-', $_POST['Return_Date']))) . "'" : "NULL"; // Chuyển đổi thành định dạng Y-m-d để lưu vào cơ sở dữ liệu
$hotel = isset($_POST['Hotel']) ? "'" . mysqli_real_escape_string($conn, $_POST['Hotel']) . "'" : "NULL";

// Tiến hành thêm dữ liệu vào bảng
$query = "INSERT INTO schedules (SC_id, Location, Departure_Dates, Return_Date, Hotel)
          VALUES ($id, $location, $departure_dates, $return_date, $hotel)";

if (mysqli_query($conn, $query)) {
    // Lấy ID của lịch trình vừa được thêm vào
    $response = array("status" => "success");
} else {
    $response = array("status" => "error", "message" => mysqli_error($conn));
}

echo json_encode($response);
?>
