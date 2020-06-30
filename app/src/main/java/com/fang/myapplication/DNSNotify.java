package com.fang.myapplication;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;

import com.apple.dnssd.DNSSD;
import com.apple.dnssd.DNSSDRegistration;
import com.apple.dnssd.DNSSDService;
import com.apple.dnssd.RegisterListener;
import com.apple.dnssd.TXTRecord;

import java.util.concurrent.locks.ReentrantLock;

public class DNSNotify {

    public static String TAG = "DNSNotify";

    private Register mAirplayRegister;
    private Register mRaopRegister;
    private String mDeviceName;
    private int mDeviceTail = 1;
    private String mMacAddress;
    Context mContext;

    public DNSNotify(Context context) {
        mMacAddress = NetUtils.getLocalMacAddress();
        mDeviceName = "t";
        mContext = context;
    }

    public void changeDeviceName() {
        mDeviceName = "t" + mDeviceTail++;
    }

    public String getDeviceName() {
        return mDeviceName;
    }

    public void startMDNS(int port) {
        //MICE, register MDNS
        Log.i(TAG, "start registering mdns for airplay at port " + port + " for "+this.mDeviceName);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            NsdServiceInfo serviceInfo = new NsdServiceInfo();
            serviceInfo.setServiceName(this.mDeviceName);
            serviceInfo.setServiceType("_airplay._tcp");
            serviceInfo.setPort(port);
            serviceInfo.setAttribute("deviceid", "00:E0:DB:22:05:9C");
            serviceInfo.setAttribute("features", "0x5A7FFFF7,0x1E");
            serviceInfo.setAttribute("srcvers", "220.68");
            serviceInfo.setAttribute("flags", "0x4");
            serviceInfo.setAttribute("vv", "2");
            serviceInfo.setAttribute("model", "AppleTV2,1");
            serviceInfo.setAttribute("pw", "false");
            serviceInfo.setAttribute("rhd", "5.6.0.0");
            serviceInfo.setAttribute("pk", "b07727d6f6cd6e08b58ede525ec3cdeaa252ad9f683feb212ef8a205246554e7");
            serviceInfo.setAttribute("pi", "2e388006-13ba-4041-9a67-25dd4a43d536");
            NsdManager.RegistrationListener registrationListener = new NsdManager.RegistrationListener() {
                @Override
                public void onRegistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
                    Log.i(TAG, "onRegistrationFailed");
                }

                @Override
                public void onUnregistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
                    Log.i(TAG, "onUnregistrationFailed");
                }

                @Override
                public void onServiceRegistered(NsdServiceInfo serviceInfo) {
                    Log.i(TAG, "onServiceRegistered");
                }

