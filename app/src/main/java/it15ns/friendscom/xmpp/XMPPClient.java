package it15ns.friendscom.xmpp;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.impl.JidCreate;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.security.InvalidParameterException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import it15ns.friendscom.R;
import it15ns.friendscom.activities.MainActivity;
import it15ns.friendscom.activities.LoginActivity;
import it15ns.friendscom.activities.RegisterActivity;
import it15ns.friendscom.activities.SpecificChatActivity;
import it15ns.friendscom.activities.SplashActivity;

public class XMPPClient {
    private static XMPPClient instance = new XMPPClient();


    // server properties
    //replace with your Server IP
    private static String ipaddress;
    private static final int PORT = 5222;
    // Für Emulator localhost
    // private String IPADRESS = "10.0.2.2";

    private XMPPTCPConnection connection ;
    private ChatManager chatmanager ;
    private Chat newChat;
    private XMPPConnectionListener connectionListener = new XMPPConnectionListener();
    private XMPPChatListener chatListener = new XMPPChatListener();
    private boolean connected;
    private boolean loggedIn;
    private boolean chatCreated;
    // initialized by init function to send messages later


    // privater Konstruktor -> Singleton
    private XMPPClient() {}

    // Wird im Chatlistener gebraucht um auf der mainactivity update() aufruffen zu können, falls eine Nachricht asynchron eintrtifft
    // MainActivity informiert die adapter
    public static void setChatActivity(MainActivity activity) {
        instance.chatListener.setMainActivity(activity);
    }

    // Wird im Chatlistener gebraucht um den specific chat zu updaten wenn eine neue nachricht eintrifft
    public static void setSpecificChatActivity(SpecificChatActivity specificChatActivity) {
        instance.chatListener.setSpecificChatActivity(specificChatActivity);
    }

    //Initialize
    public static void init(String username, String password, Context ctx) throws Exception{
        Log.i("XMPP", "Initializing!");

        // load resources to get the ipadress of the server and the ssl certificate
        Resources res = ctx.getResources();


        // get the the trust manager
        String packageName = ctx.getApplicationContext().getPackageName();
        int id = res.getIdentifier("raw/trusted_keys", "raw", packageName);
        InputStream ins = res.openRawResource(id);
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load(ins,"P@ssw0rd".toCharArray());
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(ks);

        // initialize an ssl context with the trust manager
        SSLContext sslctx = SSLContext.getInstance("TLS");
        sslctx.init(null, tmf.getTrustManagers(), new SecureRandom());
        // create
        instance.ipaddress = res.getString(R.string.ipaddress);
        // get ip address from resources and save it lcoally to use in for sending messages
        InetAddress serverInetAddress = InetAddress.getByName(instance.ipaddress);

        XMPPTCPConnectionConfiguration.Builder configBuilder = XMPPTCPConnectionConfiguration.builder()
            .setUsernameAndPassword(username, password)
            .setSecurityMode(ConnectionConfiguration.SecurityMode.required)
            .setDebuggerEnabled(true)
            .setResource("Android")
            .setCustomSSLContext(sslctx)
            //.setXmppDomain(DOMAIN)
             .setXmppDomain(instance.ipaddress)
            .setPort(PORT)
            .setHostAddress(serverInetAddress);

        instance.connection = new XMPPTCPConnection(configBuilder.build());
        instance.connection.setUseStreamManagement(false);
        instance.connection.setUseStreamManagementResumption(false);
        instance.connection.setPreferredResumptionTime(30);
        instance.connection.addConnectionListener(instance.connectionListener);
    }

    // Disconnect Function
    public static void disconnect(){
        if(instance.connected)
            instance.connection.disconnect();
    }

    /*
    public void sendStanze(Stanza stanza) throws SmackException.NotConnectedException, InterruptedException{
        connection.sendStanza(stanza);
    }
    */


    public static void connect(final RegisterActivity activity) throws ExecutionException, InterruptedException
    {
        AsyncTask<Void, Void, Boolean> connectionThread = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... arg0) {
                // Create a connection
                try {
                    instance.connection.connect();

                    Roster roster = Roster.getInstanceFor(instance.connection);
                    roster.setSubscriptionMode(Roster.SubscriptionMode.accept_all);
                    roster.setRosterLoadedAtLogin(true);

                    instance.connection.login();
                    instance.connected = true;

                    return true;
                } catch (Exception e) {
                    Log.d("XMPP",e.getMessage() + " Catch connection.connect");
                    return false;
                }
            }

