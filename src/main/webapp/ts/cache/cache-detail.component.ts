import {Component, Input} from 'angular2/angular2';
import {Cache} from './cache.ts';
@Component({
    selector: 'cache-detail',
    template: `<h4>{{cache.name}}</h4>
               <p>
               Size: {{cache.size}}<br/>
               Type: {{cache.type}}<br/>
               Source: {{cache.source}}
               </p>`
})
/*
Displays a single distributed objec entry.
 */
export class CacheDetailComponent {
    @Input() cache: Cache;
}