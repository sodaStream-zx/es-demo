package zxx.pri.core.entity;

/**
 * @Author : liyan
 * @Description : TODO
 * @date : 2019-06-05 13:53
 **/
public class SearchKeysEntity {
    private Integer read;
    private long id;
    private String searchKey;

    public Integer getRead() {
        return read;
    }

    public void setRead(Integer read) {
        this.read = read;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    @Override
    public String toString() {
        return "searchKeys{" +
                "read=" + read +
                ", id=" + id +
                ", searchKey='" + searchKey + '\'' +
                '}';
    }
}
