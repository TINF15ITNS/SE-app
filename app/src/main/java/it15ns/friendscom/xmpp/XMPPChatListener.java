package it15ns.friendscom.xmpp;

import org.jivesoftware.smack.chat2.IncomingChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jxmpp.jid.EntityBareJid;

import java.util.Date;

import it15ns.friendscom.activities.MainActivity;
import it15ns.friendscom.activities.SpecificChatActivity;
import it15ns.friendscom.datatypes.TextMessage;
import it15ns.friendscom.handler.UserHandler;
import it15ns.friendscom.model.Chat;
import it15ns.friendscom.model.User;


/**
 * Created by danie on 12/05/2017.
 */

public class XMPPChatListener  implements IncomingChatMessageListener{

    SpecificChatActivity specificChatActivity;
    MainActivity mainActivity;
    @Override
    public void newIncomingMessage(EntityBareJid from, Message message, org.jivesoftware.smack.chat2.Chat chat) {



        // jid: daniel@localhost -> nickname: daniel
        final String nickname = from.asEntityBareJidString().substring(0, from.asEntityBareJidString().indexOf("@"));
        User sender = UserHandler.getUser(nickname, mainActivity);

        Chat userChat;
        if(sender.hasChat(mainActivity))
            userChat = sender.getChat();
        else
            userChat = sender.createChat(mainActivity);

        TextMessage textMessage = new TextMessage(new Date(), sender, message.getBody());
        userChat.addMessage(textMessage);

        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainActivity.update();
            }
        });

        // nofify fragment
        if(specificChatActivity != null) {
            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    specificChatActivity.update();
                }
            });
        }
    }

    public void setSpecificChatActivity(SpecificChatActivity specificChatActivity) {
        this.specificChatActivity = specificChatActivity;
    }

    public void setMainActivity(MainActivity activity) {
        this.mainActivity = activity;
    }
}
