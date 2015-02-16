package com.mycompany.inout;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class UseParse extends Application{
@Override
        public void onCreate(){
            super.onCreate();

            Parse.initialize(this, "vXRp1PCYPUiIP7RHlH9CV8kU9HixQcWj7QSrPKiq", "bHNU0j2IziwrBBQ8HJRr1zzhs1JNVDxIoZrYCXPZ");

        }

}
