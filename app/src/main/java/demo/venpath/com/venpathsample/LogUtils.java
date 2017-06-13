package demo.venpath.com.venpathsample;

import android.util.Log;

import java.util.HashMap;

public class LogUtils
{

    public static void d(String label, String text)
    {
        if(BuildConfig.DEBUG)
        {
            Log.d(label, text);
        }
    }

    public static void e(Throwable t)
    {
        e(t, null);
    }

    public static void e(Throwable t, HashMap<String, String> keys)
    {
        if(BuildConfig.DEBUG)
        {
            if(keys != null && keys.size() > 0)
            {
                for(String key : keys.keySet())
                {
                    Log.d(LogUtils.class.getSimpleName(), key + " " + keys.get(key));
                }
            }
            t.printStackTrace();
        }
    }
}
