package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Product {
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty productName = new SimpleStringProperty();
    private IntegerProperty categoryID = new SimpleIntegerProperty();
    private IntegerProperty productPrice = new SimpleIntegerProperty();
    private IntegerProperty productQuantity = new SimpleIntegerProperty();
    private IntegerProperty supplierID = new SimpleIntegerProperty();

    public Product(int id, String productName, int categoryID, int productPrice, int productQuantity, int supplierID) {
        this.id.set(id);
        this.productName.set(productName);
        this.categoryID.set(categoryID);
        this.productPrice.set(productPrice);
        this.productQuantity.set(productQuantity);
        this.supplierID.set(supplierID);
    }

    public IntegerProperty getId() {
        return id;
    }

    public StringProperty getProductName() {
        return productName;
    }

    public IntegerProperty getCategoryID() {
        return categoryID;
    }

    public IntegerProperty getProductPrice() {
        return productPrice;
    }

    public IntegerProperty getProductQuantity() {
        return productQuantity;
    }

    public IntegerProperty getSupplierID() {
        return supplierID;
    }
}
