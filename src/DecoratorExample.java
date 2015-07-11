/**
 * An example of the Decorator Design pattern.
 * http://en.wikipedia.org/wiki/Decorator_pattern
 * The general idea is: we want to extend the functionality of a class without
 *                      affecting other objects of the same class. This pattern
 *                      suggests that we "wrap" the object we want to extend its
 *                      functionality into another (the decorator), which will
 *                      be responsible for this functionality extension.
 *
 * You may want to start from the main method at the end of this file.
 */

public class DecoratorExample {

   abstract class Sandwich {
      double price;
      double getPrice() { return price; }
   }

   // This is the magic of this pattern! Note how getPrice() is overriden to add
   // the price of the toppings to the sandwich's.
   class ToppingsDecorator extends Sandwich {

      Sandwich sandwich;
      public ToppingsDecorator(Sandwich sandwichToDecorate) {
         this.sandwich = sandwichToDecorate;
      }

      @Override
      double getPrice() {
         return (this.sandwich.getPrice() + this.price);
      }
   }

   // Concrete Sandwich classes
   class Chicken extends Sandwich {
      public Chicken() { this.price = 6.99; }
   }
   class Hotdog extends Sandwich {
      public Hotdog()  { this.price = 5.99; }
   }

   // Toppings decorators
   class ExtraCheeseTopping extends ToppingsDecorator {
      public ExtraCheeseTopping(Sandwich p) {
         super(p); // let the decorator keep a reference to p, which might be
                   // a basic sandwich or a sandwich with some toppings
         this.price = 2.0;
      }
   }
   class ExtraSauceTopping extends ToppingsDecorator {
      public ExtraSauceTopping(Sandwich p) {
         super(p);
         this.price = 0.99;
      }
   }

   public static void main(String[] args) {
      // the above classes are all inner classes, so we need to use an object of
      // the outer class (DecoratorExample) to access them.
      DecoratorExample de = new DecoratorExample();

      // start with a plain sandwich, let's go with chicken.
      Sandwich sandwich = de.new Chicken();
      System.out.println("Chicken sandwich has price: " + sandwich.getPrice());

      // let's add cheese. Note that we passed the chicken sandwich
      // as an argument to the magic decorator class, so that we can add the
      // price of the sandwich to the cheese topping.
      ExtraCheeseTopping withCheese = de.new ExtraCheeseTopping(sandwich);
      // double cheese. Note now we are passing the latest 'topping' we just created.
      // That's because we don't want to lose the latest topping we've added.
      // Remember: the decorator *is* a sandwich.
      ExtraCheeseTopping doubleCheese = de.new ExtraCheeseTopping(withCheese);
      System.out.println("With double cheese: " + doubleCheese.getPrice());

      // Finally, add some sauce...
      ExtraSauceTopping withSauce = de.new ExtraSauceTopping(doubleCheese);
      System.out.println("With double cheese and sauce: " + withSauce.getPrice());

      // and so on...

      /* Note: this example is a horrible use case for this pattern, because we can
       * actually keep a collection of Toppings in the Sandwich class, and when we
       * need the price we can loop through them and sum their prices. However, 
       * if we have a more complex functionality (not just calculate a price), we
       * may not be able to find such a simple solution, thus the Decorator pattern
       * might be necessary or maybe just a cleaner way to do the task.
       *
       * A perfect use case for this design pattern is the Java IO classes, where
       * the functionality of each class can be extended using exactly this pattern.
       * "All subclasses of java.io.InputStream, OutputStream, Reader and Writer
       * have a constructor taking an instance of same type."
       */
   }
}
