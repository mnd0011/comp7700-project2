package sms.purchase;

import sms.customer.CustomerModel;
import sms.product.ProductModel;
import sms.storemgr.StoreManagerClient;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;

public class AddPurchaseView
{
    public JFrame view;

    public GridBagLayout grid = new GridBagLayout();
    public GridBagConstraints gridBagConstraints = new GridBagConstraints();

    public JLabel labPurchaseDate = new JLabel("Purchase Date: ");
    public JLabel labPurchaseID = new JLabel("Purchase ID: ");
    public JLabel labCustomerName = new JLabel("Customer Name: ");
    public JLabel labProductName = new JLabel("Product Name: ");
    public JLabel labProductPrice = new JLabel("Product Price: ");
    public JLabel labProductQuantity = new JLabel("Purchase Quantity: ");

    public JList lstProducts = new JList();
    public DefaultListModel productListModel = null;
    public JScrollPane scrollProductList = new JScrollPane(lstProducts);

    public JList lstCustomers = new JList();
    public DefaultListModel customerListModel = null;
    public JScrollPane scrollCustomerList = new JScrollPane(lstCustomers);

    public JTextField txtPurchaseID = new JTextField(10);
    public JTextField txtPurchaseDate = new JTextField(10);
    public JTextField txtPrice = new JTextField(20);
    public JTextField txtQuantity = new JTextField(20);
    public JTextField txtCost = new JTextField(20);
    public JTextField txtTax = new JTextField(20);
    public JTextField txtTotalCost = new JTextField(20);

    public JButton btnAdd = new JButton("Add");
    public JButton btnCancel = new JButton("Cancel");

    PurchaseModel purchase;

    ArrayList<ProductModel> productList = new ArrayList<>();
    ArrayList<CustomerModel> customerList = new ArrayList<>();

    public AddPurchaseView()
    {
        view = new JFrame();
        view.setTitle("Add Purchase");
        view.setSize(700, 700);
        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel pane = new JPanel();

        pane.setLayout(grid);
        view.getContentPane().add(pane);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        labPurchaseDate.setMaximumSize(new Dimension(50,20));
        pane.add(labPurchaseDate,gridBagConstraints);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        txtPurchaseDate.setMaximumSize(new Dimension(100,20));
        pane.add(txtPurchaseDate,gridBagConstraints);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        pane.add(labPurchaseID,gridBagConstraints);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        pane.add(txtPurchaseID,gridBagConstraints);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        pane.add(labCustomerName,gridBagConstraints);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        pane.add(scrollCustomerList,gridBagConstraints);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        pane.add(labProductName,gridBagConstraints);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        pane.add(scrollProductList,gridBagConstraints);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        pane.add(labProductPrice,gridBagConstraints);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        pane.add(txtPrice,gridBagConstraints);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        pane.add(labProductQuantity,gridBagConstraints);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        pane.add(txtQuantity,gridBagConstraints);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        pane.add(new JLabel("Cost: "),gridBagConstraints);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        pane.add(txtCost,gridBagConstraints);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        pane.add(new JLabel("Tax: "),gridBagConstraints);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        pane.add(txtTax,gridBagConstraints);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        pane.add(new JLabel("Total Cost: "),gridBagConstraints);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        pane.add(txtTotalCost, gridBagConstraints);

        pane = new JPanel();
        pane.setLayout(new FlowLayout());
        pane.add(btnAdd); pane.add(btnCancel);
        view.getContentPane().add(pane);


    }

    public void run()
    {
        purchase = new PurchaseModel();
        productList = new ArrayList<>();
        customerList = new ArrayList<>();
        String purchaseID = "0";

        try
        {
            if(StoreManagerClient.getInstance() != null)
            {
                if(StoreManagerClient.getInstance().getDataAccess() != null)
                {
                    int id = StoreManagerClient.getInstance().getDataAccess().generatePurchaseID();
                    if (id != -1) purchaseID = String.valueOf(id);

                    productList = StoreManagerClient.getInstance().getDataAccess().loadAllProducts();
                    productListModel = new DefaultListModel();
                    String[] displayProductList = new String[productList.size()];
                    for (int i =0; i < productList.size(); i++)
                    {
                        productListModel.addElement(productList.get(i));
                        displayProductList[i] = productList.get(i).mName;
                    }
                    lstProducts.setModel(productListModel);
                    lstProducts.setListData(displayProductList);
                    lstProducts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

                    customerList = StoreManagerClient.getInstance().getDataAccess().loadAllCustomers();
                    customerListModel = new DefaultListModel();
                    String[] displayCustomerList = new String[customerList.size()];
                    for (int i =0; i < customerList.size(); i++)
                    {
                        customerListModel.addElement(customerList.get(i));
                        displayCustomerList[i] = customerList.get(i).mName;
                    }
                    lstCustomers.setModel(customerListModel);
                    lstCustomers.setListData(displayCustomerList);
                    lstCustomers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                }
                else
                     System.out.println("adapter is null.");
            }
            else
                System.out.println("Store Manager instance is null.");
        }
        catch(NumberFormatException numEx)
        {
            JOptionPane.showMessageDialog(null,"Please enter a correctly formatted number.");
        }

        txtPurchaseID.setText(purchaseID);
        txtPurchaseID.setEditable(false);
        txtPurchaseDate.setText(Calendar.getInstance().getTime().toString());
        txtPrice.setEditable(false);
        txtCost.setEditable(false);
        txtTax.setEditable(false);
        txtTotalCost.setEditable(false);
        btnAdd.setEnabled(false);
    }

    public void resetView()
    {
        int id = StoreManagerClient.getInstance().getDataAccess().generatePurchaseID();
        txtPurchaseID.setText(String.valueOf(id));
        txtPurchaseDate.setText(Calendar.getInstance().getTime().toString());
        txtPrice.setText("");
        txtQuantity.setText("");
        txtCost.setText("");
        txtTax.setText("");
        txtTotalCost.setText("");
    }
}
