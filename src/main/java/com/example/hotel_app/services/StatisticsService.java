package com.example.hotel_app.services;

import com.example.hotel_app.model.RoomBookingEvent;
import com.example.hotel_app.model.UserRegistrationEvent;
import com.example.hotel_app.repository.RoomBookingEventRepository;
import com.example.hotel_app.repository.UserRegistrationEventRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * Сервис по обработке запросов на получение статистики
 */
@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final UserRegistrationEventRepository userRegistrationEventRepository;
    private final RoomBookingEventRepository roomBookingEventRepository;

    /**
     * Возвращает csv в формате строки
     * @return csv
     * @throws IOException
     */
    public String statisticsToCSV() throws IOException {
        StringWriter stringWriter = new StringWriter();
        CSVPrinter csvPrinter = new CSVPrinter(stringWriter,
                CSVFormat.DEFAULT
                        .builder()
                        .setHeader("type", "value")
                        .build());

        for(UserRegistrationEvent user : userRegistrationEventRepository.findAll()){
            csvPrinter.printRecord("UserRegistrationEvent", user);
        }
        for(RoomBookingEvent bookingEvent : roomBookingEventRepository.findAll()){
            csvPrinter.printRecord("RoomBookingEvent", bookingEvent);
        }

        csvPrinter.flush();

        return  stringWriter.toString();
    }

    /**
     * Сохраняет файл по адресу ./target
     * @return Сообщение об успешной записи файла
     * @throws IOException
     */
    public String exportToFile() throws IOException {
        File targetDir = new File("target");
        if(!targetDir.exists()){
            targetDir.mkdir();
        }
        File csv = new File(targetDir, "statistics.csv");
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(csv))){
            writer.write(statisticsToCSV());
            writer.flush();
        }catch (IOException exception){
            throw new IOException("attempt to save file in dir failed");
        }
        return "successfully saved in " + csv.getAbsolutePath();
    }

}
