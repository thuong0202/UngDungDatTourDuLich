<?php
	$host = "localhost";
	$username = "root";
	$password = "";
	$database = "ung_dung_dat_tour";
	$conn = mysqli_connect($host,$username,$password,$database);
	mysqli_query($conn,"SET NAMES 'utf8'");
?>