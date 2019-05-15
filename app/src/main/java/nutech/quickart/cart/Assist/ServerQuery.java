package nutech.quickart.cart.Assist;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import nutech.quickart.cart.Item;

/**
 * An Asynchronous task to populate the
 * List/Database that is used by CartAdapter
 * from Server; parsing a URI requested JSON
 * by Shemar Antonio Henry
 */

public class ServerQuery { //URI server query class (not in use)

    private static final String TAG = "Supermercado";
    private static String USER = "android_client";
    private static String USER_PASSWORD = "";// Add password before execution
    private static final String SEARCH_PATH = "search=";
    private static final String SCHEME = "https";
    private static final String HOST = "10.0.2.2";
    private static final int PORT = 5000;
    private static final Uri CONNECT = Uri
            .parse(SCHEME + "//" + HOST + ":" + Integer.toBinaryString(PORT) + "/" + SEARCH_PATH)
            .buildUpon()
            //.appendQueryParameter("format", "json") // maybe not necessary
            //.appendQueryParameter("nojsoncallback", "1") // maybe not necessary
            //.appendQueryParameter("extras", "url_s") // maybe not necessary
            //.appendQueryParameter("user",USER)
            //.appendQueryParameter("user_password", USER_PASSWORD)
            .build();

    public ArrayList<Item> searchItem(String query) {
        String url = buildUrl(query);
        return downloadGalleryItems(url);
    }

    private String buildUrl(String query) {
        Uri.Builder uriBuilder = CONNECT.buildUpon()
                .appendQueryParameter("text", query);

        return uriBuilder.build().toString();
    }

    private ArrayList<Item> downloadGalleryItems(String url) {
        ArrayList<Item> items = new ArrayList<>();

        try {
            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON: " + jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);
            parseItems(items, jsonBody);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        }

        return items;
    }

    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    private void parseItems(List<Item> items, JSONObject jsonBody)
            throws IOException, JSONException {

        JSONObject JsonObject = jsonBody.getJSONObject("title");
        JSONArray JsonArray = JsonObject.getJSONArray("title");

        for (int i = 0; i < JsonArray.length(); i++) {
            JSONObject itemJsonObject = JsonArray.getJSONObject(i);

            Item item = new Item();
            item.setID(itemJsonObject.getString("id"));
            item.setName(itemJsonObject.getString("title"));
            item.setCost(Float.parseFloat(itemJsonObject.getString("price")));
            item.setImageURL(itemJsonObject.getString("image"));
            items.add(item);
        }
    }

    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                        ": with " +
                        urlSpec);
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }
}