                @Override
                public void onServiceUnregistered(NsdServiceInfo serviceInfo) {
                    Log.i(TAG, "onServiceUnregistered");
                }
            };
            NsdManager nsdManager = (NsdManager) mContext.getApplicationContext().getSystemService(Context.NSD_SERVICE);
            nsdManager.registerService(serviceInfo, NsdManager.PROTOCOL_DNS_SD, registrationListener);
        }
    }

    public void registerAirplay(int port) {
        Log.d(TAG, "registerAirplay port = " + port + ", mMacAddress = " + mMacAddress);
        TXTRecord txtRecord = new TXTRecord();
        txtRecord.set("deviceid", mMacAddress);
        txtRecord.set("features", "0x5A7FFFF7,0x1E");
        txtRecord.set("srcvers", "220.68");
        txtRecord.set("flags", "0x4");
        txtRecord.set("vv", "2");
        txtRecord.set("model", "AppleTV2,1");
        txtRecord.set("pw", "false");
        txtRecord.set("rhd", "5.6.0.0");
        txtRecord.set("pk", "b07727d6f6cd6e08b58ede525ec3cdeaa252ad9f683feb212ef8a205246554e7");
        txtRecord.set("pi", "2e388006-13ba-4041-9a67-25dd4a43d536");
        this.mAirplayRegister = new Register(txtRecord, this.mDeviceName, "_airplay._tcp", "local.", "", port);
    }


    public void startRaop(int port) {
        //MICE, register MDNS
        Log.i(TAG, "start Raop at port " + port + " for "+this.mDeviceName);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            NsdServiceInfo serviceInfo = new NsdServiceInfo();
            serviceInfo.setServiceName("00E0DB22059C@"+this.mDeviceName);
            serviceInfo.setServiceType("_raop._tcp");
            serviceInfo.setPort(port);
            serviceInfo.setAttribute("ch", "2");
            serviceInfo.setAttribute("cn", "0,1,2,3");
            serviceInfo.setAttribute("da", "true");
            serviceInfo.setAttribute("et", "0,3,5");
            serviceInfo.setAttribute("vv", "2");
            serviceInfo.setAttribute("ft", "0x5A7FFFF7,0x1E");
            serviceInfo.setAttribute("am", "AppleTV2,1");
            serviceInfo.setAttribute("md", "0,1,2");
            serviceInfo.setAttribute("rhd", "5.6.0.0");
            serviceInfo.setAttribute("pw", "false");
            serviceInfo.setAttribute("sr", "44100");
            serviceInfo.setAttribute("ss", "16");
            serviceInfo.setAttribute("sv", "false");
            serviceInfo.setAttribute("tp", "UDP");
            serviceInfo.setAttribute("txtvers", "1");
            serviceInfo.setAttribute("sf", "0x4");
            serviceInfo.setAttribute("vs", "220.68");
            serviceInfo.setAttribute("vn", "65537");
            serviceInfo.setAttribute("pk", "b07727d6f6cd6e08b58ede525ec3cdeaa252ad9f683feb212ef8a205246554e7");
            NsdManager.RegistrationListener registrationListener = new NsdManager.RegistrationListener() {
                @Override
                public void onRegistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
                    Log.i(TAG, "onRegistrationFailed");
                }

                @Override
                public void onUnregistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
                    Log.i(TAG, "onUnregistrationFailed");
                }

                @Override
                public void onServiceRegistered(NsdServiceInfo serviceInfo) {
                    Log.i(TAG, "onServiceRegistered");
                }

                @Override
                public void onServiceUnregistered(NsdServiceInfo serviceInfo) {
                    Log.i(TAG, "onServiceUnregistered");
                }
            };
            NsdManager nsdManager = (NsdManager) mContext.getApplicationContext().getSystemService(Context.NSD_SERVICE);
            nsdManager.registerService(serviceInfo, NsdManager.PROTOCOL_DNS_SD, registrationListener);
        }
    }


    public void registerRaop(int port) {
        Log.d(TAG, "registerRaop port = " + port);
        TXTRecord txtRecord = new TXTRecord();
        txtRecord.set("ch", "2");
        txtRecord.set("cn", "0,1,2,3");
        txtRecord.set("da", "true");
        txtRecord.set("et", "0,3,5");
        txtRecord.set("vv", "2");
        txtRecord.set("ft", "0x5A7FFFF7,0x1E");
        txtRecord.set("am", "AppleTV2,1");
        txtRecord.set("md", "0,1,2");
        txtRecord.set("rhd", "5.6.0.0");
        txtRecord.set("pw", "false");
        txtRecord.set("sr", "44100");
        txtRecord.set("ss", "16");
        txtRecord.set("sv", "false");
        txtRecord.set("tp", "UDP");
        txtRecord.set("txtvers", "1");
        txtRecord.set("sf", "0x4");
        txtRecord.set("vs", "220.68");
        txtRecord.set("vn", "65537");
        txtRecord.set("pk", "b07727d6f6cd6e08b58ede525ec3cdeaa252ad9f683feb212ef8a205246554e7");
        this.mRaopRegister = new Register(txtRecord, mMacAddress.replace(":", "") + "@" + this.mDeviceName, "_raop._tcp", "local.", "", port);
    }

    public void stop() {
        if (mAirplayRegister != null) {
            mAirplayRegister.stop();
        }
        if (mRaopRegister != null) {
            mRaopRegister.stop();
        }
    }

    class Register implements RegisterListener {
        protected DNSSDRegistration mDNSSDRegistration = null;
        private final ReentrantLock synlock = new ReentrantLock();

        public Register(TXTRecord txtRecord, String serviceName, String regType, String domain, String host, int port) {
            this.synlock.lock();
            try {
                //	public static DNSSDRegistration	register( int flags, int ifIndex, String serviceName, String regType,
                //									String domain, String host, int port, TXTRecord txtRecord, RegisterListener listener)
                this.mDNSSDRegistration = DNSSD.register(0, 0, serviceName, regType, domain, host, port, txtRecord, this);
            } catch (Throwable e) {
                e.printStackTrace();
            } finally {
                this.synlock.unlock();
            }
        }

        @Override
        public void serviceRegistered(DNSSDRegistration registration, int flags, String serviceName, String regType, String domain) {
            Log.i("TAG", "Register successfully : " + serviceName);
        }

        @Override
        public void operationFailed(DNSSDService service, int errorCode) {
            Log.e("TAG", "error " + errorCode);
        }

        public void stop() {
            this.synlock.lock();
            if (this.mDNSSDRegistration != null) {
                this.mDNSSDRegistration.stop();
                this.mDNSSDRegistration = null;
            }
            this.synlock.unlock();

        }

    }

}
