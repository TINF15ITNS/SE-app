package it15ns.friendscom.xmpp;

import org.jivesoftware.smack.chat2.IncomingChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jxmpp.jid.EntityBareJid;

import it15ns.friendscom.model.ChatHandler;


/**
 * Created by danie on 12/05/2017.
 */

public class XMPPChatListener  implements IncomingChatMessageListener{
    @Override
    public void newIncomingMessage(EntityBareJid from, Message message, org.jivesoftware.smack.chat2.Chat chat) {
        it15ns.friendscom.model.Chat newChat = new it15ns.friendscom.model.Chat();

        newChat.setName(from.asEntityBareJidString().substring(0, from.asEntityBareJidString().indexOf("@")));
        newChat.addMessage(message.getBody().toString());

        ChatHandler chatHandler = ChatHandler.getInstance();
        chatHandler.addChat(newChat);
    }
}
