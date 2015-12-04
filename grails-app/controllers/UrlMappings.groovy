class UrlMappings {
    static mappings = {
        "/"(view:"/index")

        "/city"(controller:"city", action: "keys", method: "GET")
        "/city/$id"(controller:"city", action: "get", method: "GET")
        "/city"(controller:"city", action: "create", method: "POST")
        "/city"(controller:"city", action: "update", method: "PUT")
        "/city/$id"(controller:"city", action: "delete", method: "DELETE")

        "/cache"(controller:"cache", action: "index", method: "GET")
    }
}