package sms.user;

import sms.user.UpdateUserView;
import sms.storemgr.StoreManagerClient;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class UpdateUserController
{
    public UpdateUserView view;
    public UserModel user = null;

    public UpdateUserController(UpdateUserView view)
    {
        this.view = view;

        view.btnUpdate.addActionListener(new UpdateButtonController());
    }

    class UpdateButtonController implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
          JOptionPane.showMessageDialog(null,"User Type changed successfully!");
        }
    }

    public UserModel getCustomer(String selectedUserName)
    {
        for (int i =0; i < view.userList.size(); i++)
        {
            if(view.userList.get(i).mUserName == selectedUserName)
            {
                return view.userList.get(i);
            }
        }
        return null;
    }
}

