package com.objectpartners.plummer.distributed_grails.cache

import com.objectpartners.plummer.distributed_grails.City
import com.objectpartners.plummer.distributed_grails.cache.CityCacheLoader
import com.objectpartners.plummer.distributed_grails.cache.CustomCacheListener

import javax.cache.CacheManager
import javax.cache.configuration.CacheEntryListenerConfiguration
import javax.cache.configuration.CompleteConfiguration
import javax.cache.configuration.Factory
import javax.cache.configuration.FactoryBuilder
import javax.cache.configuration.MutableCacheEntryListenerConfiguration
import javax.cache.configuration.MutableConfiguration
import javax.cache.event.CacheEntryEventFilter
import javax.cache.event.CacheEntryListener
import javax.cache.expiry.CreatedExpiryPolicy
import javax.cache.expiry.Duration
import javax.cache.integration.CacheLoader
import javax.cache.integration.CompletionListener

class CityCache implements Serializable {

    CityCache(CacheManager cacheManager) {
        System.out.println("Creating City cache...")

        // Create a simple but typesafe configuration for the cache
        CompleteConfiguration<Long, City> config = new MutableConfiguration<>()
        // We can set the key & value types here which adds an extra layer of type-checking. However,
        // attempts to retrieve this cache must also know this info which, depending on how
        // much you've abstracted you data layers, might not be nicely available in your
        // CacheResolver
//      .setTypes(Long.class, City.class);
        .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.FIVE_MINUTES))
        .setStatisticsEnabled(true)
        .setCacheLoaderFactory(new FactoryBuilder.SingletonFactory<CacheLoader<Long, City>>(new CityCacheLoader()))
//        .addCacheEntryListenerConfiguration(
//                new MutableCacheEntryListenerConfiguration(
//                    new FactoryBuilder.SingletonFactory<CacheEntryListener>(new CustomCacheListener()),
//                    null, true, false)
//        )

        cacheManager.createCache("com.objectpartners.plummer.distributed_grails.City", config)
        System.out.println("City cache created.")
    }
}
