package nutech.quickart.cart.Database;

public class ItemSchema {

    public static final class Item{
        public static final String NAME = "item";

        public static final class Col{
            public static final String ITEMID = "id";
            public static final String ITEM = "item";
            public static final String COST = "cost";
            public static final String CONTENT = "content";
            public static final String IMAGE = "image"; //might have to expand
        }
    }
}
