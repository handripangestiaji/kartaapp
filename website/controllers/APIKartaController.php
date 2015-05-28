<?php
	
use Website\Controller\Action;

class ApiKartaController extends Zend_Rest_Controller {
	
	public function init()
	{
		$this->_helper->viewRenderer->setNoRender(true);
		$this->_helper->AjaxContext()
                ->addActionContext('get','json')
                ->addActionContext('post','json')
                ->addActionContext('new','json')
                ->addActionContext('edit','json')
                ->addActionContext('put','json')
                ->addActionContext('delete','json')
                ->initContext('json');
	}
	
	private function sendResponse($content) {
		$this->getResponse()
			->setHeader('Content-Type', 'json')
			->setBody($content)
			->sendResponse();
		exit;
	}
	
	public function indexAction()
	{
		
	}
    
	public function restaurantsAction()
	{
	    
	    $params = $this->_getParam('id');
	    
	    $resto = new Object\Restaurants\Listing();
	    
	    $array = array();
	    $i = 0;
	    
	    if($params!='')
	    {
		    
		    if($this->_request->isPost())
		    {
			    if($params!='') {
					$resto->setCondition('o_id = ?', $params);
			  	}
		    }
		    else if($this->_request->isGet())
		    {
			    if($params!='') {
				    $resto->setCondition('o_id = ?', $params);
			    }
		    }
		    
		    foreach($resto as $entry)
		    {
			    $array[$i]['id'] = $entry->getO_Id();
			    $array[$i]['name'] = $entry->getName();
			    $y = 0;
			    foreach($entry->getCategory() as $category)
			    {
				    $array[$i]['categories'][$y]['id'] = $category->getO_Id();
				    $array[$i]['categories'][$y]['name'] = $category->getName();
				    $y++;
			    }
			    $array[$i]['description'] = $entry->getDescription();
			    $array[$i]['phone_number'] = $entry->getPhoneNumber();
			    $x = 0;
			    foreach($entry->getOperationHour() as $hour)
			    {
				    $array[$i]['operation'][$x]['day'] = $hour->getOperationDay();
				    $array[$i]['operation'][$x]['hour'] = $hour->getOperationHour();
				    $x++;
			    }
			    $array[$i]['website_url'] = $entry->getWebsiteUrl();
			    $array[$i]['profile_image_url'] = $entry->getProfileImage();
			    $array[$i]['email'] = $entry->getEmail();
			    $array[$i]['address'] = $entry->getAddress();
			    $array[$i]['city'] = $entry->getCity()->getName();
			    $array[$i]['state'] = $entry->getCity()->getState()->getName();
			    $array[$i]['zip_code'] = $entry->getZipCode();
			    $array[$i]['location']['longitude'] = $entry->getLocation()->getLongitude();
			    $array[$i]['location']['latitude'] = $entry->getLocation()->getLatitude();
			    $array[$i]['timestamp_creation'] = $entry->getCreationDate();
			    $array[$i]['creation_date'] = date('Y-m-d', $entry->getCreationDate());
			    $i++;
		    }
	    
	    }
	    else {
		    
		    $array[0]['error'] = "Invalid ID";
		    $array[0]['response_code'] = "403";
		    
	    }
	    $json_resto = $this->_helper->json($array);
	    $this->sendResponse($json_resto);
    }
    
    public function menulistAction()
    {	    
	    $params = $this->_getParam('category');
	
	    $menu = new Object\Menu\Listing();
	    $menu->setOrderKey('o_creationDate');
	    $menu->setOrder('desc');

	    if($params != "")
		$menu->setCondition("categories like '%" . $params . "%'");
	    	    
	    $arr = array();
	    $i = 0;
	    
	    foreach($menu as $entry)
	    {
		    $arr[$i]['id'] = $entry->getO_Id();
		    $arr[$i]['name'] = $entry->getName();
		    $arr[$i]['price'] = $entry->getPrice();
		    $arr[$i]['currency'] = $entry->getCurrency();
		    $arr[$i]['rating'] = $entry->getRating();
		    $arr[$i]['halal'] = $entry->getHalal();
		    $arr[$i]['description'] = $entry->getDescription();

		    $x = 0;
		    if(count($entry->getCategories()) > 0)
		    {
			foreach($entry->getCategories() as $category)
			{
				$arr[$i]['category'][$x]['id'] = $category->getO_Id();
				$arr[$i]['category'][$x]['name'] = $category->getName();
				$arr[$i]['category'][$x]['image'] = $_SERVER['HTTP_HOST'] . $category->image->path . $category->image->filename;;
				$x++;
			}			
		    }
		    
		    $arr[$i]['restaurants']['id'] = $entry->getRestaurants()->getO_Id();
		    $arr[$i]['restaurants']['name'] = $entry->getRestaurants()->getName();
		    $arr[$i]['restaurants']['address'] = $entry->getRestaurants()->getAddress();
		    $arr[$i]['restaurants']['location']['latitude'] = $entry->getRestaurants()->getLocation()->getLatitude();
		    $arr[$i]['restaurants']['location']['longitude'] = $entry->getRestaurants()->getLocation()->getLongitude();
		    $arr[$i]['restaurants']['city'] = $entry->getRestaurants()->getCity()->getName();
		    $arr[$i]['restaurants']['state'] = $entry->getRestaurants()->getCity()->getState()->getName();

		    $arr[$i]['thumb_image'] = $entry->thumb_image->path . $entry->thumb_image->filename;
		    
		    $x = 0;
		    if(count($entry->ingredients->items) > 0)
		    {
			foreach($entry->ingredients->items as $ingredient)
			{
				$arr[$i]['ingridients'][$x] = $ingredient->ingredient;
				$x++;
			}
		    }
		    
		    $x = 0;
		    if(count($entry->images->items) > 0)
		    {
			foreach($entry->images->items as $image)
			{
				$arr[$i]['images'][$x] = $_SERVER['HTTP_HOST'] . $image->image->path . $image->image->filename;
				$x++;
			}
		    }

		    $arr[$i]['timestamp_creation'] = $entry->getCreationDate();
		    $arr[$i]['creation_date'] = date('Y-m-d', $entry->getCreationDate());
		    
		    $i++;
	    }
	    
	    if($this->_request->isGet())
	    {
	    	$json_menu = $this->_helper->json($arr);
	    }
	    else {
		    $array = array();
		    $array[0]['error'] = "Method Not Allowed";
		    $array[0]['response_code'] = "405";
		    $json_menu = $this->_helper->json($array);
	    }
	    
	    $this->sendResponse($json_menu);
    }
    
