package net.gongmingqm10.smackdemo.xmpp;

public enum XMPPState {

    NOT_CONNECTED("Not connected"), CONNECTED("Connected"), AUTHENTICATED("Authenticated");

    private String message;

    private XMPPState(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
