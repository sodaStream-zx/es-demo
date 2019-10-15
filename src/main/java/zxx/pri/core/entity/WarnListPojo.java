package zxx.pri.core.entity;

import java.util.List;

/**
 * @Author : liyan
 * @Description : 列表时间或风险度排序
 * @date : 2019-01-03 9:59
 **/
public class WarnListPojo {

    //主键id
    private long id;
    //预警级别
    private String warnLevel;
    //文章来源
    private String sourceName;
    private String author;
    //标题
    private String title;
    //内容
    private String content;
    //时间
    private String publishDate;
    //作风，司法，机会，综合素质
    private String typeLabel;
    //是否已读
    private String read;
    //原文链接
    private String url;
    private float riskValue;
    private List<SearchKeysEntity> searchKeys;

    private Long realId;

    public Long getRealId() {
        return realId;
    }

    public void setRealId(Long realId) {
        this.realId = realId;
    }

    public List<SearchKeysEntity> getSearchKeys() {
        return searchKeys;
    }

    public void setSearchKeys(List<SearchKeysEntity> searchKeys) {
        this.searchKeys = searchKeys;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public float getRiskValue() {
        return riskValue;
    }

    public void setRiskValue(float riskValue) {
        this.riskValue = riskValue;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWarnLevel() {
        return warnLevel;
    }

    public void setWarnLevel(String warnLevel) {
        this.warnLevel = warnLevel;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getTypeLabel() {
        return typeLabel;
    }

    public void setTypeLabel(String typeLabel) {
        this.typeLabel = typeLabel;
    }


    @Override
    public String toString() {
        return "WarnListPojo{" +
                "id=" + id +
                ", warnLevel='" + warnLevel + '\'' +
                ", sourceName='" + sourceName + '\'' +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", publishDate='" + publishDate + '\'' +
                ", typeLabel='" + typeLabel + '\'' +
                ", read='" + read + '\'' +
                ", url='" + url + '\'' +
                ", riskValue=" + riskValue +
                '}';
    }
}
