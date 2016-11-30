package eb.cyrien.MineCordBot.entity;

import org.bukkit.Bukkit;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.conversations.ManuallyAbandonedConversationCanceller;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;

public class ConvoTracker {

    private LinkedList<Conversation> conversationQueue = new LinkedList();

    public ConvoTracker() {
    }

    public synchronized boolean beginConversation(Conversation conversation) {
        if (!this.conversationQueue.contains(conversation)) {
            this.conversationQueue.addLast(conversation);
            if (this.conversationQueue.getFirst() == conversation) {
                conversation.begin();
                conversation.outputNextPrompt();
                return true;
            }
        }

        return true;
    }

    public synchronized void abandonConversation(Conversation conversation, ConversationAbandonedEvent details) {
        if (!this.conversationQueue.isEmpty()) {
            if (this.conversationQueue.getFirst() == conversation) {
                conversation.abandon(details);
            }

            if (this.conversationQueue.contains(conversation)) {
                this.conversationQueue.remove(conversation);
            }

            if (!this.conversationQueue.isEmpty()) {
                ((Conversation) this.conversationQueue.getFirst()).outputNextPrompt();
            }
        }

    }

    public synchronized void abandonAllConversations() {
        LinkedList oldQueue = this.conversationQueue;
        this.conversationQueue = new LinkedList();
        Iterator var2 = oldQueue.iterator();

        while (var2.hasNext()) {
            Conversation conversation = (Conversation) var2.next();

            try {
                conversation.abandon(new ConversationAbandonedEvent(conversation, new ManuallyAbandonedConversationCanceller()));
            } catch (Throwable var5) {
                Bukkit.getLogger().log(Level.SEVERE, "Unexpected exception while abandoning a conversation", var5);
            }
        }

    }

    public synchronized void acceptConversationInput(String input) {
        if (this.isConversing()) {
            ((Conversation) this.conversationQueue.getFirst()).acceptInput(input);
        }

    }

    public synchronized boolean isConversing() {
        return !this.conversationQueue.isEmpty();
    }

    public synchronized boolean isConversingModaly() {
        return this.isConversing() && ((Conversation) this.conversationQueue.getFirst()).isModal();
    }
}
