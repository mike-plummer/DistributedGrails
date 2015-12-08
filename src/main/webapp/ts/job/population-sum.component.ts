import {Component, Input} from 'angular2/angular2';
import {PopulationSum} from './PopulationSum.ts';
@Component({
    selector: 'population-sum',
    template: `<h4>{{populationSum.method}}</h4>
               <p>
               Sum: {{populationSum.sum}}<br/>
               Duration: {{populationSum.duration}}
               </p>`
})
/*
Manages display of a single Population Summation task
 */
export class PopulationSumComponent {
    @Input() populationSum: PopulationSum;
}