package com.objectpartners.plummer.distributed_grails

import com.objectpartners.plummer.distributed_grails.cache.CityCache
import com.objectpartners.plummer.distributed_grails.cache.CustomCacheKeyGenerator
import com.objectpartners.plummer.distributed_grails.cache.CustomCacheResolverFactory

import javax.cache.annotation.*

@CacheDefaults(cacheName = CityCache.CITY_CACHE_NAME, cacheResolverFactory = CustomCacheResolverFactory.class)
class CityService {

    def cityDataService

    def keys () {
        return cityDataService.keys()
    }

    @CachePut(cacheKeyGenerator = CustomCacheKeyGenerator.class)
    City create (@CacheValue City city) {
        cityDataService.addOrUpdate(city)
    }

    @CachePut(cacheKeyGenerator = CustomCacheKeyGenerator.class)
    City update (@CacheValue City city) {
        cityDataService.addOrUpdate(city)
    }

    @CacheResult
    City get (@CacheKey long id) {
        println("Uncached get()")
        cityDataService.get(id)
    }

    @CacheRemove
    void delete (@CacheKey long id) {
        cityDataService.remove(id)
    }

    @CacheRemoveAll
    void deleteAll () {
        cityDataService.removeAll()
    }

    def count () {
        cityDataService.count()
    }
}
