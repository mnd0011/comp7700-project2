package sms;

import sms.customer.CustomerModel;
import sms.product.ProductModel;
import sms.purchase.PurchaseModel;
import sms.user.UserModel;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class TXTDataAdapter implements IDataAccess
{
    int errorCode = 0;
    Map<Integer, ProductModel> products = new HashMap<>();
    Map<Integer, UserModel> users = new HashMap<>();
    Map<Integer, CustomerModel> customers = new HashMap<>();
    Map<Integer, PurchaseModel> purchases = new HashMap<>();

    public void connect(String path)
    {
        try
        {
            Scanner scanner = new Scanner(new FileReader(new File(path)));
            if(path.contains("Products"))
            {
                while (scanner.hasNext())
                {
                    ProductModel product = new ProductModel();
                    product.mProductID = scanner.nextInt();
                    scanner.nextLine();
                    product.mName = scanner.nextLine();
                    product.mDescription = scanner.nextLine();
                    product.mPrice = scanner.nextDouble();
                    product.mQuantity = scanner.nextInt();

                    products.put(product.mProductID, product);
                    JOptionPane.showMessageDialog(null, "Product added!");
                }
            }
            else if(path.contains("Customers"))
            {
                while (scanner.hasNext())
                {
                    CustomerModel customer = new CustomerModel();
                    customer.mCustomerID = scanner.nextInt();
                    scanner.nextLine();
                    customer.mName = scanner.nextLine();
                    customer.mAddress = scanner.nextLine();
                    customer.mPhone = scanner.nextLine();
                    customer.mEmail = scanner.nextLine();

                    customers.put(customer.mCustomerID, customer);
                    JOptionPane.showMessageDialog(null, "Customer added!");
                }
            }
            else if(path.contains("Purchases"))
            {
                while (scanner.hasNext())
                {
                    PurchaseModel purchase = new PurchaseModel();
                    purchase.mDate = scanner.nextLine();
                    purchase.mPurchaseID = scanner.nextInt();
                    purchase.mCustomerID = scanner.nextInt();
                    purchase.mProductID = scanner.nextInt();
                    scanner.nextLine();
                    purchase.mPrice = scanner.nextDouble();
                    purchase.mQuantity = scanner.nextInt();
                    purchase.mCost = scanner.nextDouble();
                    purchase.mTax = scanner.nextDouble();
                    purchase.mTotalCost = scanner.nextDouble();

                    purchases.put(purchase.mPurchaseID, purchase);
                    JOptionPane.showMessageDialog(null, "Purchase added!");
                }
            }
            scanner.close();
        } catch (InputMismatchException e) {
            JOptionPane.showMessageDialog(null, "Error reading the text file." + e.getMessage());
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error reading the text file." + e.getMessage());
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

    @Override
    public boolean saveProduct(ProductModel product) { return false; }

    @Override
    public boolean updateProduct(ProductModel product) { return false; }

    @Override
    public boolean updateCustomer(CustomerModel customer) { return false; }

    @Override
    public boolean saveCustomer(CustomerModel customer) { return false; }

    @Override
    public boolean updateUser(UserModel user) { return false; }

    @Override
    public boolean saveUser(UserModel user) { return false; }

    @Override
    public boolean savePurchase(PurchaseModel purchase) { return false; }

    @Override
    public ArrayList<ProductModel> loadAllProducts(){
        return null;
    }

    @Override
    public ArrayList<CustomerModel> loadAllCustomers(){
        return null;
    }

    @Override
    public ArrayList<UserModel> loadAllUsers(){
        return null;
    }

    @Override
    public ProductModel loadProduct(int id) {return null;}

    @Override
    public PurchaseModel loadPurchase(int id) {return null;}

    @Override
    public CustomerModel loadCustomer(int id) {return null;}

    @Override
    public UserModel loadUser(int id) {return null;}

    @Override
    public int generatePurchaseID(){ return -1; }
}