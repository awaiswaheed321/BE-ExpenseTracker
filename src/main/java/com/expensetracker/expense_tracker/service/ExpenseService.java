package com.expensetracker.expense_tracker.service;

import com.expensetracker.expense_tracker.models.Category;
import com.expensetracker.expense_tracker.models.Expense;
import com.expensetracker.expense_tracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
