package model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ProductList {
    private Connection conn;

    public ProductList(Connection conn) {
        this.conn = conn;
    }

    public List<Product> getProducts (String term) throws SQLException {

    }
}
