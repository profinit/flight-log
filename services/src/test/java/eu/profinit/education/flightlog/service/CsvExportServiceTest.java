package eu.profinit.education.flightlog.service;

import eu.profinit.education.flightlog.IntegrationTestConfig;
import eu.profinit.education.flightlog.to.FileExportTo;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntegrationTestConfig.class)
@Transactional
@TestPropertySource(
    locations = "classpath:application-integrationtest.properties")
public class CsvExportServiceTest {

    @Autowired
    private CsvExportService testSubject;

    // TODO 6.1: Odstrante anotaci @Ignore, aby se test vykonaval
    @Ignore("Tested method is not implemented yet")
    @Test
    public void testCSVExport() throws Exception {
        FileExportTo allFlightsAsCsv = testSubject.getAllFlightsAsCsv();

        String resultContent = new String(allFlightsAsCsv.getContent(), allFlightsAsCsv.getEncoding());
        String expectedCsv = readFileToString("csv/test_export.csv");

        // TODO 6.2: zkontrolujte obsah CSV - muzete vyuzit pripraveny soubor v src\test\resources\csv\test_export.csv
    }

    private String readFileToString(String fileName) throws IOException, URISyntaxException {
        return new String(Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource(fileName).toURI())));
    }

}