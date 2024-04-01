<?php
require "index.php";
$query = "SELECT * FROM orders ";
$data = mysqli_query($conn,$query);
$mangOrder = array();
while($rows = mysqli_fetch_assoc($data))
{
	$mangOrder[] = $rows;
}
$json_Order = json_encode($mangOrder,JSON_UNESCAPED_UNICODE);
echo $json_Order;
?>
