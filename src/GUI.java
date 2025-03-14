import javax.swing.*;

public class GUI {
    final int APP_X_SIZE = 1000;
    final int APP_Y_SIZE = 1000;
    JFrame frame;
    JButton btn;
    JTextField text;

    GUI() {
       JFrame frame = new JFrame();
       JButton button = new JButton("Submit");
       JTextField text = new JTextField("Input file path...");

       text.setEditable(true);
       text.setBounds(20, 20, 220, 50);
       button.setBounds(20, 70, 220, 50);

       frame.setSize(APP_X_SIZE, APP_Y_SIZE);
       frame.setLayout(null);
       frame.setVisible(true);
       frame.add(button);
       frame.add(text);
   }
}
