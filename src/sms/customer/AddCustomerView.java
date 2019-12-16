package sms.customer;

import javax.swing.*;

public class AddCustomerView extends JFrame
{
    public JPanel addCustomerPanel;
    public JTextField txtName;
    public JTextField txtAddress;
    public JTextField txtPhone;
    public JTextField txtEmail;
    public JButton btnAdd;

    public AddCustomerView()
    {

    }

    public void resetView()
    {
        txtName.setText("");
        txtAddress.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
    }
}
