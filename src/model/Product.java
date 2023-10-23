package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;



public class Product {
	
	private final IntegerProperty id;
	private final StringProperty productName; 
	private final IntegerProperty productQuantity;
	private final IntegerProperty productPrice;

	private Supplier supplier;

	
	//Class Constructor
	public Product(int id, String productName,int productQuantity,int productPrice,Supplier supplier) {
	    this.id = new SimpleIntegerProperty(id);
	    this.productName = new SimpleStringProperty(productName);
	    this.productQuantity = new SimpleIntegerProperty(productQuantity);
	    this.productPrice = new SimpleIntegerProperty(productPrice);
	    this.supplier = supplier; 
	}

	
	//Getter
	
	public int getProductID() {
		return id.get();
	}
	
	public String getProductName() {
		return productName.get();
	}
	
	public int getProductPrice() {
		return productPrice.get();
	}
	
	
	public int getProductQuantity() {
		return productQuantity.get();
	}
	
	public Supplier getSupplier() {
        return supplier;
    }
	
	
	
	//Setter
	
	public void setProductID(int id) {
		this.id.set(id);
	}
	
	public void setProductName(String productName) {
		this.productName.set(productName);
	}
	
	public void setProductPrice(int productPrice) {
		this.productPrice.set(productPrice);
	}
	
	public void setProductQuantity(int productQuantity) {
		this.productQuantity.set(productQuantity);
	}
	
	public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

	

	
	//Property Values
	
	public IntegerProperty productIDProperty() {return id;}
	public IntegerProperty productPriceProperty() {return productPrice;}
	public StringProperty productNameProperty() {return productName;}
	public IntegerProperty productQuantityProperty() {return productQuantity;}

	
	
	
	
	
}
