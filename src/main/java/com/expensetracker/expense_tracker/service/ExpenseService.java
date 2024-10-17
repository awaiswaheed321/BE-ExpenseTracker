package com.expensetracker.expense_tracker.service;

import com.expensetracker.expense_tracker.models.entities.Category;
import com.expensetracker.expense_tracker.models.entities.Expense;
import com.expensetracker.expense_tracker.models.dtos.CategoryExpenseSummary;
import com.expensetracker.expense_tracker.models.dtos.DateExpenseSummary;
import com.expensetracker.expense_tracker.repository.ExpenseRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final CategoryService categoryService;

    public ExpenseService(ExpenseRepository expenseRepository, CategoryService categoryService) {
        this.expenseRepository = expenseRepository;
        this.categoryService = categoryService;
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Expense createExpense(Expense expense) {
        if (expense.getCategory() != null) {
            // Save the category if it's not already present in the database
            Category category = expense.getCategory();
            if (category.getId() == null) {
                // This assumes you're creating a new category; otherwise, fetch the existing category
                category = categoryService.createCategory(category);
            } else {
                // Fetch the existing category if it has an ID
                category = categoryService.getCategoryById(category.getId());
            }
            expense.setCategory(category);
        }
        return expenseRepository.save(expense);
    }

    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id).orElse(null);
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    public List<Expense> getExpensesByCategory(Long id) {
        Category category = categoryService.getCategoryById(id);
        if (category == null) {
            return null;
        }
        return expenseRepository.findByCategory(category);
    }

    public List<Expense> getExpensesByDate(Date date) {
        return expenseRepository.findByDate(date);
    }

    @Transactional
    public Expense updateExpense(Long id, Expense updatedExpense) throws Exception {
        Optional<Expense> optionalExpense = expenseRepository.findById(id);

        if (optionalExpense.isPresent()) {
            Expense expense = optionalExpense.get();

            // Update the fields
            expense.setName(updatedExpense.getName());
            expense.setAmount(updatedExpense.getAmount());
            expense.setDate(updatedExpense.getDate());

            // Update category if present in the request
            if (updatedExpense.getCategory() != null) {
                Long categoryId = updatedExpense.getCategory().getId();
                Category category = categoryService.getCategoryById(categoryId);
                if (category == null) {
                    throw new Exception("Category not found with id: " + categoryId);
                }
                expense.setCategory(category);
            }

            // Save and return the updated expense
            return expenseRepository.save(expense);
        } else {
            throw new Exception("Expense not found with id: " + id);
        }
    }

    public List<CategoryExpenseSummary> getExpenseSummaryByCategory() {
        return expenseRepository.getExpenseSummaryByCategory();
    }

    public List<DateExpenseSummary> getExpenseSummaryByDate() {
        return expenseRepository.getExpenseSummaryByDate();
    }
}
