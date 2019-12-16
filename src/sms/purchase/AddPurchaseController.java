package sms.purchase;

import sms.customer.CustomerModel;
import sms.product.ProductModel;
import sms.storemgr.StoreManagerClient;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.Document;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class AddPurchaseController
{
    public AddPurchaseView view;
    public ProductModel product = null;
    public CustomerModel customer = null;

    public AddPurchaseController(AddPurchaseView view)
    {
        this.view = view;
        view.btnAdd.addActionListener(new AddButtonController());
        view.btnCancel.addActionListener(new CancelButtonController());

        DocumentListener purchaseIDUpdateListener = new DocumentListener()
        {
            @Override
            public void insertUpdate(DocumentEvent e)
            {
                Document doc = e.getDocument();
                if (doc.equals(view.txtPurchaseID.getDocument())) checkForCompletePurchase();
            }

            @Override
            public void removeUpdate(DocumentEvent e)
            {
                Document doc = e.getDocument();
                if (doc.equals(view.txtPurchaseID.getDocument())) checkForCompletePurchase();
            }

            @Override
            public void changedUpdate(DocumentEvent e)
            {
                Document doc = e.getDocument();
                if (doc.equals(view.txtPurchaseID.getDocument())) checkForCompletePurchase();
            }
        };
        this.view.txtPurchaseID.getDocument().addDocumentListener(purchaseIDUpdateListener);

        DocumentListener qtyUpdateListener = new DocumentListener()
        {
            @Override
            public void insertUpdate(DocumentEvent e)
            {
                Document doc = e.getDocument();
                if (doc.equals(view.txtQuantity.getDocument()))
                {
                    if (!view.txtQuantity.getText().isEmpty() && Integer.parseInt(view.txtQuantity.getText()) >0)
                    {
                        calculateCost();
                    }
                    else clearPreviousProductInfo();
                    checkForCompletePurchase();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e)
            {
                Document doc = e.getDocument();
                if (doc.equals(view.txtQuantity.getDocument()))
                {
                    if (!view.txtQuantity.getText().isEmpty() && Integer.parseInt(view.txtQuantity.getText()) >0)
                    {
                        calculateCost();
                    }
                    else clearPreviousProductInfo();
                    checkForCompletePurchase();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e)
            {
                Document doc = e.getDocument();
                if (doc.equals(view.txtQuantity.getDocument()))
                {
                    if (!view.txtQuantity.getText().isEmpty() && Integer.parseInt(view.txtQuantity.getText()) >0)
                    {
                        calculateCost();
                    }
                    else clearPreviousProductInfo();
                    checkForCompletePurchase();
                }
            }
        };
        this.view.txtQuantity.getDocument().addDocumentListener(qtyUpdateListener);

        ListSelectionListener productListSelectionListener = listSelectionEvent -> {
            if(listSelectionEvent.getValueIsAdjusting())
            {
                JList list = (JList) listSelectionEvent.getSource();
                Object selectedValue = list.getSelectedValue();
                product = getProduct(selectedValue.toString());
                if (product != null) {
                    clearPreviousProductInfo();
                    view.txtPrice.setText(String.valueOf(product.mPrice));
                }
            }
        };
        view.lstProducts.addListSelectionListener(productListSelectionListener);

        ListSelectionListener customerListSelectionListener = new ListSelectionListener()
        {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent)
            {
                if(listSelectionEvent.getValueIsAdjusting())
                {
                    JList list = (JList) listSelectionEvent.getSource();
                    Object selectedValue = list.getSelectedValue();
                    customer = getCustomer(selectedValue.toString());
                    checkForCompletePurchase();

                }
            }
        };
        view.lstCustomers.addListSelectionListener(customerListSelectionListener);
    }

    class AddButtonController implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            try
            {
                PurchaseModel purchase = new PurchaseModel();
                purchase.mPurchaseID = Integer.parseInt(view.txtPurchaseID.getText());
                purchase.mCustomerID = customer.mCustomerID;
                purchase.mProductID = product.mProductID;
                purchase.mCost = Double.parseDouble(view.txtCost.getText());
                purchase.mTax = Double.parseDouble(view.txtTax.getText());
                purchase.mTotalCost = Double.parseDouble(view.txtTotalCost.getText());
                purchase.mPrice = Double.parseDouble(view.txtPrice.getText());
                purchase.mQuantity = Integer.parseInt(view.txtQuantity.getText());
                purchase.mDate = view.txtPurchaseDate.getText();

                if(StoreManagerClient.getInstance() != null)
                    if(StoreManagerClient.getInstance().getDataAccess() != null)
                    {
                        StoreManagerClient.getInstance().getDataAccess().savePurchase(purchase);
                        createReceipt(purchase);
                        view.resetView();
                    }
             }
            catch(NumberFormatException numEx)
            {
                JOptionPane.showMessageDialog(null,"Something is incorrectly formatted.");
            }
        }
    }

    class CancelButtonController implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            view.view.dispose();
        }
    }

    private void createReceipt(PurchaseModel purchase)
    {
        try
        {
            Path receiptPath = Files.createTempFile("sms",".rcpt");
            FileWriter receiptWriter = new FileWriter(receiptPath.getFileName().toString());
            receiptWriter.write("Purchase Date: " + purchase.mDate);
            receiptWriter.write("\nPurchase ID: " + purchase.mPurchaseID);
            receiptWriter.write("\nCustomer ID: " + purchase.mCustomerID);
            receiptWriter.write("\nProduct ID: " + purchase.mProductID);
            receiptWriter.write("\nPrice: $" + purchase.mPrice);
            receiptWriter.write("\nQuantity: " + purchase.mQuantity);
            receiptWriter.write("\nCost: $" + purchase.mCost);
            receiptWriter.write("\nTax: $" + purchase.mTax);
            receiptWriter.write("\n----------------------------");
            receiptWriter.write("\nTotal Cost: $" + purchase.mTotalCost);
            receiptWriter.close();

            ProcessBuilder showReceiptProcess = new ProcessBuilder();

            showReceiptProcess.command("notepad.exe", receiptPath.getFileName().toString());
            showReceiptProcess.start();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkForCompletePurchase()
    {
        boolean isReadyToComplete;

        isReadyToComplete = !(view.txtPurchaseID.getText().isEmpty() || view.txtPurchaseID.getText().isBlank());
        isReadyToComplete = isReadyToComplete && (customer != null) && (product != null);
        isReadyToComplete = isReadyToComplete && (!(view.txtPrice.getText().isEmpty() || view.txtPrice.getText().isBlank()));
        isReadyToComplete = isReadyToComplete && (!(view.txtQuantity.getText().isEmpty() || view.txtQuantity.getText().isBlank()));

        view.btnAdd.setEnabled(isReadyToComplete);
    }

    public void clearPreviousProductInfo()
    {
        view.txtQuantity.setText("");
        view.txtCost.setText("");
        view.txtTax.setText("");
        view.txtTotalCost.setText("");
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

    public void calculateCost()
    {
        String s = view.txtQuantity.getText();
        double qtyInCart = 0;
        try
        {
            qtyInCart = Double.parseDouble(s);
        }
        catch (NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(null, "Invalid Quantity");
            return;
        }

        if (qtyInCart > product.mQuantity)
        {
            JOptionPane.showMessageDialog(null, "Only " + product.mQuantity + " in stock!");
            view.txtQuantity.setText("");
            return;
        }

        double cost = product.mPrice * qtyInCart;
        view.txtCost.setText(String.format("%.2f",cost));
        double tax = cost * 0.09;
        view.txtTax.setText(String.format("%.2f",tax));
        double totalCost = cost + tax;
        view.txtTotalCost.setText(String.format("%.2f",totalCost));
    }
}

