import javax.swing.*;
import java.awt.event.*;

public class GUI {
    final int APP_X_SIZE = 1000;
    final int APP_Y_SIZE = 1000;
    JFrame frame = new JFrame();
    JButton submitButton = new JButton("Submit");
    JTextField inputFilePath = new JTextField("Input file path...");
    JTextField output = new JTextField("N/A");

    /* Initializes the application with:
     *  - Input field (Takes file path)
     *  - Non-editable output box (Displays file bin)
     *  - Submit button (Takes and empties the input field)
     */
    GUI() {
       inputFilePath.setEditable(true);
       inputFilePath.setBounds(20, 20, 220, 50);

       output.setEditable(false);
       output.setBounds(APP_X_SIZE / 4, APP_Y_SIZE / 4, 500, 500);

       submitButton.setBounds(20, 70, 220, 50);
       submitButton.addActionListener(ON_CLICK);

       frame.setSize(APP_X_SIZE, APP_Y_SIZE);
       frame.setLayout(null);
       frame.setVisible(true);
       frame.add(inputFilePath);
       frame.add(output);
       frame.add(submitButton);
   }

   final ActionListener ON_CLICK = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            output.setText("This works!");
            inputFilePath.setText("Input file path...");
        }
    };
}
