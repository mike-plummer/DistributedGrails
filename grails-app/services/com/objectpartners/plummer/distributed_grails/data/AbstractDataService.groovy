package com.objectpartners.plummer.distributed_grails.data

import com.hazelcast.config.MapConfig
import com.hazelcast.core.IMap
import com.hazelcast.core.IdGenerator
import com.objectpartners.plummer.distributed_grails.CacheableEntity

abstract class AbstractDataService<E extends CacheableEntity> {

    def hazelcastService

    int count () {
        return getData().size()
    }

    Set<Long> keys () {
        return new HashSet<>(getData().keySet())
    }

    E addOrUpdate (E entity) {
        entity.id = getIdGenerator().newId()
        getData().put(entity.id, entity)
    }

    E get(long id) {
        return getData().get(id)
    }

    boolean remove(long id) {
        return getData().remove(id) != null
    }

    void removeAll() {
        getData().clear()
    }

    protected abstract String getMapName();

    protected IMap<Long, E> getData() {
        return hazelcastService.getMap(getMapName())
    }

    protected IdGenerator getIdGenerator() {
        return hazelcastService.getIdGenerator(getMapName())
    }

    protected MapConfig getMapConfig() {
        MapConfig config = new MapConfig();
        config.setName(getMapName());
        // Here is where you could customize behaviors for the Hazelcast maps used
        // as the 'persistent' data store
        return config;
    }
}
