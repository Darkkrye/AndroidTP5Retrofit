package fr.opnf.androidtp5retrofit.Tools;

import fr.opnf.androidtp5retrofit.Model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * Created by OpenFieldMacMini on 24/05/2016.
 */
public interface GithubService {

    public final static String ENDPOINT = "https://api.github.com";
    public final static String TOKEN = "";
    public final static String SEARCHEDUSER = "Darkkrye";

    @GET("users/{username}")
    Call<User> getUser(@Header("authorization") String authorization, @Path("username") String username);
}
