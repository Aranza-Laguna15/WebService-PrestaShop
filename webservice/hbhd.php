<?php
$db_host="localhost";
$db_user="******";
$db_password="*****";
$db_database="*********";

try{

$con = mysql_connect($db_host, $db_user, $db_password);
mysql_select_db($db_database) or die("Error al seleccionar la base de datos:".mysql_error());

$sql="SELECT
  a.`id_product`,
  b.`name` AS `name`,
  a.`price` AS `price`,
  image_shop.`id_image` AS `id_image`,
  cl.`name` AS `name_category`,
  sav.`quantity` AS `sav_quantity`
FROM `ps_product` a LEFT JOIN `ps_product_lang` b ON (b.`id_product` = a.`id_product` AND b.`id_lang` = 2 AND b.`id_shop` = 1) LEFT JOIN `ps_stock_available` sav ON (sav.`id_product` = a.`id_product` AND sav.`id_product_attribute` = 0 AND sav.id_shop = 1 AND sav.id_shop_group = 0)JOIN `ps_product_shop` sa
ON (a.`id_product` = sa.`id_product` AND sa.id_shop = a.id_shop_default) LEFT JOIN `ps_category_lang` cl
ON (sa.`id_category_default` = cl.`id_category` AND b.`id_lang` = cl.`id_lang` AND cl.id_shop = a.id_shop_default) LEFT JOIN `ps_shop` shop
ON (shop.id_shop = a.id_shop_default) LEFT JOIN `ps_image_shop` image_shop ON(image_shop.`id_product` = a.`id_product` AND image_shop.`cover` = 1 AND image_shop.id_shop = a.id_shop_default) LEFT JOIN `ps_image` i ON (i.`id_image` = image_shop.`id_image`) LEFT JOIN `ps_product_download` pd
ON (pd.`id_product` = a.`id_product` AND pd.`active` = 1) WHERE 1 ORDER BY a.`id_product` ASC";
  
$product = mysql_query($sql,$con) or die(mysql_error());
$rows = array();
$total = mysql_num_rows($product);

if($total > 0){

while($rows=mysql_fetch_array($product,MYSQL_ASSOC)){
    $productos= array("id_product"=>$rows["id_product"], "name"=>$rows["name"],"price"=>$rows["price"], "id_image"=>$rows["id_image"],
     "category"=>$rows["name_category"], "stock"=>$rows["sav_quantity"]);
    print_r(json_encode($productos)); 
	}

}else{
	echo 'Empty data';
}

}catch (Exception $e ) {
print( "Error query MySQL: \n".$e->getMessage());
}
?>
