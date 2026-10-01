package service;

import java.util.List;
import dao.CategoryDAO_24162007;
import entity.Category_24162007;

public class CategoryService_24162007 {

    private CategoryDAO_24162007 categoryDAO = new CategoryDAO_24162007();

    public List<Category_24162007> getAllCategories() {
        return categoryDAO.getAllCategories();
    }

    public Category_24162007 getCategoryById(int id) {
        return categoryDAO.getCategoryById(id);
    }
}