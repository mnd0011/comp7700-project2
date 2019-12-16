package sms.storemgr;

import com.google.gson.Gson;
import sms.IDataAccess;
import sms.MessageModel;
import sms.SQLiteDataAdapter;
import sms.TXTDataAdapter;
import sms.customer.CustomerModel;
import sms.launcher.ModuleLauncherView;
import sms.product.ProductModel;
import sms.purchase.PurchaseModel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class StoreManagerServer
{
    static Gson gson = new Gson();
    static IDataAccess adapter;
    static ModuleLauncherView mView;

    static StoreManagerServer instance = null;

    public static void main(String[] args) {
        int port = 7777;
        try {
            setup();
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    Scanner clientStream = new Scanner(clientSocket.getInputStream());
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    String msg = clientStream.nextLine();
                    MessageModel request = gson.fromJson(msg, MessageModel.class);
                    String res = gson.toJson(process(request));
                    out.println(res);
                    clientSocket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static MessageModel process(MessageModel request) {
        if (request.code == MessageModel.GET_PRODUCT) {
            int id = Integer.parseInt(request.data);
            ProductModel model = adapter.loadProduct(id);
            if (model == null)
                return new MessageModel(IDataAccess.PRODUCT_LOAD_ID_NOT_FOUND, null);
            else
                return new MessageModel(IDataAccess.PRODUCT_LOAD_OK,gson.toJson(model));
        }
        if (request.code == MessageModel.PUT_PRODUCT) {
            ProductModel model = gson.fromJson(request.data, ProductModel.class);
            boolean saved = adapter.saveProduct(model);
            if (saved)
                return new MessageModel(IDataAccess.PRODUCT_SAVE_OK, null);
            else
                return new MessageModel(IDataAccess.PRODUCT_SAVE_FAILED, null);
        }

        if (request.code == MessageModel.GET_CUSTOMER) {
            int id = Integer.parseInt(request.data);
            CustomerModel model = adapter.loadCustomer(id);
            if (model == null)
                return new MessageModel(IDataAccess.CUSTOMER_LOAD_ID_NOT_FOUND, null);
            else
                return new MessageModel(IDataAccess.CUSTOMER_LOAD_OK,gson.toJson(model));
        }
        if (request.code == MessageModel.PUT_CUSTOMER) {
            CustomerModel model = gson.fromJson(request.data, CustomerModel.class);
            boolean saved = adapter.saveCustomer(model);
            if (saved)
                return new MessageModel(IDataAccess.CUSTOMER_SAVE_OK, null);
            else
                return new MessageModel(IDataAccess.CUSTOMER_SAVE_FAILED, null);
        }

        if (request.code == MessageModel.GET_PURCHASE) {
            int id = Integer.parseInt(request.data);
            PurchaseModel model = adapter.loadPurchase(id);
            if (model == null)
                return new MessageModel(IDataAccess.PURCHASE_LOAD_ID_NOT_FOUND, null);
            else
                return new MessageModel(IDataAccess.PURCHASE_LOAD_OK,gson.toJson(model));
        }

        if (request.code == MessageModel.PUT_PURCHASE) {
            PurchaseModel model = gson.fromJson(request.data, PurchaseModel.class);
            boolean saved = adapter.savePurchase(model);
            if (saved)
                return new MessageModel(IDataAccess.PURCHASE_SAVE_OK, null);
            else
                return new MessageModel(IDataAccess.PURCHASE_SAVE_FAILED, null);
        }

        if (request.code == MessageModel.GENERATE_PURCHASE_ID) {
            int id = adapter.generatePurchaseID();
            PurchaseModel model = new PurchaseModel();
            model.mProductID = id;
            if (id == -1)
                return new MessageModel(IDataAccess.PURCHASE_ID_NOT_GENERATED, null);
            else {
                return new MessageModel(IDataAccess.PURCHASE_ID_GENERATED, gson.toJson(model));
            }
        }


        return null;
    }

    public static StoreManagerServer getInstance()
    {
        if (instance == null) {
            instance = new StoreManagerServer();
            instance.setup();
        }
        return instance;
    }

    public IDataAccess getDataAccess() { return adapter; }

    private static void setup()
    {
        JOptionPane.showMessageDialog(null,"Hello! Please select a database/text file to proceed",
                "Store Management System",JOptionPane.PLAIN_MESSAGE);

        String connectionString = getDbPath();

        if(!connectionString.isEmpty())
        {
            try
            {
                if(connectionString.contains("db"))
                {
                    adapter = new SQLiteDataAdapter();
                    adapter.connect(connectionString);
                }
                else if (connectionString.contains("txt"))
                {
                    adapter = new TXTDataAdapter();
                    JOptionPane.showMessageDialog(null,"Please ensure that your text file is named correctly ('Customers' or 'Products').",
                            "Store Management System",JOptionPane.WARNING_MESSAGE);
                    adapter.connect(connectionString);
                }
            }
            catch (Exception ex)
            {
                adapter = null;
            };
        }
        else
            JOptionPane.showMessageDialog(null,"No database/text file selected.", "Store Management System",JOptionPane.PLAIN_MESSAGE);
    }

    public void run()
    {
        if(mView != null) mView.mainFrame.setVisible(true);
    }

    private static String getDbPath()
    {
        JFileChooser dbChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("databases, text files", "db", "txt");
        dbChooser.setFileFilter(filter);
        int returnVal = dbChooser.showOpenDialog(null);
        String connectionString = "";

        if(returnVal == JFileChooser.APPROVE_OPTION)
            connectionString = dbChooser.getSelectedFile().getAbsolutePath();

        return connectionString;
    }

}