package com.objectpartners.plummer.distributed_grails

import grails.converters.JSON

import javax.cache.Cache

/**
 * Supplies info regarding JCache and Hazelcast
 */
class CacheController {

    /**
     * JCache cache manager (Hazelcast JCache provider)
     */
    def cacheManager

    /**
     * Hazelcast interface to our data tier
     */
    def hazelcastService

    def index () {
        def caches = [];
        // Pull each cache that JCache knows about
        cacheManager.getCacheNames().each {cacheName ->
            Cache cache = cacheManager.getCache(cacheName);
            caches << [name: cacheName, type: cache.getClass().getSimpleName(), size: cache.size(), source: 'JCache']
        }
        // Pull each data structure managed by Hazelcast (not including the JCache elements)
        hazelcastService.getDistributedObjects().each { distributedObject ->
            caches << [name: distributedObject.getName(), type: distributedObject.getClass().getSimpleName(),
                       size: (distributedObject instanceof Map) ? ((Map)distributedObject).size() : 'N/A', source: ' Hazelcast ']
        }
        // Return as JSON
        def info = [caches: caches,
                    jcacheProps: cacheManager.getProperties().getProperties()]
        render info as JSON
    }
}
