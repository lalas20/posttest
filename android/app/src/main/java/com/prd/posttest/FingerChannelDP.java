package com.prd.posttest;

import static android.content.Intent.getIntent;

import android.util.Log;

import com.digitalpersona.uareu.Reader;
import com.digitalpersona.uareu.Engine;
import com.digitalpersona.uareu.Fid;
import com.digitalpersona.uareu.Fmd;
import com.digitalpersona.uareu.Reader;
import com.digitalpersona.uareu.UareUGlobal;
import com.digitalpersona.uareu.Reader.Priority;



public class FingerChannelDP {
    private static final String TAG = "FingerChannelDP";

    private Reader m_reader = null;
    private String m_deviceName = "";


    public String   initFingerDP(){
        m_deviceName= "llegando init finger"; // getIntent().getExtras().getString("device_name");
        Log.i(TAG, "initFingerDP: "+m_deviceName);
        return  m_deviceName==""?"sin device":m_deviceName;
    }
}
