package com.example.hotel_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO для отображения фильтрованных списков с пагинацией
 * @param <T> Класс, список из экземпляров которого будет сформирован список при выдаче
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoForPagination<T> {
    Long totalQuantityInDb;
    Long totalFounded;
    Long totalQuantityOnCurrentPage;
    Integer numberOfPage;
    Integer totalNumberOfPages;
    List<T> objects;
}
