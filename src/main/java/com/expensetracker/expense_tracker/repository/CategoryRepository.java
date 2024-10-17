package com.expensetracker.expense_tracker.repository;

import com.expensetracker.expense_tracker.models.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