            @Override
            protected void onPostExecute(final Boolean result) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activity.xmppLoginFinished(result);
                    }
                });
            }
        };

        connectionThread.execute();
    }


    public static void connect(final LoginActivity activity) throws ExecutionException, InterruptedException
    {
        AsyncTask<Void, Void, Boolean> connectionThread = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... arg0) {
                // Create a connection
                try {
                    instance.connection.connect();
                    login();
                    instance.connected = true;

                    return true;
                } catch (Exception e) {
                    Log.d("XMPP",e.getMessage() + " Catch connection.connect");
                    return false;
                }
            }

            @Override
            protected void onPostExecute(final Boolean result) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activity.xmppLoginFinished(result);
                    }
                });
            }
        };

        connectionThread.execute();
    }

    public static void connect(final SplashActivity activity) throws ExecutionException, InterruptedException
    {
        AsyncTask<Void, Void, Boolean> connectionThread = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... arg0) {
                // Create a connection
                try {
                    instance.connection.connect();
                    login();
                    instance.connected = true;

                    return true;
                } catch (Exception e) {
                    Log.d("XMPP",e.getMessage() + " Catch connection.connect");
                    return false;
                }
            }

            @Override
            protected void onPostExecute(final Boolean result) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activity.xmppLoginFinished(result);
                    }
                });
            }
        };

        connectionThread.execute();
    }

    public static void connect() throws ExecutionException, InterruptedException
    {
        AsyncTask<Void, Void, Boolean> connectionThread = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... arg0) {
                // Create a connection
                try {
                    instance.connection.connect();
                    login();
                    instance.connected = true;

                    return true;
                } catch (Exception e) {
                    Log.d("XMPP",e.getMessage() + " Catch connection.connect");
                    return false;
                }
            }
        };

        connectionThread.execute();
    }

    public static boolean sendMsg(String nickname, String message) {
        if (instance.connection.isConnected()) {
            instance.chatmanager = ChatManager.getInstanceFor(instance.connection);

            String jid;
            if(!nickname.contains("@"))
                jid = nickname.concat("@"+ instance.ipaddress);
            else
                jid = nickname;

            EntityBareJid jidObject;
            try {
                jidObject = JidCreate.entityBareFrom(jid);
                instance.newChat = instance.chatmanager.chatWith(jidObject);
                instance.newChat.send(message);
                return true;
            } catch (Exception e) {
                Log.d("Exeption", e.getMessage());
                return false;
            }
        } else {
            return false;
        }
    }

    public static void login() throws XMPPException, IOException, SmackException, InterruptedException {
        instance.connection.login();
        instance.loggedIn = true;
    }

    //Connection Listener to check connection state
    public class XMPPConnectionListener implements ConnectionListener {

        @Override
        public void connected(final XMPPConnection connection) {
            Log.d("xmpp", "Connected!");
            connected = true;
        }

        @Override
        public void authenticated(XMPPConnection arg0, boolean arg1) {
            Log.d("xmpp", "Authenticated!");
            loggedIn = true;

            chatmanager = ChatManager.getInstanceFor(connection);
            chatmanager.addIncomingListener(chatListener);
        }

        @Override
        public void connectionClosed() {
            Log.d("xmpp", "ConnectionCLosed!");
            connected = false;
            chatCreated = false;
            loggedIn = false;
        }

        @Override
        public void connectionClosedOnError(Exception arg0) {

            Log.d("xmpp", "ConnectionClosedOn Error!");
            connected = false;
            chatCreated = false;
            loggedIn = false;
        }

        @Override
        public void reconnectingIn(int arg0) {
            Log.d("xmpp", "Reconnectingin " + arg0);
            loggedIn = false;
        }

        @Override
        public void reconnectionFailed(Exception arg0) {
            Log.d("xmpp", "ReconnectionFailed!");
            connected = false;

            chatCreated = false;
            loggedIn = false;
        }

        @Override
        public void reconnectionSuccessful() {
            Log.d("xmpp", "ReconnectionSuccessful");
            connected = true;

            chatCreated = false;
            loggedIn = false;
        }
    }
}