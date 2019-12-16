package sms.product;

import sms.storemgr.StoreManagerClient;

import javax.swing.*;
import java.util.ArrayList;

public class UpdateProductView {
    public JPanel updateProductPanel;
    public JTextField txtDescription;
    public JTextField txtPrice;
    public JTextField txtQty;
    public JButton btnUpdate;
    public JList lstProducts;
    public JPanel update;
    public JTextField txtName;
    public JFrame view;
    public JTextField txtID = new JTextField();

    ArrayList<ProductModel> productList = new ArrayList<>();
    public DefaultListModel productListModel = null;

    public UpdateProductView()
    {
        txtID.setVisible(false);
        view = new JFrame();
        view.setTitle("Update Product");
        view.setSize(700, 700);
        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        view.getContentPane().add(update);
        view.setResizable(false);
    }

   public void run()
   {
       productList = new ArrayList<>();
       if(StoreManagerClient.getInstance() != null) {
           if (StoreManagerClient.getInstance().getDataAccess() != null) {
               productList = StoreManagerClient.getInstance().getDataAccess().loadAllProducts();
               productListModel = new DefaultListModel();
               String[] displayProductList = new String[productList.size()];
               for (int i = 0; i < productList.size(); i++) {
                   productListModel.addElement(productList.get(i));
                   displayProductList[i] = productList.get(i).mName;
               }
               lstProducts.setModel(productListModel);
               lstProducts.setListData(displayProductList);
               lstProducts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
           }
       }
   }

    public void resetView()
    {
        txtName.setText("");
        txtDescription.setText("");
        txtPrice.setText("");
        txtQty.setText("");
        txtID.setText("");
        run();
    }

}
