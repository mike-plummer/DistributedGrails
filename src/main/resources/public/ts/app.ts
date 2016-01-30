import {Component} from 'angular2/core';
import {HTTP_PROVIDERS} from 'angular2/http';
import {CacheTableComponent} from './cache/cache-table.component.ts';
import {JobsComponent} from "./job/jobs.component.ts";
@Component({
    selector: 'dist-grails',
    templateUrl: 'static/templates/app.html',
    directives: [CacheTableComponent, JobsComponent],
    providers: [HTTP_PROVIDERS]
})
/*
Base component of the application.
 */
export class AppComponent {}