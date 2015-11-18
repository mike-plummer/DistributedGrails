package com.objectpartners.plummer.distributed_grails

import grails.converters.JSON

import javax.cache.CacheManager

class CacheController {

    CacheManager cacheManager

    def index () {
        def caches = [];
        cacheManager.getCacheNames().each {cacheName ->
            caches << [name: cacheName, size: cacheManager.getCache(cacheName).size()]
        }
        def info = [caches: caches,
                    props: cacheManager.getProperties().getProperties()]
        render info as JSON
    }
}
