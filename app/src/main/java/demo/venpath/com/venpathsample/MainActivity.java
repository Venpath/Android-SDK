package demo.venpath.com.venpathsample;

import android.Manifest;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.venpath.sdk.VenPath;
import com.venpath.sdk.VenpathConfiguration;
import com.venpath.sdk.generic.VenpathGeneric;
import com.venpath.sdk.model.EventType;

import rx.Subscriber;

public class MainActivity extends AppCompatActivity
{

    RxPermissions rxPermissions;
    VenPath venpath;
    VenpathConfiguration venpathConfiguration;

    private boolean isTest;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        rxPermissions = new RxPermissions(this);

        venpath = VenPath.getInstance(MainActivity.this);

        //to send app usage and generic data
        VenpathGeneric venpathGenericData = new VenpathGeneric().putVenpathGenericAttribute("app_name", getPackageName())
                                                                .putVenpathGenericAttribute("event_date", "2016-03-28 21:58:00")
                                                                .putVenpathGenericAttribute("event_type", EventType.LAUNCH)
                                                                .putVenpathGenericAttribute("seconds_used", "100")
                                                                .putVenpathGenericAttribute("permissions", "comma,delimited,list,here");

        venpath.setVenpathGenericData(venpathGenericData);

        venpath.track(new VenPath.Callback()
        {
            @Override
            public void onSuccess(String result)
            {
                LogUtils.d("Generic_App s", result);
            }

            @Override
            public void onError(String result)
            {
                LogUtils.d("Generic_App  f", result);
            }
        });

        //to send app usage and generic data
        VenpathGeneric venpathGenericDataEmail = new VenpathGeneric().putVenpathGenericAttribute("email", "dharmarajsharma@outlook.com")
                                                                     .putVenpathGenericAttribute("new_user", true);

        venpath.setVenpathGenericData(venpathGenericDataEmail);

        venpath.track(new VenPath.Callback()
        {
            @Override
            public void onSuccess(String result)
            {
                LogUtils.d("Generic_App s", result);
            }

            @Override
            public void onError(String result)
            {
                LogUtils.d("Generic_App  f", result);
            }
        });

        //requestRequiredPermissions();
    }

    /**
     * Requests the Location permissions
     * if accepted, starts the location retrieval process
     */
    private void requestRequiredPermissions()
    {
        rxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                     .subscribe(new Subscriber<Boolean>()
                     {
                         @Override
                         public void onCompleted()
                         {
                         }

                         @Override
                         public void onError(Throwable e)
                         {
                         }

                         @Override
                         public void onNext(Boolean aBoolean)
                         {
                             if(aBoolean)
                             {
                                 venpath.trackLocation();
                             }
                             else if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                                                                                         Manifest.permission.ACCESS_COARSE_LOCATION))
                             {
                                 new AlertDialog.Builder(MainActivity.this).setTitle("Permissions Required")
                                                                           .setMessage("the location permissions are required")
                                                                           .setPositiveButton("OK", new DialogInterface.OnClickListener()
                                                                           {
                                                                               @Override
                                                                               public void onClick(DialogInterface dialogInterface, int i)
                                                                               {
                                                                                   dialogInterface.dismiss();
                                                                                   requestRequiredPermissions();
                                                                               }
                                                                           })
                                                                           .setNegativeButton("No", new DialogInterface.OnClickListener()
                                                                           {
                                                                               @Override
                                                                               public void onClick(DialogInterface dialogInterface, int i)
                                                                               {
                                                                                   dialogInterface.dismiss();
                                                                               }
                                                                           })
                                                                           .show();
                             }
                         }
                     });
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }
}
