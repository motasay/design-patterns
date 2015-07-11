
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;

/**
 * An example of the State design pattern. Generally we use this pattern when
 * we need to have a state-dependent behavior.
 *
 * To test the program: 1) run. 2) to switch the state press in key, to do an action
 * mouse-click on the small frame.
 * 
 */
public class StateExample {

   class NameFormatter {
      
      private NameFormatterState state;
      private boolean next;
      private String f, m, l;
      
      NameFormatter(String first, String middle, String last) {
         this.state = new FirstInitialAndLastState();
         this.next  = true; // a flag to switch the state

         this.f = first;
         this.m = middle;
         this.l = last;
      }

      // this method is not really part of the example, because in a real-world application
      // the state will probably change in response to a user action. For example
      // in photoshop the cursor will change from selection to pen to others
      // in response to the user clicks on the corresponding icons.
      void switchState() {
         if (this.next)
            this.state = new FirstAndLastState();
         else
            this.state = new FirstInitialAndLastState();
         this.next = !this.next;
      }

      // note here that we don't check for the current state, we just do and it's
      // the state's responsibility to do its work. Imagine how useful this would be
      // when you have 10s or 100s of states, and how easy it is to add functionality
      // without modifying or cluttering the current code.
      void printName() {
         System.out.println(this.state.formatName(this.f, this.m, this.l));
      }

   }

   // every state must implement this state interface in its own way.
   interface NameFormatterState {
      String formatName(String f, String m, String l);
   }

   class FirstInitialAndLastState implements NameFormatterState {
      public String formatName(String f, String m, String l) {
         return f.charAt(0) + ", " + l;
      }
   }

   class FirstAndLastState implements NameFormatterState {
      public String formatName(String f, String m, String l) {
         return f + ", " + l;
      }
   }

   // we can add more name formatters as we please...

   public static void main(String[] args) {

      // the above classes are all inner classes, so we need to use an object of
      // the outer class (StateExample) to access them.
      StateExample se = new StateExample();
      String f = "John";
      String m = "M";
      String l = "Doe";
      NameFormatter nf = se.new NameFormatter(f, m, l);

      se.new MyFrame("State Pattern Example", nf);
   }

   // Just a simple JFrame class to listen for keyboard and mouse events
   public class MyFrame extends JFrame implements KeyListener, MouseListener {

      NameFormatter nf; // the name formatter could be a cursor. Think how this
                        // would work.

      public MyFrame(String s, NameFormatter nf) {
         super(s);
         this.nf = nf;
         this.addKeyListener(this);
         this.addMouseListener(this);
         this.setVisible(true);
         this.setSize(200, 200);
         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      }
      
      public void keyReleased(KeyEvent e) {
         System.out.println("Switching state...");
         nf.switchState(); // if it was a cursor then we will directly tell the 
                           // cursor which state to go into.
      }

      public void mouseClicked(MouseEvent e) {
         nf.printName(); // or tell the cursor that the mouse has been clicked
                         // and it will be the cursor's responsibility to respond.
      }

      public void keyTyped(KeyEvent e) {}
      public void keyPressed(KeyEvent e) {}
      public void mousePressed(MouseEvent e) {}
      public void mouseReleased(MouseEvent e) {}
      public void mouseEntered(MouseEvent e) {}
      public void mouseExited(MouseEvent e) {}
   }
}
