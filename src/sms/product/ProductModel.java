package sms.product;

public class ProductModel
{
    public int mProductID, mQuantity;
    public String mName, mDescription;
    public double mPrice;

    public ProductModel()
    {

    }
    public ProductModel(int id, String n, String d, double p, int q)
    {
        this.mProductID = id;
        this.mName = n;
        this.mDescription = d;
        this.mPrice = p;
        this.mQuantity = q;
    }
}
