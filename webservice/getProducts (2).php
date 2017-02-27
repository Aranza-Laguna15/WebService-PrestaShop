<?php
        define('DEBUG', false);	
        define('PS_SHOP_PATH', '***************************');		// Root path of your PrestaShop store
        define('PS_WS_AUTH_KEY', '************************');
        require_once('./PSWebServiceLibrary.php');

        try{
            $webService = new PrestaShopWebservice(PS_SHOP_PATH,PS_WS_AUTH_KEY,DEBUG);
           
            $products['resource']= 'products';
            if(isset($_GET['id'])){
                $products['id']=(int)$_GET['id'];
            }
         
            $xml = $webService->get($products);

            $resources = $xml->product->children();

        }catch(PrestaShopWebserviceException $ex){
            $trace=$ex->getTrace();
            if ($trace[0]['args'][0] == 404) echo 'Bad ID '.$ex->getMessage();
	        else if ($trace[0]['args'][0] == 401) echo 'Bad auth key';
	        else echo 'Other error: '.$ex->getMessage();
        }

     echo '<table>';
        if(isset($resources)){
          //   if(!isset($_GET['id'])) {
             echo '<tr><th>ID</th><th>Nombre </th></tr>';
            foreach($resources as $resource){
                $products['id'] = $resource->attributes();
                 $xml = $webService->get($products);
                    $r = $xml->children()->children();
                    print_r($r);
                    echo '<tr><th>'.$r->id.'</th><th> </th></tr>';
            }
         /*  }else{
            foreach($resources as $key => $resource){
             echo '<tr><td>'.$key.'</td>';
             echo '<td>'.$resource.'</td></tr>';
            }
             }*/
        }
        echo '</table>';

?>

 <!--$xml = $webService ->get(array('resources' => 'products'));
            /* $products= array(
                'resource' => 'products',
                'display' => '[id_product,name]');
         
            $xml = $webService->get($products);

            $resources = $xml->products->children(); -->