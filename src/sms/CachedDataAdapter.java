package sms;

import sms.customer.CustomerModel;
import sms.product.ProductModel;
import sms.purchase.PurchaseModel;
import sms.user.UserModel;

import java.util.*;

public class CachedDataAdapter implements IDataAccess
{
    Map<Integer, ProductModel> cachedProducts = new HashMap<>();    
    Map<Integer, CustomerModel> cachedCustomers = new HashMap<>();
    Map<Integer, UserModel> cachedUsers = new HashMap<>();
    Map<Integer, PurchaseModel> cachedPurchases = new HashMap<>();
    public IDataAccess adapter;

    public CachedDataAdapter(IDataAccess adapter) {
        this.adapter = adapter;
    }


    @Override
    public void connect(String path) { this.adapter.connect(path); }

    @Override
    public boolean disconnect() {
        return false;
    }

    @Override
    public int getErrorCode() {
        return 0;
    }

    @Override
    public String getErrorMessage() {
        return null;
    }

    @Override
    public boolean saveProduct(ProductModel product)
    {
        adapter.saveProduct(product);
        cachedProducts.put(product.mProductID, product);
        return true;
    }

    @Override
    public boolean updateProduct(ProductModel product)
    {
        /*adapter.saveProduct(product);
        cachedProducts.put(product.mProductID, product);
        return true;*/
        return false;

    }

    @Override
    public boolean updateCustomer(CustomerModel customer)
    {
        /*adapter.saveProduct(product);
        cachedProducts.put(product.mProductID, product);
        return true;*/
        return false;
    }

    @Override
    public boolean updateUser(UserModel user)
    {
        /*adapter.saveProduct(product);
        cachedProducts.put(product.mProductID, product);
        return true;*/
        return false;
    }

    @Override
    public boolean saveUser(UserModel user)
    {
        adapter.saveUser(user);
        cachedUsers.put(user.mUserID, user);
        return true;
    }

    @Override
    public boolean saveCustomer(CustomerModel customer)
    {
        adapter.saveCustomer(customer);
        cachedCustomers.put(customer.mCustomerID, customer);
        return true;
    }

    @Override
    public boolean savePurchase(PurchaseModel purchase)
    {
        adapter.savePurchase(purchase);
        cachedPurchases.put(purchase.mPurchaseID, purchase);
        return true;
    }

    @Override
    public ArrayList<ProductModel> loadAllProducts()
    {
        ArrayList<ProductModel> products = new ArrayList<ProductModel>();
        for (int i =0; i < cachedProducts.size(); i++)
        {
            products.add(cachedProducts.get(i));

        }
        return products;
    }

    @Override
    public ArrayList<CustomerModel> loadAllCustomers()
    {
        ArrayList<CustomerModel> customers = new ArrayList<CustomerModel>();
        for (int i =0; i < cachedCustomers.size(); i++)
        {
            customers.add(cachedCustomers.get(i));

        }
        return customers;
    }

    @Override
    public ArrayList<UserModel> loadAllUsers()
    {
        ArrayList<UserModel> users = new ArrayList<UserModel>();
        for (int i =0; i < cachedUsers.size(); i++)
        {
            users.add(cachedUsers.get(i));

        }
        return users;
    }

    @Override
    public ProductModel loadProduct(int id) {
        if (cachedProducts.containsKey(id))
            return cachedProducts.get(id);
        else {
            ProductModel product = adapter.loadProduct(id);
            cachedProducts.put(id, product);
            return product;
        }
    }

    @Override
    public CustomerModel loadCustomer(int id) {
        if (cachedCustomers.containsKey(id))
            return cachedCustomers.get(id);
        else {
            CustomerModel customer = adapter.loadCustomer(id);
            cachedCustomers.put(id, customer);
            return customer;
        }
    }

    @Override
    public UserModel loadUser(int id) {
        if (cachedUsers.containsKey(id))
            return cachedUsers.get(id);
        else {
            UserModel user = adapter.loadUser(id);
            cachedUsers.put(id, user);
            return user;
        }
    }


    @Override
    public PurchaseModel loadPurchase(int id) {
        if (cachedPurchases.containsKey(id))
            return cachedPurchases.get(id);
        else {
            PurchaseModel purchase = adapter.loadPurchase(id);
            cachedPurchases.put(id, purchase);
            return purchase;
        }
    }


    @Override
    public int generatePurchaseID()
    {
        int id = -1;
        try {
            ArrayList<PurchaseModel> purchases = new ArrayList<PurchaseModel>();
            for (int i = 0; i < cachedPurchases.size(); i++) {
                purchases.add(cachedPurchases.get(i));
            }
            PurchaseModel purchase = Collections.max(purchases, Comparator.comparing(p -> p.mPurchaseID));
            id = purchase.mPurchaseID + 1;
            return id;
        }
        catch (NoSuchElementException nex)
        {
            return id;
        }
    }
}
