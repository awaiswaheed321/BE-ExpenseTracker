package com.expensetracker.expense_tracker.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double amount;
    private Date date;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    public Expense(String name, double amount, Category category, Date date) {
        this.name = name;
        this.amount = amount;
        this.category = category;  // Update constructor parameter to accept Category
        this.date = date;
    }
}
