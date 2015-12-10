import {Component, Input} from 'angular2/angular2';
import {Http, Response, Headers} from "angular2/http";
import {PopulationResultComponent} from './population-result.component.ts';
import {PopulationSum} from './PopulationSum.ts';
@Component({
    selector: 'population-job',
    templateUrl: `templates/population-job.html`,
    directives: [PopulationResultComponent]
})
/*
Manages display of all results from the population summation job
 */
export class PopulationJobComponent {
    results: Array<PopulationSum> = [];
    nodeHostname: string;
    hidden: boolean = true;

    constructor(public http: Http) {}
    /*
     Execute service call to invoke summation tasks. Parses result into bound properties and un-hides
     UI elements.
     */
    countPopulation() {
        this.hidden = true;
        this.results = [];
        this.http.get('city/population').subscribe( (response: Response) => {
            this.nodeHostname = response.headers.get('X-ClusterNode-Hostname');
            this.results = response.json().map( (sumResponse: any) => {
                return new PopulationSum(sumResponse.method, sumResponse.sum, sumResponse.duration);
            });
            this.hidden = false;
        });
    }
}