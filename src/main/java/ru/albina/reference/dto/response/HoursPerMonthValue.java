package ru.albina.reference.dto.response;

import lombok.Data;

@Data
public class HoursPerMonthValue {

    private int year;

    private int month;

    private double hours;
}
