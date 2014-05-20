<?php
header('content-type=application/json;charset=utf-8');
 
/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */
 
// array for JSON response
$response = array();
 
// check for required fields
if (isset($_GET['pid']) && isset($_GET['cible']) && isset($_GET['montant']) && isset($_GET['libelle'])) {

	$today = gmdate("Y-m-d H:i:s");
	$pid = $_GET['pid'];
	$cible = $_GET['cible'];
    $montant = $_GET['montant'];
    $libelle = $_GET['libelle'];
 
    // include db connect class
    require_once __DIR__ . '/db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();
 
    // mysql inserting a new row
	mysql_query("INSERT INTO operation_compte_depot(id_compte,libelle,montant,date_op) VALUES(".$pid.",'".$libelle."',-".$montant.",NOW());");
    $result = mysql_query("INSERT INTO operation_compte_depot(id_compte,libelle,montant,date_op) VALUES(".$cible.",'".$libelle."',".$montant.",NOW());");
 
    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "Virement effectue avec succes !.";
 
        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Oops! Erreur.";
 
        // echoing JSON response
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Champs(s) manquant(s)";
 
    // echoing JSON response
    echo json_encode($response);
}
?>