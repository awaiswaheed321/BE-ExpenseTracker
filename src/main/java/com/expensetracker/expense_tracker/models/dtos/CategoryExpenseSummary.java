package com.expensetracker.expense_tracker.models.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryExpenseSummary {
    private String categoryName;
    private double totalAmount;
}

