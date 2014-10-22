package net.gongmingqm10.smackdemo.activity;

import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import net.gongmingqm10.smackdemo.R;
import net.gongmingqm10.smackdemo.adapter.ChatListAdapter;
import net.gongmingqm10.smackdemo.model.ChatMessage;
import net.gongmingqm10.smackdemo.xmpp.XMPPManager;
import net.gongmingqm10.smackdemo.xmpp.XMPPState;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends ActionBarActivity {

    @InjectView(R.id.messageEdit)
    EditText messageEdit;
    @InjectView(R.id.messageList)
    ListView messageList;
    @InjectView(R.id.sendBtn)
    Button sendBtn;

    private ChatManager chatManager;
    private Chat chat;

    private ChatListAdapter adapter;

    private String username;
    private final int MESSAGE_FLAG = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = getIntent().getStringExtra("friend");
        ButterKnife.inject(this);
        initState();
        sendBtn.setOnClickListener(new SendMessageListener());
        adapter = new ChatListAdapter(this);
        messageList.setAdapter(adapter);
    }

    private class SendMessageListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String message = messageEdit.getText().toString().trim();
            if (TextUtils.isEmpty(message) || chatManager == null) return;
            try {
                chat.sendMessage(message);
            } catch (XMPPException e) {
                e.printStackTrace();
            } catch (SmackException.NotConnectedException e) {
                e.printStackTrace();
            }
            ChatMessage chatMessage = new ChatMessage().setLocal(true).setContent(message);
            addMessageToList(chatMessage);
            messageEdit.setText("");
        }
    }

    private void addMessageToList(ChatMessage message) {
        adapter.addMessage(message);
    }



    private void initState() {
        getSupportActionBar().setTitle(XMPPManager.getInstance().getState().getMessage());
        chatManager = ChatManager.getInstanceFor(XMPPManager.getInstance().connection);
        chatManager.addChatListener(new ChatManagerListener() {
            @Override
            public void chatCreated(Chat chat, boolean createdLocally) {
                if (createdLocally) {
                   // Chat created by myself
                } else {
                   // Chat created by others
                    chat.addMessageListener(chatMessageListener);
                }

            }
        });

        if (!TextUtils.isEmpty(username)) {
            chat = chatManager.createChat(username + "@" + XMPPManager.serverName, chatMessageListener);
        }

    }

    private MessageListener chatMessageListener = new MessageListener() {
        @Override
        public void processMessage(Chat chat, Message message) {
            // Process the incoming message.
            android.os.Message sendMessage = new android.os.Message();
            sendMessage.what = MESSAGE_FLAG;
            sendMessage.obj = message.getBody();
            handler.sendMessage(sendMessage);
        }
    };

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            if (msg.what == MESSAGE_FLAG) {
                String message = (String) msg.obj;
                addMessageToList(new ChatMessage().setLocal(false).setContent(message));
            }

        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_connect) {
            if (XMPPManager.getInstance().getState() == XMPPState.NOT_CONNECTED) {
                XMPPManager.getInstance().reConnect();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            XMPPManager.getInstance().connection.disconnect();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
    }
}
