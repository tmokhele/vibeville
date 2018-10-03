package bttc.app.controller;

import bttc.app.model.Chat;
import bttc.app.model.User;
import bttc.app.service.ChatService;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/chat/")
public class ChatApplicationController {

    @Autowired
    ChatService chatService;
    List<Chat> chats;

    @GetMapping("/all")
    public @ResponseBody
    List<Chat> retrieveChats() {
        chats = new ArrayList<>();
        try {
            CompletableFuture<List<String>> allChats = chatService.getAllChats();
            for (String s : allChats.get()) {
                mapChats(s);
            }

        } catch (Exception ex) {

        }
        return chats;
    }

    private void mapChats(String entry) {
        Gson g = new Gson();
//        Message value = g.fromJson(entry, Message.class);
        Chat c = new Chat();
        User u = new User();
        c.setFrom(u);
        chats.add(c);
    }

    @PostMapping
    public @ResponseBody
    Chat saveChat(@RequestBody Chat chat) {
        UUID idOne = UUID.randomUUID();
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("chats");
        DatabaseReference childRef = ref.child(String.valueOf(idOne));
//        childRef.setValue(message);
        childRef.push();
        return chat;
    }
}
