<?php

use Website\Controller\Action;
use Pimcore\Model\Document;

class DefaultController extends Action {
	
	public function defaultAction () {
				
		$this->enableLayout();
				
	}
	
	public function joinBetaAction () {
		
		$email = $this->getParam("email");
		
		$arr = explode("@", $email, 2);
		
		$name = str_replace('.', '_', $arr[0]);
		
		$key = $name."_".strtotime(date('YmdHis'));
		
		$objs = new Object_List();
		$objs->setCondition("o_key='email-leads'");
		
		$o_pid = '121';
		
		foreach($objs as $parent)
		{
			$o_pid = $parent->getO_id();
		}
		
		$checkEmail = Object\Leads::getByEmail($email);
		
		$countEmail = $checkEmail->count();
		
		if($countEmail >= 1) {
			die('registered');
		}
		else {
			$leads = new Object_Leads();
			$leads->setemail($email);
			$leads->setKey($key);
			$leads->setO_parentId($o_pid);
			$leads->setIndex(0);
			$leads->setPublished(1);
			$leads->save();
			
			$list = new Document\Listing();
			
			$list->setCondition('type = "email" AND id = 3');
			
			$documents = $list->load();
			
			$mail = new Pimcore_Mail();
			$mail->setSubject($documents[0]->subject);
			$mail->setFrom($documents[0]->from);
			$mail->setDocument("/email/beta-confirmation");
			$mail->addTo($email);
			$mail->addCc($documents[0]->cc);
			$mail->send();
			
			die('success');
		}
		
	}
	
	public function betaEmailConfirmationAction() {
		
	}
	
	public function thankyouAction() {
		
		$this->enableLayout();
		
	}
	
	 public function routerAction() {
        $action = $this->_getParam("actionname");
        $controller = $this->_getParam("controllername");
        $this->_forward($action, $controller, $this->_getParam("module"), $this->_getAllParams());
    }
}
