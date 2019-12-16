package sms.product;

import sms.storemgr.StoreManagerClient;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class UpdateProductController
{
    public UpdateProductView view;
    public ProductModel product = null;

    public UpdateProductController(UpdateProductView view)
    {
        this.view = view;

        view.btnUpdate.addActionListener(new UpdateButtonController());

        ListSelectionListener productListSelectionListener = listSelectionEvent -> {
            if(listSelectionEvent.getValueIsAdjusting())
            {
                JList list = (JList) listSelectionEvent.getSource();
                Object selectedValue = list.getSelectedValue();
                product = getProduct(selectedValue.toString());
                if (product != null) {
                    //view.resetView();
                    view.txtID.setText(String.valueOf(product.mProductID));
                    view.txtName.setText(String.valueOf(product.mName));
                    view.txtPrice.setText(String.valueOf(product.mPrice));
                    view.txtDescription.setText(product.mDescription);
                    view.txtPrice.setText(String.valueOf(product.mPrice));
                    view.txtQty.setText(String.valueOf(product.mQuantity));
                }
            }
        };
        view.lstProducts.addListSelectionListener(productListSelectionListener);
    }

    class UpdateButtonController implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            try
            {
                ProductModel product = new ProductModel();
                product.mProductID = Integer.parseInt(view.txtID.getText());
                product.mName = view.txtName.getText();
                product.mDescription = view.txtDescription.getText();
                product.mPrice = Double.parseDouble(view.txtPrice.getText());
                product.mQuantity = Integer.parseInt(view.txtQty.getText());

                if(StoreManagerClient.getInstance() != null)
                {
                    if(StoreManagerClient.getInstance().getDataAccess() != null)
                    {
                        StoreManagerClient.getInstance().getDataAccess().updateProduct(product);
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

    public ProductModel getProduct(String selectedProductName)
    {
        for (int i =0; i < view.productList.size(); i++)
        {
            if(view.productList.get(i).mName == selectedProductName)
            {
                return view.productList.get(i);
            }
        }
        return null;
    }
}

