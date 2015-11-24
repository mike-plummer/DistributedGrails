package com.objectpartners.plummer.distributed_grails.cache

import com.hazelcast.cache.impl.HazelcastServerCachingProvider

import javax.cache.Cache
import javax.cache.Caching
import javax.cache.annotation.CacheInvocationContext
import javax.cache.annotation.CacheMethodDetails
import javax.cache.annotation.CacheResolver
import javax.cache.annotation.CacheResolverFactory
import javax.cache.annotation.CacheResult
import java.lang.annotation.Annotation

class CustomCacheResolverFactory implements CacheResolverFactory {

    def resolver = new CustomCacheResolver()

    @Override
    CacheResolver getCacheResolver(CacheMethodDetails<? extends Annotation> cacheMethodDetails) {
        return resolver
    }

    @Override
    CacheResolver getExceptionCacheResolver(CacheMethodDetails<CacheResult> cacheMethodDetails) {
        return resolver
    }

    class CustomCacheResolver implements CacheResolver {
        def cacheManager = Caching.getCachingProvider(HazelcastServerCachingProvider.class.getName()).getCacheManager()

        @Override
        def <K, V> Cache<K, V> resolveCache(CacheInvocationContext<? extends Annotation> ctx) {
            println("Retrieving cache ${ctx.getCacheName()}")
            return cacheManager.getCache(ctx.getCacheName())
        }
    }
}
