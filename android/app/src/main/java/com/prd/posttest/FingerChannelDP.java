package com.prd.posttest;

import com.digitalpersona.uareu.Reader;
import com.digitalpersona.uareu.Engine;
import com.digitalpersona.uareu.Fid;
import com.digitalpersona.uareu.Fmd;
import com.digitalpersona.uareu.Reader;
import com.digitalpersona.uareu.UareUGlobal;
import com.digitalpersona.uareu.Reader.Priority;

public class FingerChannelDP {
    private Reader m_reader = null;
    private String m_deviceName = "";

    public initFingerDP(){
        m_deviceName=   getIntent().getExtras().getString("device_name");
    }
}
