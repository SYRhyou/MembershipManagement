<?php
	$con = mysqli_connect("localhost", "management", "fbtpduf94", "management");
	
	$userID = $_POST["userID"];
	$userPassword = $_POST["userPassword"];
	$userName = $_POST["userName"];
	$userAge = $_POST["userAge"];
	
	$statement = mysqli_prepare($con, "INSERT INTO MEMBER VALUES (?,?,?,?)");
	mysqli_stmt_bind_param($statement, "ssss", $userID, $userPassword, $userName, $userAge);
	mysqli_stmt_execute($statement);
	
	$response = array();
	$response["success"] = true;
	
	$json = json_encode(array("response"=>$response), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
	echo json_encode($response);
?>
	