package sms.storemgr;


import sms.IDataAccess;
import sms.SQLiteDataAdapter;
import sms.launcher.ModuleLauncherController;
import sms.launcher.ModuleLauncherView;
import sms.user_credentials.UserCredentialsController;
import sms.user_credentials.UserCredentialsView;

import javax.swing.*;

public class StoreManagerClient
{
    public static IDataAccess adapter = null;
    //ModuleLauncherView mView;
    //ModuleLauncherController mController;
    UserCredentialsView mView;
    UserCredentialsController mController;

    static StoreManagerClient instance = null;

    public static StoreManagerClient getInstance()
    {
        if (instance == null) {
            instance = new StoreManagerClient();
            instance.mView = new UserCredentialsView();
            //instance.mView = new ModuleLauncherView();
            //instance.mController = new ModuleLauncherController(instance.mView);
            instance.mController = new UserCredentialsController(instance.mView);
            JFrame frame = new JFrame("ModuleLauncher");
            frame.setContentPane(instance.mView.userCredentialsPanel);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setResizable(false);
            frame.pack();
            frame.setVisible(true);
        }
        return instance;
    }


    public static void setAdapter() {
        try
        {
            instance.adapter = new SQLiteDataAdapter();
        }
        catch (Exception ex)
        {
            adapter = null;
        }
    }
    public static void setAdapter(String host , int port)
    {
        try
        {
            //instance.adapter = new RemoteDataAdapter(host, port);
        }
        catch (Exception ex)
        {
            adapter = null;
        }
    }
    public IDataAccess getDataAccess() { return adapter; }

    public void run()
    {
        if(mView != null)
            mView.userCredentialsPanel.setVisible(true);
    }
}