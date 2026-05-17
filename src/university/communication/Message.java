package university.communication;

import java.io.Serializable;
import java.util.Date;

import university.employees.Employee;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    private String messageId;
    private Employee sender;
    private Employee receiver;
    private String content;
    private Date date;

    public Message(String messageId, Employee sender, Employee receiver, String content) {
        this.messageId = messageId;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.date = new Date();
    }

    public String getMessageId() {
        return messageId;
    }

    public Employee getSender() {
        return sender;
    }

    public Employee getReceiver() {
        return receiver;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return new Date(date.getTime());
    }

    @Override
    public String toString() {
        return "[" + date + "] " + sender.getName() + " -> " + receiver.getName() + ": " + content;
    }
}
