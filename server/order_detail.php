<?php
include "index.php";
$query = "SELECT * FROM order_detail";
$data = mysqli_query($conn,$query);
$mangOrderDetail = array();
while($rows = mysqli_fetch_assoc($data))
{
	$mangOrderDetail[] = $rows;
}
$json_OrderDetail = json_encode($mangOrderDetail,JSON_UNESCAPED_UNICODE);
echo json_OrderDetail;
?>