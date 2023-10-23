package view;
import model.Supplier;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Database;
import model.Product;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.fxml.FXML;

public class UpdateDeleteProduct_View{
	
	Database con = new Database();
	
	

    private TextField searchField;
    private Button searchButton;
    private Label titleLabel;
    private TextField productNameField;
    private ComboBox<String> categoryComboBox;
    private TextField priceField;
    private TextField quantityField;
    private TextField supplierField;
    private Button updateButton; 
    private Button deleteButton;
    private FlowPane buttonWrapper;
    private TableView<Product> productTableView;
    private Stage primaryStage;

    public UpdateDeleteProduct_View(Stage primaryStage){
    	this.primaryStage = primaryStage;
    }

    public void ShowUpdateDeleteProductScene() {
        primaryStage.setTitle("FreshFind Inventory Management System");
        
        

        // Create menu bar and menus
        Menu menuProduct = new Menu("Product Management");
        MenuItem addProductItem = new MenuItem("Add New Product");
        MenuItem updateProductItem = new MenuItem("Update or Remove Product");
        menuProduct.getItems().addAll(addProductItem, updateProductItem);

        Menu menuSupplier = new Menu("Supplier Management");
        MenuItem addSupplierItem = new MenuItem("Add New Supplier");
        MenuItem updateSupplierItem = new MenuItem("Update or Remove Supplier");
        menuSupplier.getItems().addAll(addSupplierItem, updateSupplierItem);

        Menu menuProfile = new Menu("Profile");

        MenuBar menuBar = new MenuBar(menuProduct, menuSupplier, menuProfile);

        // Create center content
        productTableView = createProductTableView();

        // Create top content
        HBox searchBox = createSearchBox();

        // Create right content
        VBox addProductBox = createAddProductBox();

        // Create layout
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(searchBox);
        BorderPane.setAlignment(searchBox, Pos.CENTER);
        BorderPane.setMargin(productTableView, new Insets(0, 10, 10, 10)); // Add margin
        borderPane.setCenter(productTableView);
        borderPane.setRight(addProductBox);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(menuBar,borderPane);
        vbox.setPrefSize(640, 400);

        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);
        primaryStage.show();
        
//      Create an event handler for the "Add New Product" menu item
        EventHandler<ActionEvent> addProductHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleAddProductMenuItemClick();
            }
        };

        // Attach the event handler to the "Add New Product" menu item
        addProductItem.setOnAction(addProductHandler);
        
        deleteButton.setOnAction(event -> {
            Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
            	deleteProduct(selectedProduct.getProductName());

            }
        });

    }

    private HBox createSearchBox() {
        searchField = new TextField();
        searchField.setPromptText("Search");
        searchButton = new Button("Search");
        HBox searchBox = new HBox(searchField, searchButton);
        searchBox.setAlignment(Pos.CENTER_LEFT);
        searchBox.setSpacing(10);
        searchBox.setPadding(new Insets(14, 0, 14, 28));
        return searchBox;
    }

    private TableView<Product> createProductTableView() {
          TableView<Product> tableView = new TableView<>();
//        TableColumn<Product, Integer> productIdColumn = new TableColumn<Product, Integer>("Product ID");
//        TableColumn<Product, String> productNameColumn = new TableColumn<Product, String>("Product Name");
//        TableColumn<Product, Integer> quantityColumn = new TableColumn<Product, Integer>("Quantity");
//
//        productIdColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
//        productNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("productName"));
//        quantityColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("productQuantity"));
    	
    	TableColumn<Product, Integer> productIdColumn = new TableColumn<Product, Integer>("Product ID");
    	TableColumn<Product, String> productNameColumn = new TableColumn<Product, String>("Product Name");
    	TableColumn<Product, Integer> quantityColumn = new TableColumn<Product, Integer>("Quantity");

    	 productIdColumn.setCellValueFactory(data -> data.getValue().productIDProperty().asObject());
    	 productNameColumn.setCellValueFactory(data -> data.getValue().productNameProperty());
    	 quantityColumn.setCellValueFactory(data -> data.getValue().productQuantityProperty().asObject());

 
        tableView.getColumns().addAll(productIdColumn, productNameColumn, quantityColumn);
        tableView.setPrefHeight(310);
        tableView.setPrefWidth(281);

        con.getData(); // Panggil getData() untuk mengambil data dari database
        tableView.setItems(con.productData); // Setelah data diambil, set produkData ke TableView
        
        tableView.setOnMouseClicked(event -> {
            Product selectedProduct = tableView.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                productNameField.setText(selectedProduct.getProductName());
                priceField.setText(String.valueOf(selectedProduct.getProductPrice()));
                quantityField.setText(String.valueOf(selectedProduct.getProductQuantity()));
                
//                Supplier supplier = selectedProduct.getSupplier();
//                if (supplier != null) { 
//                    supplierField.setText(supplier.getSupplierName());
//                } else {
//                    supplierField.clear();
//                }
            }
        });

        return tableView;
    }


    private VBox createAddProductBox() {
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setPadding(new Insets(0, 20, 0, 20));
        vbox.setSpacing(10);
        Label titleLabel = new Label("Update or Delete Product");
        titleLabel.setPadding(new Insets(10, 0, 0, 0));
        GridPane grid = createAddProductForm();
        vbox.getChildren().addAll(titleLabel, grid);
        return vbox;
    }

    private GridPane createAddProductForm() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        Label productNameLabel = new Label("Product Name:");
        Label categoryLabel = new Label("Category:");
        Label priceLabel = new Label("Price:");
        Label quantityLabel = new Label("Quantity:");
        Label supplierLabel = new Label("Supplier:");

        productNameField = new TextField();
        categoryComboBox = new ComboBox<>();
        categoryComboBox.setPromptText("Pick Category");
        priceField = new TextField();
        quantityField = new TextField();
        supplierField = new TextField();
        updateButton = new Button("Update");
        deleteButton = new Button("Delete");
        buttonWrapper = new FlowPane();
        buttonWrapper.getChildren().add(updateButton);
        buttonWrapper.getChildren().add(deleteButton);
        
        buttonWrapper.setAlignment(Pos.CENTER_LEFT); // Set alignment to center
        buttonWrapper.setHgap(3); // Set horizontal gap between nodes
        buttonWrapper.setVgap(3); // Set vertical gap between nodes
        buttonWrapper.setPrefWrapLength(Region.USE_PREF_SIZE);

        grid.add(productNameLabel, 0, 0);
        grid.add(categoryLabel, 0, 1);
        grid.add(priceLabel, 0, 2);
        grid.add(quantityLabel, 0, 3);
        grid.add(supplierLabel, 0, 4);
        grid.add(productNameField, 1, 0);
        grid.add(categoryComboBox, 1, 1);
        grid.add(priceField, 1, 2);
        grid.add(quantityField, 1, 3);
        grid.add(supplierField, 1, 4);
        grid.add(buttonWrapper, 1, 5);
        
        

        

        return grid;
        
        
    }
    
    
    
    private void handleAddProductMenuItemClick() {
        AddProduct_View addProduct = new AddProduct_View(primaryStage);
        addProduct.ShowAddProductScene();
    }
    
    public Connection connection;
	public Statement statement;
	public ResultSet resultSet;
	public ResultSetMetaData resultMeta;
	public PreparedStatement preparedStatement;
	
	private ObservableList<Product> productData;
	
	

	private void deleteProduct(String productName) {
	    Connection conn = null;
	    PreparedStatement preparedStatement = null;

	    try {
	        conn = con.getConnection();
	        String deleteQuery = "DELETE FROM product WHERE productName = ?";
	        preparedStatement = conn.prepareStatement(deleteQuery);
	        preparedStatement.setString(1, productName);
	        int deletedRows = preparedStatement.executeUpdate();

	        if (deletedRows > 0) {
	            // Remove item from TableView
	            Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
	            if (selectedProduct != null) {
	                productTableView.getItems().remove(selectedProduct);
	            }

	            // Clear input fields
	            productNameField.clear();
	            priceField.clear();
	            quantityField.clear();
	            supplierField.clear();
	            
	           
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        if (preparedStatement != null) {
	            try {
	                preparedStatement.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        
	    }
	}



    
    
    
    
    

	
}