    public function menuAction()
    {
	    $params = $this->_getParam('id');
	    
	    $menu = new Object\Menu\Listing();
	    
	    $arr = array();
	    $i = 0;
	    
	    if($params!='')
	    {
		    if($this->_request->isPost())
		    {
			    $menu->setCondition('o_id = ?', $params);
		    }
		    else if($this->_request->isGet())
		    {
				$menu->setCondition('o_id = ?', $params);
		    }
		    
		    foreach($menu as $entry)
		    {
			    $arr[$i]['id'] = $entry->getO_Id();
			    $arr[$i]['name'] = $entry->getName();
			    $arr[$i]['restaurants_id'] = $entry->getRestaurants()->getO_Id();
			    $arr[$i]['restaurants'] = $entry->getRestaurants()->getName();
			    
			    $x = 0;
			    foreach($entry->getRestaurants()->getCategory() as $category)
			    {
				    $arr[$i]['category'][$x]['id'] = $category->getO_Id();
				    $arr[$i]['category'][$x]['name'] = $category->getName();
				    $x++;
			    }
			    
			    $arr[$i]['image_url'] = $entry->getImage();
			    $arr[$i]['currency'] = $entry->getCurrency();
			    $arr[$i]['price'] = $entry->getPrice();
			    $arr[$i]['description'] = $entry->getDescription();
			    $arr[$i]['halal'] = $entry->getHalal();
			    
			    foreach($entry->getIngredients() as $ing)
			    {
				    $arr[$i]['ingredients'][] = $ing->getIngredient();
			    }
			    $arr[$i]['timestamp_creation'] = $entry->getCreationDate();
			    $arr[$i]['creation_date'] = date('Y-m-d', $entry->getCreationDate());
			    $i++;
		    }
	    }
		else {
			$arr[0]['error'] = 'Invalid ID';
			$arr[0]['response_code'] = '403';
		}
	    
	    $json_menu = $this->_helper->json($arr);
	    $this->sendResponse($json_menu);
    }
    
    public function categoriesAction()
    {
	    $limit = 10;
	    $page = (int) $this->_getParam('page') == '' ? 1 : $this->_getParam('page');
	    
	    $start = ($page * $limit) - $limit;
	    
	    $id = $this->_getParam('id');
	    
	    $category = new Object\Categories\Listing();
	    $category->setOffset($start);
	    $category->setLimit($limit);
	    
	    $arr = array();
	    $i = 0;
	    
	    if($this->_request->isPost())
	    {
		    if($id != '') {
		    	$category->setCondition('o_id = ?', $id);
		    }
	    }
	    else if($this->_request->isGet())
	    {
		    if($id != '') {
		    	$category->setCondition('o_id = ?', $id);
		    }
	    }
	    
	    foreach($category as $entry)
	    {
		    $arr[$i]['id'] = $entry->getO_Id();
		    $arr[$i]['name'] = $entry->getName();
		    $arr[$i]['image_url'] = $entry->getImage();
		    $arr[$i]['timestamp_creation'] = $entry->getCreationDate();
		    $arr[$i]['creation_date'] = date('Y-m-d', $entry->getCreationDate());
		    
		    $i++;
	    }
	    
	    $json_cat = $this->_helper->json($arr);
	    $this->sendResponse($arr);
    }
    
    public function headAction()
    {
	    
    }
    
    public function getAction()
    {
	    $content = "getAction()";
	    $this->sendResponse($content);
    }
    
    public function postAction()
    {
	    
    }
    
    public function putAction()
    {
	    
    }
    
    public function deleteAction()
    {
	    
    }
	
}