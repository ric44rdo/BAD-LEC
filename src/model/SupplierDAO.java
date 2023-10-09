package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO {


    private  Connection conn;
    private  Database db;

    public SupplierDAO() {
        //Open connection to DB
        db = new Database();
        db.connect();
        conn = db.getConnection();
    }

    //Generate List from Database
    public List<Supplier> getSupplier() {
        List<Supplier> suppliers = new ArrayList<>();
        String query = "SELECT * FROM supplier";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                suppliers.add(new Supplier(rs.getInt("id"),
                        rs.getString("supplierName"),
                        rs.getString("supplierAddress"),
                        rs.getString("supplierPhone"),
                        rs.getString("payment_terms"),
                        rs.getInt("categoryID")
                ));
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suppliers;
    }


}
