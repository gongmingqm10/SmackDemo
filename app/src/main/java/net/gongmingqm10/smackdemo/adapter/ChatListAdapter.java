package net.gongmingqm10.smackdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.gongmingqm10.smackdemo.R;
import net.gongmingqm10.smackdemo.model.ChatMessage;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class ChatListAdapter extends BaseAdapter {
    private Context context;
    private List<ChatMessage> messages;

    public ChatListAdapter(Context context) {
        this.messages = new ArrayList<ChatMessage>();
        this.context = context;
    }

    public void addMessage(ChatMessage message) {
        messages.add(message);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ChatMessage chatMessage = (ChatMessage) getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.message_left, parent, false);
            holder.content = ButterKnife.findById(convertView, R.id.content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.content.setText(chatMessage.getContent());
        return convertView;
    }

    private class ViewHolder {
        TextView content;
    }
}
