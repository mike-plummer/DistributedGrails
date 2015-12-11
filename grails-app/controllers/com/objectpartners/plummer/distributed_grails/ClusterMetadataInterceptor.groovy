package com.objectpartners.plummer.distributed_grails

/**
 * Fires on all service requests to add a response header indicating which cluster node
 * handled the service request. This allows the UI to display the 'round robin' behavior
 * provided we're deployed in a cluster.
 */
class ClusterMetadataInterceptor {

    ClusterMetadataInterceptor() {
        matchAll()
    }

    boolean before() {
        header("X-ClusterNode-Hostname", InetAddress.getLocalHost().getHostName())
        true }

    boolean after() { true }

    void afterView() {}
}
