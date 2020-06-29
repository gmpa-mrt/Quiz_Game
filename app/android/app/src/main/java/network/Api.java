package network;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    @POST("/api/user")
    Call<User> createUser(@Body User user);

    @POST("/api/auth")
    Call<User> auth(@Body User user);

    @GET("/api/theme")
    Call<List<Theme>> getTheme();

    @POST("api/theme")
    Call<Theme> postTheme(@Body Theme theme);

    @POST("/api/quiz")
    Call<Quiz> postQuiz(@Body Quiz quiz);

    @GET("/api/question")
    Call<List<Question>> getQuestion();

    @POST("/api/question")
    Call<Question> postQuestion(@Body Question question);

    @GET("/api/answer")
    Call<List<Answer>> getAnswerList();

    @PATCH("/api/quiz/{id}")
    Call<Quiz> updateQuiz(@Path("id") Integer id, @Body Quiz quiz);

};

