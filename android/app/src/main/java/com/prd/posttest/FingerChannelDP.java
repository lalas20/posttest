package com.prd.posttest;

import static android.content.Intent.getIntent;
import android.content.Context;

import android.util.Log;

import com.digitalpersona.uareu.Reader;
import com.digitalpersona.uareu.Engine;
import com.digitalpersona.uareu.Fid;
import com.digitalpersona.uareu.Fmd;
import com.digitalpersona.uareu.Reader;
import com.digitalpersona.uareu.ReaderCollection;
import com.digitalpersona.uareu.UareUGlobal;
import com.digitalpersona.uareu.Reader.Priority;




public class FingerChannelDP {
    private static final String TAG = "FingerChannelDP";

    private Reader m_reader = null;
    private ReaderCollection readers = null;
    private String m_deviceName = "";
    private Reader.Capabilities cap=null;
    private Reader.Description des=null;


    public String   initFingerDP(Context applContext){
        Log.i(TAG, "initFingerDP: 31 ini" );
        m_deviceName="sin data";
        try {

            readers = UareUGlobal.GetReaderCollection(applContext);
            Log.i(TAG, "initFingerDP: 36" );
            readers.GetReaders();
            if (readers.size()==0)
            {
                m_deviceName="Dispositivo no reconocido";
                return m_deviceName;
            }
            Log.i(TAG, "cantidad reader:" + readers.size() );
            Log.i(TAG,"NAME READER:"+readers.get(0).GetDescription().name);

            Log.i(TAG,"NAME READER:"+readers.get(0).GetDescription().serial_number);

            Log.i(TAG,"NAME READER:"+readers.get(0).GetDescription().label);
           m_reader= readers.get(0);
           m_deviceName=readers.get(0).GetDescription().name;
           //return m_deviceName;

/*
            //m_reader=Reader();

            Log.i(TAG, "initFingerDP: 41" );
            m_reader.Open(Priority.COOPERATIVE);
            Log.i(TAG, "initFingerDP: 43" );
            cap=m_reader.GetCapabilities();
            Log.i(TAG, "initFingerDP: 45" );
            des=m_reader.GetDescription();
            Log.i(TAG, "initFingerDP: 47" );
            m_deviceName=des.name+des.serial_number;
            Log.i(TAG, "m_devicename: "+m_deviceName );
            //m_deviceName = "llegando init finger"; // getIntent().getExtras().getString("device_name");
            Log.i(TAG, "initFingerDP: " + m_deviceName);
*/
        }
        catch (Exception e)
        {
            Log.i(TAG, "excepcion: "+e.getMessage() );
            m_deviceName="error reader";
        }
        return  m_deviceName;
    }
}
