<?php
require "index.php";

// Khởi tạo biến $response
$response = array();

// Kiểm tra xem có tồn tại SC_id trong yêu cầu không
if (isset($_POST['SC_id'])) {
    $id = $_POST['SC_id'];

    // Tạo câu lệnh SQL để xóa dữ liệu
    $query = "DELETE FROM schedules WHERE SC_id = $id";

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
