package eu.profinit.education.flightlog.dao;

import eu.profinit.education.flightlog.exceptions.ExternalSystemException;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@Profile("!stub")
public class ClubDatabaseDaoImpl implements ClubDatabaseDao {

    private final RestTemplate restTemplate;
    private final String clubDbBaseUrl;

    // TODO: načtěte property integration.clubDb.baseUrl z application.properties (hint: CsvExportServiceImpl)
    public ClubDatabaseDaoImpl() {
        this.restTemplate = new RestTemplate();
        this.clubDbBaseUrl = null;
    }


    @Override
    public List<User> getUsers() {

        ResponseEntity<List<User>> usersResponse;
        try {

            // TODO: implement call to ClubDatabase API using RestTemplate
            usersResponse = null;
            throw new NotImplementedException("Integration is not implemented. Use stub implementation instead");
        } catch (RuntimeException e) {
            throw new ExternalSystemException("Cannot get users from Club database. URL: {}. Call resulted in exception.", e, clubDbBaseUrl);
        }
//        if (usersResponse.getStatusCode().isError()) {
//            throw new ExternalSystemException("Cannot get users from Club database. HTTP status: {}", usersResponse.getStatusCode());
//        }
//        return usersResponse.getBody();
    }
}
