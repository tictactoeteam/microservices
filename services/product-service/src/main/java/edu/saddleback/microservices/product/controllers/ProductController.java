package edu.saddleback.microservices.product.controllers;

import com.google.gson.*;
import edu.saddleback.microservices.product.db.ProductDao;
import edu.saddleback.microservices.product.model.Product;

import java.sql.SQLException;
import java.util.ArrayList;

public class ProductController {
    //convert from DAO to JSON
    ProductDao productDao = new ProductDao();
    Gson gson = new GsonBuilder().create();
    ArrayList<Product> productList = new ArrayList<>();

    {
        try {
            productList = productDao.getAllProducts();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    JsonArray jsonProductList = gson.toJsonTree(productList).getAsJsonArray();

    public JsonArray convertDaoListToJson() {
        for (int i = 0; i < productList.size(); i++) {

            Product product = new Product();

            product = productList.get(i);
            product.setProductID(productList.get(i).getProductId());
            product.setProductName(productList.get(i).getProductName());
            product.setProductQuantity(productList.get(i).getProductQuantity());
            product.setProductPrice(productList.get(i).getProductPrice());

            JsonObject res = new JsonObject();
            res.addProperty("id", product.getProductId().toString());
            res.addProperty("name", product.getProductName());
            res.addProperty("price", product.getProductPrice().toString());
            res.addProperty("quantity", product.getProductQuantity().toString());

            jsonProductList.add(res);
        }
        return jsonProductList;
    }

    //Need to convert one DAO query to Json
    public JsonObject convertDaoToJson(Product someProduct) {
        JsonObject res = new JsonObject();
        res.addProperty("id", someProduct.getProductId().toString());
        res.addProperty("name", someProduct.getProductName());
        res.addProperty("price", someProduct.getProductPrice().toString());
        res.addProperty("quantity", someProduct.getProductQuantity().toString());

        return res;
    }


    public JsonObject getProductJson(String UUID) {
        JsonObject res = new JsonObject();
        Product someProduct = new Product();

        try {
            someProduct = productDao.getProductByID(UUID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        res = convertDaoToJson(someProduct);

        return res;
    }
}
