package com.objectpartners.plummer.distributed_grails


class ClusterMetadataInterceptor {

    ClusterMetadataInterceptor() {
        matchAll()
    }

    boolean before() {
        // Add a header to help identify the cluster node that handled this request
        header("X-ClusterNode-Hostname", InetAddress.getLocalHost().getHostName())
        true }

    boolean after() { true }

    void afterView() {}
}
