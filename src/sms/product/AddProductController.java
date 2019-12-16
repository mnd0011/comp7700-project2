package sms.product;

import sms.storemgr.StoreManagerClient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddProductController
{
    public AddProductView view;

    public AddProductController(AddProductView view)
    {
        this.view = view;

        view.btnAdd.addActionListener(new AddButtonController());
    }

    class AddButtonController implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            try
            {
                ProductModel product = new ProductModel();
                product.mName = view.txtName.getText();
                product.mDescription = view.txtDescription.getText();
                product.mPrice = Double.parseDouble(view.txtPrice.getText());
                product.mQuantity = Integer.parseInt(view.txtQty.getText());

                if(StoreManagerClient.getInstance() != null)
                {
                    if(StoreManagerClient.getInstance().getDataAccess() != null)
                    {
                        StoreManagerClient.getInstance().getDataAccess().saveProduct(product);
                        view.resetView();
                    }
                    else
                        System.out.println("adapter is null.");
                }
                else
                    System.out.println("Store Manager instance is null.");
            }
            catch(NumberFormatException numEx)
            {
                JOptionPane.showMessageDialog(null,"Please enter a correctly formatted number.");
            }
        }
    }
}

