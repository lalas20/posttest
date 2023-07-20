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
    private Engine m_engine = null;
    private Fmd m_fmd = null;

    private Bitmap m_bitmap = null;
    	private Reader.CaptureResult cap_result = null;


    	private int m_DPI = 0;

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

    public String captureFinger(Context applContext)
    {
        String vresul="captureFinger";
       try 
		{
			//Context applContext = getApplicationContext();
			m_reader = Globals.getInstance().getReader(m_deviceName, applContext);
			m_reader.Open(Priority.EXCLUSIVE);
            Log.i("captureFinger", "antes de get FirstDPI: 178" );

			m_DPI = Globals.GetFirstDPI(m_reader);
            Log.i("captureFinger", "GetFirstDPI:"+m_DPI );
            m_engine = UareUGlobal.GetEngine();
            cap_result = m_reader.Capture(Fid.Format.ANSI_381_2004, Globals.DefaultImageProcessing, m_DPI, -1);

            Log.i("Capture", "spoof_result: "+cap_result.spoof_result );
            Log.i("Capture", "score: "+cap_result.score );
            Log.i("Capture", "spoof_score: "+cap_result.spoof_score );
            Log.i("Capture", "quality.name: "+cap_result.quality.name() );
            Log.i("Capture", "quality.tostring: "+cap_result.quality.toString() );

            if(cap_result.image != null){
                // save bitmap image locally
                m_bitmap = Globals.GetBitmapFromRaw(cap_result.image.getViews()[0].getImageData(), cap_result.image.getViews()[0].getWidth(), cap_result.image.getViews()[0].getHeight());
                Log.i("GetBitmapFromRaw", "despues de crear: ");

                // calculate nfiq score
                DpfjQuality quality = new DpfjQuality();
                int nfiqScore = quality.nfiq_raw(
                        cap_result.image.getViews()[0].getImageData(),	// raw image data
                        cap_result.image.getViews()[0].getWidth(),		// image width
                        cap_result.image.getViews()[0].getHeight(),		// image height
                        m_DPI,											// device DPI
                        cap_result.image.getBpp(),						// image bpp
                        Quality.QualityAlgorithm.QUALITY_NFIQ_NIST		// qual. algo.
                );

                // log NFIQ score
                Log.i("nfiq_raw", "capture result nfiq score: " + nfiqScore);

                m_fmd = m_engine.CreateFmd(cap_result.image, Fmd.Format.ANSI_378_2004);
                Log.i("CreateFmd", "m_fmd.tostring: " + m_fmd.toString());
                Log.i("CreateFmd", "m_fmd.getData: " + m_fmd.getData().toString());
                Log.i("CreateFmd", "m_fmd.getData.length: " + m_fmd.getData().length);

                vresul=Globals.ConvertByteArrayBase64(m_fmd.getData());


            }
            else{
               // m_bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.black);
                // update ui string
                vresul = Globals.QualityToString(cap_result);
            }


          //  vresul="captureFinger";
		} catch (Exception e) {
			Log.w("captureFinger", "Exception:" + e.getMessage());
			m_deviceName = "";
            vresul="Exception captureFinger";
			//onBackPressed();
			
		}
        onBackPressed();
return vresul;

    }
    public void onBackPressed()
	{
		try 
		{

			try {m_reader.CancelCapture(); } catch (Exception e) {}
			m_reader.Close();
		}
		catch (Exception e)
		{
			Log.w("UareUSampleJava", "error during reader shutdown");
		}
	}


    public void disposeDP(){

       try {m_reader.CancelCapture(); } catch (Exception e) {}
        try {m_reader.Close(); } catch (Exception e) {}

                }
}
