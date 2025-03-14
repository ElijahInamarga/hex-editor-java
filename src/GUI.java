import javax.swing.*;

public class GUI {
    final int APP_X_SIZE = 1000;
    final int APP_Y_SIZE = 1000;
    JFrame frame;
    JButton submitButton;
    JTextField filePath;
    JTextField output;

    GUI() {
       frame = new JFrame();
       submitButton = new JButton("Submit");
       filePath = new JTextField("Input file path...");
       output = new JTextField("N/A");

       filePath.setEditable(true);
       filePath.setBounds(20, 20, 220, 50);
       output.setEditable(false);
       output.setBounds(APP_X_SIZE / 4, APP_Y_SIZE / 4, 500, 500);
       submitButton.setBounds(20, 70, 220, 50);

       frame.setSize(APP_X_SIZE, APP_Y_SIZE);
       frame.setLayout(null);
       frame.setVisible(true);
       frame.add(submitButton);
       frame.add(filePath);
       frame.add(output);
   }
}
