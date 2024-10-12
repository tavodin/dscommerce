package com.tavodin.dscommerce.tests;

import com.tavodin.dscommerce.entities.Category;
import com.tavodin.dscommerce.entities.Product;

public class ProductFactory {

    public static Product createProduct() {
        Category category = CategoryFactory.createCategory();
        Product product = new Product();
        product.setId(1L);
        product.setName("Console Playstation 5");
        product.setDescription("Is simply dummy text of the printing and typesetting industry.");
        product.setPrice(3999.0);
        product.setImgUrl("https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/2-big.jpg");
        product.getCategories().add(category);
        return product;
    }

    public static Product createProduct(String name) {
        Product product = createProduct();
        product.setName(name);
        return product;
    }
}
