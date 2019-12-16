package sms.customer;

import sms.storemgr.StoreManagerClient;

import javax.swing.*;
import java.util.ArrayList;

public class UpdateCustomerView {
    public JTextField txtName;
    public JButton btnUpdate;
    public JFrame view;
    public JTextField txtID = new JTextField();

    ArrayList<CustomerModel> customerList = new ArrayList<>();
    public DefaultListModel customerListModel = null;
    public JTextField txtEmail;
    public JTextField txtAddress;
    public JTextField txtPhone;
    public JList lstCustomers;
    public JPanel update;

    public UpdateCustomerView()
    {
        txtID.setVisible(false);
        view = new JFrame();
        view.setTitle("Update Customer");
        view.setSize(500, 401);
        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        view.getContentPane().add(update);
        view.setResizable(false);
    }

    public void run()
    {
        customerList = new ArrayList<>();
        if(StoreManagerClient.getInstance() != null) {
            if (StoreManagerClient.getInstance().getDataAccess() != null) {
                customerList = StoreManagerClient.getInstance().getDataAccess().loadAllCustomers();
                customerListModel = new DefaultListModel();
                String[] displayCustomerList = new String[customerList.size()];
                for (int i = 0; i < customerList.size(); i++) {
                    customerListModel.addElement(customerList.get(i));
                    displayCustomerList[i] = customerList.get(i).mName;
                }
                lstCustomers.setModel(customerListModel);
                lstCustomers.setListData(displayCustomerList);
                lstCustomers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            }
        }
    }

    public void resetView()
    {
        txtName.setText("");
        txtAddress.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        txtID.setText("");
        run();
    }
}
