<?php
include "index.php";
$query = "SELECT * FROM product_image";
$data = mysqli_query($conn,$query);
$mangProductImage = array();
while($rows = mysqli_fetch_assoc($data))
{
    $mangProductImage[] = $rows;
}
$json_ProductImage = json_encode($mangProductImage,JSON_UNESCAPED_UNICODE);
echo $json_ProductImage;
?>