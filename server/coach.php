<?php
include "index.php";
$query = "SELECT * FROM coach";
$data = mysqli_query($conn,$query);
$mangCoach = array();
while($rows = mysqli_fetch_assoc($data))
{
	$mangCoach[] = $rows;
}
$json_Coach = json_encode($mangCoach,JSON_UNESCAPED_UNICODE);
echo $json_Coach;
?>