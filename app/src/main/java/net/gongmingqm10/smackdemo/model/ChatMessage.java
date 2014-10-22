package net.gongmingqm10.smackdemo.model;

public class ChatMessage {
    private String content;
    private boolean isLocal;

    public String getContent() {
        return content;
    }

    public ChatMessage setContent(String content) {
        this.content = content;
        return this;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public ChatMessage setLocal(boolean isLocal) {
        this.isLocal = isLocal;
        return this;
    }
}
