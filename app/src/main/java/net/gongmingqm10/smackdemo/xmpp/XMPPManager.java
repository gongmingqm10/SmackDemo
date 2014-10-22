package net.gongmingqm10.smackdemo.xmpp;

import android.util.Log;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

import java.io.IOException;

public class XMPPManager {


    private final String TAG = "XMPPManager";

    private final String serverAddress = "replaceWithYourIP"; // Your server address or IP
    public static final String serverName = "replaceWithYourServerName"; //xmpp name or your server name
    private XMPPState state = XMPPState.NOT_CONNECTED;
    private ConnectionConfiguration config;
    private static XMPPManager instance;
    public XMPPTCPConnection connection;

    private String username;
    private String password;

    private XMPPManager() {
        config = new ConnectionConfiguration(serverAddress, 5222, serverName);
        config.setReconnectionAllowed(true);
        config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        connection = new XMPPTCPConnection(config);
        connection.addConnectionListener(new XMPPConnectionListener());
    }

    public static XMPPManager getInstance() {
        if (instance == null) {
            instance = new XMPPManager();
        }
        return instance;
    }

    public XMPPState getState() {
        return state;
    }

    public void connect(final String username, final String password) {
        this.username = username;
        this.password = password;
        reConnect();
    }

    public void reConnect() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    connection.connect();
                    connection.login(username, password);
                } catch (XMPPException e) {
                    e.printStackTrace();
                } catch (SmackException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private class XMPPConnectionListener implements ConnectionListener {

        @Override
        public void connected(XMPPConnection xmppConnection) {
            Log.i(TAG, "--connected--");
            state = XMPPState.CONNECTED;
        }

        @Override
        public void authenticated(XMPPConnection xmppConnection) {
            Log.i(TAG, "--authenticated--");
            state = XMPPState.AUTHENTICATED;
        }

        @Override
        public void connectionClosed() {
            Log.i(TAG, "--connectionClosed--");
            state = XMPPState.NOT_CONNECTED;
        }

        @Override
        public void connectionClosedOnError(Exception e) {
            Log.e(TAG, "--connectionClosedOnError--");
        }

        @Override
        public void reconnectingIn(int i) {
            Log.i(TAG, "--reconnectingIn--");
        }

        @Override
        public void reconnectionSuccessful() {
            Log.i(TAG, "--reconnectionSuccessful--");
        }

        @Override
        public void reconnectionFailed(Exception e) {
            Log.e(TAG, "--reconnectionFailed--");
        }
    }

}
