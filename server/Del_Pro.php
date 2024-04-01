<?php
require "index.php";

// Khởi tạo biến $response
$response = array();

// Kiểm tra xem có tồn tại ID trong request không
if (isset($_POST['Pro_id'])) {
    $id = $_POST['Pro_id'];

    // Tạo câu lệnh SQL để xóa dữ liệu
    $query = "DELETE FROM product WHERE Pro_id = $id";

    // Thực thi câu lệnh SQL
    if (mysqli_query($conn, $query)) {
        $response = array("status" => "success");
    } else {
        $response = array("status" => "error", "message" => mysqli_error($conn));
    }
}

// Trả về kết quả dưới dạng JSON
echo json_encode($response);
?>
