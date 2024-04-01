<?php
require "index.php";

// Khởi tạo biến $response
$response = array();

// Kiểm tra xem có tồn tại ID của bảng product trong request không
if (isset($_POST['Pro_id'])) {
    $id = $_POST['Pro_id'];

    // Lấy thông báo lỗi từ kết nối
    $error_message = mysqli_error($conn);

    if (!empty($error_message)) {
        // Xử lý lỗi nếu có
        $response = array("status" => "error", "message" => $error_message);
    } else {
        // Lấy các giá trị được gửi từ form
        $name = isset($_POST['Pro_Name']) ? "'" . mysqli_real_escape_string($conn, $_POST['Pro_Name']) . "'" : "NULL";
        $price = isset($_POST['Price']) ? mysqli_real_escape_string($conn, $_POST['Price']) : "NULL";
        $location = isset($_POST['Location']) ? "'" . mysqli_real_escape_string($conn, $_POST['Location']) . "'" : "NULL";
        $schedule = isset($_POST['Schedule_id']) ? mysqli_real_escape_string($conn, $_POST['Schedule_id']) : "NULL";
        $description = isset($_POST['Desciption']) ? "'" . mysqli_real_escape_string($conn, $_POST['Desciption']) . "'" : "NULL";
        $quantity = isset($_POST['Quantity']) ? "'" . mysqli_real_escape_string($conn, $_POST['Quantity']) . "'" : "NULL";
        $coach = isset($_POST['Coach_id']) ? mysqli_real_escape_string($conn, $_POST['Coach_id']) : "NULL";
        $sl = isset($_POST['Number_Of_People_On_Tour']) ? "'" . mysqli_real_escape_string($conn, $_POST['Number_Of_People_On_Tour']) . "'" : "NULL";
        $cate = isset($_POST['Cate_id']) ? mysqli_real_escape_string($conn, $_POST['Cate_id']) : "NULL";
        $ac = isset($_POST['Ac_id']) ? mysqli_real_escape_string($conn, $_POST['Ac_id']) : "NULL";
        $path = isset($_POST['Pro_path']) ? "'" . mysqli_real_escape_string($conn, $_POST['Pro_path']) . "'" : "NULL";

        // Tạo câu lệnh SQL để cập nhật dữ liệu trong bảng product
        $query = "UPDATE product SET 
                    Pro_Name = $name, 
                    Price = $price, 
                    Location = $location, 
                    Schedule_id = $schedule, 
                    Desciption = $description, 
                    Quantity = $quantity, 
                    Coach_id = $coach, 
                    Number_Of_People_On_Tour = $sl, 
                    Cate_id = $cate, 
                    Ac_id = $ac, 
                    Pro_path = $path
                  WHERE Pro_id = $id";

        // Thực thi câu lệnh SQL
        if (mysqli_query($conn, $query)) {
            $response = array("status" => "success");
        } else {
            $response = array("status" => "error", "message" => mysqli_error($conn));
        }
    }
} elseif (isset($_POST['SC_id'])) { // Kiểm tra xem có tồn tại ID của bảng schedules trong request không
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

        // Tạo câu lệnh SQL để cập nhật dữ liệu trong bảng schedules
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
