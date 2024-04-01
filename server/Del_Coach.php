<?php
require "index.php";

// Kiểm tra kết nối có lỗi không
$error_message = mysqli_error($conn); 
if (!empty($error_message)) {
    // Xử lý lỗi nếu có
    $response = array("status" => "error", "message" => $error_message);
} else {
    // Khởi tạo $response trước khi sử dụng
    $response = array();

    // Kiểm tra xem biến POST tồn tại trước khi truy cập vào nó
    if(isset($_POST['Coach_id'])) {
        $id = $_POST['Coach_id'];

        // Thực hiện truy vấn xóa
        $query = "DELETE FROM coach WHERE Coach_id = '$id'";
        if (mysqli_query($conn, $query)) {
            // Nếu truy vấn thành công, trả về trạng thái "success"
            $response = array("status" => "success");
        } else {
            // Nếu có lỗi trong quá trình thực hiện truy vấn, trả về trạng thái "error" và thông điệp lỗi
            $response = array("status" => "error", "message" => mysqli_error($conn));
        }
    } 
}

// Trả về dữ liệu dưới dạng JSON
echo json_encode($response);
?>
