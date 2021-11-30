package com.my.clickapp.act;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.my.clickapp.GPSTracker;
import com.my.clickapp.Preference;
import com.my.clickapp.R;
import com.my.clickapp.act.actProvider.HomeActivityProvider;
import com.my.clickapp.databinding.ActivityLoginBinding;
import com.my.clickapp.model.LoginModel;
import com.my.clickapp.utils.RetrofitClients;
import com.my.clickapp.utils.SessionManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    ActivityLoginBinding binding;
    private AlertDialog  alertDialog;
    String Login="";

    String Email="";
    String Password ="";
    GPSTracker gpsTracker;

    String token="";
    private SessionManager sessionManager;
    String latitude="";
    String longitude="";

    //Google SignIn
    private RelativeLayout RR_faceBook_login;
    private RelativeLayout RR_google_login;
    private SignInButton signInButton;
    FirebaseAuth mAuth;
    private final static int RC_SIGN_IN = 1;
    private GoogleApiClient googleApiClient;
    private static final String TAG = "fireBaseToken";

    String Socilal_FirstName="";
    String Socilal_last_name="";
    String Socilal_email="";
    String Socilal_mobile="";
    String Socilal_city="";
    String Socilal_address="";
    String Socilal_address2="";
    String Socilal_type="";
    String social_id="";
    String social_image="";

    //FaceBook
    CallbackManager mCallbackManager;
    LoginButton loginButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        Intent intent=getIntent();
        if(intent !=null)
        {
            Login=intent.getStringExtra("LoginType");
        }

        sessionManager = new SessionManager(LoginActivity.this);

        //Gps Lat Long
        gpsTracker = new GPSTracker(LoginActivity.this);
        if (gpsTracker.canGetLocation()) {

            latitude = String.valueOf(gpsTracker.getLatitude());
            longitude = String.valueOf(gpsTracker.getLongitude());

        } else {
            gpsTracker.showSettingsAlert();
        }


        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(runnable -> {
            token = runnable.getToken();
            Log.e( "Tokennnn" ,token);
        });


        try {
            PackageInfo info = this.getPackageManager().getPackageInfo(this.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i(TAG, "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "printHashKey()", e);
        } catch (Exception e) {
            Log.e(TAG, "printHashKey()", e);
        }


        //Google SignIn
        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        //FaceBook
        loginButton = findViewById(R.id.connectWithFbButton);
        mCallbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("TAG", "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }
            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "btnCancel", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "facebook:onCancel");
                // ...
            }
            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this, "Btnerrror", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "facebook:onError", error);
                // ...
            }
        });

        binding.RRFacebook.setOnClickListener(v -> {

            loginButton.performClick();
        });

        binding.RRGoogle.setOnClickListener(v -> {

            Intent intent1 = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
            startActivityForResult(intent1, RC_SIGN_IN);

        });

        binding.llCreate.setOnClickListener(v -> {

            Intent intent2=new Intent(LoginActivity.this,CreateProfile.class);
            intent2.putExtra("LoginTypeSign",Login);
            startActivity(intent2);

        });

        binding.RRforgot.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, ForogotPassword.class));
        });

        binding.btLogin.setOnClickListener(v -> {

             if(Login.equalsIgnoreCase("User"))
            {
                Validation("User");

            }else
            {
                Validation("Provider");
               // startActivity(new Intent(LoginActivity.this, HomeActivityProvider.class));

            }
        });
    }

    //Google Login
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult( requestCode, resultCode, data );
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent( data );
            handleSignInResult( result );
        }else {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {

        if (result.isSuccess()) {

            GoogleSignInAccount account = result.getSignInAccount();

            Socilal_FirstName=account.getDisplayName();
            Socilal_last_name="";
            Socilal_email=account.getEmail();
            Socilal_mobile="";
            Socilal_city="";
            Socilal_address="";
            Socilal_address2="";
            Socilal_type="";
            social_id=account.getId();
            social_image= String.valueOf(account.getPhotoUrl());

            social_image= String.valueOf(account.getPhotoUrl());

            Toast.makeText(LoginActivity.this, ""+Socilal_FirstName, Toast.LENGTH_SHORT).show();

          if (sessionManager.isNetworkAvailable()) {

                binding.progressBar.setVisibility(View.VISIBLE);

                ApISocialogin("User");

            }else {

                Toast.makeText(this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
            }

        } else {

            Toast.makeText( this, "Login Unsuccessful", Toast.LENGTH_SHORT ).show();
        }
    }




    private void AlertDaliog() {

        LayoutInflater li;
        Button btn_no,btn_yes;
        CheckBox checkAgree;
        CheckBox checkEighty;
        AlertDialog.Builder alertDialogBuilder;
        li = LayoutInflater.from(this);
        li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.alert_dailoge, null);
        btn_yes = (Button) promptsView.findViewById(R.id.btn_yes);
        checkAgree = (CheckBox) promptsView.findViewById(R.id.checkAgree);
        checkEighty = (CheckBox) promptsView.findViewById(R.id.checkEighty);
        alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptsView);

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!checkAgree.isChecked())
                {
                    Toast.makeText(LoginActivity.this, "Please Both box Check", Toast.LENGTH_SHORT).show();

                }else if(!checkEighty.isChecked())
                {
                    Toast.makeText(LoginActivity.this, "Please Both box Check", Toast.LENGTH_SHORT).show();

                }else
                {
                    if (sessionManager.isNetworkAvailable()) {

                        binding.progressBar.setVisibility(View.VISIBLE);

                        loginMethod();

                    }else {
                        Toast.makeText(LoginActivity.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
                    }
                    alertDialog.dismiss();
                }

            }
        });


        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void Validation(String Type) {

        Email = binding.edtEmail.getText().toString();
        Password =  binding.edtPassword.getText().toString();

        if(Email.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();

        }else if(Password.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();

        }else
        {
            if(Type.equalsIgnoreCase("Provider"))
            {
                if (sessionManager.isNetworkAvailable()) {

                    binding.progressBar.setVisibility(View.VISIBLE);

                    loginMethod();

                }else {
                    Toast.makeText(LoginActivity.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
                }

            }else {

                AlertDaliog();
            }


        }
    }

    private void loginMethod(){

        Call<LoginModel> call = RetrofitClients.getInstance().getApi()
                .login(Email,Password,latitude,longitude,token);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {

                binding.progressBar.setVisibility(View.GONE);

                LoginModel finallyPr = response.body();

                String status = finallyPr.status;

                if (status.equalsIgnoreCase("1")) {

                    if(finallyPr.result.type.equalsIgnoreCase("User"))
                    {
                        Preference.save(LoginActivity.this,Preference.KEY_USER_ID,finallyPr.result.id);
                        Preference.save(LoginActivity.this,Preference.KEY_mobile,finallyPr.result.mobile);

                        Preference.save(LoginActivity.this,Preference.KEY_User_name,finallyPr.result.getName());
                        Preference.save(LoginActivity.this,Preference.KEY_User_email,finallyPr.result.email);
                        Preference.save(LoginActivity.this,Preference.KEY_USer_img,finallyPr.result.image);


                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));

                    }else {

                        Preference.save(LoginActivity.this,Preference.KEY_USER_ID,finallyPr.result.id);
                        Preference.save(LoginActivity.this,Preference.KEY_mobile,finallyPr.result.mobile);

                        Preference.save(LoginActivity.this,Preference.KEY_User_name,finallyPr.result.getName());
                        Preference.save(LoginActivity.this,Preference.KEY_User_email,finallyPr.result.email);
                        Preference.save(LoginActivity.this,Preference.KEY_USer_img,finallyPr.result.image);

                        startActivity(new Intent(LoginActivity.this, HomeActivityProvider.class));
                    }
                } else {
                    binding.progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, finallyPr.message, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ApISocialogin(String Type){

        Call<LoginModel> call = RetrofitClients
                .getInstance()
                .getApi()
                .social_login(Socilal_FirstName,Socilal_email,Socilal_mobile,token,latitude,longitude,Type,social_id,social_image);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {

                binding.progressBar.setVisibility(View.GONE);

                LoginModel finallyPr = response.body();

                String status = finallyPr.status;

                if (status.equalsIgnoreCase("1")) {

                    if(finallyPr.result.type.equalsIgnoreCase("User"))
                    {
                        Preference.save(LoginActivity.this,Preference.KEY_USER_ID,finallyPr.result.id);

                        Preference.save(LoginActivity.this,Preference.KEY_User_name,finallyPr.result.getName());
                        Preference.save(LoginActivity.this,Preference.KEY_User_email,finallyPr.result.email);
                        Preference.save(LoginActivity.this,Preference.KEY_USer_img,finallyPr.result.image);
                        Preference.save(LoginActivity.this,Preference.KEY_mobile,finallyPr.result.mobile);

                        //startActivity(new Intent(LoginActivity.this, MemberShipActivity.class));
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));

                    }else {

                        Preference.save(LoginActivity.this,Preference.KEY_USER_ID,finallyPr.result.id);

                        Preference.save(LoginActivity.this,Preference.KEY_User_name,finallyPr.result.getName());
                        Preference.save(LoginActivity.this,Preference.KEY_User_email,finallyPr.result.email);
                        Preference.save(LoginActivity.this,Preference.KEY_USer_img,finallyPr.result.image);
                        Preference.save(LoginActivity.this,Preference.KEY_mobile,finallyPr.result.mobile);

                        startActivity(new Intent(LoginActivity.this, HomeActivityProvider.class));
                    }

                } else {
                    binding.progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, finallyPr.message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {

                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("TAG", "handleFacebookAccessToken:" + token);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //   Toast.makeText(Activity_LoginOption.this, ""+token, Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();

                            String UsernAME=user.getDisplayName();
                            String email=user.getEmail();
                            String SocialId=user.getUid();
                            Uri Url=user.getPhotoUrl();

                            Socilal_FirstName=user.getDisplayName();
                            Socilal_last_name="";
                            Socilal_email=user.getEmail();
                            Socilal_mobile="";
                            Socilal_city="";
                            Socilal_address="";
                            Socilal_address2="";
                            Socilal_type="";
                            social_id=user.getUid();

                            social_image= String.valueOf(user.getPhotoUrl());

                            if (sessionManager.isNetworkAvailable()) {

                                binding.progressBar.setVisibility(View.VISIBLE);

                                ApISocialogin("Manager");

                            }else {

                                Toast.makeText(LoginActivity.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
                            }

                            Toast.makeText(LoginActivity.this, ""+UsernAME, Toast.LENGTH_SHORT).show();
                        } else {

                            Toast.makeText(LoginActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}