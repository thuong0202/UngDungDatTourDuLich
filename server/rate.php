<?php
include "index.php";
$query = "SELECT * FROM rate";
$data = mysqli_query($conn,$query);
$mangRate = array();
while($rows = mysqli_fetch_assoc($data))
{
    $mangRate[] = $rows;
}
$json_Rate = json_encode($mangRate,JSON_UNESCAPED_UNICODE);
echo $json_Rate;
?>