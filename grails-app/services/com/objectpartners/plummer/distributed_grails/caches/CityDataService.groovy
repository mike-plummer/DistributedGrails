package com.objectpartners.plummer.distributed_grails.caches

import com.objectpartners.plummer.distributed_grails.City

class CityDataService extends AbstractDataService<City> {

    private static final String CITY_CACHE = "com.objectpartners.plummer.distributed_grails.City"

    protected String getCacheName() {
        return CITY_CACHE;
    }
}
