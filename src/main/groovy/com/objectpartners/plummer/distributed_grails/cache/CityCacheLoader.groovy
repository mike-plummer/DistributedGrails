package com.objectpartners.plummer.distributed_grails.cache

import com.objectpartners.plummer.distributed_grails.City

import javax.cache.integration.CacheLoader
import javax.cache.integration.CacheLoaderException

class CityCacheLoader implements CacheLoader<Long, City>, Serializable {

    @Override
    City load(Long key) throws CacheLoaderException {
        return City.get(key)
    }

    @Override
    Map loadAll(Iterable keys) throws CacheLoaderException {
        return City.getAll(keys)
    }
}
