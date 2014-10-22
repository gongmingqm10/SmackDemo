SmackDemo
=========

A demo for IM in Android based on XMPP protocol. With the server of Openfire.

IM实时通讯在Android中的实现，通过本应用可以实现实时聊天。本应用为研究Openfire的demo，更多功能扩展请移步[Openfire](http://www.igniterealtime.org/)

###How
Please config the folowing parameters with your own Openfire server address and your servername.

```
    private final String serverAddress = "replaceWithYourIP"; // Your server address or IP
    public static final String serverName = "replaceWithYourServerName"; //xmpp name or your server name
```

###Website
[http://www.gongmingqm10.net/blog/2014/10/21/openfire-android-im-kuang-jia-shi-yong/](http://www.gongmingqm10.net/blog/2014/10/21/openfire-android-im-kuang-jia-shi-yong/)

###Note
1. Make sure you have added the Internet permission in your manifest to avoid [org.jivesoftware.smack.SmackException$NotConnectedException, `<uses-permission android:name="android.permission.INTERNET"/>`.
2. When you want to use `ChatManeger.createChat(String userJID, MessageListener listener)`, please make sure userJID is correct. Currently I use `yourusername@servicename`, otherwise the chat cannot be created correctly. More details please see [here](http://stackoverflow.com/questions/21093383/what-should-be-the-jid-for-a-user-in-openfire-server)


###About me
I am a Android developer works at ThoughtWorks Inc.

Personal blog: [http://www.gongmingqm10.net/](http://www.gongmingqm10.net/).

Any problem, don't hesitate to make a pull request.
