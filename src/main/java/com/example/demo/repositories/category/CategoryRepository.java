package com.example.demo.repositories.category;

import com.example.demo.entities.Article;
import com.example.demo.entities.Category;

import java.util.List;

public interface CategoryRepository {
    public Category addCategory(Category category);
    public List<Category> allCategories();
    public Category findCategory(String category);
    public void deleteCategory(String category);
    public Category modifyCategory(Category category, String categoryName);
    public List<Article> articlesByCategory(String category);
    public List<Category> allCategoriesOnPage(Integer pageNum);
    public Integer categoryCount();
    public Integer articlesCountByCategory(String category);
    public List<Article> articlesByCategoryOnPage(String category, Integer pageNum);
    public List<Article> searchArticlesByCategory(String category, String search ,Integer pageNum);
    public Integer articlesCountByCategorySearch(String category, String search);
}
