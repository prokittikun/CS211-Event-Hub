package cs211.project.models.collections;

import cs211.project.models.Chat;

import java.util.ArrayList;

public class ChatCollection {
    private ArrayList<Chat> chats;
    public ChatCollection() {
        chats = new ArrayList<>();
    }

    public void addNewChat(Chat chat){
        chats.add(chat);
    }

    public ArrayList<Chat> getChats() {
        return chats;
    }


}
