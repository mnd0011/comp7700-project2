package sms.user;

import sms.storemgr.StoreManagerClient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddUserController
{
    public AddUserView view;
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PHONE_PATTERN = "[0-9]{10}";

    public AddUserController(AddUserView view)
    {
        this.view = view;
        view.btnAdd.addActionListener(new AddButtonController());
    }

    class AddButtonController implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            JOptionPane.showMessageDialog(null,"New user added!!");
        }
    }
}

