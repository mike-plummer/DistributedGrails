import {Component} from "angular2/angular2";
import {Http, Response, Headers} from "angular2/http";
import {CacheDetailComponent} from './cache-detail.component.ts';
import {Cache} from './cache.ts';

@Component({
    selector: 'cache-list',
    template: `<h2>Cache Node: {{nodeHostname}}</h2>
               <cache-detail *ng-for="#cache of caches" [cache]="cache">
               </cache-detail>`,
    directives: [CacheDetailComponent]
})
export class CacheListComponent {
    caches: Array<Cache>;
    nodeHostname: string;
    constructor(public http: Http) {
        http.get('cache').map( (response: Response) => {
            this.nodeHostname = response.headers.get('X-ClusterNode-Hostname')
            return response.json().caches;
        }).map( (data: any) => {
            translated: Array<Cache>;
            return data.map(jsonItem => {
                return new Cache(jsonItem.name, jsonItem.size, jsonItem.type, jsonItem.source);
            });
        }).subscribe( data => this.caches = data);
    }
}