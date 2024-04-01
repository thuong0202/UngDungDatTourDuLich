<?php
require "index.php";

// Khởi tạo biến $response
$response = array();

// Kiểm tra xem có tồn tại ID trong request không
if (isset($_POST['SC_id'])) {
    $id = $_POST['SC_id'];

    // Lấy thông báo lỗi từ kết nối
    $error_message = mysqli_error($conn);

    if (!empty($error_message)) {
        // Xử lý lỗi nếu có
        $response = array("status" => "error", "message" => $error_message);
    } else {
        // Lấy các giá trị được gửi từ form
        $location = isset($_POST['Location']) ? "'" . mysqli_real_escape_string($conn, $_POST['Location']) . "'" : "NULL";
        $departure_dates = isset($_POST['Departure_Dates']) ? "'" . date('Y-m-d', strtotime(str_replace('/', '-', $_POST['Departure_Dates']))) . "'" : "NULL"; // Chuyển đổi thành định dạng Y-m-d để lưu vào cơ sở dữ liệu
        $return_date = isset($_POST['Return_Date']) ? "'" . date('Y-m-d', strtotime(str_replace('/', '-', $_POST['Return_Date']))) . "'" : "NULL"; // Chuyển đổi thành định dạng Y-m-d để lưu vào cơ sở dữ liệu
        $hotel = isset($_POST['Hotel']) ? "'" . mysqli_real_escape_string($conn, $_POST['Hotel']) . "'" : "NULL";

        // Tạo câu lệnh SQL để cập nhật dữ liệu
        $query = "UPDATE schedules 
                  SET Location = $location, 
                      Departure_Dates = $departure_dates, 
                      Return_Date = $return_date, 
                      Hotel = $hotel
                  WHERE SC_id = $id";

        // Thực thi câu lệnh SQL
        if (mysqli_query($conn, $query)) {
            $response = array("status" => "success");
        } else {
            $response = array("status" => "error", "message" => mysqli_error($conn));
        }
    }
} 

// Trả về kết quả dưới dạng JSON
echo json_encode($response);
?>
