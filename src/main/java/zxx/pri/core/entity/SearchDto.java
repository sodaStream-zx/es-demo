package zxx.pri.core.entity;

import java.io.Serializable;

/**
 * @program: yunce-cloud
 * @description:
 * @author: TangChao
 * @create: 2019-04-15 17:28
 **/

public class SearchDto implements Serializable {
    private Long id;
    private String searchKeys;

    public SearchDto() {
    }

    public SearchDto(Long id, String searchKeys) {
        this.id = id;
        this.searchKeys = searchKeys;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSearchKeys() {
        return searchKeys;
    }

    public void setSearchKeys(String searchKeys) {
        this.searchKeys = searchKeys;
    }

    @Override
    public String toString() {
        return "SearchDto{" +
                "id=" + id +
                ", searchKeys='" + searchKeys + '\'' +
                '}';
    }
}
