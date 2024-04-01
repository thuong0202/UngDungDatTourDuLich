<?php
include "index.php"; // Kết nối đến cơ sở dữ liệu

// if(isset($_POST['search'])){
//     // Lấy từ khóa tìm kiếm từ tham số POST
//     $searchTerm = "Nha";

//     // Truy vấn SQL với điều kiện tìm kiếm chỉ trong cột Pro_Name của bảng product
//     $query = "SELECT p.*, s.*, c.*, ca.* 
//               FROM product p 
//               JOIN schedules s ON p.Schedule_id = s.SC_id
//               JOIN coach c ON p.Coach_id = c.Coach_id
//               JOIN category ca ON p.Cate_id = ca.Cate_id
//               WHERE p.Pro_Name LIKE '%$searchTerm%'";
// } else {
//     // Nếu không có yêu cầu tìm kiếm, lấy tất cả dữ liệu
//     $query = "SELECT p.*, s.*, c.*, ca.* 
//               FROM product p 
//               JOIN schedules s ON p.Schedule_id = s.SC_id
//               JOIN coach c ON p.Coach_id = c.Coach_id
//               JOIN category ca ON p.Cate_id = ca.Cate_id";
// }

// $data = mysqli_query($conn, $query);
// $mangData = array();
// while ($rows = mysqli_fetch_assoc($data)) {
//     $mangData[] = $rows;
// }
$searchTerm = $_GET['search'];


    //Truy vấn SQL với điều kiện tìm kiếm chỉ trong cột Pro_Name của bảng product
    $query = "SELECT p.*, s.*, c.*, ca.* 
              FROM product p 
              JOIN schedules s ON p.Schedule_id = s.SC_id
              JOIN coach c ON p.Coach_id = c.Coach_id
              JOIN category ca ON p.Cate_id = ca.Cate_id
              WHERE p.Pro_Name LIKE '%$searchTerm%'";
$data = mysqli_query($conn, $query);
$mangData = array();
while ($rows = mysqli_fetch_assoc($data)) {
    $mangData[] = $rows;
}
// Chuyển đổi mảng dữ liệu thành JSON
$json_Data = json_encode($mangData, JSON_UNESCAPED_UNICODE);
echo $json_Data; // Xuất dữ liệu dưới dạng JSON
?>
