package com.objectpartners.plummer.distributed_grails.data

import com.hazelcast.core.ICompletableFuture
import com.hazelcast.mapreduce.Collator
import com.hazelcast.mapreduce.Context
import com.hazelcast.mapreduce.Job
import com.hazelcast.mapreduce.JobTracker
import com.hazelcast.mapreduce.KeyValueSource
import com.hazelcast.mapreduce.Mapper
import com.hazelcast.mapreduce.aggregation.impl.LongSumAggregation
import com.objectpartners.plummer.distributed_grails.City

class CityDataService extends AbstractDataService<City> {

    private static final String CITY_MAP_NAME = "com.objectpartners.plummer.distributed_grails.data.City"

    def simpleSumPopulation() {
        return (Long) getData().values().sum { value ->
            return value.population
        }
    }

    /**
     * Distributes a job across the cluster which totals the population for the Cities stored on each node
     * in parallel then aggregates those values into the final total. In a true distributed cluster this is
     * much more efficient - the alternative is to pull all data to a single node and iterate it there which
     * may exceed available memory and leaves the processors of the rest of the cluster un-used.
     *
     * @return
     */
    def distributedSumPopulation() {
        JobTracker tracker = hazelcastService.getJobTracker("CITY_POPULATION_SUM")
        KeyValueSource<Long, City> source = KeyValueSource.fromMap(getData());
        Job job = tracker.newJob(source)
        ICompletableFuture<Long> future = job
                //Extract population from each city
                .mapper(new PopulationMapper())
                //Combine extracted populations within each node
                .reducer(new LongSumAggregation.LongSumReducerFactory())
                //Generate final value using reduced values from each node
                .submit(new PopulationCollator())

        return future.get()
    }

    protected String getMapName() {
        return CITY_MAP_NAME
    }
}

/**
 * Responsible for extracting the value of interest from each {@link City} and publishing (emitting)
 * it with a specific String key. A single Job could potentially operate on multiple values from
 * each entry so the key allows the Reducer to tell them apart - in this job we only have one so the key
 * ends up being ignored.
 */
class PopulationMapper implements Mapper<Long, City, String, Long> {
    @Override
    public void map(Long key, City value, Context<String, Long> context) {
        context.emit("populationsum", value.population);
    }
}

/**
 * Is run on the invoking cluster node to combine the totals returned by each node's
 * reducer. This is what yields the 'final' value for the job.
 */
class PopulationCollator implements Collator<Map.Entry<String, Long>, Long> {
    @Override
    public Long collate(Iterable<Map.Entry<String, Long>> values) {
        return (Long) values.sum { value ->
            return value.getValue()
        }
    }
}
