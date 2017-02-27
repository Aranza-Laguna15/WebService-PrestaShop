<?php
$db_host="*********";
$db_name="************";
$db_user="***";
$db_password="****";

    try {
$con = new PDO("mysql:host=$db_host;dbname=$db_name", $db_user, $db_password);
echo 'Connected';

}catch ( PDOException $e ) {
print( "Error connecting to MySQL Server.\n" );
die(print_r($e->getMessage()));
}

/*if(function_exists('get_magic_quotes_gpc') && get_magic_quotes_gpc()) 
    { 
        function undo_magic_quotes_gpc(&$array) 
        { 
            foreach($array as &$value) 
            { 
                if(is_array($value)) 
                { 
                    undo_magic_quotes_gpc($value); 
                } 
                else 
                { 
                    $value = stripslashes($value); 
                } 
            } 
        } 
        undo_magic_quotes_gpc($_POST); 
        undo_magic_quotes_gpc($_GET); 
        undo_magic_quotes_gpc($_COOKIE); 
    } 
    
session_start();*/


?>

<!-- ;Database=$db_name -->