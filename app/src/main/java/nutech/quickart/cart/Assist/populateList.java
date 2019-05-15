package nutech.quickart.cart.Assist;

import java.util.ArrayList;

import nutech.quickart.cart.Item;
import nutech.quickart.cart.R;

public class populateList {

    private int[] myImageList = new int[]{R.drawable.benz, R.drawable.bike,
            R.drawable.car,R.drawable.carrera
            ,R.drawable.ferrari,R.drawable.harly,
            R.drawable.lamborghini,R.drawable.silver};

    private String[] myImageNameList = new String[]{"Benz", "Bike",
            "Car","Carrera"
            ,"Ferrari","Harly",
            "Lamborghini","Silver"};

    private float[] myImageCostList = new float[]{1000, 1000, 1000,1000,1000,1000, 1000,1000};

    public ArrayList<Item> populateList(){

        ArrayList<Item> list = new ArrayList<>();

        for(int i = 0; i < 8; i++){
            Item item = new Item(myImageNameList[i], myImageCostList[i], myImageList[i]);
            list.add(item);
        }

        return list;
    }
}
