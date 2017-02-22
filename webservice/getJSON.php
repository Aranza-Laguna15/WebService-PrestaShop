<?php

    $site_base_path="../";
    if(!file_exists($site_base_path.'init.php')){
        die("There was an error connecting to Prestashop");
    }else{
        include($site_base_path . 'config/config.inc.php');
	    require_once($site_base_path . 'init.php');
    }

    $json = array();
    $json['products'] = getProducts();

    echo json_encode($json, true);

    function getProducts(){
	$db = Db::getInstance();
		
	$sql = 'SELECT p.id_product, pl.name, i.id_image, i.cover, pl.id_lang, pl.description_short, pl.description, p.price, 
				sa.quantity, p.reference, cp.id_category
			FROM '._DB_PREFIX_.'product p, '._DB_PREFIX_.'product_lang pl, '._DB_PREFIX_.'image i, '._DB_PREFIX_.'stock_available sa,
					'._DB_PREFIX_.'category_product cp
			WHERE pl.id_product = p.id_product
			AND i.id_product = p.id_product
			AND sa.id_product = p.id_product
			AND cp.id_product = p.id_product';
	if ($results = Db::getInstance()->ExecuteS($sql)){
		$i = 0;
		$data = array();
	    foreach ($results as $row){
	    	$i = $row['id_product'];
	        $data[$i]['lang'][$row['id_lang']]['name'] = $row['name'];
	        $data[$i]['lang'][$row['id_lang']]['description_short'] = $row['description_short'];
	        $data[$i]['lang'][$row['id_lang']]['description'] = $row['description'];
	        $data[$i]['code'] = $row['reference'];
	        $data[$i]['price'] = $row['price'];
	        $data[$i]['quantity'] = $row['quantity'];
	        if($row['cover'] == 1) $data[$i]['main_image'] = getProductImage($row['id_image']);
	        else{
	        	if(!isset($data[$i]['images'])) $data[$i]['images'] = array();
	        	$data[$i]['images'][$row['id_image']] = getProductImage($row['id_image']);
	        }
	        if(!isset($data[$i]['categories'])) $data[$i]['categories'] = array();
	        $data[$i]['categories'][$row['id_category']] = $row['id_category'];
	        $i++;
	    }
	}
	return $data;
}
function url(){
	$base_url = str_replace('e-gigi.com/', '', $_SERVER['HTTP_HOST'].$_SERVER['REQUEST_URI']);
    if(isset($_SERVER['HTTPS'])){
        $protocol = ($_SERVER['HTTPS'] && $_SERVER['HTTPS'] != "off") ? "https" : "http";
    }
    else{
        $protocol = 'http';
    }
    return $protocol . "://" . $base_url;
}

?>

<!-- 1*G!2,1b]: