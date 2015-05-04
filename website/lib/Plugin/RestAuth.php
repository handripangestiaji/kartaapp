<?php

namespace Plugin;

class Plugin_RestAuth extends Zend_Controller_Plugin_Abstract
{
	public function preDispatch(Zend_Controller_Request_Abstract $request)
	{
		$apikey = $request->getHeader('apikey');
		
		if($apikey != 'secret')
		{
			$this->getResponse()
                    ->setHttpResponseCode(403)
                    ->appendBody("Invalid API Key\n")
                    ;
            $request->setModuleName('default')
                        ->setControllerName('error')
                        ->setActionName('access')
                        ->setDispatched(true);
		}
	}
}