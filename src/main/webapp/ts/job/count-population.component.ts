import {Component} from "angular2/angular2";
import {Http, Response, Headers} from "angular2/http";
import {PopulationSum} from "./PopulationSum.ts";
import {PopulationSumComponent} from "./population-sum.component.ts";
@Component({
    selector: 'count-population',
    template: `<h2>Jobs</h2>
               <button (click)="countPopulation()">
                   Count Population
               </button>
               <div [hidden]="hidden">
                   <h2>Population Count</h2>
                   <h3>Data supplied by node: {{nodeHostname}}</h3>
                   <br/>
                   <population-sum *ng-for="#populationSum of populationSums" [populationSum]="populationSum">
                   </population-sum>
               </div>`,
    directives: [PopulationSumComponent]
})
/*
Supplies a UI control to invoke population summation tasks on the server then display the result(s)
 */
export class CountPopulationComponent {
    populationSums: Array<PopulationSum> = [];
    nodeHostname: string;
    hidden: boolean = true;
    constructor(public http: Http) {
    }

    /*
    Execute service call to invoke summation tasks. Parses result into bound properties and un-hides
    UI elements.
     */
    countPopulation() {
        this.http.get('city/population').subscribe( (response: Response) => {
            this.nodeHostname = response.headers.get('X-ClusterNode-Hostname');
            this.populationSums = response.json().map( (sumResponse: any) => {
                return new PopulationSum(sumResponse.method, sumResponse.sum, sumResponse.duration);
            });
            this.hidden = false;
        });
    }
}