package pl.konradcygal.expressexample;

public class ChatItem {
    private String msg;
    private int type;

    ChatItem(String msg, int type) {
        this.msg = msg;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public String getMsg() {
        return msg;
    }
}
