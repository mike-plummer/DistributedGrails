package com.objectpartners.plummer.distributed_grails.cache

import com.objectpartners.plummer.distributed_grails.City

import javax.cache.event.CacheEntryCreatedListener
import javax.cache.event.CacheEntryEvent
import javax.cache.event.CacheEntryExpiredListener
import javax.cache.event.CacheEntryListenerException
import javax.cache.event.CacheEntryRemovedListener
import javax.cache.event.CacheEntryUpdatedListener

class CityCacheListener implements CacheEntryExpiredListener<Long, City>,
                                   CacheEntryCreatedListener<Long, City>,
                                   CacheEntryRemovedListener<Long, City>,
                                   CacheEntryUpdatedListener<Long, City>,
                                   Serializable {

    @Override
    void onCreated(Iterable<CacheEntryEvent<? extends Long, ? extends City>> iterable) throws CacheEntryListenerException {
        System.out.println("${iterable.size()} items created")
    }

    @Override
    void onExpired(Iterable<CacheEntryEvent<? extends Long, ? extends City>> iterable) throws CacheEntryListenerException {
        System.out.println("${iterable.size()} items expired")
    }

    @Override
    void onRemoved(Iterable<CacheEntryEvent<? extends Long, ? extends City>> iterable) throws CacheEntryListenerException {
        System.out.println("${iterable.size()} items removed")
    }

    @Override
    void onUpdated(Iterable<CacheEntryEvent<? extends Long, ? extends City>> iterable) throws CacheEntryListenerException {
        System.out.println("${iterable.size()} items updated")
    }
}
