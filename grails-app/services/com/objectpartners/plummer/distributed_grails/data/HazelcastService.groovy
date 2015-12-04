package com.objectpartners.plummer.distributed_grails.data

import com.hazelcast.config.Config
import com.hazelcast.core.Hazelcast
import com.hazelcast.core.HazelcastInstance

class HazelcastService implements HazelcastInstance {

    @Delegate
    private static HazelcastInstance hazelcastInstance;

    def static List<AbstractDataService> dataServices;

    static {
        Config config = new Config();

        dataServices.each {
            config.addMapConfig(it.getMapConfig())
        }

        hazelcastInstance = Hazelcast.newHazelcastInstance(config);
    }
}
