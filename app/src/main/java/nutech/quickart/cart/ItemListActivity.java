package nutech.quickart.cart;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.transition.Slide;
import android.transition.TransitionInflater;

import android.view.View;


import nutech.quickart.cart.Assist.CartAdapter;

import java.util.ArrayList;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * by Shemar Antonio Henry
 */

public class ItemListActivity extends AppCompatActivity {

    private CartAdapter cartAdapter;
    private ArrayList<Item> checkOut; // Was ArrayList
    private String CODEKEY = "DEEZNUTZGODIM";
    private int codenum = 0000000;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cartAdapter.getContent().isEmpty() != true) {
                    checkOut();
                    Snackbar.make(view, "Collect Items At Supermarket with code: " + code, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else{
                    Snackbar.make(view, "FIRST ADD ITEMS TO CART", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        View recyclerView = findViewById(R.id.item_list); ////make this to  RECYCLER VIEW
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        setupWindowAnimations();
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        cartAdapter = new CartAdapter(this, "Milk");
        recyclerView.setAdapter(cartAdapter);
    }

    private void setupWindowAnimations() {
        Slide slide = (Slide) TransitionInflater.from(this).inflateTransition(R.transition.item_select_transition);
        getWindow().setExitTransition(slide);
    }

    public void checkOut(){

        checkOut = cartAdapter.getContent();
        cartAdapter.emptyCart();
        codenum +=1;
        code = CODEKEY + Integer.toString(codenum);
    }

}
