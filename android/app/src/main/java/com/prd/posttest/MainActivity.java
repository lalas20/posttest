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
        String methodChannelName = "com.prodem/mc";
        String eventChannelFingerName = "com.prodem/emcF";


        FingerChannelDP fingerChannelDP = new FingerChannelDP();

        MethodChannel methodChannel = new MethodChannel(messenger, methodChannelName);


        methodChannel.setMethodCallHandler(new MethodChannel.MethodCallHandler() {
            @Override
            public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
                String vResFinger = "";
                switch (call.method) {
                    case "disposeEMV":

                        Log.i("onMethodCall", "disposeEMV: 55");
                        break;
                    case "searchEMV":
                        Log.i("onMethodCall", "disposeEMV: 55");

                        break;

                    default:
                        result.notImplemented();

                }
            }
        });

        new EventChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), eventChannelFingerName).setStreamHandler(fingerChannelDP);

    }

    }
