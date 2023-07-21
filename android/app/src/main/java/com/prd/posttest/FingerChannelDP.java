package com.prd.posttest;

import static android.content.Intent.getIntent;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.util.Log;

import com.digitalpersona.uareu.Quality;
import com.digitalpersona.uareu.Reader;
import com.digitalpersona.uareu.Engine;
import com.digitalpersona.uareu.Fid;
import com.digitalpersona.uareu.Fmd;
import com.digitalpersona.uareu.Reader;
import com.digitalpersona.uareu.ReaderCollection;
import com.digitalpersona.uareu.UareUException;
import com.digitalpersona.uareu.UareUGlobal;
import com.digitalpersona.uareu.Reader.Priority;
import com.digitalpersona.uareu.dpfpddusbhost.DPFPDDUsbException;
import com.digitalpersona.uareu.dpfpddusbhost.DPFPDDUsbHost;
import com.digitalpersona.uareu.jni.DpfjQuality;


public class FingerChannelDP {
    private static final String TAG = "FingerChannelDP";
    private static final String ACTION_USB_PERMISSION = "com.digitalpersona.uareu.dpfpddusbhost.USB_PERMISSION";

    private Reader m_reader = null;
    private ReaderCollection readers = null;
    private String m_deviceName = "";
    private Reader.Capabilities cap=null;
    private Reader.Description des=null;
private Reader.Status status=null;

    private void displayReaderNotFound()
    {
        m_deviceName="displayReaderNotFound ";

    }
    protected void CheckDevice()
    {
        try
        {
            m_reader.Open(Priority.EXCLUSIVE);
            Reader.Capabilities cap = m_reader.GetCapabilities();

            m_reader.Close();
        }
        catch (UareUException e1)
        {
            displayReaderNotFound();
        }
    }
    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver()
    {
        public void onReceive(Context context, Intent intent)
        {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action))
            {
                synchronized (this)
                {
                    UsbDevice device = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false))
                    {
                        if(device != null)
                        {
                            //call method to set up device communication
                            CheckDevice();
                        }
                    }
                    /* else
                    {
                        setButtonsEnabled(false);
                    }
                    */

                }
            }
        }
    };
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

            Log.i(TAG,"NAME READER:"+readers.get(0).GetDescription().id.product_name);
           m_reader= readers.get(0);
           //m_deviceName=readers.get(0).GetDescription().id.product_name;
            m_deviceName=readers.get(0).GetDescription().name;
           //add permisos usb
            if((m_deviceName != null) && !m_deviceName.isEmpty())
            {
                //m_selectedDevice.setText("Device: " + m_deviceName);

                try {
                   // Context applContext = getApplicationContext();
                   // m_reader = Globals.getInstance().getReader(m_deviceName, applContext);

                    {
                        Log.i("USB","VERIFICANDO USB:");
                        PendingIntent mPermissionIntent;
                        Log.i("USB","mPermissionIntent:");

                        mPermissionIntent = PendingIntent.getBroadcast(applContext, 0, new Intent(ACTION_USB_PERMISSION), 0);
                        Log.i("USB","getBroadcast:");

                        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
                        Log.i("USB","IntentFilter:");

                        applContext.registerReceiver(mUsbReceiver, filter);
                        Log.i("USB","registerReceiver:");


                        if(DPFPDDUsbHost.DPFPDDUsbCheckAndRequestPermissions(applContext, mPermissionIntent, m_deviceName))
                        {
                            Log.i("USB","DPFPDDUsbCheckAndRequestPermissions:");
                            CheckDevice();
                        }
                    }
                } catch (DPFPDDUsbException e)
                {
                    Log.i("USB","DPFPDDUsbException:" +e.getMessage());

                    displayReaderNotFound();
                }

            } else
            {
                Log.i("USB","else 144:");
                displayReaderNotFound();
            }
            //fin add usb permisos



           Log.i("status","antes del status:"+m_deviceName);
            m_reader.Open(Priority.COOPERATIVE);
            status= m_reader.GetStatus();
            Log.i("status","NAME READER:"+status.status.name());
            Log.i("status","NAME READER:"+status.toString());
            m_reader.Close();
        }
        catch (Exception e)
        {
            Log.i(TAG, "excepcion: "+e.getMessage() );
            m_deviceName="error reader";
        }
        return  m_deviceName;
    }
}
