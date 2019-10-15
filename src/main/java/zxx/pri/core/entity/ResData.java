package zxx.pri.core.entity;

public class ResData<T> extends ResDto {
    private T data;

    public ResData(Integer status, String message, T data) {
        super(status, message);
        this.data = data;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String toString() {
        return "ResData{data=" + this.data + '}';
    }
}