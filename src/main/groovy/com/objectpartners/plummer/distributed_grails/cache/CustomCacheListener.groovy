package com.objectpartners.plummer.distributed_grails.cache

import javax.cache.event.CacheEntryCreatedListener
import javax.cache.event.CacheEntryExpiredListener
import javax.cache.event.CacheEntryListenerException
import javax.cache.event.CacheEntryRemovedListener
import javax.cache.event.CacheEntryUpdatedListener

class CustomCacheListener implements CacheEntryExpiredListener, CacheEntryCreatedListener, CacheEntryRemovedListener, CacheEntryUpdatedListener {

    @Override
    void onCreated(Iterable iterable) throws CacheEntryListenerException {
        System.out.println(iterable.size() + " items created")
    }

    @Override
    void onExpired(Iterable iterable) throws CacheEntryListenerException {
        System.out.println(iterable.size() + " items expired")
    }

    @Override
    void onRemoved(Iterable iterable) throws CacheEntryListenerException {
        System.out.println(iterable.size() + " items removed")
    }

    @Override
    void onUpdated(Iterable iterable) throws CacheEntryListenerException {
        System.out.println(iterable.size() + " items updated")
    }
}
