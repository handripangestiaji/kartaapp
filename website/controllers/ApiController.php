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
		
	public function registerAction()
	{
		$email = $_POST['email'];
		$password = $_POST['password'];
		
		if($email == '')
		{
			$array = array(
			       'status' => 'failed',
			       'message' => 'Email is required',
			       'data' => '');			

			$json_cat = $this->_helper->json($array);
			$this->sendResponse($json_cat);
			die();
		}
		if($password == '')
		{
			$array = array(
			       'status' => 'failed',
			       'message' => 'Password is required',
			       'data' => '');			

			$json_cat = $this->_helper->json($array);
			$this->sendResponse($json_cat);
			die();
		}
		
		// Get and set ID PARENT FOLDER
		$id_folder = new Object_List();
		$id_folder->setCondition("o_key='customer-list'");		    
		$parent_id = 0;
		foreach ($id_folder as $parent){
			$parent_id = $parent->getO_id();
		}
		
		$cek_user = new Object\Customers\Listing();
		$cek_user->setCondition("email = '". $email ."'");

		if($cek_user < 1)
		{
			$register = Object\Customers::create();
			$register->setKey(key);
			$register->setParentId($parent_id);
			$register->setemail($email);
			$register->setpassword(md5($password));
			$register->setPublished(1);	    
			$register->save();			

			$array = array(
				       'status' => 'success',
				       'message' => 'success',
				       'data' => $register);			
		}
		else
		{
			$array = array(
			       'status' => 'failed',
			       'message' => 'Email has been registered by  system',
			       'data' => '');						
		}    		
		
		$json_cat = $this->_helper->json($array);
		$this->sendResponse($json_cat);
	}

	public function loginAction()
	{		
		$email = $this->_getParam('email');
		$password = md5($this->_getParam('password'));
		
		$logins = new Object\Customers\Listing();
		$logins->setCondition("email = '". $email ."' AND password = '". $password ."'");
    		
		$array = array();
		if(count($logins) > 0)
		{
			foreach($logins as $login)
			{
				$array = array(
					'status' => 'success',
					'message' => 'success',
					'data' => $login);				
			}			
		}
		else
		{
			$array = array(
				'status' => 'failed',
				'message' => 'Email and password is not match',
				'data' => '');			
		}
		
		$json_cat = $this->_helper->json($array);
		$this->sendResponse($json_cat);
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
	
	public function setCounterCategoriesAction()
	{
		$params = $_GET['list_id_category'];
		$array_id = explode("@", $params);
		
		foreach($array_id as $id)
		{
			$myObject = Object_Categories::getById($id);
			$myObject->setViewCounter(($myObject->getViewCounter() == null) ? 1 : ($myObject->getViewCounter() + 1));
			$myObject->save();			
		}									
	}
		
	public function menuAction()
	{
		$params = $this->_getParam('id');
		$category = $this->_getParam('category');
		$latitude = $this->_getParam('latitude');
		$longitude = $this->_getParam('longitude');
		$unit_distance = $this->_getParam('unit_distance');
		$radius_distance = $this->_getParam('radius_distance');
				
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
			try
			{
				$valid = 1;
				$arr = array();
				
				$arr['o_key'] = $entry->o_key;
				$arr['id'] = $entry->getO_Id();
				$arr['name'] = $entry->getName();
				$arr['price'] = sprintf('%0.2f', $entry->getPrice());
				$arr['currency'] = $entry->getCurrency()->symbol;
				$arr['rating'] = $entry->getRating();
				$arr['halal'] = ($entry->getHalal() != null) ? $entry->getHalal() : false;
				$arr['description'] = ($entry->getDescription() != null) ? $entry->getDescription() : 'No Description';
	
				// get categories
				$arr['list_category'] = '';
				$arr['category'] = null;
				$x = 0;
				if(count($entry->getCategories()) > 0)
				{
					foreach($entry->getCategories() as $category)
					{
						$arr['list_category'] .= $arr['list_category'] == '' ? $category->getO_Id() : '@'. $category->getO_Id(); 
						
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
				
				// get sub categories
				$arr['sub_category'] = null;
				$x = 0;
				if(count($entry->subCategories) > 0)
				{
					foreach($entry->subCategories as $sub_category)
					{
						$arr['sub_category'][$x]['id'] = $sub_category->getO_Id();
						$arr['sub_category'][$x]['name'] = $sub_category->getName();
						$x++;
					}			
				}
												    
				// get restaurant relation data
				if($entry->getRestaurants() != null)
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
					if($distance_restaurant > $radius_distance)
					{
						$valid = 0;
					}
					else
					{
						$arr['restaurants']['distance_value'] = $distance_restaurant;
						$arr['restaurants']['distance_string'] = Website_CalculateDistance::formating($distance_restaurant, $unit_distance);
					}
				}
				
				if($valid)
					array_push($array, $arr);				
			}
			catch(Exception $e){}				
		}
				
		$json_menu = $this->_helper->json($array);
		$this->sendResponse($json_menu);
	}
	
	public function restaurantsAction()
	{	    
		$params = $this->_getParam('id');
		$limit = $this->_getParam('limit');
		$latitude = $this->_getParam('latitude');
		$longitude = $this->_getParam('longitude');
		$unit_distance = $this->_getParam('unit_distance');
		$radius_distance = $this->_getParam('radius_distance');
		
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
			$array['list_category'] = array();
			$array['list_sub_category'] = array();
			$array['all_menu'] = array();

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
					$full_menu[$j]['id'] = $m->getO_Id();
					$full_menu[$j]['name'] = $m->getName();
					$full_menu[$j]['price'] = sprintf('%0.2f', $m->getPrice());
					$full_menu[$j]['currency'] = $m->getCurrency()->symbol;
					$full_menu[$j]['rating'] = $m->getRating();
					$full_menu[$j]['halal'] = ($m->getHalal() != null) ? $m->getHalal() : false;
					$full_menu[$j]['description'] = ($m->getDescription() != null) ? $m->getDescription() : "No description";
		    
					if($m->thumb_image != null)
					{
						$full_menu[$j]['thumb_image'] = $_SERVER['REQUEST_SCHEME'] . '://' .  $_SERVER['HTTP_HOST'] . $m->thumb_image->path . $m->thumb_image->filename;						
					}
					else
					{
						$full_menu[$j]['thumb_image'] = null;						
					}
					
					if(count($m->getCategories()) > 0)
					{
						foreach($m->getCategories() as $category)
						{
							if(!in_array(array($category->name, $category->o_id), $array['list_category']))
							{
								array_push($array['list_category'], array($category->name, $category->o_id));
							}
						}			
					}

					if(count($m->getSubCategories()) > 0)
					{
						foreach($m->getSubCategories() as $subCategory)
						{
							if(!in_array(array($subCategory->name, $subCategory->o_id), $array['list_sub_category']))
							{
								array_push($array['list_sub_category'], array($subCategory->name, $subCategory->o_id));
							}
						}			
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
			$limit = ($total_menu > 15) ? ceil(0.2 * $total_menu) : 3;
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
					$recomended_menu[$j]['id'] = $m->getO_Id();
					$recomended_menu[$j]['name'] = $m->getName();
					$recomended_menu[$j]['price'] = sprintf('%0.2f', $m->getPrice());
					$recomended_menu[$j]['currency'] = $m->getCurrency()->symbol;
					$recomended_menu[$j]['rating'] = $m->getRating();
					$recomended_menu[$j]['halal'] = ($m->getHalal() != null) ? $m->getHalal() : false;
					$recomended_menu[$j]['description'] = ($m->getDescription() != null) ? $m->getDescription() : "No description";
		    
					if($m->thumb_image != null)
					{
						$recomended_menu[$j]['thumb_image'] = $_SERVER['REQUEST_SCHEME'] . '://' .  $_SERVER['HTTP_HOST'] . $m->thumb_image->path . $m->thumb_image->filename;						
					}
					else
					{
						$recomended_menu[$j]['thumb_image'] = null;						
					}
									
					$j++;				
				}			    
			}
			else
			{
				$valid = 0;
			}
			
			$array['all_menu'] = array();
			if($valid)
			{
				array_push($array['all_menu'], array("Top Rated Menu", $recomended_menu));
				array_push($array['all_menu'], array("Full Menu", $full_menu));				
			}
			else
			{
				$valid = 1;
			}
			
			// query menu from categories
			if(isset($array['list_category']))
			{
				foreach($array['list_category'] as $category)
				{					
					$menu = new Object\Menu\Listing();	
					$menu->setCondition("restaurants__id = ". $entry->getO_Id() . " AND categories like '%object|". $category[1] ."%'");
					$menu->setOrderKey('name');
					$menu->setOrder('ASC');
					
					$categories_menu =  array();
					if($menu->count() > 0)
					{
						$j = 0;			
						foreach($menu as $m)
						{
							$categories_menu[$j]['id'] = $m->getO_Id();
							$categories_menu[$j]['name'] = $m->getName();
							$categories_menu[$j]['price'] = sprintf('%0.2f', $m->getPrice());
							$categories_menu[$j]['currency'] = $m->getCurrency()->symbol;
							$categories_menu[$j]['rating'] = $m->getRating();
							$categories_menu[$j]['halal'] = ($m->getHalal() != null) ? $m->getHalal() : false;
							$categories_menu[$j]['description'] = ($m->getDescription() != null) ? $m->getDescription() : "No description";
				    
							if($m->thumb_image != null)
							{
								$categories_menu[$j]['thumb_image'] = $_SERVER['REQUEST_SCHEME'] . '://' .  $_SERVER['HTTP_HOST'] . $m->thumb_image->path . $m->thumb_image->filename;						
							}
							else
							{
								$categories_menu[$j]['thumb_image'] = null;						
							}
							$j++;				
						}
						
						array_push($array['all_menu'], array($category[0], $categories_menu));
					}		
				}
			}
			
			// query menu from sub categories
			if(isset($array['list_sub_category']))
			{
				foreach($array['list_sub_category'] as $sub_category)
				{					
					$menu = new Object\Menu\Listing();	
					$menu->setCondition("restaurants__id = ". $entry->getO_Id() . " AND subCategories like '%object|". $sub_category[1] ."%'");
					$menu->setOrderKey('name');
					$menu->setOrder('ASC');
					
					$sub_categories_menu =  array();
					if($menu->count() > 0)
					{
						$j = 0;			
						foreach($menu as $m)
						{
							$sub_categories_menu[$j]['id'] = $m->getO_Id();
							$sub_categories_menu[$j]['name'] = $m->getName();
							$sub_categories_menu[$j]['price'] = sprintf('%0.2f', $m->getPrice());
							$sub_categories_menu[$j]['currency'] = $m->getCurrency()->symbol;
							$sub_categories_menu[$j]['rating'] = $m->getRating();
							$sub_categories_menu[$j]['halal'] = ($m->getHalal() != null) ? $m->getHalal() : false;
							$sub_categories_menu[$j]['description'] = ($m->getDescription() != null) ? $m->getDescription() : "No description";
				    
							if($m->thumb_image != null)
							{
								$sub_categories_menu[$j]['thumb_image'] = $_SERVER['REQUEST_SCHEME'] . '://' .  $_SERVER['HTTP_HOST'] . $m->thumb_image->path . $m->thumb_image->filename;						
							}
							else
							{
								$sub_categories_menu[$j]['thumb_image'] = null;						
							}
							$j++;				
						}
						
						array_push($array['all_menu'], array($sub_category[0], $sub_categories_menu));
					}		
				}
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
		    
			$array['operation_day'] = array();
			$array['operation_hour'] = array();
			$x = 0;
			if(count($entry->getOperationHour()->items) > 0)
			{
				foreach($entry->getOperationHour() as $hour)
				{
					$array['operation_day'][$x] = $hour->getOperationDay();
					$array['operation_hour'][$x] = $hour->getOperationHour();
					$x++;
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

			$temp_list_category = array();
			foreach($array['list_category'] as $lst_ctgr)
			{
				array_push($temp_list_category, $lst_ctgr[1]);
			}
			$array['list_id_category'] = implode("@", $temp_list_category);				
			
			if($entry->getLocation()->getLongitude() != null && $entry->getLocation()->getLatitude() != null)
			{
				$array['location']['longitude'] = $entry->getLocation()->getLongitude();
				$array['location']['latitude'] = $entry->getLocation()->getLatitude();				
			}
			else
			{
				$valid = 0;
			}
			
			if(isset($latitude) && isset($longitude))
			{
				$distance_restaurant = Website_CalculateDistance::calculation($latitude, $longitude, $array['location']['latitude'], $array['location']['longitude'], $unit_distance);
				if($distance_restaurant > $radius_distance)
				{
					$valid = 0;
				}
				else
				{
					$array['distance_value'] = $distance_value;
					$array['distance_string'] = Website_CalculateDistance::formating($distance_restaurant, $unit_distance);
				}
			}
			 
			if($valid)
				array_push($arrays, $array);
				
		}
					
		$json_resto = $this->_helper->json($arrays);
		$this->sendResponse($json_resto);
	}
	
	public function restaurantsLimitAction()
	{	    
		$params = $this->_getParam('id');
		$limit = $this->_getParam('limit');
		$latitude = $this->_getParam('latitude');
		$longitude = $this->_getParam('longitude');
		$unit_distance = $this->_getParam('unit_distance');
		$radius_distance = $this->_getParam('radius_distance');
		
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
			if(isset($latitude) && isset($longitude))
			{
				$distance_restaurant = Website_CalculateDistance::calculation($latitude, $longitude, $entry->getLocation()->getLatitude(), $entry->getLocation()->getLongitude(), $unit_distance);
				if($distance_restaurant <= $radius_distance){
					$myObject = Object_Restaurants::getById($entry->o_id);
					$myObject->setDistance($distance_restaurant);
					$myObject->save();
				}
			}
		}
		
		$resto = new Object\Restaurants\Listing();
		$resto->setOrderKey('distance');
		$resto->setOrder('ASC');
		$resto->setLimit($limit);
		
		foreach($resto as $entry)
		{
			$valid = 1;
			$array = array();
			
			$array['o_key'] = $entry->o_key;
			$array['id'] = $entry->getO_Id();
			$array['name'] = $entry->getName();
					    
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
			
			if(isset($latitude) && isset($longitude))
			{
				if($distance_restaurant > $radius_distance)
				{
					$valid = 0;
				}
				else
				{
					$array['distance_value'] = $entry->getDistance();
					$array['distance_string'] = Website_CalculateDistance::formating($entry->getDistance(), $unit_distance);
				}
			}
			 
			if($valid)
				array_push($arrays, $array);
				
		}
					
		$json_resto = $this->_helper->json($arrays);
		$this->sendResponse($json_resto);
	}
	
	public function searchResultAction()
	{		
		$keyword = $this->_getParam('keyword');		
		$search_by = $this->_getParam('search_by');		
		$type = $this->_getParam('type');
		$category = $this->_getParam('category');
		$sort_by = $this->_getParam('sort_by');
		$sort_type = $this->_getParam('sort_type');
		$latitude = $this->_getParam('latitude');
		$longitude = $this->_getParam('longitude');
		$unit_distance = $this->_getParam('unit_distance');
		$radius_distance = $this->_getParam('radius_distance');
		$limit = $this->_getParam('limit');
		
		$full_keyword = $keyword;
		$keyword = explode(' ', strtolower ($keyword));
		
		$array = array();
		$i = 0;
		
		$select = '';
		$where = '';
		$where1 = '';
		$groupby = '';
		$join_asset = '';
		
		if($category != "all categories" &&  $category != "")
		{
			$where .= " (categories like '%object|" . $category . ",%') AND ";
			$where1 .= " (categories like '%object|" . $category . ",%') AND ";
		}
		
		if(count($keyword) > 0)
		{
			if($type == "menus")
			{
				if($search_by == 'name or ingredients')
				{
					if(count($keyword) > 1)
						$where1 .= "tblMenu.name like '%" . $full_keyword . "%' OR tblIngredients.ingredient like '%" . $full_keyword . "%' ";							
										
					$i = 0;
					foreach($keyword as $key)
					{
						$where .= " tblMenu.name like '%" . $key . "%' OR tblIngredients.ingredient like '%" . $key . "%' " . ((++$i >= count($keyword)) ? "" : " OR ");							
					}
				}
				else if($search_by == 'name')
				{
					if(count($keyword) > 1)
						$where1 .= " tblMenu.name like '%" . $full_keyword . "%' ";							

					$i = 0;
					foreach($keyword as $key)
					{
						$where .= " tblMenu.name like '%" . $key . "%' " . ((++$i >= count($keyword)) ? "" : " OR ");							
					}
				}
				else
				{
					if(count($keyword) > 1)
						$where1 .= " tblIngredients.ingredient like '%" . $full_keyword . "%' ";							

					$i = 0;
					foreach($keyword as $key)
					{
						$where .= "tblIngredients.ingredient like '%" . $key . "%' " . ((++$i >= count($keyword)) ? "" : " OR ");							
					}										
				}
				
				$where .= 'AND (tblMenu.o_published = 1)';
				$where1 .= ((count($keyword) > 1) ? " AND " : " ") . "(tblMenu.o_published = 1)";
				$select = "tblRestaurant.oo_id as restaurant_id,
						tblRestaurant.name as restaurant_name,
						tblMenu.oo_id as menu_id,
						tblMenu.name as menu_name,
						tblCurrency.symbol as menu_currency,
						tblMenu.price as menu_price,
						tblMenu.rating as menu_rating,
						tblMenu.description as menu_description,
						tblMenu.halal as menu_halal,
						tblAssets.path as image_path,
						tblAssets.filename as image_filename
					";
					
				$groupby = "GROUP BY tblMenu.name";
				$join_asset = 'left join assets as tblAssets on tblMenu.thumb_image = tblAssets.id';
			}			
			else
			{
				if($search_by == 'name or ingredients')
				{
					if(count($keyword) > 1)
						$where1 .= " tblRestaurant.name like '%" . $full_keyword . "%' OR tblIngredients.ingredient like '%" . $full_keyword . "%' " . ((++$i >= count($keyword)) ? "" : " OR ");							

					$i = 0;
					foreach($keyword as $key)
					{
						$where .= "tblRestaurant.name like '%" . $key . "%' OR tblIngredients.ingredient like '%" . $key . "%' " . ((++$i >= count($keyword)) ? "" : " OR ");							
					}						
				}
				else if($search_by == 'name')
				{
					if(count($keyword) > 1)
						$where1 .= " tblRestaurant.name like '%" . $full_keyword . "%' " . ((++$i >= count($keyword)) ? "" : " OR ");							

					$i = 0;
					foreach($keyword as $key)
					{
						$where .= "tblRestaurant.name like '%" . $key . "%' " . ((++$i >= count($keyword)) ? "" : " OR ");							
					}											
				}
				else
				{
					if(count($keyword) > 1)
						$where1 .= " tblIngredients.ingredient like '%" . $full_keyword . "%' " . ((++$i >= count($keyword)) ? "" : " OR ");							

					$i = 0;
					foreach($keyword as $key)
					{
						$where .= "tblIngredients.ingredient like '%" . $key . "%' " . ((++$i >= count($keyword)) ? "" : " OR ");							
					}											
				}
				
				$where .= 'AND (tblRestaurant.o_published = 1)';
				$where1 .= 'AND (tblRestaurant.o_published = 1)';
				$select = "tblRestaurant.oo_id as restaurant_id,
						tblRestaurant.name as restaurant_name,
						tblRestaurant.address as restaurant_address,
						tblAssets.path as image_path,
						tblAssets.filename as image_filename
					";
					
				$groupby = "GROUP BY tblRestaurant.name";
				$join_asset = 'left join assets as tblAssets on tblRestaurant.profileImage = tblAssets.id';
			}			
		}
		
		$cons = 6371;
		if($unit_distance == "mi"){ $cons = 3956; }
		$select .= ", (". $cons ." * 2 * ASIN(SQRT( POWER(SIN(((tblRestaurant.location__latitude) - (". $latitude .")) *  pi()/180 / 2), 2) +COS((tblRestaurant.location__latitude) * pi()/180) * COS((". $latitude .") * pi()/180) * POWER(SIN(((tblRestaurant.location__longitude) - (". $longitude .")) * pi()/180 / 2), 2) ))) as distance";
		
		if($radius_distance != '')
			$having = "HAVING distance < " . $radius_distance;
			
		$orderby = 'distance';
		if($sort_by == 'name')
			$orderby = ($type == "menus") ? 'menu_name' : 'restaurant_name';
		else if($sort_by == 'price')
			$orderby = 'cast(menu_price as unsigned)';
		else if($sort_by == 'rating' && $type != 'restaurant')
			$orderby = 'cast(menu_rating as unsigned)';
			
		if($sort_type == "descending")
			$order_by .= ' DESC';
			
		$orderby = "ORDER BY " . $orderby;
			 		
		$sql = "SELECT ". $select ."	 
			FROM object_6 as tblRestaurant
			left join object_7 as tblMenu on tblMenu.restaurants__id = tblRestaurant.oo_id
			left join object_14 as tblCurrency on tblCurrency.oo_id = tblMenu.currency__id
			left join object_collection_ingredients_7 as tblIngredients on tblMenu.oo_id = tblIngredients.o_id
			". $join_asset ."
			" .(($where != '') ? ("WHERE " . $where) : ""). "
			". $groupby ."
			". $having ."
			". $orderby ."
		"; 

		$sql1 = "SELECT ". $select ."	 
			FROM object_6 as tblRestaurant
			left join object_7 as tblMenu on tblMenu.restaurants__id = tblRestaurant.oo_id
			left join object_14 as tblCurrency on tblCurrency.oo_id = tblMenu.currency__id
			left join object_collection_ingredients_7 as tblIngredients on tblMenu.oo_id = tblIngredients.o_id
			". $join_asset ."
			" .(($where1 != '') ? ("WHERE " . $where1) : ""). "
			". $groupby ."
			". $having ."
			". $orderby ."
		"; 

		//print_r($db->fetchAll($sql));
		//print_r($sql1);
		//die();

		//ALL
		$db = Pimcore_Resource_Mysql::get();
		
		if(count($keyword) > 1)
		{
			$array2 = $db->fetchAll($sql);
			$array1 = $db->fetchAll($sql1);		
			$array = array_merge($array1, $array2);			
		}
		else
		{
			$array = $db->fetchAll($sql);			
		}
		
		$results = array();
		foreach($array as $temp)
		{
			if($type == 'menus')
			{
				$result['restaurants']['id'] = $temp['restaurant_id'];
				$result['restaurants']['name'] = $temp['restaurant_name'];
				
				$result['id'] = $temp['menu_id'];
				$result['name'] = $temp['menu_name'];
				$result['currency'] = $temp['menu_currency'];
				$result['price'] = sprintf('%0.2f', $temp['menu_price']);
				$result['rating'] = $temp['menu_rating'];
				$result['description'] = ($temp['menu_description'] != null) ? $temp['menu_description'] : "No description";
				$result['halal'] = ($temp['menu_halal'] != null) ? $temp['menu_halal'] : false;
				$result['thumb_image'] = ($temp['image_filename'] != '') ? ($_SERVER['REQUEST_SCHEME'] . '://' .  $_SERVER['HTTP_HOST'] . $temp['image_path'] . $temp['image_filename']) : '';				
			}
			else
			{
				$result['id'] = $temp['restaurant_id'];
				$result['name'] = $temp['restaurant_name'];
				$result['address'] = $temp['restaurant_address'];
				$result['rating'] = 0.0;
				$result['distance_string'] = round($temp['distance'], 1) . (($unit_distance == 'mi') ? " miles " : " km ") . "away";
				$result['profile_image'] = ($temp['image_filename'] != '') ? ($_SERVER['REQUEST_SCHEME'] . '://' .  $_SERVER['HTTP_HOST'] . $temp['image_path'] . $temp['image_filename']) : '';								
			}
			
			if(!in_array($result, $results))
				array_push($results, $result);
		}
		
		//print_r($results);
		//die();		
		
		$json_cat = $this->_helper->json($results);
		$this->sendResponse($json_cat);
	}
	
	public function restaurantsMapsAction()
	{		
		$latitude = $this->_getParam('latitude');
		$longitude = $this->_getParam('longitude');
		$unit_distance = $this->_getParam('unit_distance');
		$radius_distance = $this->_getParam('radius_distance');
		$limit = $this->_getParam('limit');

		$array = array();
		$i = 0;
				
		$where .= '(tblRestaurant.o_published = 1)';
		$select = "tblRestaurant.oo_id as restaurant_id,
				tblRestaurant.name as restaurant_name,
				tblRestaurant.address as restaurant_address,
				tblAssets.path as image_path,
				tblAssets.filename as image_filename,
				tblRestaurant.location__latitude as latitude, 
				tblRestaurant.location__longitude as longitude
			";
			
		$groupby = "GROUP BY tblRestaurant.name";
		$join_asset = 'left join assets as tblAssets on tblRestaurant.profileImage = tblAssets.id';
		
		$cons = 6371;
		if($unit_distance == "mi"){ $cons = 3956; }
		$select .= ", (". $cons ." * 2 * ASIN(SQRT( POWER(SIN(((tblRestaurant.location__latitude) - (". $latitude .")) *  pi()/180 / 2), 2) +COS((tblRestaurant.location__latitude) * pi()/180) * COS((". $latitude .") * pi()/180) * POWER(SIN(((tblRestaurant.location__longitude) - (". $longitude .")) * pi()/180 / 2), 2) ))) as distance";
		
		if($radius_distance != '')
			$having = "HAVING distance < " . $radius_distance;
						
		if($limit != '')
			$limit = "LIMIT " . $limit;

		$orderby = "ORDER BY distance";
			 		
		$sql = "SELECT ". $select ."	 
			FROM object_6 as tblRestaurant
			left join object_7 as tblMenu on tblMenu.restaurants__id = tblRestaurant.oo_id
			left join object_14 as tblCurrency on tblCurrency.oo_id = tblMenu.currency__id
			left join object_collection_ingredients_7 as tblIngredients on tblMenu.oo_id = tblIngredients.o_id
			". $join_asset ."
			" .(($where != '') ? ("WHERE " . $where) : ""). "
			". $groupby ."
			". $having ."
			". $orderby ."
			". $limit ."
		"; 

		//print_r($db->fetchAll($sql));
		//print_r($sql);
		//die();

		//ALL
		$db = Pimcore_Resource_Mysql::get();
		
		$array = $db->fetchAll($sql);			
		
		$results = array();
		foreach($array as $temp)
		{
			$result['id'] = $temp['restaurant_id'];
			$result['name'] = $temp['restaurant_name'];
			$result['address'] = $temp['restaurant_address'];
			$result['rating'] = 0.0;
			$result['distance_value'] = round($temp['distance'], 1);
			$result['distance_string'] = round($temp['distance'], 1) . (($unit_distance == 'mi') ? " miles " : " km ") . "away";
			$result['profile_image'] = ($temp['image_filename'] != '') ? ($_SERVER['REQUEST_SCHEME'] . '://' .  $_SERVER['HTTP_HOST'] . $temp['image_path'] . $temp['image_filename']) : '';								
			$result['location']['latitude'] = $temp['latitude'];
			$result['location']['longitude'] = $temp['longitude'];
			
			if(!in_array($result, $results))
				array_push($results, $result);
		}	
		
		//print_r($results);
		//die();		
		
		$json_cat = $this->_helper->json($results);
		$this->sendResponse($json_cat);
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