<?php

class Website_CalculateDistance {
    
    public function calculation($point1_lat, $point1_long, $point2_lat, $point2_long, $unit = 'km', $decimals = 1) {
        $degrees = rad2deg(acos((sin(deg2rad($point1_lat))*sin(deg2rad($point2_lat))) + (cos(deg2rad($point1_lat))*cos(deg2rad($point2_lat))*cos(deg2rad($point1_long-$point2_long)))));

        if($unit == 'mi')
            $distance = $degrees * 69.05482; // 1 degree = 69.05482 miles, based on the average diameter of the Earth (7,913.1 miles)
        else if($unit == 'nmi')
            $distance =  $degrees * 59.97662; // 1 degree = 59.97662 nautic miles, based on the average diameter of the Earth (6,876.3 nautical miles)
        else 
            $distance = $degrees * 111.13384; // 1 degree = 111.13384 km, based on the average diameter of the Earth (12,735 km)
 
        return round($distance, $decimals);
    }
    
    public function formating($distance, $unit = 'km') { 
        if($unit == 'mi')
            $distance_string = $distance . " Miles away";
        else if($unit == 'nmi')
            $distance_string = $distance . " nmi away";
        else 
            $distance_string = $distance . " Km away";

        return $distance_string;
    }

	
}