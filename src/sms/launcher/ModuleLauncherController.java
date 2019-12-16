package sms.launcher;

import sms.customer.AddCustomerController;
import sms.customer.AddCustomerView;
import sms.customer.UpdateCustomerController;
import sms.customer.UpdateCustomerView;
import sms.product.AddProductController;
import sms.product.AddProductView;
import sms.product.UpdateProductController;
import sms.product.UpdateProductView;
import sms.purchase.AddPurchaseController;
import sms.purchase.AddPurchaseView;
import sms.storemgr.StoreManagerClient;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModuleLauncherController
{
    public ModuleLauncherView view;
    String connectionString = "";

    public  ModuleLauncherController(ModuleLauncherView view)
    {
        this.view = view;
        view.addProductAccess.addActionListener(new AddProductAccess());
        view.addPurchaseAccess.addActionListener(new AddPurchaseAccess());
        view.addCustomerAccess.addActionListener(new AddCustomerAccess());
        view.updateProductAccess.addActionListener(new UpdateProductAccess());
        view.updatePurchaseAccess.addActionListener(new UpdatePurchaseAccess());
        view.updateCustomerAccess.addActionListener(new UpdateCustomerAccess());
        SetAdapter sa = new SetAdapter();
        view.localMode.addActionListener(sa);
        view.remoteMode.addActionListener(sa);
    }

    class SetAdapter implements ActionListener
    {
        @Override
        public void actionPerformed (ActionEvent actionEvent)
        {
           setAdapter();
        }
    }
    class AddProductAccess implements ActionListener
    {
        @Override
        public void actionPerformed (ActionEvent actionEvent)
        {
            if (connectionString.isEmpty())
                    if(view.localMode.isSelected()) setAdapter();

            AddProductView addProductView = new AddProductView();
            AddProductController addProductController = new AddProductController(addProductView);
            JFrame frame = new JFrame("AddProductView");
            frame.setContentPane(addProductView.addProductPanel);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setResizable(false);
            frame.pack();
            frame.setVisible(true);
        }
    }

    class UpdateProductAccess implements ActionListener
    {
        @Override
        public void actionPerformed (ActionEvent actionEvent)
        {
            if (connectionString.isEmpty())
                if(view.localMode.isSelected()) setAdapter();

            UpdateProductView updateProductView = new UpdateProductView();
            UpdateProductController updateProductController = new UpdateProductController(updateProductView);
            updateProductView.run();
            updateProductView.view.setVisible(true);
        }
    }

    class AddPurchaseAccess implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            if (connectionString.isEmpty() && view.localMode.isSelected()) setAdapter();

            AddPurchaseView addPurchaseView = new AddPurchaseView();
            AddPurchaseController addPurchaseController = new AddPurchaseController(addPurchaseView);
            addPurchaseView.run();
            addPurchaseView.view.setVisible(true);
            JFrame frame = new JFrame("AddPurchaseView");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setResizable(false);
            frame.pack();

        }
    }

    class UpdatePurchaseAccess implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            if (connectionString.isEmpty() && view.localMode.isSelected()) setAdapter();

            AddPurchaseView addPurchaseView = new AddPurchaseView();
            addPurchaseView.run();
            addPurchaseView.view.setVisible(true);
            JFrame frame = new JFrame("AddPurchaseView");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setResizable(false);
            frame.pack();

        }
    }

    class AddCustomerAccess implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            if (connectionString.isEmpty() && view.localMode.isSelected()) setAdapter();

            AddCustomerView addCustomerView = new AddCustomerView();
            //AddCustomerController addCustomerController = new AddCustomerController(addCustomerView);
            JFrame frame = new JFrame("AddCustomerView");
            frame.setContentPane(addCustomerView.addCustomerPanel);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setResizable(false);
            frame.pack();
            frame.setVisible(true);
        }
    }

    class UpdateCustomerAccess implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            if (connectionString.isEmpty() && view.localMode.isSelected()) setAdapter();

            UpdateCustomerView updateCustomerView = new UpdateCustomerView();
            UpdateCustomerController updateCustomerController = new UpdateCustomerController(updateCustomerView);
            updateCustomerView.run();
            updateCustomerView.view.setVisible(true);
        }
    }

    public void setAdapter(){
        if (view.localMode.isSelected()) {
            JOptionPane.showMessageDialog(null, "Hello! Please select a database to proceed",
                    "Store Management System - Local Mode", JOptionPane.PLAIN_MESSAGE);

            connectionString = getDbPath();

            if (!connectionString.isEmpty()) {
                try {
                    if (connectionString.contains("db"))
                    {
                        if(StoreManagerClient.getInstance() != null)
                            StoreManagerClient.getInstance().setAdapter();
                        else
                            System.out.println("Store Manager instance is null.");
                        if(StoreManagerClient.getInstance().getDataAccess() != null)
                            StoreManagerClient.getInstance().getDataAccess().connect(connectionString);
                        else
                            System.out.println("adapter is null.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Unable to connect", "Store Management System - Local Mode", JOptionPane.PLAIN_MESSAGE);
                }
            } else
                JOptionPane.showMessageDialog(null, "No database selected.", "Store Management System - Local Mode", JOptionPane.PLAIN_MESSAGE);
        } else if (view.remoteMode.isSelected())
            if(StoreManagerClient.getInstance() != null)
                StoreManagerClient.getInstance().setAdapter("localhost", 7777);
            else
                System.out.println("adapter is null.");
    }

    private static String getDbPath()
    {
        JFileChooser dbChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("databases, text files", "db", "txt");
        dbChooser.setFileFilter(filter);
        int returnVal = dbChooser.showOpenDialog(null);
        String connectionString = "";

        if(returnVal == JFileChooser.APPROVE_OPTION)
            connectionString = dbChooser.getSelectedFile().getAbsolutePath();

        return connectionString;
    }
}
