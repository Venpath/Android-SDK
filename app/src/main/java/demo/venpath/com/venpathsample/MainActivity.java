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

        ////////////////
        // To send email data, use this code on registration.
        // Make sure it's the actual email, not a sample email or you will be banned.
        // Note:  The email address is encoded and never transmitted raw.
        VenpathGeneric venpathEmailDataRegister = new VenpathGeneric().putVenpathGenericAttribute("email", myEmailVar)
                                                                     .putVenpathGenericAttribute("new_user", true); // Note how this is true.  That means it's a registration event.

        venpath.setVenpathGenericData(venpathEmailDataRegister);

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
        ////////////////


        ////////////////
        // To send email data, use this code on log in
        // Make sure it's the actual email, not a sample email or you will be banned.
        // Note:  The email address is encoded and never transmitted raw.
        VenpathGeneric venpathEmailData = new VenpathGeneric().putVenpathGenericAttribute("email", myEmailVar)
                .putVenpathGenericAttribute("new_user", false); // Note how this is false.  That means it's a login event.

        venpath.setVenpathGenericData(venpathEmailData);

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
        ////////////////

        requestRequiredPermissions();
    }

    /**
     * Requests the Location permissions
     * If accepted, starts the location retrieval process
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
