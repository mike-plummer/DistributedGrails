class UrlMappings {
    static mappings = {
        // Default landing page, renders index.gsp
        "/"(view:"/index")

        // City controller
        "/city"(controller:"city", action: "keys", method: "GET")
        "/city/population"(controller:"city", action: "population", method: "GET")
        "/city/$id"(controller:"city", action: "get", method: "GET")
        "/city"(controller:"city", action: "create", method: "POST")
        "/city"(controller:"city", action: "update", method: "PUT")
        "/city/$id"(controller:"city", action: "delete", method: "DELETE")

        // Cache controller
        "/cache"(controller:"cache", action: "index", method: "GET")
    }
}