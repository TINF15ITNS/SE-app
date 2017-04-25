package it15ns.friendscom;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.impl.JidCreate;

import java.io.IOException;
import java.net.InetAddress;
import java.security.InvalidParameterException;
import java.security.spec.InvalidParameterSpecException;

public class XMPPClient {

    private static final String DOMAIN = "localhost";
    private static final String HOST = "";
    private static final String IPADRESS = "192.168.1.24";
    private static final int PORT = 5222;
    private String username ="";
    private String password = "";
    AbstractXMPPConnection connection ;
    ChatManager chatmanager ;
    Chat newChat;
    XMPPConnectionListener connectionListener = new XMPPConnectionListener();
    private boolean connected;
    private boolean isToasted;
    private boolean chatCreated;
    private boolean loggedIn;


    //Initialize
    public void init(String username,String password ) throws Exception{
        Log.i("XMPP", "Initializing!");

        this.username = username;
        this.password = password;

        XMPPTCPConnectionConfiguration.Builder configBuilder = XMPPTCPConnectionConfiguration.builder()
            .setUsernameAndPassword(username, password)
            .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
            .setResource("Android")
            .setXmppDomain(DOMAIN)
            .setPort(PORT);

        if(HOST != "") {
            configBuilder.setHost(HOST);
        } else if(IPADRESS != "") {
            InetAddress inetAddress = InetAddress.getByName(IPADRESS);
            configBuilder.setHostAddress(inetAddress);
        } else {
            throw new InvalidParameterException();
        }

        connection = new XMPPTCPConnection(configBuilder.build());
        connection.addConnectionListener(connectionListener);
    }

    // Disconnect Function
    public void disconnectConnection(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                connection.disconnect();
            }
        }).start();
    }

    public void connectConnection()
    {
        AsyncTask<Void, Void, Boolean> connectionThread = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... arg0) {
                // Create a connection
                try {
                    connection.connect();
                    login();
                    connected = true;

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        connectionThread.execute();
    }

    public boolean sendMsg(String jid, String message) throws Exception{
        if (connection.isConnected()== true) {
            // Assume we've created an XMPPConnection name "connection"._
            chatmanager = ChatManager.getInstanceFor(connection);
            EntityBareJid jidObject = JidCreate.entityBareFrom(jid);

            newChat = chatmanager.chatWith(jidObject);

            try {
                newChat.send(message);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    public void login() {

        try {
            connection.login(username, password);
            Log.i("LOGIN", "Yey! We're connected to the Xmpp server!");
        } catch (XMPPException | SmackException | IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }

    }



    //Connection Listener to check connection state
    public class XMPPConnectionListener implements ConnectionListener {
        @Override
        public void connected(final XMPPConnection connection) {

            Log.d("xmpp", "Connected!");
            connected = true;
            if (!connection.isAuthenticated()) {
                login();
            }
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

        @Override
        public void authenticated(XMPPConnection arg0, boolean arg1) {
            Log.d("xmpp", "Authenticated!");
            loggedIn = true;

            chatCreated = false;
            new Thread(new Runnable() {

                @Override
                public void run() {
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
        }
    }
}