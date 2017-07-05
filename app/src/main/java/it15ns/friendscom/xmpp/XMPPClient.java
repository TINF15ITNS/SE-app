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

import it15ns.friendscom.activities.MainActivity;
import it15ns.friendscom.activities.LoginActivity;
import it15ns.friendscom.activities.RegisterActivity;
import it15ns.friendscom.activities.SpecificChatActivity;
import it15ns.friendscom.activities.SplashActivity;

public class XMPPClient {
    private static XMPPClient instance = new XMPPClient();

    public static boolean USE_STREAM_MANAGEMENT = false;
    public static int STREAM_MANAGEMENT_RESUMPTION_TIME = 30;

    private static final String DOMAIN = "localhost";
    private static final String HOST = "";
    //private static final String IPADRESS = "10.0.2.2";

    //private static final String IPADRESS = "192.168.1.43";
    private static final String IPADRESS = "141.72.191.147";

    private static final int PORT = 5222;

    private String username ="";
    private String password = "";

    XMPPTCPConnection connection ;
    ChatManager chatmanager ;
    Chat newChat;
    XMPPConnectionListener connectionListener = new XMPPConnectionListener();
    XMPPChatListener chatListener = new XMPPChatListener();

    private boolean connected;
    private boolean isToasted;
    private boolean chatCreated;
    private boolean loggedIn;

    // privater Konstruktor -> Singleton pattern
    private XMPPClient() {}

    // Um das Fragment upzudaten aus dem ChatListener
    public static void setChatActivity(MainActivity activity) {
        instance.chatListener.setMainActivity(activity);
    }

    public static void setSpecificChatActivity(SpecificChatActivity specificChatActivity) {
        instance.chatListener.setSpecificChatActivity(specificChatActivity);
    }

    //Initialize
    public static void init(String username, String password, Context ctx) throws Exception{
        Log.i("XMPP", "Initializing!");

        Resources res = ctx.getResources();
        String packageName = ctx.getApplicationContext().getPackageName();
        int id = res.getIdentifier("raw/trusted_keys", "raw", packageName);
        InputStream ins = res.openRawResource(id);

        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load(ins,"P@ssw0rd".toCharArray());

        TrustManagerFactory tmf =
                TrustManagerFactory
                        .getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(ks);

        SSLContext sslctx = SSLContext.getInstance("TLS");
        sslctx.init(null, tmf.getTrustManagers(), new SecureRandom());

        instance.username = username;
        instance.password = password;
        XMPPTCPConnectionConfiguration.Builder configBuilder = XMPPTCPConnectionConfiguration.builder()
            .setUsernameAndPassword(username, instance.password)
            .setSecurityMode(ConnectionConfiguration.SecurityMode.required)
            .setDebuggerEnabled(true)
            .setResource("Android")
            .setCustomSSLContext(sslctx)
            //.setXmppDomain(DOMAIN)
             .setXmppDomain(IPADRESS)
            .setPort(PORT);

        if(HOST != "") {
            configBuilder.setHost(HOST);
        } else if(IPADRESS != "") {
            InetAddress inetAddress = InetAddress.getByName(IPADRESS);
            configBuilder.setHostAddress(inetAddress);
        } else {
            throw new InvalidParameterException();
        }

        instance.connection = new XMPPTCPConnection(configBuilder.build());
        instance.connection.setUseStreamManagement(USE_STREAM_MANAGEMENT);
        instance.connection.setUseStreamManagementResumption(USE_STREAM_MANAGEMENT);
        instance.connection.setPreferredResumptionTime(STREAM_MANAGEMENT_RESUMPTION_TIME);
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
            // Assume we've created an XMPPConnection name "connection"._
            instance.chatmanager = ChatManager.getInstanceFor(instance.connection);

            String jid;
            if(!nickname.contains("@"))
                jid = nickname.concat("@localhost");
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
    }

    //Connection Listener to check connection state
    public class XMPPConnectionListener implements ConnectionListener {

        @Override
        public void connected(final XMPPConnection connection) {
            Log.d("xmpp", "Connected!");
            connected = true;
            if (!connection.isAuthenticated()) {
                //login();
            }
        }

        @Override
        public void authenticated(XMPPConnection arg0, boolean arg1) {
            Log.d("xmpp", "Authenticated!");
            loggedIn = true;

            chatmanager = ChatManager.getInstanceFor(connection);
            chatmanager.addIncomingListener(chatListener);

            /*
            chatCreated = false;
            new Thread(new Runnable() {

            @Override
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
               // TODO Auto-generated catch block
                e.printStackTrace();
            }

                }
            }).start();

            if (isToasted)
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                    }
                });
            */
        }

        @Override
        public void connectionClosed() {
            if (isToasted)

                new Handler(Looper.getMainLooper()).post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub


                    }
                });
            Log.d("xmpp", "ConnectionCLosed!");
            connected = false;
            chatCreated = false;
            loggedIn = false;
        }

        @Override
        public void connectionClosedOnError(Exception arg0) {
            if (isToasted)

                new Handler(Looper.getMainLooper()).post(new Runnable() {

                    @Override
                    public void run() {

                    }
                });
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
            if (isToasted)

                new Handler(Looper.getMainLooper()).post(new Runnable() {

                    @Override
                    public void run() {



                    }
                });
            Log.d("xmpp", "ReconnectionFailed!");
            connected = false;

            chatCreated = false;
            loggedIn = false;
        }

        @Override
        public void reconnectionSuccessful() {
            if (isToasted)

                new Handler(Looper.getMainLooper()).post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub

                    }
                });
            Log.d("xmpp", "ReconnectionSuccessful");
            connected = true;

            chatCreated = false;
            loggedIn = false;
        }
    }
}