package kr.puze.imagediary.Data;

public class DiaryData {
    private String date = "";
    private String image = "";
    private String title = "";
    private String content = "";

    public DiaryData(){
    }

    public DiaryData(String date, String image, String title, String content) {
        this.date = date;
        this.image = image;
        this.title = title;
        this.content = content;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
}
