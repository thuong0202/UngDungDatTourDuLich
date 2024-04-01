<?php
include "index.php";
$query = "SELECT * FROM cate_image";
$result = mysqli_query($conn,$query);
foreach($result as $row)
    print_r($row);
?>
