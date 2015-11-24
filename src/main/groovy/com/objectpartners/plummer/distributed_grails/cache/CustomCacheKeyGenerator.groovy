package com.objectpartners.plummer.distributed_grails.cache

import javax.cache.annotation.CacheKeyGenerator
import javax.cache.annotation.CacheKeyInvocationContext
import javax.cache.annotation.GeneratedCacheKey
import java.lang.annotation.Annotation

class CustomCacheKeyGenerator implements CacheKeyGenerator, Serializable {
    @Override
    GeneratedCacheKey generateCacheKey(CacheKeyInvocationContext<? extends Annotation> context) {
        return new DelegatingCacheKey(context.getValueParameter().getValue())
    }

    // This class isn't strictly necessary - we could just return the Long value from the generateCacheKey
    // method since all method resolution in Groovy is dynamic. Doing it this way helps keep the IDE happy
    private class DelegatingCacheKey implements GeneratedCacheKey {

        def delegates;

        DelegatingCacheKey(def delegates) {
            println(delegates)
            this.delegates = delegates;
        }

        @Override
        public int hashCode() {
            return delegates.hashCode()
        }

        @Override
        public boolean equals(Object obj) {
            return delegates.equals(obj)
        }
    }
}
