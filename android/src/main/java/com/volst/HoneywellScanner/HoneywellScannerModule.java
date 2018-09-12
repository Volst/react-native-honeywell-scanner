package com.volst.HoneywellScanner;

import java.lang.reflect.Method;
import java.util.Set;
import javax.annotation.Nullable;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.util.Log;
import android.util.Base64;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.Promise;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import static com.volst.HoneywellScanner.HoneywellScannerPackage.TAG;

import com.honeywell.aidc.AidcManager;
import com.honeywell.aidc.AidcManager.CreatedCallback;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.ScannerUnavailableException;

@SuppressWarnings("unused")
public class HoneywellScannerModule extends ReactContextBaseJavaModule implements ActivityEventListener, LifecycleEventListener, BarcodeReader.BarcodeListener {

    // Debugging
    private static final boolean D = true;

    private static BarcodeReader barcodeReader;
    private AidcManager manager;
    private ReactApplicationContext mReactContext;
    private static final String BARCODE_READ = "barcodeRead";


    public HoneywellScannerModule(ReactApplicationContext reactContext) {
        super(reactContext);

        mReactContext = reactContext;

        AidcManager.create(mReactContext, new CreatedCallback() {
            @Override
            public void onCreated(AidcManager aidcManager) {
                manager = aidcManager;
                barcodeReader = manager.createBarcodeReader();
                if(barcodeReader != null){
                    barcodeReader.addBarcodeListener(HoneywellScannerModule.this);
                    try {
                        barcodeReader.claim();
                    } catch (ScannerUnavailableException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        mReactContext.addActivityEventListener(this);
        mReactContext.addLifecycleEventListener(this);
        
    }

    @Override
    public String getName() {
        return "HoneywellScanner";
    }

    /**
     * Send event to javascript
     * @param eventName Name of the event
     * @param params Additional params
     */
    private void sendEvent(String eventName, @Nullable WritableMap params) {
        if (mReactContext.hasActiveCatalystInstance()) {
            if (D) Log.d(TAG, "Sending event: " + eventName);
            mReactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
        }
    }


    @Override
    public void onNewIntent(Intent intent) {
        if (D) Log.d(TAG, "On new intent");
    }


    @Override
    public void onHostResume() {
        if (D) Log.d(TAG, "Host resume");
    }

    @Override
    public void onHostPause() {
        if (D) Log.d(TAG, "Host pause");
    }

    @Override
    public void onHostDestroy() {
        if (D) Log.d(TAG, "Host destroy");
        // TODO
        // mBluetoothService.stop();
    }

    @Override
    public void onCatalystInstanceDestroy() {
        if (D) Log.d(TAG, "Catalyst instance destroyed");
        super.onCatalystInstanceDestroy();
        // TODO
        // mBluetoothService.stop();
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent intent) {
        if (D) Log.d(TAG, "On activity result request: " + requestCode + ", result: " + resultCode);
    }

    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        if (D) Log.d(TAG, "KEES - BARCODE SCAN SUCCESS");
        WritableMap params = Arguments.createMap();
        params.putString("code", barcodeReadEvent.getBarcodeData());
        sendEvent(BARCODE_READ, params);
    }

    @Override
    public void onFailureEvent(BarcodeFailureEvent barcodeFailureEvent) {
        if (D) Log.d(TAG, "KEES - Barcode scan failed");
    }

    /*******************************/
    /** Methods Available from JS **/
    /*******************************/

    /*************************************/

    @ReactMethod
    public void requestEnable(Promise promise) {
        promise.resolve(true);
    }
}
