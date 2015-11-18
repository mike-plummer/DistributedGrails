import com.objectpartners.plummer.distributed_grails.City
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser

class BootStrap {

    private static final FORMAT = CSVFormat.DEFAULT.withHeader("name", "state", "population")

    def cityDataService

    def init = { servletContext ->

        // Load City data from CSV into Hazelcast
        System.out.println("Loading City data...")
        BootStrap.class.getResourceAsStream("/cities.csv").withStream { stream ->
            CSVParser parser = new CSVParser(new InputStreamReader(stream), FORMAT)
            parser.getRecords().each({ record ->
                City city = new City(name: record.get("name"), state: record.get("state"), population: Long.valueOf(record.get("population")))
                cityDataService.add(city)
            })
        }
        System.out.println("City data loaded.")
    }

    def destroy = {
    }
}
