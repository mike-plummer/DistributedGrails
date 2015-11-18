package com.objectpartners.plummer.distributed_grails

interface CacheableEntity extends Serializable {
    Long getId()
    void setId(Long id)
}