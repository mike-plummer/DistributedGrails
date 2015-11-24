package com.objectpartners.plummer.distributed_grails

import grails.converters.JSON

import javax.cache.CacheManager

class CacheController {

    def cacheManager

    def hazelcastService

    def index () {
        def caches = [];
        cacheManager.getCacheNames().each {cacheName ->
            caches << [name: cacheName, size: cacheManager.getCache(cacheName).size(), source: 'JCache']
        }
        hazelcastService.getDistributedObjects().each { distributedObject ->
            caches << [name: distributedObject.getName(), type: distributedObject.getClass().getSimpleName(), source: 'Hazelcast']
        }
        def info = [caches: caches,
                    jcacheProps: cacheManager.getProperties().getProperties()]
        render info as JSON
    }
}
