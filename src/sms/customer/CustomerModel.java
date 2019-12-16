package sms.customer;

public class CustomerModel {
    public int mCustomerID;
    public String mName, mEmail, mAddress, mPhone;

    public CustomerModel()
    {

    }
    public CustomerModel(int id, String n, String a, String p, String e)
    {
        this.mCustomerID = id;
        this.mName = n;
        this.mAddress = a;
        this.mPhone = p;
        this.mEmail = e;
    }
}
