package com.expensetracker.expense_tracker.models.dtos;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DateExpenseSummary {
    private Date date;
    private double totalAmount;
}

