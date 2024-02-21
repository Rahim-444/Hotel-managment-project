//print hello worold

package main.java.com.example.Poo;

import main.java.com.*;
import main.java.com.example.Poo.model.Product;

public class Application {
    public static void main(String[] args) {
        Product product = new Product("TV", 1000.0, 10);
        System.out.println(product.getName());
    }
}
