package sms;

import sms.customer.CustomerModel;
import sms.product.ProductModel;
import sms.purchase.PurchaseModel;
import sms.user.UserModel;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class SQLiteDataAdapter implements IDataAccess
{
    Connection conn = null;
    int errorCode = 0;

    public void connect(String connectionString)
    {
        try
        {

            String url = "jdbc:sqlite:" + connectionString;
            conn = DriverManager.getConnection(url);

            JOptionPane.showMessageDialog(null,"Connection to SQLite has been established.",
                    "Store Management System",JOptionPane.PLAIN_MESSAGE);
        } catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }

    @Override
    public boolean disconnect() {
        return true;
    }

    @Override
    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMessage() {
        switch (errorCode) {
            case CONNECTION_OPEN_FAILED:
                return "Connection is not opened!";
            case PRODUCT_LOAD_FAILED:
                return "Cannot load the product!";
        }
        ;
        return "OK";
    }

    public int generatePurchaseID()
    {
        int id = -1;

        try
        {
            String sql = "SELECT MAX(PurchaseID) FROM Purchases";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next())
            {
                id = rs.getInt("MAX(PurchaseID)") + 1;
            }
            return id;
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "Error retrieving id." + e.getMessage());
            return id;
        }
    }

    public boolean saveProduct(ProductModel product)
    {
        try
        {
            String sql = "INSERT INTO Products ('Name','Description', 'Price', 'Quantity')" +
                            "VALUES ('"+product.mName+"','"+product.mDescription+"','"+product.mPrice+"','"+product.mQuantity+"')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            JOptionPane.showMessageDialog(null,"Product Added!","Store Management System",JOptionPane.PLAIN_MESSAGE);
            return true;
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null,e.getMessage(),"Store Management System",JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean savePurchase(PurchaseModel purchase)
    {
        try
        {
            String sql = "INSERT INTO Purchases ('Date','PurchaseID','CustomerID', 'ProductID', 'Price', 'Quantity'," +
                    "'Cost','Tax','TotalCost')" +
                    "VALUES ('"+purchase.mDate+"','"+purchase.mPurchaseID+"','"+purchase.mCustomerID+"'," +
                    "'"+purchase.mProductID+"','"+purchase.mPrice+"','"+purchase.mQuantity+"','"+purchase.mCost+"'," +
                    "'"+purchase.mTax+"','"+purchase.mTotalCost+"')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            JOptionPane.showMessageDialog(null,"Purchase Added!","Store Management System",JOptionPane.PLAIN_MESSAGE);
            return true;
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null,e.getMessage(),"Store Management System",JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean saveCustomer(CustomerModel customer)
    {
        try
        {
            String sql = "INSERT INTO Customers ('Name','Address', 'Email', 'Phone')" +
                    "VALUES ('" + customer.mName + "','" + customer.mAddress + "','" + customer.mEmail + "','" + customer.mPhone + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Customer Added!", "Store Management System", JOptionPane.PLAIN_MESSAGE);
            return true;
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Store Management System", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean saveUser(UserModel user)
    {
        try
        {
            String sql = "INSERT INTO Users ('Username','Password', 'Fullname', 'Usertype')" +
                    "VALUES ('" + user.mUserName + "','" + user.mPassword + "','" + user.mFullName + "','" + user.mUserType + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "User Added!", "Store Management System", JOptionPane.PLAIN_MESSAGE);
            return true;
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Store Management System", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean updateCustomer(CustomerModel c) throws SQLException {
        Statement stmt = null;
        try {
            String sql = "UPDATE Customers " +
                    "SET Name = '"+c.mName+"',Address = '"+c.mAddress+"'," +
                    "Email = '"+c.mEmail+"', Phone = '"+c.mPhone+"'" +
                    "WHERE CustomerId = '" + c.mCustomerID + "'";
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            //conn.close();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            errorCode = CUSTOMER_LOAD_FAILED;
            return false;
        }
        finally
        {
            if(stmt != null) stmt.close();
            return false;
        }
    }

    public boolean updateUser(UserModel c) throws SQLException {
        Statement stmt = null;
        try {
            String sql = "UPDATE Users " +
                    "SET Username = '"+c.mUserName+"',Password = '"+c.mPassword+"'," +
                    "Fullname = '"+c.mFullName+"', Usertype = '"+c.mUserType+"'" +
                    "WHERE UserID = '" + c.mUserID + "'";
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            //conn.close();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            errorCode = USER_LOAD_FAILED;
            return false;
        }
        finally
        {
            if(stmt != null) stmt.close();
            return false;
        }
    }

    public ArrayList<ProductModel> loadAllProducts()
    {
        try
        {
            ArrayList<ProductModel> products = new ArrayList<ProductModel>();

            String sql = "SELECT * FROM Products";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                int productID = rs.getInt("ProductID");
                String name = rs.getString("Name");
                String description = rs.getString("Description");
                double price = rs.getDouble("Price"); //type is real in db
                int qty = rs.getInt("Quantity");
                products.add(new ProductModel(productID,name,description,price,qty));
            }
            return products;
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "Error loading Products." + e.getMessage());
            return null;
        }
    }

    public ArrayList<CustomerModel> loadAllCustomers()
    {
        try
        {
            ArrayList<CustomerModel> customers = new ArrayList<CustomerModel>();

            String sql = "SELECT * FROM Customers";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                int customerID = rs.getInt("CustomerID");
                String name = rs.getString("Name");
                String phone = rs.getString("Phone");
                String address = rs.getString("Address");
                String email = rs.getString("Email");
                customers.add(new CustomerModel(customerID,name,phone,address,email));
            }
            return customers;
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "Error loading Customers." + e.getMessage());
            return null;
        }
    }

    public ArrayList<UserModel> loadAllUsers() {
        try {
            ArrayList<UserModel> users = new ArrayList<UserModel>();

            String sql = "SELECT * FROM Users";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int userID = rs.getInt("UserID");
                String fullName = rs.getString("Fullname");
                String password = rs.getString("Password");
                String userName = rs.getString("Username");
                int userType = rs.getInt("Usertype");
                users.add(new UserModel(userID, fullName, password, userName, userType));
            }
            return users;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading Users." + e.getMessage());
            return null;
        }
    }
    public ProductModel loadProduct(int productID) {
        ProductModel product = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Products WHERE ProductId = " + productID);
            if (rs.next()) {
                product = new ProductModel();
                product.mProductID = rs.getInt("ProductId");
                product.mName = rs.getString("Name");
                product.mPrice = rs.getDouble("Price");
                product.mQuantity = rs.getInt("Quantity");
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            errorCode = PRODUCT_LOAD_FAILED;
        }
        return product;
    }

    public boolean updateProduct(ProductModel p) throws SQLException {
        Statement stmt = null;
        try {
            String sql = "UPDATE Products " +
                    "SET Name = '"+p.mName+"',Description = '"+p.mDescription+"'," +
                    "Price = '"+p.mPrice+"', Quantity = '"+p.mQuantity+"'" +
                    "WHERE ProductId = '" + p.mProductID + "'";
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            //conn.close();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            errorCode = PRODUCT_LOAD_FAILED;
            return false;
        }
        finally
        {
            if(stmt != null) stmt.close();
            return false;
        }
    }

    public CustomerModel loadCustomer(int id)
    {
        CustomerModel customer = new CustomerModel();
        return customer;
    }

    public PurchaseModel loadPurchase(int id)
    {
        PurchaseModel purchase = new PurchaseModel();
        return purchase;
    }

    public UserModel loadUser(int id)
    {
        UserModel user = new UserModel();
        return user;
    }
}
