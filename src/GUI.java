import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI {
    final int WIDTH = 1020;
    final int HEIGHT = 840;
    JFrame frame = new JFrame("Hex Editor");
    JButton submitButton = new JButton("Submit");
    JTextField inputFilePath = new JTextField("Input file path here...");
    JTextArea outputArea = new JTextArea();
    JScrollPane outputScrollPane = new JScrollPane(outputArea);
    FileManager manager;
    byte[] fileData;
    final int BYTES_PER_LINE = 50; // Max bytes per line in output text box

    GUI() {
        inputFilePath.setEditable(true);
        outputArea.setEditable(false);
        submitButton.addActionListener(ON_CLICK);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(inputFilePath, BorderLayout.CENTER);
        inputPanel.add(submitButton, BorderLayout.EAST);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(outputScrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    final ActionListener ON_CLICK = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            manager = new FileManager(inputFilePath.getText());

            if (manager.isFileExist() && manager.isReadableFile() && manager.isWritableFile()) {
                fileData = manager.getFileData();
                StringBuilder hexString = new StringBuilder();
                for (int i = 0; i < fileData.length; i++) {
                    hexString.append(ConversionsTemp.byteToHex(fileData[i])).append(" ");

                    // Prevent string overflow in output text box
                    if ((i + 1) % BYTES_PER_LINE == 0) {
                        hexString.append("\n");
                    }
                }
                outputArea.setText(hexString.toString().trim());
                inputFilePath.setText("Input file path..."); // Reset input
            }
        }
    };
}