package sms.product;

import javax.swing.*;

public class AddProductView extends JFrame
{
    public JPanel addProductPanel;
    public JTextField txtName;
    public JTextField txtDescription;
    public JTextField txtPrice;
    public JTextField txtQty;
    public JButton btnAdd;

    public AddProductView()
    {

    }

    public void resetView()
    {
        txtName.setText("");
        txtDescription.setText("");
        txtPrice.setText("");
        txtQty.setText("");
    }

}
