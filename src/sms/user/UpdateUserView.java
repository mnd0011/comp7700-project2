package sms.user;

import sms.storemgr.StoreManagerClient;

import javax.swing.*;
import java.util.ArrayList;

public class UpdateUserView {
    public JTextField txtFullName;
    public JButton btnUpdate;
    public JFrame view;
    public JTextField txtID = new JTextField();

    ArrayList<UserModel> userList = new ArrayList<>();
    public DefaultListModel userListModel = null;
    public JTextField txtUsername;
    public JTextField txtPassword;
    public JTextField txtUserType;
    public JList lstUsers;
    public JPanel update;
    private JRadioButton cashierRadioButton;
    private JRadioButton customerRadioButton;
    private JRadioButton managerRadioButton;
    private JRadioButton adminRadioButton;

    public UpdateUserView()
    {
        txtID.setVisible(false);
        view = new JFrame();
        view.setTitle("Update User");
        view.setSize(500, 401);
        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        view.getContentPane().add(update);
        view.setResizable(false);
    }

    public void run()
    {
        userList = new ArrayList<>();
        if(StoreManagerClient.getInstance() != null) {
            if (StoreManagerClient.getInstance().getDataAccess() != null) {
                userList = StoreManagerClient.getInstance().getDataAccess().loadAllUsers();
                userListModel = new DefaultListModel();
                String[] displayCustomerList = new String[userList.size()];
                for (int i = 0; i < userList.size(); i++) {
                    userListModel.addElement(userList.get(i));
                    displayCustomerList[i] = userList.get(i).mUserName;
                }
                lstUsers.setModel(userListModel);
                lstUsers.setListData(displayCustomerList);
                lstUsers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            }
        }
    }

    public void resetView()
    {
        txtFullName.setText("");
        txtPassword.setText("");
        txtUsername.setText("");
        txtUserType.setText("");
        txtID.setText("");
        run();
    }
}
