package model;

public class Product {
    private int id;
    private String productName;
    private int categoryID;
    private int productPrice;
    private int productQuantity;
    private int supplierID;

    public Product(int id, String productName, int categoryID, int productPrice, int productQuantity, int supplierID) {
        this.id = id;
        this.productName = productName;
        this.categoryID = categoryID;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.supplierID = supplierID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }
}
