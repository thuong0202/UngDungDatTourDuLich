<?php
include "index.php";
$query = "SELECT * FROM category";
$result = mysqli_query($conn, $query);
$mangCate = array();
while ($rows = mysqli_fetch_assoc($result)) {
    $mangCate[] = $rows;
}
$json_cate = json_encode($mangCate); 
echo $json_cate;
?>
