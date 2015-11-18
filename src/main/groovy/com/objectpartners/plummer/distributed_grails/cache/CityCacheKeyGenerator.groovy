package com.objectpartners.plummer.distributed_grails.cache

import com.objectpartners.plummer.distributed_grails.City

import javax.cache.annotation.CacheKeyGenerator
import javax.cache.annotation.CacheKeyInvocationContext
import javax.cache.annotation.GeneratedCacheKey
import java.lang.annotation.Annotation

class CityCacheKeyGenerator implements CacheKeyGenerator {
    @Override
    GeneratedCacheKey generateCacheKey(CacheKeyInvocationContext<? extends Annotation> context) {
        City entity = (City) context.getValueParameter().getValue()
        return entity.getId()
    }
}
