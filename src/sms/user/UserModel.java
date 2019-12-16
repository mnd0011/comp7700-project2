package sms.user;

public class UserModel {
    public int mUserID, mUserType;
    public String mUserName, mPassword, mFullName;

    public UserModel()
    {

    }
    public UserModel(int id, String u, String p, String f, int ut)
    {
        this.mUserID = id;
        this.mUserName = u;
        this.mPassword = p;
        this.mFullName = f;
        this.mUserType = ut;
    }
}
