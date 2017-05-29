package it15ns.friendscom.xmpp;

import org.jivesoftware.smack.chat2.IncomingChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jxmpp.jid.EntityBareJid;

import java.util.Date;

import it15ns.friendscom.activities.ChatActivity;
import it15ns.friendscom.activities.SpecificChatActivity;
import it15ns.friendscom.datatypes.TextMessage;
import it15ns.friendscom.fragments.SpecificChatFragment;
import it15ns.friendscom.model.Chat;
import it15ns.friendscom.model.FriendList;
import it15ns.friendscom.model.Handler;
import it15ns.friendscom.model.User;


/**
 * Created by danie on 12/05/2017.
 */

public class XMPPChatListener  implements IncomingChatMessageListener{

    SpecificChatActivity specificChatActivity;
    ChatActivity activity;
    @Override
    public void newIncomingMessage(EntityBareJid from, Message message, org.jivesoftware.smack.chat2.Chat chat) {

        Handler handler = Handler.getInstance();

        // jid: daniel@localhost -> nickname: daniel
        final String nickname = from.asEntityBareJidString().substring(0, from.asEntityBareJidString().indexOf("@"));
        User sender = handler.getUser(nickname);

        Chat userChat;
        if(sender.hasChat())
            userChat = sender.getChat();
        else
            userChat = sender.createChat();

        TextMessage textMessage = new TextMessage(new Date(), sender, message.getBody());
        userChat.addMessage(textMessage);

        // nofify fragment
        if(specificChatActivity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    specificChatActivity.update();
                }
            });
        }
    }

    public void setNotifyActivity(SpecificChatActivity specificChatActivity) {
        this.specificChatActivity = specificChatActivity;
    }

    public void setChatActivity(ChatActivity activity) {
        this.activity = activity;
    }
}
