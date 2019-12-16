package sms.user;

import javax.swing.*;

public class AddUserView extends JFrame
{
    public JPanel addUserPanel;
    public JTextField txtFullName;
    public JTextField txtPassword;
    public JTextField txtUsername;
    public JTextField txtUserType;
    public JButton btnAdd;
    private JRadioButton cashierRadioButton;
    private JRadioButton managerRadioButton;
    private JRadioButton customerRadioButton;
    private JRadioButton adminRadioButton;

    public AddUserView()
    {

    }

    public void resetView()
    {
        txtFullName.setText("");
        txtPassword.setText("");
        txtUsername.setText("");
        txtUserType.setText("");
    }
}
