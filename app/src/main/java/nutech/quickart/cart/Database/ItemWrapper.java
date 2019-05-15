package nutech.quickart.cart.Database;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.graphics.BitmapFactory;

import java.text.ParseException;

import nutech.quickart.cart.Item;

public class ItemWrapper extends CursorWrapper {

    public  ItemWrapper(Cursor cursor){super(cursor);}

    public Item getItem() throws ParseException {

        Item item;
        String item_id = getString(getColumnIndex(ItemSchema.Item.Col.ITEMID));
        String item_name = getString(getColumnIndex(ItemSchema.Item.Col.ITEM));
        String content = getString(getColumnIndex(ItemSchema.Item.Col.CONTENT));
        String cost = getString(getColumnIndex(ItemSchema.Item.Col.COST));
        byte[] image  =  getBlob(getColumnIndex(ItemSchema.Item.Col.IMAGE));

        item = new Item(item_id, item_name, content, Float.parseFloat(cost),BitmapFactory.decodeByteArray(image, 0 ,image.length));

        return item;
    }
}
