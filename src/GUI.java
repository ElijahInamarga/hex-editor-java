import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI {
    private final int WIDTH = 1100;
    private final int HEIGHT = 900;
    private final int BYTES_PER_LINE = 50; // Max bytes per line in output text box
    private FileManager manager;
    private byte[] fileData;

    // GUI Panels
    private JFrame frame = new JFrame("Hex Editor");
    private JButton submitButton = new JButton("Submit");
    private JButton findFileButton = new JButton("Find File");
    private JTextField inputFilePath = new JTextField("Input file path here...");
    private JTextArea outputArea = new JTextArea();
    private JScrollPane outputScrollPane = new JScrollPane(outputArea);
    private JFileChooser fileChooser = new JFileChooser();


    GUI() {
        inputFilePath.setEditable(true);
        outputArea.setEditable(false);
        submitButton.addActionListener(ON_SUBMIT);
        findFileButton.addActionListener(ON_FIND_FILE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new BorderLayout());

        inputPanel.add(inputFilePath, BorderLayout.CENTER);
        inputPanel.add(submitButton, BorderLayout.EAST);
        inputPanel.add(findFileButton, BorderLayout.WEST);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(outputScrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /*
     * Opens a file explorer for the user
     * Allows user to choose a file
     * Sets inputFilePath text as the file path the user saves
     */
    private final ActionListener ON_FIND_FILE = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int returnVal = fileChooser.showOpenDialog(frame);

            // When user clicks on "save", set inputFilePath text as file path
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                String FILE_PATH = fileChooser.getSelectedFile().getAbsolutePath();
                inputFilePath.setText(FILE_PATH);
            }
        }
    };

    /*
    * Takes text from inputFilePath
    * Passes inputFilePath into FileManager
    * Translates result from FileManager into hex
    * Displays translated result
     */
    private final ActionListener ON_SUBMIT = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            manager = new FileManager(inputFilePath.getText());

            if(manager.isFileExist() && manager.isReadableFile() && manager.isWritableFile()) {
                fileData = manager.getFileData();
                StringBuilder hexString = new StringBuilder();
                for(int i = 0; i < fileData.length; i++) {
                    hexString.append(ConversionsTemp.byteToHex(fileData[i])).append(" ");

                    // Prevent string overflow in output text box
                    if((i + 1) % BYTES_PER_LINE == 0) {
                        hexString.append("\n");
                    }
                }
                outputArea.setText(hexString.toString().trim());
            }
        }
    };
}