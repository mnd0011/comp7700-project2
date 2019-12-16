package sms.customer;

import sms.storemgr.StoreManagerClient;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class UpdateCustomerController
{
    public UpdateCustomerView view;
    public CustomerModel customer = null;

    public UpdateCustomerController(UpdateCustomerView view)
    {
        this.view = view;

        view.btnUpdate.addActionListener(new UpdateButtonController());

        ListSelectionListener customerListSelectionListener = listSelectionEvent -> {
            if(listSelectionEvent.getValueIsAdjusting())
            {
                JList list = (JList) listSelectionEvent.getSource();
                Object selectedValue = list.getSelectedValue();
                customer = getCustomer(selectedValue.toString());
                if (customer != null) {
                    //view.resetView();
                    view.txtID.setText(String.valueOf(customer.mCustomerID));
                    view.txtName.setText(customer.mName);
                    view.txtAddress.setText(customer.mAddress);
                    view.txtEmail.setText(customer.mEmail);
                    view.txtPhone.setText(customer.mPhone);
                }
            }
        };
        view.lstCustomers.addListSelectionListener(customerListSelectionListener);
    }

    class UpdateButtonController implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            try
            {
                CustomerModel customer = new CustomerModel();
                customer.mCustomerID = Integer.parseInt(view.txtID.getText());
                customer.mName = view.txtName.getText();
                customer.mAddress = view.txtAddress.getText();
                customer.mEmail = view.txtEmail.getText();
                customer.mPhone = view.txtPhone.getText();

                if(StoreManagerClient.getInstance() != null)
                {
                    if(StoreManagerClient.getInstance().getDataAccess() != null)
                    {
                        StoreManagerClient.getInstance().getDataAccess().updateCustomer(customer);
                        view.resetView();
                    }
                    else
                        System.out.println("adapter is null.");
                }
                else
                    System.out.println("Store Manager instance is null.");
            }
            catch(NumberFormatException | SQLException numEx)
            {
                JOptionPane.showMessageDialog(null,"Please enter a correctly formatted number.");
            }
        }
    }

    public CustomerModel getCustomer(String selectedCustomerName)
    {
        for (int i =0; i < view.customerList.size(); i++)
        {
            if(view.customerList.get(i).mName == selectedCustomerName)
            {
                return view.customerList.get(i);
            }
        }
        return null;
    }
}

