package com.objectpartners.plummer.distributed_grails.data

import com.hazelcast.config.Config
import com.hazelcast.core.Hazelcast
import com.hazelcast.core.HazelcastInstance

/**
 * Interface to Hazelcast
 */
class HazelcastService implements HazelcastInstance {

    /**
     * Make this class behave like a HazelcastInstance
     */
    @Delegate
    private static HazelcastInstance hazelcastInstance;

    /**
     * Grab all AbstractDataServices - at application startup time we want to grab the distribute data structure
     * configurations that each service wants and pre-register them with Hazelcast. Once this is done then
     * we fire up our Hazelcast instance; this will invoke the Multicast search for any other cluster nodes.
     */
    def static List<AbstractDataService> dataServices;

    static {
        Config config = new Config();

        dataServices.each {
            config.addMapConfig(it.getMapConfig())
        }

        hazelcastInstance = Hazelcast.newHazelcastInstance(config);
    }
}
