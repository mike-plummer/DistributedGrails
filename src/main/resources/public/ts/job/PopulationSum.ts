/*
Data object representing the outcome of an summation of City population data.
 */
export class PopulationSum {
    constructor(public method: string,
                public sum: number,
                public duration: number) {}
}