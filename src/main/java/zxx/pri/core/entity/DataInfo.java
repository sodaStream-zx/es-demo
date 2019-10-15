package zxx.pri.core.entity;

/**
 * 数据详情表  t_data_info
 *
 * @program: yunce-cloud
 * @description:
 * @author: TangChao
 * @create: 2019-01-18 14:39
 **/

public class DataInfo {
    //'主键'
    private Long id;
    //'手机序列号'
    private String imie;
    //'标题'
    private String title;
    //'内容'
    private String content;
    //'内容分词'
    private String contentWords;
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
    //'一条数据的风险值'
    private Float riskValue;
    //'作风，司法，机会，综合素质'
    private String typeLabel;
    //'是否已读'
    private Integer read;
    //'是否预警'
    private Integer warn;
    //'预警级别'
    private String warnLevel;
    //'预警'
    private String warnLabel;
    //点击
    private Integer click;
    //点赞
    private Integer zanTotal;
    //回复或评论
    private Integer repeatTotal;
    //转发
    private Integer forward;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
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

    public String getContentWords() {
        return contentWords;
    }

    public void setContentWords(String contentWords) {
        this.contentWords = contentWords;
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

    public Float getRiskValue() {
        return riskValue;
    }

    public void setRiskValue(Float riskValue) {
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

    public Integer getClick() {
        return click;
    }

    public void setClick(Integer click) {
        this.click = click;
    }

    public Integer getZanTotal() {
        return zanTotal;
    }

    public void setZanTotal(Integer zanTotal) {
        this.zanTotal = zanTotal;
    }

    public Integer getRepeatTotal() {
        return repeatTotal;
    }

    public void setRepeatTotal(Integer repeatTotal) {
        this.repeatTotal = repeatTotal;
    }

    public Integer getForward() {
        return forward;
    }

    public void setForward(Integer forward) {
        this.forward = forward;
    }

}
