package com.rhapsody.xhan.sandbox;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Messenger;

/**
 * Created by xhan on 10/10/14.
 */
public class UIServiceConnection implements ServiceConnection {
    Messenger messenger;
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        messenger = new Messenger(service);

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        messenger = null;
    }
}
