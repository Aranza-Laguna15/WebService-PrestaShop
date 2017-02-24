<?php
$db_host="localhost";
$db_user="******";
$db_password="*********";
$db_database="**********";

try{

$con = mysql_connect($db_host, $db_user, $db_password);
mysql_select_db($db_database) or die("Error al seleccionar la base de datos:".mysql_error());

$sql="SELECT `comment_ID`,`comment_author`,`comment_author_email`,`comment_date`,`comment_content` FROM `wp_comments`";

$comments = mysql_query($sql,$con) or die(mysql_error());
$rows = array();
$total = mysql_num_rows($comments);

if($total > 0){
    while($rows=mysql_fetch_array($comments,MYSQL_ASSOC)){
    $comentarios= array("id_comment"=>$rows["comment_ID"], "author"=>$rows["comment_author"],"author_email"=>$rows["comment_author_email"], 
    "date"=>$rows["comment_date"], "content"=>$rows["comment_content"]);
    print_r(json_encode($comentarios)); 
	}

}else{
	echo 'Empty data';
}

}catch (Exception $e ) {
print( "Error query MySQL: \n".$e->getMessage());
}
?>