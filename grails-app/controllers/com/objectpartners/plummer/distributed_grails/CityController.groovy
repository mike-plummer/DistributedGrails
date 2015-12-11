package com.objectpartners.plummer.distributed_grails

import grails.converters.JSON

/**
 * Basic CRUD service for city data.
 */
class CityController {

    def cityService

    // GET /city
    def keys () {
        render cityService.keys() as JSON
    }

    // GET /city/$id
    def get (Long id) {
        def city = cityService.get(id)
        if (city != null) {
            render city as JSON
        } else {
            render status: 404, text: "Failed to find City ${id}"
        }
    }

    // POST /city
    def create (City city) {
        if (city.id != null) {
            render status: 400, text: "City must have a null ID for CREATE"
        } else {
            render cityService.create(city) as JSON
        }
    }

    // PUT /city
    def update (City city) {
        if (city.id == null) {
            render status: 400, text: "City must have a non-null ID for UPDATE"
        } else {
            render cityService.update(city) as JSON
        }
    }

    // DELETE /city/$id
    def delete(Long id) {
        cityService.delete(id)
    }

    // GET /city/population
    /**
     * Invokes two different jobs to acquire the  total population of all Cities.
     * @return
     */
    def population() {
        // Pull data locally and iterate
        long start = System.currentTimeMillis()
        long simplePopulationSum = cityService.simpleSumPopulation()
        long simpleDuration = System.currentTimeMillis() - start

        // Fire off MapReduce job to work across the cluster
        start = System.currentTimeMillis()
        long distributedPopulationSum = cityService.distributedSumPopulation()
        long distributedDuration = System.currentTimeMillis() - start

        // Return as JSON
        def info = []
        info << [ method: 'simple',
                  sum: simplePopulationSum,
                  duration: simpleDuration ]
        info << [ method: 'distributed',
                  sum: distributedPopulationSum,
                  duration: distributedDuration ]
        render info as JSON
    }
}
