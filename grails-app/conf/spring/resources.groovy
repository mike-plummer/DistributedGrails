import com.hazelcast.cache.impl.HazelcastServerCacheManager
import com.hazelcast.cache.impl.HazelcastServerCachingProvider
import com.objectpartners.plummer.distributed_grails.cache.CityCache

import javax.cache.Caching

// Place your Spring DSL code here
beans = {
    cacheManager (HazelcastServerCacheManager) { beanDefinition ->

        println("Configuring CacheManager...")

        // Pass in the Hazelcast provider and Hazelcast instance
        // Use 'null' for custom URI, classloader, and properties since we just want to use the defaults
        beanDefinition.constructorArgs = [Caching.getCachingProvider(HazelcastServerCachingProvider.class.getName()),
                                          ref('hazelcastService'), null, null, null]

        println("CacheManager configured.")
    }

    cityCache (CityCache) { beanDefinition ->
        beanDefinition.constructorArgs = [ref('cacheManager')]
        println("Initializing CityCache")
    }
}
