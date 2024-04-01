<?php
include "index.php";
$query = "SELECT * FROM users";
$data = mysqli_query($conn,$query);
$mangUser = array();
while($rows = mysqli_fetch_assoc($data))
{
    $mangUser[] = $rows;
}
$json_User = json_encode($mangUser,JSON_UNESCAPED_UNICODE);
echo $json_User;
?>