package com.objectpartners.plummer.distributed_grails

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString
@EqualsAndHashCode
class City implements CacheableEntity {
    Long id
    String name
    String state
    Long population
}