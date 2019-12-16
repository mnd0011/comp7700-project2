package sms.user_credentials;

import sms.customer.AddCustomerController;
import sms.customer.AddCustomerView;
import sms.launcher.ModuleLauncherController;
import sms.launcher.ModuleLauncherView;
import sms.product.AddProductController;
import sms.product.AddProductView;
import sms.purchase.AddPurchaseController;
import sms.purchase.AddPurchaseView;
import sms.user.AddUserController;
import sms.user.AddUserView;
import sms.user.UpdateUserController;
import sms.user.UpdateUserView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserCredentialsController
{
    public UserCredentialsView view;
    public ModuleLauncherView mView;
    public ModuleLauncherController mController;

    public UserCredentialsController(UserCredentialsView view)
    {
        this.view = view;
        //this.view.lblModule.setText(label);

        view.btnLogin.addActionListener(new LoginButtonController());
        view.btnAdd.addActionListener(new AddUserButtonController());
        view.btnUpdate.addActionListener(new UpdateUserButtonController());
    }

    class LoginButtonController implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            JFrame frame = null;
            mView = new ModuleLauncherView();
            mController = new ModuleLauncherController(mView);
            //UpdateUserView updateUserView = new UpdateUserView();
            //UpdateUserController updateUserController = new UpdateUserController(updateUserView);
            frame = new JFrame("UpdateUserView");
            frame.setContentPane(mView.mainFrame);
            //JOptionPane.showMessageDialog(null, "Requires higher permission level.", "Store Management System", JOptionPane.PLAIN_MESSAGE);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setResizable(false);
            frame.pack();
            frame.setVisible(true);
        }
    }

    class AddUserButtonController implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            JFrame frame = null;
            AddUserView addUserView = new AddUserView();
            addUserView.txtFullName.setText("");
            addUserView.txtUsername.setText("");
            addUserView.txtPassword.setText("");
            AddUserController addUserController = new AddUserController(addUserView);
            frame = new JFrame("AddUserView");
            frame.setContentPane(addUserView.addUserPanel);
            //JOptionPane.showMessageDialog(null, "Requires higher permission level.", "Store Management System", JOptionPane.PLAIN_MESSAGE);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setResizable(false);
            frame.pack();
            frame.setVisible(true);
        }
    }

    class UpdateUserButtonController implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            JFrame frame = null;
            UpdateUserView updateUserView = new UpdateUserView();
            updateUserView.txtFullName.setText("Eve Garden");
            updateUserView.txtUsername.setText("eve");
            updateUserView.txtPassword.setText("******");
            UpdateUserController updateUserController = new UpdateUserController(updateUserView);
            frame = new JFrame("UpdateUserView");
            frame.setContentPane(updateUserView.update);
            //JOptionPane.showMessageDialog(null, "Login Successful", "Store Management System", JOptionPane.PLAIN_MESSAGE);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setResizable(false);
            frame.pack();
            frame.setVisible(true);
        }
    }

    public boolean validateCredentials()
    {
        boolean isValid = true;
        return isValid;
    }


}
