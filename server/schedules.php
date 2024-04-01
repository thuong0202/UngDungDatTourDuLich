<?php
include "index.php";
$query = "SELECT * FROM schedules";
$data = mysqli_query($conn,$query);
$mangSchedules = array();
while($rows = mysqli_fetch_assoc($data))
{
    $mangSchedules[] = $rows;
}
$json_Schedules = json_encode($mangSchedules,JSON_UNESCAPED_UNICODE);
echo $json_Schedules;
?>