package com.objectpartners.plummer.distributed_grails

import grails.converters.JSON

class CityController {

    /*
        HTTP  | URI	          | Action
        ----------------------------------
        GET	    /city/create	create
        POST	/city	        save
        GET	    /city	        show
        GET	    /city/edit	    edit
        PUT	    /city	        update
        DELETE	/book	        delete
     */

    def cityService

    def keys () {
        render cityService.keys() as JSON
    }

    def get (Long id) {
        def city = cityService.get(id)
        if (city != null) {
            render city as JSON
        }
    }

    def create (City city) {
        render cityService.create(city) as JSON
    }

    def update (City city) {
        render cityService.update(city) as JSON
    }

    def delete(Long id) {
        cityService.delete(id)
    }
}
