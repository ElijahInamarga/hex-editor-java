import javax.swing.*;
import java.io.*;

public class Gui {
    JFrame frame;
    JButton button;
    JTextField text;

    Gui() {
       JFrame frame = new JFrame();
       JButton button = new JButton("Submit");
       JTextField text = new JTextField("Input file path...");

       text.setEditable(true);
       text.setBounds(340, 200, 220, 50);
       button.setBounds(340, 250, 220, 50);
       frame.add(button);
       frame.add(text);
       frame.setSize(900, 700);
       frame.setLayout(null);
       frame.setVisible(true);
   }
}
