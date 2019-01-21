package com.example.mac_os.foodrecipe;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.CheckResult;
import android.widget.CheckBox;
import android.widget.Toast;

public class CheckConnection {
    Context mContext;

    CheckConnection(Context context) {
        mContext = context;
    }

    public boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(mContext.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

    }

    public void show() {
        boolean mConnected = isConnected();
        if (!mConnected) {
            Intent intent = new Intent(mContext, NoInternet.class);
            mContext.startActivity(intent);
            //Toast.makeText(mContext, "There is no Connection, Check your internet connection", Toast.LENGTH_LONG).show();

        }
    }
}
