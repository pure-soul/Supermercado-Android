package nutech.quickart.cart;

import android.graphics.Bitmap;

public class Item {

    private int Quantity;
    private String iName;
    private String iID;
    private String iContent;
    private float iCost;
    private Bitmap iBitImage;
    private int iQuantity;
    private int mIsleNum;
    private String iImageURL;// to replace bitmap
    private int image_drawable;


    public Item() {
        this.image_drawable = R.drawable.silver;//for testing
    }

    public void setID(String id) {
        this.iID = id;
    }

    public void setName(String name) {
        this.iName = name;
    }

    public void setContent(String content) {
        this.iContent = content;
    }

    public void setCost(float cost) {
        this.iCost = cost;
    }

    public void setBitImage(Bitmap bitimage) {
        this.iBitImage = bitimage;
    }

    public void setImageURL(String image) {
        this.iImageURL = image;
    }

    public Item(String id, String name, String content, float cost, Bitmap image) {
        iID = id;
        iName = name;
        iContent = content;
        iCost = cost;
        iBitImage = image;
    }

    public Item(String id, String name, String content, float cost, String image, int quan, int isle_num) {
        iID = id;
        iName = name;
        iContent = content;
        iCost = cost;
        iImageURL = image;
        iQuantity = quan;
        mIsleNum = isle_num;
    }

    public Item(String name, float cost, int drawable){
        iName = name;
        iCost = cost;
        image_drawable = drawable;
    }

    public String getID() {
        return iID;
    }

    public String getName() {
        return iName;
    }

    public String getContent() {
        return iContent;
    }

    public String getCost() {
        return Float.toString(iCost);
    }

    public Bitmap getImage() {
        return iBitImage;
    }

    public int getImageDrawable() {
        return image_drawable;
    }
}
