package zxx.pri.core.entity;

/**
 * @Author : liyan
 * @Description : TODO
 * @date : 2019-05-17 15:56
 **/
public class DataPojo {

    //'主键'
    private Long id;

    //'标题'
    private String title;
    //'内容'
    private String content;
    //'作者'
    private String author;
    //'时间'
    private String publishDate;
    //'数据来源类型'
    private String type;
    //'链接url'
    private String url;
    //'媒体来源'
    private String sourceName;
    //'正中负'
    private String appraise;

    private Long time;

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getAppraise() {
        return appraise;
    }

    public void setAppraise(String appraise) {
        this.appraise = appraise;
    }

    @Override
    public String toString() {
        return "DataPojo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                ", publishDate='" + publishDate + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", sourceName='" + sourceName + '\'' +
                ", appraise='" + appraise + '\'' +
                '}';
    }
}
