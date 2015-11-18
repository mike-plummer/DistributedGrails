package com.objectpartners.plummer.distributed_grails.caches

import com.hazelcast.core.IMap
import com.hazelcast.core.IdGenerator
import com.objectpartners.plummer.distributed_grails.CacheableEntity

abstract class AbstractDataService<E extends CacheableEntity> {

    def hazelcastService

    def count () {
        return getCacheMap().size()
    }

    def keys () {
        return new HashSet<>(getCacheMap().keySet())
    }

    def add (E entity) {
        if (entity.id == null) {
            entity.id = getIdGenerator().newId()
        }
        getCacheMap().lock(entity.id)
        try {
            if (getCacheMap().containsKey(entity.id)) {
                throw new IllegalArgumentException("Duplicate ID being added to cache")
            }
            getCacheMap().put(entity.id, entity)
        } finally {
            getCacheMap().unlock(entity.id)
        }
    }

    protected abstract String getCacheName();

    protected IMap<Long, E> getCacheMap() {
        return hazelcastService.getMap(getCacheName())
    }

    protected IdGenerator getIdGenerator() {
        return hazelcastService.getIdGenerator(getCacheName())
    }
}
