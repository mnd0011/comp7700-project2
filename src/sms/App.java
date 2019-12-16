package sms;

import sms.storemgr.StoreManagerClient;

public class App
{
    public static void main(String[] args)
    {
        StoreManagerClient.getInstance().run();
    }
}
