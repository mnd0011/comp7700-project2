package sms.customer;

import sms.storemgr.StoreManagerClient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddCustomerController
{
    public AddCustomerView view;
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PHONE_PATTERN = "[0-9]{10}";

    public AddCustomerController(AddCustomerView view)
    {
        this.view = view;
        view.btnAdd.addActionListener(new AddButtonController());
    }

    class AddButtonController implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            CustomerModel customer = new CustomerModel();
            customer.mName = view.txtName.getText();
            customer.mAddress = view.txtAddress.getText();
            customer.mPhone = (view.txtPhone.getText().matches(PHONE_PATTERN) == true) ? view.txtPhone.getText() : "";
            customer.mEmail = (view.txtEmail.getText().matches(EMAIL_PATTERN) == true) ? view.txtEmail.getText() : "";

            if(customer.mPhone != "" && customer.mEmail != "")
            {
                if (StoreManagerClient.getInstance() != null)
                {
                    if (StoreManagerClient.getInstance().getDataAccess() != null)
                    {
                        StoreManagerClient.getInstance().getDataAccess().saveCustomer(customer);
                        view.resetView();
                    }
                    else
                        System.out.println("adapter is null.");
                }
                else
                    System.out.println("Store Manager instance is null.");
            }
            else
                JOptionPane.showMessageDialog(null,"Please check the formatting of the email or phone number.");
        }
    }
}

