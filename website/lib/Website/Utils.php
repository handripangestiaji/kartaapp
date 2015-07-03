<?php

class Website_Utils {
    
    function sortArrayBy($field, &$array, $direction = 'asc')
    {
	usort($array, create_function('$a, $b', '
	    $a = $a["' . $field . '"];1
	    $b = $b["' . $field . '"];
    
	    if ($a == $b)
	    {
		return 0;
	    }
    
	    return ($a ' . ($direction == 'desc' ? '>' : '<') .' $b) ? -1 : 1;
	'));
    
	return true;
    }    	
}