package demo.venpath.com.venpathsample;

import android.app.Application;

import com.venpath.sdk.BuildConfig;
import com.venpath.sdk.VenPath;

public class VenpathApplication extends Application
{
    boolean isTest;
    boolean isPassiveBackground;

    @Override
    public void onCreate() {
        super.onCreate();

        // If you are in development, set isTest = true;
        if(BuildConfig.DEBUG) {
            isTest = true;
        } else {
            isTest = false;
        }

        // If you want to enable passive background location mode, set isPassiveBackground = true;
        isPassiveBackground = true;
        
        // And now, we auth.
        VenPath.init(isTest, "bosskey", "shhhh", "lookatme", isPassiveBackground);
    }
}