package com.example.demo.services;

import com.example.demo.entities.Article;
import com.example.demo.entities.Category;
import com.example.demo.repositories.category.CategoryRepository;

import javax.inject.Inject;
import java.util.List;

public class CategoryService {
    public CategoryService() {
        System.out.println(this);
    }

    @Inject
    private CategoryRepository categoryRepository;

    public Category addCategory(Category category) {
        return this.categoryRepository.addCategory(category);
    }

    public List<Category> allCategories() {
        return this.categoryRepository.allCategories();
    }

    public Category findCategory(String category) {
        return this.categoryRepository.findCategory(category);
    }

    public void deleteCategory(String category) {
        this.categoryRepository.deleteCategory(category);
    }

    public Category modifyCategory(Category category, String categoryName) {
        return this.categoryRepository.modifyCategory(category, categoryName);
    }

    public List<Article> findArticlesByCategory(String category) {
        return this.categoryRepository.articlesByCategory(category);
    }

    public Object allCategoriesByPage(Integer pageNum) {
        return this.categoryRepository.allCategoriesOnPage(pageNum);
    }

    public Integer categoryCount() {
        return this.categoryRepository.categoryCount();
    }

    public Integer articlesCountByCategory(String category) {
        return this.categoryRepository.articlesCountByCategory(category);
    }

    public List<Article> findArticlesByCategoryOnPage(String category, Integer pageNum) {
        return this.categoryRepository.articlesByCategoryOnPage(category, pageNum);
    }

    public List<Article> searchArticlesByCategory(Integer pageNum, String category, String string) {
        return this.categoryRepository.searchArticlesByCategory(category, string, pageNum);
    }

    public Integer articlesCountByCategorySearch(String category, String search) {
        return this.categoryRepository.articlesCountByCategorySearch(category, search);
    }
}
