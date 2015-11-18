package com.objectpartners.plummer.distributed_grails

import com.objectpartners.plummer.distributed_grails.cache.CityCacheKeyGenerator
import com.objectpartners.plummer.distributed_grails.cache.CustomCacheResolverFactory
import grails.transaction.Transactional

import javax.cache.annotation.*

@Transactional
@CacheDefaults(cacheName = "com.objectpartners.plummer.distributed_grails.City",
cacheResolverFactory = CustomCacheResolverFactory.class)
class CityService {

    def cityDataService

    def keys () {
        return cityDataService.keys()
    }

    @CachePut(cacheKeyGenerator = CityCacheKeyGenerator.class)
    City create (@CacheValue City city) {
        cityDataService.add(city)
    }

    @CachePut(cacheKeyGenerator = CityCacheKeyGenerator.class)
    City update (@CacheValue City city) {
        cityDataService.update(city)
    }

    @CacheResult
    City get (@CacheKey Long id) {
        System.out.println("Uncached get()")
        cityDataService.get(id)
    }

    @CacheRemove
    void delete (@CacheKey Long id) {
        cityDataService.remove(flush:true)
    }

    @CacheRemoveAll
    void deleteAll () {
        cityDataService.removeAll()
    }

    def count () {
        cityDataService.count()
    }
}
