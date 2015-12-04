import {Component} from "angular2/angular2";
import {Http, Response} from "angular2/http";
import {CacheDetailComponent} from './cache-detail.component.ts';
import {Cache} from './cache.ts';

@Component({
    selector: 'cache-list',
    template: `<cache-detail *ng-for="#cache of caches" [cache]="cache">
               </cache-detail>`,
    directives: [CacheDetailComponent]
})
export class CacheListComponent {
    caches: Array<Cache>;
    constructor(public http: Http) {
        http.get('/cache').map( (response: Response) => {
            return response.json().caches;
        }).map( (data: any) => {
            translated: Array<Cache>;
            return data.map(jsonItem => {
                return new Cache(jsonItem.name, jsonItem.size, jsonItem.type, jsonItem.source);
            });
        }).subscribe( data => this.caches = data);
    }
}