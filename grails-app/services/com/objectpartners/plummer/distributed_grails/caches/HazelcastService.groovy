package com.objectpartners.plummer.distributed_grails.caches

import com.hazelcast.config.Config
import com.hazelcast.core.Hazelcast
import com.hazelcast.core.HazelcastInstance

class HazelcastService implements HazelcastInstance {

    @Delegate
    private static HazelcastInstance hazelcastInstance;

    static {
        Config config = new Config();
        hazelcastInstance = Hazelcast.newHazelcastInstance(config);
    }
}
