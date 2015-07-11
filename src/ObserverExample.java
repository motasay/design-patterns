
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

/**
 * An example of the Observer design pattern.
 *
 * Main idea: decouple the observer from the observable.
 * The most well-known example for this pattern is the event handling systems
 * provided by Java, iOS and almost all other languages and platforms. You express
 * your interest in a particular event, and implement an appropriate method that's
 * specified by an interface so that you get updates through this method.
 *
 * Another good use case is in the Model-View-Controller (MVC) architectures, where
 * the view (V) observes the state of the objects in the model (M) through this pattern
 * without actually having any coupling between the two.
 */

public class ObserverExample {

   public class MyFrame extends JFrame implements KeyListener {

      public MyFrame(String s) {
         super(s);
         this.addKeyListener(this); // here we're expressing our interest in observing
                                    // this event. The 'publisher' now assumes that
                                    // we have implemented the keyReleased and the other
                                    // KeyListener interface methods.
         this.setVisible(true);
         this.setSize(200, 200);
         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      }
      
      public void keyReleased(KeyEvent e) {
         System.out.println("I know about the key click without knowing about "
                 + "the caller of the method");
      }
      public void keyTyped(KeyEvent e) {}
      public void keyPressed(KeyEvent e) {}
   }

   public static void main(String[] args) {

      // the above classes are all inner classes, so we need to use an object of
      // the outer class (StateExample) to access them.
      ObserverExample oe = new ObserverExample();

      oe.new MyFrame("Observer Pattern Example");
   }
}
