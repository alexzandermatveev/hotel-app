package com.example.hotel_app.controllers;

import com.example.hotel_app.services.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Контроллер для работы со статистикой
 */
@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {
    private final StatisticsService statisticsService;

    /**
     * Создает csv со статистикой по пользователям и их броням
     * @param toDir {@link com.example.hotel_app.model.Booking} При равном true сохраняет файл "statistics.csv" по адресу ./target
     * @return {@link ResponseEntity}
     * @throws IOException
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getStatistics(@RequestParam(defaultValue = "false") Boolean toDir) throws IOException {

        if (toDir) {
            return ResponseEntity.ok(statisticsService.exportToFile());
        }

        String csv = statisticsService.statisticsToCSV();
        InputStreamResource inputStreamResource = new InputStreamResource(new ByteArrayInputStream(csv.getBytes()));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=statistics.csv")
                .contentType(MediaType.TEXT_PLAIN)
                .body(inputStreamResource);


    }
}
