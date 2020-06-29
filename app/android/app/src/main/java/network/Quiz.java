package network;

public class Quiz {

    private Integer id;
    private Integer user_id;
    private Integer theme_id;
    private Integer score;

    public Quiz(Integer user_id, Integer theme_id, Integer score){
        this.user_id = user_id;
        this.theme_id = theme_id;
        this.score = score;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getTheme_id() {
        return theme_id;
    }

    public void setTheme_id(Integer theme_id) {
        this.theme_id = theme_id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
