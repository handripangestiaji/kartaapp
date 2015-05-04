<?php

use Website\Controller\Action;

class DefaultController extends Action {
	
	public function defaultAction () {
		
	}
	
	 public function routerAction() {
        $action = $this->_getParam("actionname");
        $controller = $this->_getParam("controllername");
        $this->_forward($action, $controller, $this->_getParam("module"), $this->_getAllParams());
    }
}
