<?php
include "index.php"; // Kết nối đến cơ sở dữ liệu

// Kiểm tra xem có yêu cầu tìm kiếm không
if(isset($_GET['search'])){
    // Lấy từ khóa tìm kiếm từ tham số GET
    $searchTerm = $_GET['search'];
    
    // Truy vấn SQL với điều kiện tìm kiếm
    $query = "SELECT o.*, p.* 
              FROM orders o 
              JOIN product p ON o.pro_id = p.pro_id
              WHERE p.pro_name LIKE '%$searchTerm%'";
} else {
    // Nếu không có yêu cầu tìm kiếm, lấy tất cả dữ liệu
    $query = "SELECT o.*, p.* 
              FROM orders o 
              JOIN product p ON o.pro_id = p.pro_id";
}

$data = mysqli_query($conn, $query);
$mangData = array();
while ($rows = mysqli_fetch_assoc($data)) {
    $mangData[] = $rows;
}
$json_Data = json_encode($mangData);
echo $json_Data; // Xuất dữ liệu dưới dạng JSON
?>
