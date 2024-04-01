<?php
require "index.php";

// Kiểm tra kết nối có lỗi không
$response = array();

if ($conn) {
    // Kiểm tra xem các biến POST tồn tại trước khi truy cập vào chúng
    if(isset($_POST['Coach_id'], $_POST['Coach_Name'], $_POST['Phone'], $_POST['Birthday'], $_POST['Gender'])) {
        $id = $_POST['Coach_id'];
        $name = $_POST['Coach_Name'];
        $phone = $_POST['Phone'];
        $raw_birthday = $_POST['Birthday'];
        $birthday = date('Y-m-d', strtotime($raw_birthday));

        $gender = $_POST['Gender'];

        // Kiểm tra xem số điện thoại đã tồn tại trong bảng user hay chưa
        $check_query = "SELECT * FROM users WHERE Phone = '$phone'";
        $check_result = mysqli_query($conn, $check_query);

        if ($check_result && mysqli_num_rows($check_result) == 0) {
            // Nếu số điện thoại chưa tồn tại, thực hiện truy vấn cập nhật thông tin của coach
            $update_query = "UPDATE coach 
                            SET Coach_Name = '$name', Phone = '$phone', Birthday = '$birthday', Gender = '$gender'
                            WHERE Coach_id = '$id'";
            
            if (mysqli_query($conn, $update_query)) {
                // Nếu truy vấn thành công, trả về trạng thái "success"
                $response = array("status" => "success");
            } else {
                // Nếu có lỗi trong quá trình thực hiện truy vấn, trả về trạng thái "error" và thông điệp lỗi
                $response = array("status" => "error", "message" => "Lỗi trong quá trình cập nhật thông tin của coach.");
            }
        } else {
            // Nếu số điện thoại đã tồn tại, trả về thông điệp
            $response = array("status" => "error", "message" => "Số điện thoại đã được sử dụng.");
        }
    }
}
echo json_encode($response);
?>
