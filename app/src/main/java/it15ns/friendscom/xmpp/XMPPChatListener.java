package it15ns.friendscom.xmpp;

import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.IncomingChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jxmpp.jid.EntityBareJid;

/**
 * Created by danie on 12/05/2017.
 */

public class XMPPChatListener  implements IncomingChatMessageListener{

    @Override
    public void newIncomingMessage(EntityBareJid from, Message message, Chat chat) {

    }
}
