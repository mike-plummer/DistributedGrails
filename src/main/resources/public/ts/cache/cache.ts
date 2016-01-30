/*
Data object representing a single Distributed Object managed by Hazelcast
 */
export class Cache {
    constructor(public name: string,
                public size: number,
                public type: string,
                public source: string) {}
}