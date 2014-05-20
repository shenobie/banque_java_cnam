<?php
header('content-type=application/json;charset=utf-8');


 
/*
 * Following code will get single product details
 * A product is identified by product id (pid)
 */
 
// array for JSON response
$response = array();
 
// include db connect class
require_once __DIR__ . '/db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();
 
// check for post data
if (isset($_GET["pid"])) {
    $pid = $_GET['pid'];
    


       // $requete = "SELECT libelle,solde FROM livret_jeune WHERE id_client='".$login."'";
      // $result2 = mysql_query("SELECT libelle,solde FROM livret_jeune WHERE id_client=$pid");

        
        $result = mysql_query("SELECT id,libelle,solde FROM compte_depot WHERE id_client = $pid");
        
        if (!empty($result)) {
        // check for empty result
        if (mysql_num_rows($result) > 0)
        {
 
            $result = mysql_fetch_array($result);
 
            $compte = array();
			$compte["id"] = $result["id"];
            $compte["libelle"] = $result["libelle"];
            $compte["solde"] = $result["solde"];
            
            // success
            $response["success"] = 1;
 
            // user node
            $response["compte"] = array();
 
            array_push($response["compte"], $compte);
 
            // echoing JSON response
            echo json_encode($response);
        } else {
            // no product found
            $response["success"] = 0;
            $response["message"] = "No compte found";
 
            // echo no users JSON
            echo json_encode($response);
        }
    } else {
        // no product found
        $response["success"] = 0;
        $response["message"] = "No compte found";
 
        // echo no users JSON
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}
?>