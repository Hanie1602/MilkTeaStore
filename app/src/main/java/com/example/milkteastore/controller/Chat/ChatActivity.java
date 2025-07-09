package com.example.milkteastore.controller.Chat;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milkteastore.Adapter.MessageAdapter;
import com.example.milkteastore.R;
import com.example.milkteastore.model.Message;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerMessages;
    private EditText edtMessage;
    private Button btnSend;
    private List<Message> messageList;
    private MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recyclerMessages = findViewById(R.id.recyclerMessages);
        edtMessage = findViewById(R.id.edtMessage);
        btnSend = findViewById(R.id.btnSend);

        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(this, messageList);
        recyclerMessages.setLayoutManager(new LinearLayoutManager(this));
        recyclerMessages.setAdapter(messageAdapter);

        btnSend.setOnClickListener(v -> {
            String userText = edtMessage.getText().toString().trim();
            if (!userText.isEmpty()) {
                messageList.add(new Message(userText, true));
                messageAdapter.notifyItemInserted(messageList.size() - 1);
                recyclerMessages.scrollToPosition(messageList.size() - 1);
                edtMessage.setText("");

                new Handler().postDelayed(() -> {
                    String reply = ChatBot.getReply(userText);
                    messageList.add(new Message(reply, false));
                    messageAdapter.notifyItemInserted(messageList.size() - 1);
                    recyclerMessages.scrollToPosition(messageList.size() - 1);
                }, 1000); // giả lập bot trả lời sau 1 giây
            }
        });
    }
}
