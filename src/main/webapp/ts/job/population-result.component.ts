import {Component, Input} from 'angular2/core';
import {PopulationSum} from './PopulationSum.ts';
@Component({
    selector: 'population-result',
    template: `<pre><code>
<strong>{{result.method}}</strong><br/>
Sum: {{result.sum}}<br/>
Duration: {{result.duration}}
            </code></pre>`
})
/*
Manages display of a single Population Summation result
 */
export class PopulationResultComponent {
    // Bind this field via an attribute defined on the HTML element for this directive
    @Input() result: PopulationSum;

    constructor() {}
}