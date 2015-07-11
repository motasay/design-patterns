/**
 * An example of the Visitor design pattern.
 *
 * This is a quote from the GoF book: "Visitor lets you define a new operation
 * without changing the classes of the elements on which it operates." That's
 * the whole point of this pattern, so keep it in mind. So it's a way to decouple
 * objects and operations, which makes the classes simpler and more coherent in
 * most cases.
 *
 * A good use case IMHO is when you're working on a game that has many different
 * objects that respond differently to the same event (something like animals or
 * zombies with different capabilities). In this case, you want the classes to be as simple
 * as possible by including only the main properties including their setters and getters,
 * but if you also write their behaviors (such as walking, making sounds, etc.) in the
 * class, then the classes will most likely become very big and hard to read. Using
 * the visitor pattern we can encapsulate each behavior in a separate class, so we'd
 * have a WalkVisitor, a SoundVisitor, and so on.
 *
 * The example here is borrowed from http://stackoverflow.com/a/255300 and is very
 * simple, but it should demonstrate how the pattern is used. We will have a hierarchy
 * of animal objects (instead of game), and we want each of them to be able to walk
 * and just print its sound and announce its location.
 *
 * You may want to start from the main method at the end of this file.
 */
public class VisitorExample {

   // our animal hierarchy
   abstract class Animal {
      // the following is not part of the pattern, but is used to make them "walk"
      private int location = 0; // to make them walk
      void walkDistance(int d) {
         this.location += d;
      }
      int getLocation() { return this.location; }

      abstract void accept(AnimalVisitor v); // this is the magic of the Visitor pattern
   }
   class Cat extends Animal {
      @Override
      void accept(AnimalVisitor v) {
         v.visitCat(this);
      }
   }
   class Dog extends Animal {
      @Override
      void accept(AnimalVisitor v) {
         v.visitDog(this);
      }
   }

   // now we implement the behavior (i.e. the Visitors)
   abstract class AnimalVisitor {
      abstract void visitCat(Cat c);
      abstract void visitDog(Dog d);
   }
   // the walk visitor
   class WalkVisitor extends AnimalVisitor {
      @Override
      void visitCat(Cat c) {
         int catSpeed = 1;
         c.walkDistance(catSpeed);
      }
      @Override
      void visitDog(Dog d) {
         int dogSpeed = 2;
         d.walkDistance(dogSpeed);
      }
   }
   // the sound visitor
   class SoundVisitor extends AnimalVisitor {
      @Override
      void visitCat(Cat c) {
         System.out.println("Meao! I'm at " + c.getLocation());
      }
      @Override
      void visitDog(Dog d) {
         System.out.println("Woof! I'm at " + d.getLocation());
      }
   }
   // and so on. We can add a visitor for each new behavior we want to implement
   // without actually modifying the classes.

   public static void main(String[] args) {

      // the above classes are all inner classes, so we need to use an object of
      // the outer class (VisitorExample) to access them.
      VisitorExample ve = new VisitorExample();

      // create our lovely pets
      Animal c = ve.new Cat();
      Animal d = ve.new Dog();

      // let's hear from them using the SoundVisitor
      SoundVisitor sv = ve.new SoundVisitor();
      c.accept(sv);
      d.accept(sv);

      // let's make them walk using the WalkVisitor
      WalkVisitor wv = ve.new WalkVisitor();
      c.accept(wv);
      d.accept(wv);

      // let's hear from them again using the SoundVisitor!
      c.accept(sv);
      d.accept(sv);
   }
}
