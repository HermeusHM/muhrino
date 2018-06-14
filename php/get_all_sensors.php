<?php

$response = array();

require 'db_connect.php';

$db = new DB_CONNECT();

$result = mysql_query("SELECT *FROM points") or die(mysql_error());

if (mysql_num_rows($result) > 0) {
    $response = array();

    while ($row = mysql_fetch_array($result)) {
        $sensor = array();
        $sensor["pid"] = $row["id"];
        $sensor["name"] = $row["name"];
        $sensor["measurements"] = $row["measurements"];

        array_push($response, $sensor);
    }

    echo json_encode($response);
} else {
    echo json_encode($response);
}
?>