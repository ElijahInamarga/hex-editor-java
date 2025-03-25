import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.nio.file.InvalidPathException;

public class GUI {
    private final int WIDTH = 1065;
    private final int HEIGHT = 900;
    private final int BYTES_PER_LINE = 50; // Max bytes per line in output text box

    // GUI Panels
    private JFrame frame = new JFrame("Hex Editor");
    private JButton submitButton = new JButton("Submit");
    private JButton findFileButton = new JButton("Find File");
    private JTextField inputFilePath = new JTextField("Input file path here...");
    private JTextPane outputArea = new JTextPane();
    private JScrollPane outputScrollPane = new JScrollPane(outputArea);
    private JFileChooser fileChooser = new JFileChooser();

    GUI() {
        inputFilePath.setEditable(true);
        outputArea.setEditable(false);
        submitButton.addActionListener(ON_SUBMIT);
        findFileButton.addActionListener(ON_FIND_FILE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLayout(new BorderLayout());

        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        outputPanel.add(outputScrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(inputFilePath, BorderLayout.CENTER);
        inputPanel.add(submitButton, BorderLayout.EAST);
        inputPanel.add(findFileButton, BorderLayout.WEST);

        // Clicking on the file path input text box auto selects the whole input field
//        inputFilePath.addFocusListener(new FocusAdapter() {
//            @Override
//            public void focusGained(FocusEvent e) {
//                inputFilePath.setSelectionColor(Color.ORANGE);
//                inputFilePath.selectAll();
//            }
//        });

        //clicking on file path input text box auto deletes everything in the input field.
        inputFilePath.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                // Clear text only if it hasn't already been cleared
                if (!inputFilePath.getText().isEmpty()) {
                    inputFilePath.setText("");
                }

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(outputPanel, BorderLayout.CENTER);

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
            if (returnVal == JFileChooser.APPROVE_OPTION) {
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
            try {
                FileManager manager = new FileManager(inputFilePath.getText());
                if (manager.isFileExist() && manager.isReadableFile() && manager.isWritableFile()) {
                    byte[] fileData = manager.getFileData();
                    StringBuilder hexString = new StringBuilder();
                    for (int i = 0; i < fileData.length; i++) {
                        hexString.append(ConversionsTemp.byteToHex(fileData[i])).append(" ");

                        // Prevent string overflow in output text box
                        if ((i + 1) % BYTES_PER_LINE == 0) {
                            hexString.append("\n");
                        }
                    }
                    outputArea.setText(hexString.toString());
                }
            } catch(IOException | InvalidPathException ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

        }
    };
}