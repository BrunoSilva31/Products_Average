package app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Product;

public class Program {
    public static void main(String[] args) {
        
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        List<Product> list = new ArrayList<>();

        System.out.print("Enter file path: ");
        String path = sc.nextLine();

        try (BufferedReader br = new BufferedReader(new FileReader(path))){
            
            String item = br.readLine();
            while (item != null) {
                
                String [] fields = item.split(",");
                list.add(new Product(fields[0], Double.parseDouble(fields[1])));

                item = br.readLine();
            }

            Double average = list.stream().map(p -> p.getPrice()).reduce(0.0, (x, y) -> x + y) / list.size();
            System.out.print("Average price: " + String.format("%.2f", average));
            System.out.println();

            Comparator<String> comp = (s1, s2) -> s1.toUpperCase().compareTo(s2.toUpperCase());

            List<String> names = list.stream().filter(p -> p.getPrice() < average).map(p -> p.getName()).sorted(comp.reversed()).collect(Collectors.toList());

            names.forEach(System.out::println);

        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        sc.close();
    }
}