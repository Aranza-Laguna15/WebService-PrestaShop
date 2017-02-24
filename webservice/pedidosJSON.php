<?php
$db_host="localhost";
$db_user="******";
$db_password="*********";
$db_database="*********";

try{

$con = mysql_connect($db_host, $db_user, $db_password);
mysql_select_db($db_database) or die("Error al seleccionar la base de datos:".mysql_error());

$sql="SELECT SQL_CALC_FOUND_ROWS a.`id_order`, `reference`, `total_paid_tax_incl`, `payment`, a.`date_add` AS `date_add`, CONCAT(LEFT(c.`firstname`, 1), '. ', c.`lastname`) AS `customer`, osl.`name` AS `osname`, IF((SELECT so.id_order FROM `ps_orders` so WHERE so.id_customer = a.id_customer AND so.id_order < a.id_order LIMIT 1) > 0, 0, 1) as new, country_lang.name as cname FROM `ps_orders` a LEFT JOIN `ps_customer` c ON (c.`id_customer` = a.`id_customer`) LEFT JOIN `ps_address` address ON address.id_address = a.id_address_delivery LEFT JOIN `ps_country` country ON address.id_country = country.id_country LEFT JOIN `ps_country_lang` country_lang ON (country.`id_country` = country_lang.`id_country` AND country_lang.`id_lang` = 2) LEFT JOIN `ps_order_state` os ON (os.`id_order_state` = a.`current_state`) LEFT JOIN `ps_order_state_lang` osl ON (os.`id_order_state` = osl.`id_order_state` AND osl.`id_lang` = 2) WHERE 1 ORDER BY a.`id_order` ASC";

$orders = mysql_query($sql,$con) or die(mysql_error());
$rows = array();
$total = mysql_num_rows($orders);

if($total > 0){
    while($rows=mysql_fetch_array($orders,MYSQL_ASSOC)){
    $pedidos= array("id_order"=>$rows["id_order"], "reference"=>$rows["reference"],"total"=>$rows["total_paid_tax_incl"], 
    "payment"=>$rows["payment"], "date"=>$rows["date_add"], "customer"=>$rows["customer"], "state"=>$rows["osname"], 
    "new"=>$rows["new"], "country"=>$rows["cname"]);
    print_r(json_encode($pedidos)); 
	}

}else{
	echo 'Empty data';
}

}catch (Exception $e ) {
print( "Error query MySQL: \n".$e->getMessage());
}
?>