import {bootstrap, Component, CORE_DIRECTIVES} from 'angular2/angular2';
import {HTTP_PROVIDERS} from 'angular2/http';
import {CacheTableComponent} from './cache/cache-table.component.ts';
import {JobsComponent} from "./job/jobs.component.ts";
@Component({
    selector: 'dist-grails',
    templateUrl: 'templates/app.html',
    directives: [CacheTableComponent, JobsComponent, CORE_DIRECTIVES]
})
/*
Base component of the application.
 */
class AppComponent {}
bootstrap(AppComponent, [HTTP_PROVIDERS]);