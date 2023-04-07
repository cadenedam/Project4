import java.util.ArrayList;

public class ShoppingCart {

    public String username;
    public String password;
    public String [] shoppingCart;

    public ShoppingCart(String username, String password) {
        this.username = username;
        this.password = password;
    }

    void addToCart(String product) {
        ArrayList<String> cart = new ArrayList<String>();
        for (int i = 0; i < shoppingCart.length; i++) {
            cart.add(shoppingCart[i]);
        }
        cart.add(product);
        String[] list = cart.toArray(new String[0]);
        this.shoppingCart = list;
    }
}
