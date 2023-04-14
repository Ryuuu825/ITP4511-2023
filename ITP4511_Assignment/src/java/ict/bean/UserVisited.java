package ict.bean;
import java.io.Serializable;

public class UserVisited implements Serializable{
    private int count;

    public UserVisited(int count) {
        this.count = count;
    }

    public UserVisited() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
