package network;

public class Question {

    private Integer id;
    private Integer theme_id;
    private String wording;


    public Question(Integer theme_id) {
       // this.wording = wording;
        this.theme_id = theme_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTheme_id() {
        return theme_id;
    }

    public void setTheme_id(Integer theme_id) {
        this.theme_id = theme_id;
    }

    public String getWording() {
        return wording;
    }

    public void setWording(String wording) {
        this.wording = wording;
    }
}
