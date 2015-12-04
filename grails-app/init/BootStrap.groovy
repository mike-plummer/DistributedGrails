import com.objectpartners.plummer.distributed_grails.City
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser

class BootStrap {

    private static final FORMAT = CSVFormat.DEFAULT.withHeader("name", "state", "population")

    def hazelcastService

    def cityDataService

    def init = { servletContext ->
        // Use an AtomicReference as a global "data is loaded" indicator. The first node to reach this point
        // will set the AR to 'true' and the old value (null) will be returned. Since null != true it detects
        // that it's the first and thus starts loading data. Any other nodes that reach this point will re-set
        // the AR to true but since the old value will be returned as true as well they know not to load data.
        Boolean shouldLoadData = hazelcastService.getAtomicReference("CITY_DATA_LOADED_INDICATOR").getAndSet(Boolean.TRUE);
        if (shouldLoadData != Boolean.TRUE) {
            // Load City data from CSV into Hazelcast
            println("Loading City data...")
            long counter = 0;
            BootStrap.class.getResourceAsStream("/cities.csv").withStream { stream ->
                CSVParser parser = new CSVParser(new InputStreamReader(stream), FORMAT)
                parser.getRecords().each({ record ->
                    City city = new City(name: record.get("name"), state: record.get("state"), population: Long.valueOf(record.get("population")))
                    cityDataService.addOrUpdate(city)
                    if (++counter % 100 == 0) {
                        println("Loaded ${counter} records")
                    }
                })
            }
            println("City data loaded.")
        } else {
            println("Detected another node already loaded data, skipping.")
        }
    }

    def destroy = {
    }
}
