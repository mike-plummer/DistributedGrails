package com.objectpartners.plummer.distributed_grails.data

import com.objectpartners.plummer.distributed_grails.City

class CityDataService extends AbstractDataService<City> {

    private static final String CITY_MAP_NAME = "com.objectpartners.plummer.distributed_grails.data.City"

    protected String getMapName() {
        return CITY_MAP_NAME;
    }
}
