package com.objectpartners.plummer.distributed_grails.cache

import com.objectpartners.plummer.distributed_grails.City

import javax.cache.CacheManager
import javax.cache.configuration.CompleteConfiguration
import javax.cache.configuration.FactoryBuilder
import javax.cache.configuration.MutableCacheEntryListenerConfiguration
import javax.cache.configuration.MutableConfiguration
import javax.cache.event.CacheEntryListener
import javax.cache.expiry.CreatedExpiryPolicy
import javax.cache.expiry.Duration
import javax.cache.integration.CacheLoader

class CityCache implements Serializable {

    public static final String CITY_CACHE_NAME = "com.objectpartners.plummer.distributed_grails.cache.City"

    CityCache(CacheManager cacheManager) {
        println("Creating City cache...")

        // Create a simple but typesafe configuration for the cache
        CompleteConfiguration<Long, City> config = new MutableConfiguration<>()
        .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.ONE_MINUTE))
        .addCacheEntryListenerConfiguration(
                new MutableCacheEntryListenerConfiguration(
                    new FactoryBuilder.SingletonFactory<CacheEntryListener>(new CityCacheListener()),
                    null, true, false)
        )

        cacheManager.createCache(CITY_CACHE_NAME, config)
        println("City cache created.")
    }
}
