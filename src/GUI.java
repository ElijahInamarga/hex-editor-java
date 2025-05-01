import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;

public class GUI extends JFrame{
    private final int WIDTH = 1065;
    private final int HEIGHT = 900;
    private final int BYTES_PER_LINE = 50; // Max bytes per line in output text box
    private FileManager manager;

    // GUI Panels
    private JFrame frame = new JFrame("Hex Editor");
    private JButton submitButton = new JButton("Submit");
    private JButton findFileButton = new JButton("Find File");
    private JButton saveButton = new JButton("Save File");
    private JTextField inputFilePath = new JTextField("Input file path here...");
    private JTextPane outputArea = new JTextPane();
    private JScrollPane outputScrollPane = new JScrollPane(outputArea);
    private JFileChooser fileChooser = new JFileChooser();



    GUI() {
        inputFilePath.setEditable(true);
        outputArea.setEditable(true);
        submitButton.addActionListener(ON_SUBMIT);
        findFileButton.addActionListener(ON_FIND_FILE);
        saveButton.addActionListener(ON_SAVE_FILE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLayout(new BorderLayout());
        CommentManager commentManager = new CommentManager(outputArea);
        commentManager.installIconPainter();

        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        outputPanel.add(outputScrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(inputFilePath, BorderLayout.CENTER);
        inputPanel.add(submitButton, BorderLayout.EAST);
        inputPanel.add(saveButton, BorderLayout.PAGE_END);
        inputPanel.add(findFileButton, BorderLayout.WEST);

        JPopupMenu menu = new JPopupMenu();
        JMenuItem addCommentItem = new JMenuItem("Add Comment…");
        menu.add(addCommentItem);
        outputArea.setComponentPopupMenu(menu);
        addCommentItem.addActionListener(e -> {
            int start = outputArea.getSelectionStart();
            int end = outputArea.getSelectionEnd();
            if (start == end) return;
            String selectedText;
            try {
                selectedText = outputArea.getDocument().getText(start, end - start);
            } catch (BadLocationException ex) {
                ex.printStackTrace();
                return;
            }
            String comment = JOptionPane.showInputDialog(
                    outputArea,
                    "Comment for:\n“" + selectedText + "”",
                    "New Comment",
                    JOptionPane.PLAIN_MESSAGE
            );
            if (comment != null && !comment.trim().isEmpty()) {
                commentManager.addComment(start, end, comment.trim());
            }
        });

        /*
         * Clicking on the file path input text box auto selects the whole input field
         * Leaving the input text field empty brings back previous entry
         */
        inputFilePath.addFocusListener(new FocusAdapter() {
            String prevString;
            @Override
            public void focusGained(FocusEvent e) {
                if(inputFilePath.getText().equals("Input file path here...")){
                    inputFilePath.setText("");
                }
                inputFilePath.setSelectionColor(Color.ORANGE);
                inputFilePath.selectAll();
                prevString = inputFilePath.getText();
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(inputFilePath.getText().isEmpty()) {
                    if(prevString.isEmpty()){
                        inputFilePath.setText("Input file path here...");
                    }
                    else {
                        inputFilePath.setText(prevString);
                    }
                }
            }
        });

        frame.getRootPane().setDefaultButton(submitButton);
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
                manager = new FileManager(inputFilePath.getText());
                if (manager.isFileExist() && manager.isReadableFile() && manager.isWritableFile()) {
                    ArrayList<Byte> fileData = manager.getFileData();
                    StringBuilder hexString = new StringBuilder();
                    for (int i = 0; i < fileData.size(); i++) {
                        hexString.append(ConversionsTemp.byteToHex(fileData.get(i))).append(" ");

                        // Prevent string overflow in output text box
                        if ((i + 1) % BYTES_PER_LINE == 0) {
                            hexString.append("\n");
                        }
                    }
                    outputArea.setText(hexString.toString());
                }
            } catch(IOException | InvalidPathException ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage() + "not found", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    };

    private final ActionListener ON_SAVE_FILE = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            byte[] fileData ;
            String hexString = outputArea.getText().replaceAll("\\s+", "");
            try {
                manager.actuallyWriteFileData(ConversionsTemp.hexToByte(hexString));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    };
}