package sms;

import sms.customer.CustomerModel;
import sms.product.ProductModel;
import sms.purchase.PurchaseModel;
import sms.user.UserModel;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IDataAccess
{
    public static final int CONNECTION_OPEN_OK = 1;
    public static final int CONNECTION_OPEN_FAILED = 2;

    public static final int PRODUCT_SAVE_OK = 101;
    public static final int PRODUCT_SAVE_FAILED = 102;
    public static final int PRODUCT_UPDATE_OK = 103;

    public static final int PRODUCT_LOAD_OK = 201;
    public static final int PRODUCT_LOAD_FAILED = 202;
    public static final int PRODUCT_LOAD_ID_NOT_FOUND = 203;

    public static final int CUSTOMER_SAVE_OK = 301;
    public static final int CUSTOMER_SAVE_FAILED = 302;
    public static final int CUSTOMER_UPDATE_OK = 303;

    public static final int CUSTOMER_LOAD_OK = 401;
    public static final int CUSTOMER_LOAD_FAILED = 402;
    public static final int CUSTOMER_LOAD_ID_NOT_FOUND = 403;

    public static final int PURCHASE_SAVE_OK = 501;
    public static final int PURCHASE_SAVE_FAILED = 502;
    public static final int PURCHASE_SAVE_DUPLICATE = 503;

    public static final int PURCHASE_LOAD_OK = 601;
    public static final int PURCHASE_LOAD_FAILED = 602;
    public static final int PURCHASE_LOAD_ID_NOT_FOUND = 603;
    public static final int PURCHASE_ID_NOT_GENERATED = 604;
    public static final int PURCHASE_ID_GENERATED = 605;

    public static final int USER_LOAD_OK = 701;
    public static final int USER_LOAD_FAILED = 702;
    public static final int USER_LOAD_ID_NOT_FOUND = 703;

    public void connect(String path);
    public boolean disconnect();
    public int getErrorCode();
    public String getErrorMessage();

    //products
    public boolean saveProduct(ProductModel product);
    public ArrayList<ProductModel> loadAllProducts();
    public ProductModel loadProduct(int id);
    public boolean updateProduct(ProductModel p) throws SQLException;

    //purchases
    public boolean savePurchase(PurchaseModel purchase);
    public int generatePurchaseID();

    //customers
    public boolean saveCustomer(CustomerModel customer);
    public ArrayList<CustomerModel> loadAllCustomers();
    public boolean updateCustomer(CustomerModel c) throws SQLException;

    //users
    public boolean saveUser(UserModel user);
    public ArrayList<UserModel> loadAllUsers();
    public boolean updateUser(UserModel u) throws SQLException;

    public CustomerModel loadCustomer(int id);

    public PurchaseModel loadPurchase(int id);

    public UserModel loadUser(int id);

}