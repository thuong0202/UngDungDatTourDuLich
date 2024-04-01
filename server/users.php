<?php
    include "index.php";
    $query = "SELECT * FROM users";
    $data = mysqli_query($conn,$query);
    $mangUsers = array();
    while ($rows = mysqli_fetch_assoc($data)) 
    {
        $mangUsers[] = rows;
    }
$json_Users = json_encode($mangUsers,JSON_UNESCAPED_UNICODE);
echo $json_Users;
?>
