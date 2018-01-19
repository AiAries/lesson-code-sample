package oo.aries.com.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * Created by Aries on 2017/4/10.
 */

public class MyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBind;
    }
    private IRemoteService.Stub mBind = new IRemoteService.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public int getPid() throws RemoteException {
            return Process.myPid();
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
