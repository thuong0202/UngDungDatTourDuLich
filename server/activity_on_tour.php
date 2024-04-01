<?php
    include "index.php";
    $query = "SELECT * FROM activity_on_tour";
    $data = mysqli_query($conn,$query);
    $mangAc = array();
    while ($rows = mysqli_fetch_assoc($data)) 
    {
        $mangAc[] = rows;
    }
$json_activity = json_encode($mangAc,JSON_UNESCAPED_UNICODE);
echo $json_activity;
?>
