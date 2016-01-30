import {Component} from "angular2/core";
import {Http, Response, Headers} from "angular2/http";
import {Cache} from './cache.ts';

@Component({
    selector: 'cache-table',
    templateUrl: `static/templates/cache-table.html`
})
/*
Lists all Distributed Objects managed by Hazelcast.
 */
export class CacheTableComponent {
    caches: Array<Cache>;
    nodeHostname: string;
    /*
    Invokes service call to retrieve distributed objects from the server. Parses results into bound properties.
     */
    constructor(public http: Http) {
        http.get('cache').subscribe( (response: Response) => {
            this.nodeHostname = response.headers.get('X-ClusterNode-Hostname');
            this.caches = response.json().caches.map( (cacheResponse: any) => {
                return new Cache(cacheResponse.name, cacheResponse.size, cacheResponse.type, cacheResponse.source);
            });
        });
    }
}