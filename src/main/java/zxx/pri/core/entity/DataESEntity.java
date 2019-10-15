package zxx.pri.core.entity;

import java.io.Serializable;

/**
 * @Author : liyan
 * @Description : TODO
 * @date : 2019-03-08 11:14
 **/
public class DataESEntity implements Serializable {
    private Long id;
    private float riskValue;
    private String typeLabel;
    private Integer read;
    private String author;
    private Integer forward;
    private String publishDate;
    private Integer zanTotal;
    private String sourceName;
    private String title;
    private String type;
    private Integer click;
    private String content;
    private String url;
    private Integer repeatTotal;
    private String appraise;
    private Integer warn;
    private String warnLevel;
    private String warnLabel;
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getRiskValue() {
        return riskValue;
    }

    public void setRiskValue(float riskValue) {
        this.riskValue = riskValue;
    }

    public String getTypeLabel() {
        return typeLabel;
    }

    public void setTypeLabel(String typeLabel) {
        this.typeLabel = typeLabel;
    }

    public Integer getRead() {
        return read;
    }

    public void setRead(Integer read) {
        this.read = read;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getForward() {
        return forward;
    }

    public void setForward(Integer forward) {
        this.forward = forward;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public Integer getZanTotal() {
        return zanTotal;
    }

    public void setZanTotal(Integer zanTotal) {
        this.zanTotal = zanTotal;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getClick() {
        return click;
    }

    public void setClick(Integer click) {
        this.click = click;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getRepeatTotal() {
        return repeatTotal;
    }

    public void setRepeatTotal(Integer repeatTotal) {
        this.repeatTotal = repeatTotal;
    }

    public String getAppraise() {
        return appraise;
    }

    public void setAppraise(String appraise) {
        this.appraise = appraise;
    }

    public Integer getWarn() {
        return warn;
    }

    public void setWarn(Integer warn) {
        this.warn = warn;
    }

    public String getWarnLevel() {
        return warnLevel;
    }

    public void setWarnLevel(String warnLevel) {
        this.warnLevel = warnLevel;
    }

    public String getWarnLabel() {
        return warnLabel;
    }

    public void setWarnLabel(String warnLabel) {
        this.warnLabel = warnLabel;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "DataESEntity{" +
                "riskValue=" + riskValue +
                ", typeLabel='" + typeLabel + '\'' +
                ", read=" + read +
                ", author='" + author + '\'' +
                ", forward=" + forward +
                ", publishDate='" + publishDate + '\'' +
                ", zanTotal=" + zanTotal +
                ", sourceName='" + sourceName + '\'' +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", click=" + click +
                ", content='" + content + '\'' +
                ", url='" + url + '\'' +
                ", repeatTotal=" + repeatTotal +
                ", appraise='" + appraise + '\'' +
                ", warn=" + warn +
                ", warnLevel='" + warnLevel + '\'' +
                ", warnLabel='" + warnLabel + '\'' +
                ", userId=" + userId +
                '}';
    }
}
