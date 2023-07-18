package com.prd.posttest;

import com.digitalpersona.uareu.Reader;
import com.digitalpersona.uareu.Engine;
import com.digitalpersona.uareu.Fid;
import com.digitalpersona.uareu.Fmd;
import com.digitalpersona.uareu.Reader;
import com.digitalpersona.uareu.UareUGlobal;
import com.digitalpersona.uareu.Reader.Priority;

import org.gradle.internal.operations.logging.LoggingBuildOperationProgressBroadcaster;

public class FingerChannelDP {
    private static final String TAG = "FingerChannelDP";

    private Reader m_reader = null;
    private String m_deviceName = "";


    public String   initFingerDP(){
        m_deviceName=   getIntent().getExtras().getString("device_name");
        Log.i(TAG, "initFingerDP: "+m_deviceName);
        return  m_deviceName;
    }
}
