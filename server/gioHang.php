<?php
require "index.php";

$response = array();

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    // Kiểm tra dữ liệu đã được gửi qua POST chưa
    if (
        isset($_POST['Phone']) &&
        isset($_POST['Pro_id']) &&
        isset($_POST['BookingTour']) &&
        isset($_POST['orderNumber'])
    ) {
        // Lấy dữ liệu từ POST và chuẩn bị chúng
        $phone = mysqli_real_escape_string($conn, $_POST['Phone']);
        $idPro = intval($_POST['Pro_id']);
        $date = date('Y-m-d', strtotime(str_replace('/', '-', $_POST['BookingTour'])));
        $numberOrder = intval($_POST['orderNumber']);

        // Chuẩn bị câu lệnh kiểm tra số điện thoại tồn tại trong bảng orders
        $checkQuery = "SELECT * FROM orders WHERE Phone = ?";
        $checkStmt = mysqli_prepare($conn, $checkQuery);
        mysqli_stmt_bind_param($checkStmt, "s", $phone);
        mysqli_stmt_execute($checkStmt);
        $checkResult = mysqli_stmt_get_result($checkStmt);

        if (mysqli_num_rows($checkResult) > 0) {
            // Số điện thoại đã tồn tại, trả về thông báo lỗi
            $response = array("status" => "phone_exists", "message" => "Số điện thoại đã đặt hàng");
        } else {
            // Lấy thông tin số lượng người tham gia tour hiện tại từ bảng product
            $getPeopleOnTourQuery = "SELECT Number_Of_People_On_Tour FROM product WHERE Pro_id = ?";
            $getPeopleOnTourStmt = mysqli_prepare($conn, $getPeopleOnTourQuery);
            mysqli_stmt_bind_param($getPeopleOnTourStmt, "i", $idPro);
            mysqli_stmt_execute($getPeopleOnTourStmt);
            $peopleOnTourResult = mysqli_stmt_get_result($getPeopleOnTourStmt);
            $row = mysqli_fetch_assoc($peopleOnTourResult);
            $peopleOnTour = $row ? $row['Number_Of_People_On_Tour'] : 0;

            // Kiểm tra nếu orderNumber lớn hơn số người tham gia tour hiện tại
            if ($numberOrder > $peopleOnTour) {
                $response = array("status" => "over", "message" => "Số lượng người đặt hàng vượt quá số người tham gia tour hiện tại");
            } else {
                // Tiến hành thêm dữ liệu vào bảng
                $query = "INSERT INTO orders (Phone, Pro_id, BookingTour, orderNumber) VALUES (?, ?, ?, ?)";
                $insertStmt = mysqli_prepare($conn, $query);
                mysqli_stmt_bind_param($insertStmt, "sisi", $phone, $idPro, $date, $numberOrder);
                
                if (mysqli_stmt_execute($insertStmt)) {
                    // Nếu insert thành công, cập nhật lại Number_Of_People_On_Tour trong bảng product
                    $updateQuery = "UPDATE product SET Number_Of_People_On_Tour = CASE 
                                    WHEN Number_Of_People_On_Tour < ? THEN 0 
                                    ELSE Number_Of_People_On_Tour - ? 
                                    END 
                                    WHERE Pro_id = ?";
                    $updateStmt = mysqli_prepare($conn, $updateQuery);
                    mysqli_stmt_bind_param($updateStmt, "iii", $numberOrder, $numberOrder, $idPro);
                    mysqli_stmt_execute($updateStmt);

                    $response = array("status" => "success");
                } else {
                    $response = array("status" => "error", "message" => mysqli_error($conn));
                }
            }
        }
    } else {
        $response = array("status" => "error", "message" => "Dữ liệu không hợp lệ");
    }
} 
echo json_encode($response);
?>
