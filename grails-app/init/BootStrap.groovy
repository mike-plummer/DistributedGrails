import com.hazelcast.core.ILock
import com.objectpartners.plummer.distributed_grails.City
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser

class BootStrap {

    private static final FORMAT = CSVFormat.DEFAULT.withHeader("name", "state", "population")

    def hazelcastService

    def cityDataService

    def init = { servletContext ->
        // A fairly crude mechanism for ensuring only one cluster member tries to load data into
        // Hazelcast. We only allow the oldest member of the cluster to perform the inital load.
        // A better solutions would probably involve distributed locks but is overkill for this
        // example application.
        def oldestClusterMember = hazelcastService.getCluster().getMembers().iterator().next()
        if (oldestClusterMember.getUuid().equals(hazelcastService.getCluster().getLocalMember().getUuid())) {
            // Load City data from CSV into Hazelcast
            System.out.println("Loading City data...")
            BootStrap.class.getResourceAsStream("/cities.csv").withStream { stream ->
                CSVParser parser = new CSVParser(new InputStreamReader(stream), FORMAT)
                parser.getRecords().each({ record ->
                    City city = new City(name: record.get("name"), state: record.get("state"), population: Long.valueOf(record.get("population")))
                    cityDataService.addOrUpdate(city)
                })
            }
            System.out.println("City data loaded.")
        } else {
            System.out.println("Not oldest cluster member, skipping City data loading.")
        }
    }

    def destroy = {
    }
}
