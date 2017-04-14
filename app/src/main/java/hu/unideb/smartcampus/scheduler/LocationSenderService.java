package hu.unideb.smartcampus.scheduler;

import android.Manifest;
import android.app.IntentService;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaCollector;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.bosh.BOSHConfiguration;
import org.jivesoftware.smack.bosh.XMPPBOSHConnection;
import org.jivesoftware.smack.packet.IQ;
import org.jxmpp.jid.EntityFullJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import java.io.IOException;

import hu.unideb.smartcampus.shared.iq.request.UserLocationIqRequest;
import hu.unideb.smartcampus.xmpp.Connection;

import static com.google.android.gms.location.LocationServices.FusedLocationApi;
import static hu.unideb.smartcampus.xmpp.Connection.ADMINJID;
import static hu.unideb.smartcampus.xmpp.Connection.HOSTNAME;
import static java.lang.Thread.sleep;

public class LocationSenderService extends IntentService implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    public LocationSenderService() {
        super("SchedulingService");
    }

    XMPPBOSHConnection xmppConnection;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    public static final String TAG = "LocationService";

    @Override
    protected void onHandleIntent(Intent intent) {
        // BEGIN_INCLUDE(service_onhandle)
        BOSHConfiguration config = null;
        xmppConnection = null;
        try {
            config = BOSHConfiguration.builder()
                    // .setUsernameAndPassword(finalActualUserInfo.getUsername(), finalActualUserInfo.getXmppPassword())
                    //TODO READ FROM DB
                    .setUsernameAndPassword("headswitcher", "39a35530-0ff3-48f1-a9cb-36ff3c4315e2")
                    //.setUsernameAndPassword("testuser", "admin")
                    .setXmppDomain(HOSTNAME)
                    .setHost(HOSTNAME)
                    .setPort(80)
                    .setFile("/http-bind/")
                    .setResource("Smartcampus")
                    .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                    .setDebuggerEnabled(false)
                    .build();
            xmppConnection = new XMPPBOSHConnection(config);

            xmppConnection.connect();
            sleep(SmackConfiguration.getDefaultReplyTimeout());
            xmppConnection.login();

            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            mGoogleApiClient.connect();

        } catch (InterruptedException | IOException | SmackException | XMPPException e) {
            Log.e(TAG, "onHandleIntent: ERROR");
            e.printStackTrace();
        }

        LocationAlarmReceiver.completeWakefulIntent(intent);
        // END_INCLUDE(service_onhandle)
    }

    private void getLocationForService() {
        if (mLastLocation != null) {

            try {
                sleep(SmackConfiguration.getDefaultReplyTimeout());
                UserLocationIqRequest userLocationIqRequest = new UserLocationIqRequest();
                EntityFullJid user = null;

                //TODO Read from db
                if (Connection.getInstance().getXmppConnection() != null && Connection.getInstance().getXmppConnection().isAuthenticated()) {
                    user = Connection.getInstance().getXmppConnection().getUser();
                } else {
                    user = xmppConnection.getUser();
                }
                userLocationIqRequest.setUsername(user.getLocalpartOrThrow().toString());
                userLocationIqRequest.setAccuracy((double) mLastLocation.getAccuracy());
                userLocationIqRequest.setLatitude(mLastLocation.getLatitude());
                userLocationIqRequest.setLongitude(mLastLocation.getLongitude());
                userLocationIqRequest.setTimeStamp(mLastLocation.getTime());

                userLocationIqRequest.setTo(JidCreate.from(ADMINJID));
                userLocationIqRequest.setType(IQ.Type.set);
                userLocationIqRequest.setFrom(user);

                final StanzaCollector stanzaCollectorAndSend = xmppConnection.createStanzaCollectorAndSend(userLocationIqRequest);
                stanzaCollectorAndSend.nextResultOrThrow(5000);
                xmppConnection.disconnect();
                Log.i(TAG, "getLocationForService: SUCCES");
            } catch (XmppStringprepException | InterruptedException | SmackException.NotConnectedException | SmackException.NoResponseException | XMPPException.XMPPErrorException e) {
                Log.e(TAG, "getLocationForService: ERROR ");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLastLocation = FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        getLocationForService();
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, new LocationRequest(), new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                    }
                });


    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "onConnectionFailed");
    }
}
