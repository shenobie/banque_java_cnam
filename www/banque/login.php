<?php
    unset($_GET);
    
    if( isset($_POST['username']) && isset($_POST['password']) ) {
    
        echo '<?xml version="1.0"?>'."\n";
        echo "<login>\n";
    
    // host doit �tre remplac� par le serveur de la base de donn�es.
    // user repr�sente le nom d'utilisateur de la base de donn�es.
    // pass est le mot de passe pour acc�der � cette base de donn�es avec cette 
	// utilisateur.
        if (!@mysql_connect('localhost','root', '')) { error(1); }
    
	// database repr�sente le nom d ela base de donn�es
	    if (!mysql_select_db('banque')) { error(2); }
    
        if(get_magic_quotes_gpc()) {
            $login = stripslashes($_POST['username']);
            $pass  = stripslashes($_POST['password']);
        } else {
            $login = $_POST['username'];
            $pass  = $_POST['password'];
        }
    
        unset($_POST);
        
        $kid = login($login, $pass);
        if($kid == -1)
		{
			error(3); 
        }
		else
		{
            printf('<user id="%d"/>'."\n",$kid);
        }
                
        echo "</login>";
    }

    function error($ec)
	{
        printf('<error value="%d"/>'."\n".'</login>',$ec);
        die();
    }

    function login($login, $pass)
	{
        $select = "
		    SELECT id 
		    FROM client
		    WHERE id = '%s' AND password = '%s'";
		$fixedlogin = mysql_real_escape_string($login);
		$fixedpass  = mysql_real_escape_string($pass);
		$query = sprintf($select, $fixedlogin, $fixedpass);

        $result = mysql_query($query);
        if(mysql_num_rows($result) != 1)
		{
			return -1; 
		}    
        $row = mysql_fetch_row($result);
        return $row[0];
    }
?>
