import {Component} from "angular2/angular2";
import {Http, Response, Headers} from "angular2/http";
import {CacheDetailComponent} from './cache-detail.component.ts';
import {Cache} from './cache.ts';

@Component({
    selector: 'cache-list',
    template: `<div class="list">
               <h2>Distributed Data Structures</h2>
               <h3>Data supplied by node: {{nodeHostname}}</h3>
               <br/>
               <cache-detail *ng-for="#cache of caches" [cache]="cache">
               </cache-detail>
               </div>`,
    directives: [CacheDetailComponent]
})
/*
Lists all Distributed Objects managed by Hazelcast.
 */
export class CacheListComponent {
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