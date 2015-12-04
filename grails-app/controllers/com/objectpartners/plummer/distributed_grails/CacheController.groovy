package com.objectpartners.plummer.distributed_grails

import grails.converters.JSON

import javax.cache.Cache

class CacheController {

    def cacheManager

    def hazelcastService

    def index () {
        def caches = [];
        cacheManager.getCacheNames().each {cacheName ->
            Cache cache = cacheManager.getCache(cacheName);
            caches << [name: cacheName, type: cache.getClass().getSimpleName(), size: cache.size(), source: 'JCache']
        }
        hazelcastService.getDistributedObjects().each { distributedObject ->
            caches << [name: distributedObject.getName(), type: distributedObject.getClass().getSimpleName(),
                       size: (distributedObject instanceof Map) ? ((Map)distributedObject).size() : 'N/A', source: ' Hazelcast ']
        }
        def info = [caches: caches,
                    jcacheProps: cacheManager.getProperties().getProperties()]
        render info as JSON
    }
}
