import java.util.Scanner;

/**
 * Original console exercise preserved for history.
 * The production-style implementation now lives under src/.
 */
public class ShippingCost {
   public static double calcTax(double cost) {
     return cost * 0.15;
   }

   public static double calcShippingCost(double weight) {
      double cost;
      if (weight < 1) cost = 7.88;
      else if (weight < 6) cost = 14.32;
      else if (weight < 10) cost = 21.11;
      else cost = 25.5;
      return cost + calcTax(cost);
   }

   public static void main(String[] args) {
      Scanner scanner = new Scanner(System.in);
      System.out.print("Enter package weight: ");
      double weight = scanner.nextDouble();
      System.out.printf("Shipping cost: $%.2f%n", calcShippingCost(weight));
   }
}
