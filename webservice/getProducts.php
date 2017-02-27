<?php
        define('PS_SHOP_PATH', 'http://www.*****.com/*****/');		// Root path of your PrestaShop store
        define('PS_WS_AUTH_KEY', '***************************');
        require_once( './PSWebServiceLibrary.php' );

        try{
            $webService = new PrestaShopWebService(PS_SHOP_PATH,PS_WS_AUTH_KEY,false);
            //$xml = $webService ->get(array('resources' => 'products'));
            $products= array(
                'resource' => 'products',
                'display' => '[id_product,name]');
         
            $xml = $webService->get($products);

            $resources = $xml->products()->children();

        }catch(Exception $ex){
            $trace=$ex->getTrace();
            if ($trace[0]['args'][0] == 404) echo 'Bad ID';
	        else if ($trace[0]['args'][0] == 401) echo 'Bad auth key';
	        else echo 'Other error: '.$ex->getMessage();
        }

        echo '<table>';

        if(isset($resources)){
            echo '<tr><th>ID</th><th>Nombre </th></tr>';
            foreach($resources as $key => $resource){
                echo '<tr><td>'.$key.'</td><td>'.$resource.'</td></tr>';
            }
        }
        echo '</table>';
?>