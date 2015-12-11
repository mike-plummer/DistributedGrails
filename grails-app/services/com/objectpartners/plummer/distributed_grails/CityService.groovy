package com.objectpartners.plummer.distributed_grails

import com.objectpartners.plummer.distributed_grails.cache.CityCache
import com.objectpartners.plummer.distributed_grails.cache.CustomCacheKeyGenerator
import com.objectpartners.plummer.distributed_grails.cache.CustomCacheResolverFactory

import javax.cache.annotation.*

/**
 * JCache-enabled service for City data - all operations dealing with City objects that pass through this layer
 * will get/add/remove items from JCache.
 */
//These defaults apply to all JCache annotations below unless overridden. Not necessary, just reduces repetition.
@CacheDefaults(cacheName = CityCache.CITY_CACHE_NAME, cacheResolverFactory = CustomCacheResolverFactory.class)
class CityService {

    /**
     * Interface to the data tier
     */
    def cityDataService

    /**
     * Get all IDs
     * @return
     */
    def keys () {
        return cityDataService.keys()
    }

    /**
     * Store the provided City - method will be invoked and the return value will be stored into JCache
     * @param city
     * @return
     */
    @CachePut(cacheKeyGenerator = CustomCacheKeyGenerator.class)
    City create (@CacheValue City city) {
        cityDataService.addOrUpdate(city)
    }

    /**
     * Modify the provided City - method will be invoked and return value will be stored into JCache
     * @param city
     * @return
     */
    @CachePut(cacheKeyGenerator = CustomCacheKeyGenerator.class)
    City update (@CacheValue City city) {
        cityDataService.addOrUpdate(city)
    }

    /**
     * Attempt to retrieve specified key from JCache. If missing then invoke method and store return value into JCache.
     * Method will only be invoked if item is not in the cache.
     * @param id
     * @return
     */
    @CacheResult
    City get (@CacheKey long id) {
        println("Uncached get()")
        cityDataService.get(id)
    }

    /**
     * Delete the City with the specified ID - method will be invoked and the specified ID will be removed from the cache.
     * @param id
     */
    @CacheRemove
    void delete (@CacheKey long id) {
        cityDataService.remove(id)
    }

    /**
     * Delete all Cities - method will be invoked and all elements removed from the cache.
     */
    @CacheRemoveAll
    void deleteAll () {
        cityDataService.removeAll()
    }

    /**
     * Total number of Cities in the data tier
     * @return
     */
    def count () {
        cityDataService.count()
    }

    def simpleSumPopulation() {
        cityDataService.simpleSumPopulation()
    }

    def distributedSumPopulation() {
        cityDataService.distributedSumPopulation()
    }
}
