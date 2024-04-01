<?php
include "index.php"; // Kết nối đến cơ sở dữ liệu

// Kiểm tra xem có yêu cầu tìm kiếm không
if(isset($_GET['search'])){
    // Lấy từ khóa tìm kiếm từ tham số GET
    $searchTerm = $_GET['Hoàn thành Thăng Long'];
    
    // Truy vấn SQL với điều kiện tìm kiếm
    $query = "SELECT p.*, s.*, c.*, ca.* 
              FROM product p 
              JOIN schedules s ON p.Schedule_id = s.SC_id
              JOIN coach c ON p.Coach_id = c.Coach_id
              JOIN category ca ON p.Cate_id = ca.Cate_id
              WHERE p.pro_name LIKE '%$searchTerm%'";
} else {
    // Nếu không có yêu cầu tìm kiếm, lấy tất cả dữ liệu
    $query = "SELECT p.*, s.*, c.*, ca.* 
              FROM product p 
              JOIN schedules s ON p.Schedule_id = s.SC_id
              JOIN coach c ON p.Coach_id = c.Coach_id
              JOIN category ca ON p.Cate_id = ca.Cate_id";
}

$data = mysqli_query($conn, $query);
$mangData = array();
while ($rows = mysqli_fetch_assoc($data)) {
    $mangData[] = $rows;
}
$json_Data = json_encode($mangData);
echo $json_Data; // Xuất dữ liệu dưới dạng JSON
?>
