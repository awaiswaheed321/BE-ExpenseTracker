package com.expensetracker.expense_tracker.repository;

import com.expensetracker.expense_tracker.models.Category;
import com.expensetracker.expense_tracker.models.Expense;
import com.expensetracker.expense_tracker.models.dtos.CategoryExpenseSummary;
import com.expensetracker.expense_tracker.models.dtos.DateExpenseSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByCategory(Category category);

    List<Expense> findByDate(Date date);

    @Query("SELECT new com.expensetracker.expense_tracker.models.dtos.CategoryExpenseSummary(e.category.name, SUM(e.amount)) " +
            "FROM Expense e GROUP BY e.category.name")
    List<CategoryExpenseSummary> getExpenseSummaryByCategory();

    @Query("SELECT new com.expensetracker.expense_tracker.models.dtos.DateExpenseSummary(e.date, SUM(e.amount)) " +
            "FROM Expense e GROUP BY e.date")
    List<DateExpenseSummary> getExpenseSummaryByDate();
}
