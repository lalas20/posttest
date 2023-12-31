package com.prd.posttest;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class MainActivity extends FlutterActivity {
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);
        BinaryMessenger messenger = flutterEngine.getDartExecutor().getBinaryMessenger();
        String methodChannelName = "com.prodem/mcDP";
        String eventChannelFingerName = "com.prodem/emcF";


        FingerChannelDP fingerChannelDP = new FingerChannelDP();

        MethodChannel methodChannel = new MethodChannel(messenger, methodChannelName);


        methodChannel.setMethodCallHandler(new MethodChannel.MethodCallHandler() {
             //Log.i("setMethodCallHandler", "MethodCallHandler: 43");
            @Override
            public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
                String vResFinger = "";
             Log.i("setMethodCallHandler", "MethodCallHandler: 43"+ call.method);

                switch (call.method) {
                    case "captureNameDevice":
                        Log.i("onMethodCall", "captureNameDevice: 43");
                        String vNameDevice= fingerChannelDP.initFingerDP (getApplicationContext());
                        result.success(vNameDevice);
                        Log.i("onMethodCall", "vNameDevice: 55");
                        break;
                    case "captureFinger":
                        Log.i("onMethodCall", "disposeEMV: 55");
                        String resul= fingerChannelDP.captureFinger (getApplicationContext());
                        result.success(resul);

                        break;


                    default:
                        Log.i("CaptureNameDevice", "notImplemented: 54");
                        result.notImplemented();

                }
            }
        });

       // new EventChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), eventChannelFingerName).setStreamHandler(fingerChannelDP);

    }

    }
