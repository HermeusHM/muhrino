<?php
$response = array();

if (isset($_POST['values']) && isset($_POST['date']) && isset($_POST['id_sensor'])) {

    $value = $_POST['values'];
    $date = $_POST['date'];
    $id_sensor = $_POST['id_sensor'];

    require 'db_connect.php';

    $db = new DB_CONNECT();

    $result = mysql_query("INSERT INTO data(value, date, idMeasurement) VALUES('$value', '$date', (SELECT id FROM measurements WHERE idPoint = '$id_sensor' LIMIT 1))");

    if ($result) {
        $response["success"] = 1;
        $response["message"] = "Product successfully created.";

        echo json_encode($response);
    } else {
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";

        echo json_encode($response);
    }
} else {
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    echo json_encode($response);
}
?>