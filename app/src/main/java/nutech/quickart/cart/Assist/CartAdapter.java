package nutech.quickart.cart.Assist;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import nutech.quickart.cart.Item;
import nutech.quickart.cart.R;

/**
 * The Adapter that Feeds the
 * Shopper/Client's Cart Recycler
 * Viewer in activity_item_list.
 * by Shemar Antonio Henry
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context context;
    public static ArrayList<Item> CartArrayList = new ArrayList<>(); //was ArrayList

    public CartAdapter(Context context, String query) {

        this.context = context;
        new Searcher().execute();
        //new FetchItemsTask(query).execute();
    }

    public void remove(int position) {
        CartArrayList.remove(position);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_content, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.name.setText(CartArrayList.get(position).getName());
        holder.cost.setText(CartArrayList.get(position).getCost());
        //holder.image.setImageBitmap(CartArrayList.get(position).getImage());
        holder.image.setImageResource(CartArrayList.get(position).getImageDrawable());
        holder.options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.options, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (CartArrayList.isEmpty()) {
            return 0;
        }
        return CartArrayList.size();
    }

    private void showPopupMenu(View view, int position) {
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.cart_options, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(position));
        popup.show();
    }

    public ArrayList<Item> getContent() {
        if (CartArrayList != null) {
            return this.CartArrayList;
        }
        return new ArrayList<>();
    }

    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        private int oPosition;

        public MyMenuItemClickListener(int position){ oPosition = position;}

        @Override
        public boolean onMenuItemClick(MenuItem item) {

            switch (item.getItemId()){
                case R.id.delete_item:
                    remove(this.oPosition);//delete
                    Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show();
                    return true;

                 default:
            }
            return false;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        protected TextView name;
        private ImageView image;
        private TextView cost;
        private ImageView options;

        ViewHolder(View view) {
                super(view);
                name = (TextView) view.findViewById(R.id.id_text);
                cost = (TextView) view.findViewById(R.id.cost);
                image = (ImageView) view.findViewById(R.id.item_image);
                options = (ImageView) view.findViewById(R.id.menu);
            }

    }

    public void emptyCart(){
        int i = getItemCount();
        while (i>0){
            remove(i-1);
            i--;
        }
    }

    public class Searcher extends AsyncTask<Void, String, ArrayList<Item>> {

        private final String SEARCH_URL = "http://10.0.2.2:5000/supermercado/api/v1.0"; //not connecting
        private String mStringData = "";
        public ArrayList<Item> RESULTS = new ArrayList<>();


        @Override
        protected ArrayList<Item> doInBackground(Void... params) {

            try {
                URL searchURL = new URL(SEARCH_URL);
                HttpURLConnection connection = (HttpURLConnection) searchURL.openConnection();
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                String line = "";
                while (line != null){
                    line = reader.readLine();
                    mStringData += line;
                }

                JSONArray resultARRAY = new JSONArray(mStringData);
                for(int i = 0; i < resultARRAY.length(); i++){
                    Item item = new Item();
                    JSONObject searchItem = (JSONObject) resultARRAY.get(i);
                    item.setName(searchItem.getString("title"));
                    item.setCost((float) searchItem.getDouble("price"));
                    //item.setImageURL(searchItem.getString("image"));
                    this.RESULTS.add(item);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            CartArrayList = RESULTS;

            return RESULTS;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(ArrayList<Item> items) {
            super.onPostExecute(items);
            //CartArrayList = items;//fix
        }

        public ArrayList<Item> getRESULTS() {
            return RESULTS;
        }
    }
}