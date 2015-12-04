import {bootstrap, Component, CORE_DIRECTIVES} from 'angular2/angular2';
import {HTTP_PROVIDERS} from 'angular2/http';
import {CacheListComponent} from './cache/cache-list.component.ts';
@Component({
    selector: 'dist-grails',
    templateUrl: '../templates/app.html',
    directives: [CacheListComponent, CORE_DIRECTIVES]
})
class AppComponent {}
bootstrap(AppComponent, [HTTP_PROVIDERS]);