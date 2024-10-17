package com.expensetracker.expense_tracker.controllers;

import com.expensetracker.expense_tracker.models.Expense;
import com.expensetracker.expense_tracker.models.dtos.CategoryExpenseSummary;
import com.expensetracker.expense_tracker.models.dtos.DateExpenseSummary;
import com.expensetracker.expense_tracker.service.ExpenseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/expenses")
public class ExpenseController {
    // Logger for this class
    private static final Logger logger = LoggerFactory.getLogger(ExpenseController.class);

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    // Get all expenses
    @GetMapping
    public List<Expense> getAllExpenses() {
        logger.info("Fetching all expenses");
        return expenseService.getAllExpenses();
    }

    // Create a new expense
    @PostMapping
    public Expense createExpense(@RequestBody Expense expense) {
        logger.info("Creating a new expense with name: {}", expense.getName());
        return expenseService.createExpense(expense);
    }

    // Get expense by ID
    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable Long id) {
        logger.info("Fetching expense with ID: {}", id);
        Expense expense = expenseService.getExpenseById(id);
        if (expense != null) {
            logger.info("Expense found with ID: {}", id);
            return ResponseEntity.ok(expense);
        } else {
            logger.warn("Expense not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    // Delete expense by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        logger.info("Deleting expense with ID: {}", id);
        Expense expense = expenseService.getExpenseById(id);
        if (expense != null) {
            expenseService.deleteExpense(id);
            logger.info("Expense deleted with ID: {}", id);
            return ResponseEntity.noContent().build();
        } else {
            logger.warn("Expense not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<Expense>> getExpensesByCategory(@PathVariable Long id) {
        logger.info("Fetching expenses by category with ID: {}", id);
        List<Expense> res = expenseService.getExpensesByCategory(id);
        if (res == null) {
            logger.warn("Category not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(res);
        }
    }

    @GetMapping("/date/{dateString}")
    public ResponseEntity<List<Expense>> getExpensesByDate(@PathVariable String dateString) {
        logger.info("Fetching expenses by date with date: {}", dateString);

        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        try {
            Date date = formatter.parse(dateString);
            // Assuming a service method that fetches expenses by date
            List<Expense> expenses = expenseService.getExpensesByDate(date);
            return ResponseEntity.ok(expenses);
        } catch (ParseException e) {
            logger.error("Invalid date format: {}", dateString, e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpenseById(@PathVariable Long id, @RequestBody Expense updatedExpense) {
        try {
            Expense updated = expenseService.updateExpense(id, updatedExpense);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/summary/category")
    public ResponseEntity<List<CategoryExpenseSummary>> getExpenseSummaryByCategory() {
        List<CategoryExpenseSummary> summary = expenseService.getExpenseSummaryByCategory();
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/summary/date")
    public ResponseEntity<List<DateExpenseSummary>> getExpenseSummaryByDate() {
        List<DateExpenseSummary> summary = expenseService.getExpenseSummaryByDate();
        return ResponseEntity.ok(summary);
    }
}
