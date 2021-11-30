package com.my.clickapp.act;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.daprlabs.cardstack.SwipeDeck;
import com.my.clickapp.Preference;
import com.my.clickapp.R;
import com.my.clickapp.adapter.DeckAdapter;
import com.my.clickapp.databinding.ActivityShopAnimationShopBinding;
import com.my.clickapp.model.CourseModal;
import com.my.clickapp.utils.RetrofitClients;
import com.my.clickapp.utils.SessionManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopAnimationActivityShop extends AppCompatActivity {


    ActivityShopAnimationShopBinding binding;

    // for our array list and swipe deck.

    private ArrayList<CourseModal.Result> courseModalArrayList;
    int Pos=0;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shop_animation_shop);

        // on below line we are initializing our array list and swipe deck.
        courseModalArrayList = new ArrayList<>();

        sessionManager = new SessionManager(ShopAnimationActivityShop.this);

        binding.RRback.setOnClickListener(v -> {
            onBackPressed();
        });


        if (sessionManager.isNetworkAvailable()) {

            binding.progressBar.setVisibility(View.VISIBLE);

            getNearesShop();

        }else {
            Toast.makeText(ShopAnimationActivityShop.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
        }

        // on below line we are setting event callback to our card stack.
        binding.cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {
                // on card swipe left we are displaying a toast message.
                Toast.makeText(ShopAnimationActivityShop.this, "Card Swiped Left", Toast.LENGTH_SHORT).show();
                Pos =position;
            }
            @Override
            public void cardSwipedRight(int position) {
                // on card swiped to right we are displaying a toast message.
                Toast.makeText(ShopAnimationActivityShop.this, "Card Swiped Right", Toast.LENGTH_SHORT).show();
                Pos =position;
            }

            @Override
            public void cardsDepleted() {
                // this method is called when no card is present
                if(courseModalArrayList!=null)
                {
                    setList(courseModalArrayList);
                }
            }

            @Override
            public void cardActionDown() {
                // this method is called when card is swiped down.
                Log.i("TAG", "CARDS MOVED DOWN");
            }

            @Override
            public void cardActionUp() {
                // this method is called when card is moved up.
                Log.i("TAG", "CARDS MOVED UP");
            }
        });


    }



    public void getNearesShop() {

        String Category_id = Preference.get(ShopAnimationActivityShop.this,Preference.KEY_category_id);

        if(Category_id.equalsIgnoreCase("0"))
        {
            Category_id="";
        }

        Call<CourseModal> call = RetrofitClients
                .getInstance()
                .getApi()
                .get_shop_by_category(Category_id);
        call.enqueue(new Callback<CourseModal>() {
            @Override
            public void onResponse(Call<CourseModal> call, Response<CourseModal> response) {
                try {

                    binding.progressBar.setVisibility(View.GONE);

                    CourseModal myclass = response.body();

                    String status = String.valueOf(myclass.getStatus());
                    String message = myclass.getMessage();

                    if (status.equalsIgnoreCase("1")) {

                        courseModalArrayList= (ArrayList<CourseModal.Result>) myclass.getResult();

                        setList(courseModalArrayList);

                    } else {

                        Toast.makeText(ShopAnimationActivityShop.this, message, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<CourseModal> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(ShopAnimationActivityShop.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setList(ArrayList<CourseModal.Result> courseModalArrayList) {

        final DeckAdapter adapter = new DeckAdapter(courseModalArrayList, this);
        // on below line we are setting adapter to our card stack.
        binding.cardStack.setAdapter(adapter);
    }


}