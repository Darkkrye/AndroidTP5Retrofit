package fr.opnf.androidtp5retrofit;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import fr.opnf.androidtp5retrofit.Model.User;
import fr.opnf.androidtp5retrofit.Tools.GithubService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    // Views
    LinearLayout ll_searchLayout;
    LinearLayout ll_result_layout;

    ProgressBar pb_loading;

    EditText et_username;

    Button btn_search;
    Button btn_github_redirect;

    CircleImageView civ_profile_image;

    TextView tv_name;
    TextView tv_pseudo;
    TextView tv_followers;

    // Variables
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get Views
        this.ll_searchLayout = (LinearLayout) findViewById(R.id.ll_search_layout);
        this.ll_result_layout = (LinearLayout) findViewById(R.id.ll_result_layout);

        this.pb_loading = (ProgressBar) findViewById(R.id.pb_loading);

        this.et_username = (EditText) findViewById(R.id.et_username);

        this.btn_search = (Button) findViewById(R.id.btn_search);
        this.btn_github_redirect = (Button) findViewById(R.id.btn_github_redirect);

        this.civ_profile_image = (CircleImageView) findViewById(R.id.civ_profile_image);

        this.tv_name = (TextView) findViewById(R.id.tv_name);
        this.tv_pseudo = (TextView) findViewById(R.id.tv_pseudo);
        this.tv_followers = (TextView) findViewById(R.id.tv_followers);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show / Hide
                pb_loading.setVisibility(View.VISIBLE);
                hideKeyboard();

                String username = et_username.getText().toString().trim();
                if (username.isEmpty())
                    showError("Please write a username");
                else {
                    search(username);
                }
            }
        });

        btn_github_redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (user != null) {
                    Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse(user.getHtml_url()));
                    startActivity(viewIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "Vous essayez d'accéder à quelque chose auquel vous n'avez pas le droit :P", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void search(String username) {
        // Retrofit Builder
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GithubService.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GithubService apiService = retrofit.create(GithubService.class);

        Call<User> call = apiService.getUser(GithubService.TOKEN, username);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                int statusCode = response.code();
                User user = response.body();

                if (user != null)
                    showUser(user, statusCode);
                else
                    showError("User does not exist !");
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                showError("Une erreur est survenue !");
            }
        });
    }

    private void showUser(User user, int statusCode) {
        // Save current user
        this.user = user;

        // Fill with data
        Picasso.with(getApplicationContext()).load(user.getAvatar_url()).into(civ_profile_image);

        if (user.getName() == null)
            tv_name.setText("Nom : (Aucun nom n'a été renseigné)");
        else
            tv_name.setText("Nom : " + user.getName());

        tv_pseudo.setText("Pseudo : " + user.getLogin());
        tv_followers.setText("Followers : " + user.getFollowers());

        // Toggles
        pb_loading.setVisibility(View.GONE);
        ll_result_layout.setVisibility(View.VISIBLE);
        civ_profile_image.setVisibility(View.VISIBLE);
        btn_github_redirect.setVisibility(View.VISIBLE);
    }

    private void showError(String message) {
        // Show message
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        et_username.requestFocus();

        // Toggles
        pb_loading.setVisibility(View.GONE);
        ll_result_layout.setVisibility(View.GONE);
        civ_profile_image.setVisibility(View.GONE);
        btn_github_redirect.setVisibility(View.GONE);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.ll_searchLayout.getWindowToken(), 0);
    }
}
