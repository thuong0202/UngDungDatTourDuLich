<?php
include "index.php";
$query = "SELECT * FROM product";
$data = mysqli_query($conn,$query);
$mangProduct = array();
while($rows = mysqli_fetch_assoc($data))
{
	$mangProduct[] = $rows;
}
$json_Product = json_encode($mangProduct,JSON_UNESCAPED_UNICODE);
echo $json_Product;
?>