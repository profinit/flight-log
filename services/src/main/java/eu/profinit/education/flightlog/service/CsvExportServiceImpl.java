package eu.profinit.education.flightlog.service;

import eu.profinit.education.flightlog.domain.entities.Flight;
import eu.profinit.education.flightlog.exceptions.FlightLogException;
import eu.profinit.education.flightlog.to.FlightTo;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import eu.profinit.education.flightlog.domain.repositories.FlightRepository;
import eu.profinit.education.flightlog.to.FileExportTo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
@Service
public class CsvExportServiceImpl implements CsvExportService {

    // TODO 4.4(DONE) Vhodné vlastnosti CSV souboru definujte jako konstanty
    private static final String DATE_PATTERN = "dd.MM.yyyy HH:mm:ss";
    private final FlightRepository flightRepository;

    private final String fileName;

    public CsvExportServiceImpl(FlightRepository flightRepository, @Value("${csv.export.flight.fileName}") String fileName) {
        this.flightRepository = flightRepository;
        this.fileName = fileName;
    }

    @Override
    public FileExportTo getAllFlightsAsCsv() {
        // TODO 4.3: Naimplementujte vytváření CSV.
        // Tip: můžete použít Apache Commons CSV - https://commons.apache.org/proper/commons-csv/ v příslušných pom.xml naleznete další komentáře s postupem
        FileExportTo fileExportTo;
        List<Flight> flights = flightRepository.findAll(
            Sort.by(Sort.Order.asc("takeoffTime"),
                Sort.Order.asc("id")));

        try (ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
             Writer printWriter = new OutputStreamWriter(byteOutputStream, StandardCharsets.UTF_8);
             CSVPrinter csvExport = new CSVPrinter(printWriter, CSVFormat.DEFAULT)) {
// TODO: print data to CSV
            csvExport.printRecord("sep=,");
            csvExport.printRecord("FlightID","TakeoffTime","LandingTime","Immatriculation","Type","Pilot","Copilot","Task","TowplaneID","GliderID");
            for (Flight flight : flights) {
                FlightTo flightTo = FlightTo.fromEntity(flight);

                String pilot = flightTo.getPilot().getFirstName() + " " + flightTo.getPilot().getLastName();
                String copilot = (flightTo.getCopilot() == null) ? "" : flightTo.getCopilot().getFirstName() + " " + flightTo.getCopilot().getLastName();
                String towplaneID = (flight.getTowplaneFlight() == null) ? "" : FlightTo.fromEntity(flight.getTowplaneFlight()).getId().toString();
                String gliderID = (flight.getGliderFlight() == null) ? "" : FlightTo.fromEntity(flight.getGliderFlight()).getId().toString();
                csvExport.printRecord(
                    flightTo.getId(),
                    formatDateTime(flightTo.getTakeoffTime()),
                    formatDateTime(flightTo.getLandingTime()),
                    flightTo.getAirplane().getImmatriculation(),
                    flightTo.getAirplane().getType(),
                    pilot,
                    copilot,
                    flightTo.getTask(),
                    towplaneID,
                    gliderID
                    );
            }
            csvExport.flush();
// TODO: get data from byteOutputStream using toByteArray()
            fileExportTo = new FileExportTo(fileName, MediaType.TEXT_PLAIN,"UTF-8",byteOutputStream.toByteArray());

        } catch (IOException exception) {
            throw new FlightLogException("Error during flights CSV export", exception);
        }
        return fileExportTo;
    }

    private String formatDateTime(LocalDateTime dateTime){
        if (dateTime == null){
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        return formatter.format(dateTime);
    }
}