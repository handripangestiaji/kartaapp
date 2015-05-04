<?php
	
use Website\Controller\Action;

class ApiController extends Zend_Rest_Controller {
	
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
		    $array[$i]['longitude'] = $entry->getLongitude();
		    $array[$i]['latitude'] = $entry->getLatitude();
		    $array[$i]['timestamp_creation'] = $entry->getCreationDate();
		    $array[$i]['creation_date'] = date('Y-m-d', $entry->getCreationDate());
		    $i++;
	    }
	    
	    $json_resto = $this->_helper->json($array);
	    $this->sendResponse($json_resto);
    }
    
    public function menuAction()
    {
	    $params = $this->_getParam('id');
	    
	    $menu = new Object\Menu\Listing();
	    
	    $arr = array();
	    $i = 0;
	    
	    if($this->_request->isPost())
	    {
		    if($params!='') {
		    	$menu->setCondition('o_id = ?', $params);
		    }
	    }
	    else if($this->_request->isGet()){
		    if($params!='') {
			    $menu->setCondition('o_id = ?', $params);
		    }
	    }
	    
	    foreach($menu as $entry)
	    {
		    $arr[$i]['id'] = $entry->getO_Id();
		    $arr[$i]['name'] = $entry->getName();
		    $arr[$i]['restaurants_id'] = $entry->getRestaurants()->getO_Id();
		    $arr[$i]['restaurants'] = $entry->getRestaurants()->getName();
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
	    
	    $json_menu = $this->_helper->json($arr);
	    $this->sendResponse($json_menu);
    }
    
    public function categoriesAction()
    {
	    $id = $this->_getParam('id');
	    
	    $category = new Object\Categories\Listing();
	    
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