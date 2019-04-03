package eu.profinit.education.flightlog.service;

import eu.profinit.education.flightlog.domain.entities.Flight;
import eu.profinit.education.flightlog.domain.repositories.FlightRepository;
import eu.profinit.education.flightlog.exceptions.FlightLogException;
import eu.profinit.education.flightlog.to.FileExportTo;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CsvExportServiceImpl implements CsvExportService {

    private static final String DATE_PATTERN = "dd.MM.yyyy HH:mm:ss";
    private static final String ENCODING = "Cp1250";
    public static final MediaType CVS_CONTENT_TYPE = MediaType.valueOf("text/csv");

    private final FlightRepository flightRepository;

    private final String fileName;

    public CsvExportServiceImpl(FlightRepository flightRepository, @Value("${csv.export.flight.fileName}") String fileName) {
        this.flightRepository = flightRepository;
        this.fileName = fileName;
    }

    @Override
    public FileExportTo getAllFlightsAsCsv() {
        // ID is used to have always same order when takeoffTime is same and coincidentally it will order towplane before glider for each flight
        List<Flight> flights = flightRepository.findAll(Sort.by(Sort.Order.asc("takeoffTime"), Sort.Order.asc("id")));

        try (
            ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
            Writer printWriter = new OutputStreamWriter(byteOutputStream, (Charset.forName(ENCODING)));
            CSVPrinter csvExport = new CSVPrinter(printWriter, CSVFormat.DEFAULT)
        ) {
            // TODO 4: Naimplementujte vytváření CSV.
            csvExport.printRecord("Column 1", "Colum 2 ěščř");

            printWriter.flush();
            byteOutputStream.flush();
            return new FileExportTo(fileName, CVS_CONTENT_TYPE, ENCODING, byteOutputStream.toByteArray());
        } catch (IOException exception) {
            throw new FlightLogException("Error during flights CSV export", exception);
        }
    }

    private String formatDateTime(LocalDateTime dateTime){
        if (dateTime == null){
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        return formatter.format(dateTime);
    }
}
