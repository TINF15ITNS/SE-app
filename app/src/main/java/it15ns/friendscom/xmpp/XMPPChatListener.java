package it15ns.friendscom.xmpp;

import org.jivesoftware.smack.chat2.IncomingChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jxmpp.jid.EntityBareJid;

import java.util.Date;

import it15ns.friendscom.activities.ChatActivity;
import it15ns.friendscom.datatypes.ChatMessage;
import it15ns.friendscom.datatypes.TextMessage;
import it15ns.friendscom.fragments.SpecificChatFragment;
import it15ns.friendscom.model.Chat;
import it15ns.friendscom.model.ChatHandler;


/**
 * Created by danie on 12/05/2017.
 */

public class XMPPChatListener  implements IncomingChatMessageListener{
    SpecificChatFragment fragment;
    ChatActivity activity;
    @Override
    public void newIncomingMessage(EntityBareJid from, Message message, org.jivesoftware.smack.chat2.Chat chat) {
        ChatHandler chatHandler = ChatHandler.getInstance();

        final String name = from.asEntityBareJidString().substring(0, from.asEntityBareJidString().indexOf("@"));
        Chat chatForMessage;

        if(chatHandler.chatExists(name)) {
            chatForMessage = chatHandler.getChat(name);
        } else {
            chatForMessage = chatHandler.createNewChat(name);
        }

        chatForMessage.addMessage(new TextMessage(new Date(), name, message.getBody()));

        // nofify fragment
        if(fragment != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    fragment.update();
                }
            });
        }
    }

    public void setNotifyFragment(SpecificChatFragment fragment) {
        this.fragment = fragment;
    }

    public void setChatActivity(ChatActivity activity) {
        this.activity = activity;
    }
}
