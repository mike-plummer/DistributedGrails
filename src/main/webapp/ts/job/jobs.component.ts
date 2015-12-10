import {Component} from "angular2/angular2";
import {PopulationJobComponent} from "./population-job.component.ts";
@Component({
    selector: 'jobs',
    templateUrl: `templates/jobs.html`,
    directives: [PopulationJobComponent]
})
/*
Supplies a UI control to invoke jobs on the server then display the result(s)
 */
export class JobsComponent {
    constructor() {
    }
}