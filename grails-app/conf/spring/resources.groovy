import com.hazelcast.cache.impl.HazelcastServerCacheManager
import com.hazelcast.cache.impl.HazelcastServerCachingProvider
import com.objectpartners.plummer.distributed_grails.cache.CityCache

import javax.cache.Caching

beans = {
    cacheManager (HazelcastServerCacheManager) { beanDefinition ->

        println("Configuring CacheManager...")

        // This is where we are configuring JCache to use Hazelcast. Normally this occurs automatically behind the scenes
        // but doing it this way gives us a handle on the Hazelcast config and lets us customize it prior to startup.
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
