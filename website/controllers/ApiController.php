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
		
	public function categoriesAction()
	{
		
		$id = $this->_getParam('id');
		
		$category = new Object\Categories\Listing();
		$category->setOrderKey('name');
		$category->setOrder('ASC');
//		$category->setCondition("categoriesType = 'Restaurant'");
    		
		$array = array();
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
			$arr = array();

			$arr['id'] = $entry->getO_Id();
			$arr['name'] = $entry->getName();
			$arr['image_url'] = $entry->getImage();
			
			array_push($array, $arr);
		}
		
		$json_cat = $this->_helper->json($array);
		$this->sendResponse($arr);
	}
	
	public function menuAction()
	{
		$params = $this->_getParam('id');
		$category = $this->_getParam('category');
		$latitude = $this->_getParam('latitude');
		$longitude = $this->_getParam('longitude');
		$unit_distance = $this->_getParam('unit_distance');
		
		//$latitude = -6.552484;
		//$longitude = 106.771291;
		//$unit_distance = 'mi';
		
		$menu = new Object\Menu\Listing();
		
		$array = array();
		$i = 0;
		
		$where = '';
		
		if($category != "")
			$where = "categories like '%" . $category . "%' ";

		if($params != '')
		{
			if($this->_request->isPost())
			{
				$where .= (($where != "") ? " AND " : "") .  "o_id = " . $params;
			}
			else if($this->_request->isGet())
			{
				$where .= (($where != "") ? " AND " : "") .  "o_id = " . $params;
			}
		}
		
		if($where != '')
			$menu->setCondition($where);
		
		foreach($menu as $entry)
		{
			$valid = 1;
			$arr = array();
			
			$arr['o_key'] = $entry->o_key;
			$arr['id'] = $entry->getO_Id();
			$arr['name'] = $entry->getName();
			$arr['price'] = $entry->getPrice();
			$arr['currency'] = $entry->getCurrency()->symbol;
			$arr['rating'] = $entry->getRating();
			$arr['halal'] = $entry->getHalal();
			$arr['description'] = ($entry->getDescription() != null) ? $entry->getDescription() : 'No Description';

			// get diet categories
			$x = 0;
			if(count($entry->getCategories()) > 0)
			{
				foreach($entry->getCategories() as $category)
				{
					$arr['category'][$x]['id'] = $category->getO_Id();
					$arr['category'][$x]['name'] = $category->getName();
					$arr['category'][$x]['image'] = $_SERVER['REQUEST_SCHEME'] . '://' .  $_SERVER['HTTP_HOST'] . $category->image->path . $category->image->filename;;
					$x++;
				}			
			}
			else
			{
				$valid = 0;
			}
											    
			// get restaurant relation data
			if($entry->getRestaurants()->getO_Id() != null)
			{
				$arr['restaurants']['id'] = $entry->getRestaurants()->getO_Id();
				$arr['restaurants']['name'] = $entry->getRestaurants()->getName();
				$arr['restaurants']['address'] = $entry->getRestaurants()->getAddress();
				$arr['restaurants']['location']['latitude'] = $entry->getRestaurants()->getLocation()->getLatitude();
				$arr['restaurants']['location']['longitude'] = $entry->getRestaurants()->getLocation()->getLongitude();
				//$arr['restaurants']['city'] = $entry->getRestaurants()->getCity()->getName();
				//$arr['restaurants']['state'] = $entry->getRestaurants()->getCity()->getState()->getName();
			}
			else
			{
				$valid = 0;
			}

			// get image thumbnail
			if($entry->thumb_image != null)
			{
				$arr['thumb_image'] = $_SERVER['REQUEST_SCHEME'] . '://' .  $_SERVER['HTTP_HOST'] . $entry->thumb_image->path . $entry->thumb_image->filename;					
			}
			else
			{
				$arr['thumb_image'] = null;
			}
		    
			// get ingredients menu
			$x = 0;
			if(count($entry->ingredients->items) > 0)
			{
				foreach($entry->ingredients->items as $ingredient)
				{
					$arr['ingredients'][$x] = $ingredient->ingredient;
					$x++;
				}
			}
			else
			{
				$arr['ingredients'][$x] = "-";					
			}
		    
			// get image collection menu
			$x = 0;
			if(count($entry->images->items) > 0)
			{
			    foreach($entry->images->items as $image)
			    {
				    $arr['images'][$x] = $_SERVER['REQUEST_SCHEME'] . '://' .  $_SERVER['HTTP_HOST'] . $image->image->path . $image->image->filename;
				    $x++;
			    }
			}

			$arr['timestamp_creation'] = $entry->getCreationDate();
			$arr['creation_date'] = date('Y-m-d', $entry->getCreationDate());
			
			if(isset($latitude) && isset($longitude))
			{
				$distance_restaurant = Website_CalculateDistance::calculation($latitude, $longitude, $arr['restaurants']['location']['latitude'], $arr['restaurants']['location']['longitude'], $unit_distance);
				if($distance_restaurant > 70)
				{
					$valid = 0;
				}
				else
				{
					$arr['restaurants']['distance'] = Website_CalculateDistance::formating($distance_restaurant, $unit_distance);
				}
			}
			
			if($valid)
				array_push($array, $arr);
				
		}
				
		$json_menu = $this->_helper->json($array);
		$this->sendResponse($json_menu);
	}
	
	public function restaurantsAction()
	{	    
		$params = $this->_getParam('id');
		
		$resto = new Object\Restaurants\Listing();
		
		$arrays = array();
		$i = 0;
					
		if($params != '')
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
		}
		
		foreach($resto as $entry)
		{
			$valid = 1;
			$array = array();
			
			$array['o_key'] = $entry->o_key;
			$array['id'] = $entry->getO_Id();
			$array['name'] = $entry->getName();

			// query full menu
			$menu = new Object\Menu\Listing();	
			$menu->setCondition("restaurants__id = ". $entry->getO_Id());
			$menu->setOrderKey('name');
			$menu->setOrder('ASC');

			if($menu->count() > 0)
			{
				$average_rating = 0;
				$j = 0;			
				foreach($menu as $m)
				{
					$array['full_menu'][$j]['id'] = $m->getO_Id();
					$array['full_menu'][$j]['name'] = $m->getName();
					$array['full_menu'][$j]['price'] = $m->getPrice();
					$array['full_menu'][$j]['currency'] = $m->getCurrency()->symbol;
					$array['full_menu'][$j]['rating'] = $m->getRating();
					$array['full_menu'][$j]['halal'] = $m->getHalal();
		    
					if($m->thumb_image != null)
					{
						$array['full_menu'][$j]['thumb_image'] = $_SERVER['REQUEST_SCHEME'] . '://' .  $_SERVER['HTTP_HOST'] . $m->thumb_image->path . $m->thumb_image->filename;						
					}
					else
					{
						$array['full_menu'][$j]['thumb_image'] = null;						
					}
					
					$average_rating += $m->getRating();
					
					$j++;				
				}
				
				$total_menu = $j;
				$rating_restaurant = $average_rating / $total_menu;			    
			}
			else
			{
				$valid = 0;
			}
			
			// query top rated menu
			$limit = (j> 15) ? ceil(0.2 * $total_menu) : 3;
			$menu = new Object\Menu\Listing();
			$menu->setCondition("restaurants__id = ". $entry->getO_Id());
			$menu->setOrderKey('rating');
			$menu->setOrder('DESC');
			$menu->setLimit($limit);
			    
			if($menu->count() > 0)
			{
			    $j = 0;			
			    foreach($menu as $m)
			    {
				$array['recomended_menu'][$j]['id'] = $m->getO_Id();
				$array['recomended_menu'][$j]['name'] = $m->getName();
				$array['recomended_menu'][$j]['price'] = $m->getPrice();
				$array['recomended_menu'][$j]['currency'] = $m->getCurrency()->symbol;
				$array['recomended_menu'][$j]['rating'] = $m->getRating();
				$array['recomended_menu'][$j]['halal'] = $m->getHalal();
	    
				if($m->thumb_image != null)
				{
					$array['recomended_menu'][$j]['thumb_image'] = $_SERVER['REQUEST_SCHEME'] . '://' .  $_SERVER['HTTP_HOST'] . $m->thumb_image->path . $m->thumb_image->filename;						
				}
				else
				{
					$array['recomended_menu'][$j]['thumb_image'] = null;						
				}
								
				$j++;				
			    }
			}
			else
			{
				$valid = 0;
			}

			$x = 0;
			if(count($entry->imageCollection->items) > 0)
			{
				foreach($entry->imageCollection->items as $image)
				{
					$array['image_collection'][$x] = $_SERVER['REQUEST_SCHEME'] . '://' .  $_SERVER['HTTP_HOST'] . $image->image->path . $image->image->filename;
					$x++;
				}
			}
			else
			{
				$array['image_collection'] = null;
			}
		    
			$array['operation'] = 'No information';
			if(count($entry->getOperationHour()->items) > 0)
			{
				$array['operation'] = '';
				foreach($entry->getOperationHour() as $hour)
				{
					$array['operation'] .= $hour->getOperationDay() . ' ' .  $hour->getOperationHour() . '\n';
				}
			}
			
			if($entry->profileImage != null)
			{
				$array['profile_image'] = $_SERVER['REQUEST_SCHEME'] . '://' .  $_SERVER['HTTP_HOST'] . $entry->profileImage->path . $entry->profileImage->filename;				
			}
			else
			{
				$array['profile_image'] = null;
			}
		    
			$array['description'] = ($entry->getDescription() != null) ? $entry->getDescription() : 'No information';
			$array['phone_number'] = ($entry->getPhoneNumber() != null) ? $entry->getPhoneNumber() : 'No information';
			$array['website_url'] = ($entry->getWebsiteUrl() != null) ? $entry->getWebsiteUrl() : 'No information';
			$array['email'] = ($entry->getEmail() != null) ? $entry->getEmail() : 'No information';
			$array['zip_code'] = $entry->getZipCode();
			$array['address'] = ($entry->getAddress() != null) ?  $entry->getAddress() : '';

			$array['city'] = ($entry->getCity()->getName() != null) ? $entry->getCity()->getName() : '';
			$array['state'] = ($entry->getCity()->getState()->getName() != null) ? $entry->getCity()->getState()->getName() : '';

			$array['rating'] = round($rating_restaurant, 1);
			
			if($entry->getLocation()->getLongitude() != null && $entry->getLocation()->getLatitude() != null)
			{
				$array['location']['longitude'] = $entry->getLocation()->getLongitude();
				$array['location']['latitude'] = $entry->getLocation()->getLatitude();				
			}
			else
			{
				$valid = 0;
			}
			 
			if($valid)
				array_push($arrays, $array);
				
		}		
			
		$json_resto = $this->_helper->json($arrays);
		$this->sendResponse($json_resto);
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