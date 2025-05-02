import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
public class GUI extends JFrame{
    private final int WIDTH = 1065;
    private final int HEIGHT = 900;
    private final int BYTES_PER_LINE = 50; // Max bytes per line in output text box
    private FileManager manager;
    private CommentManager commentManager;

    // GUI Panels

    private JButton submitButton = new JButton("Submit");
    private JButton findFileButton = new JButton("Find File");
    private JButton saveButton = new JButton("Save File");
    private JTextField inputFilePath = new JTextField("Input file path here...");
    private JTextPane outputArea = new JTextPane();
    private JScrollPane outputScrollPane = new JScrollPane(outputArea);
    private JFileChooser fileChooser = new JFileChooser();



    GUI() {
        super("Hex Editor");
        inputFilePath.setEditable(true);
        outputArea.setEditable(true);
        submitButton.addActionListener(ON_SUBMIT);
        findFileButton.addActionListener(ON_FIND_FILE);
        saveButton.addActionListener(ON_SAVE_FILE);
        setSize(WIDTH, HEIGHT);
        setLayout(new BorderLayout(10,10));
        commentManager = new CommentManager(outputArea);

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
        JMenuItem editCommentItem = new JMenuItem("Edit Comment…");
        JMenuItem deleteCommentItem = new JMenuItem("Delete Comment…");
        menu.add(addCommentItem);
        menu.add(editCommentItem);
        menu.add(deleteCommentItem);
        outputArea.setComponentPopupMenu(menu);

        addCommentItem.addActionListener(e -> handleAddComment());
        editCommentItem.addActionListener(e -> handleEditCommentWindow());
        deleteCommentItem.addActionListener(e -> handleDeleteComment());

        add(outputPanel, BorderLayout.CENTER);
        setVisible(true);

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

        add(inputPanel, BorderLayout.NORTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void handleAddComment() {
        int start = outputArea.getSelectionStart();
        int end = outputArea.getSelectionEnd();
        if (start == end) return;
        try {
            String selectedText = outputArea.getDocument().getText(start, end - start);
            String comment = JOptionPane.showInputDialog(
                    outputArea,
                    "Comment for:\n“" + selectedText + "”",
                    "New Comment",
                    JOptionPane.PLAIN_MESSAGE
            );
            if (comment != null && !comment.trim().isEmpty()) {
                commentManager.addComment(start, end, comment.trim());
            }
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }

    private void handleEditCommentWindow() {
        int pos = outputArea.getCaretPosition();
        Comment toEdit = commentManager.findCommentAt(pos);
        if (toEdit == null) {
            JOptionPane.showMessageDialog(outputArea, "No comment at this position.", "Edit Comment", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        // Create a new window for editing
        JFrame editFrame = new JFrame("Edit Comment");
        editFrame.setSize(400, 300);
        editFrame.setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea(toEdit.getText());
        JScrollPane scrollPane = new JScrollPane(textArea);
        editFrame.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        editFrame.add(buttonPanel, BorderLayout.SOUTH);

        saveButton.addActionListener(e -> {
            String updated = textArea.getText().trim();
            if (!updated.isEmpty()) {
                commentManager.updateComment(toEdit, updated);
            }
            editFrame.dispose();
        });
        cancelButton.addActionListener(e -> editFrame.dispose());

        editFrame.setLocationRelativeTo(outputArea);
        editFrame.setVisible(true);
    }

    private void handleDeleteComment() {
        int pos = outputArea.getCaretPosition();
        Comment toDelete = commentManager.findCommentAt(pos);
        if (toDelete == null) {
            JOptionPane.showMessageDialog(
                    outputArea,
                    "No comment at this position.",
                    "Delete Comment",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }
        int choice = JOptionPane.showConfirmDialog(
                outputArea, "Delete this comment?\n“" + toDelete.getText() + "”", "Delete Comment", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            commentManager.removeComment(toDelete);
        }
    }

    /*
     * Opens a file explorer for the user
     * Allows user to choose a file
     * Sets inputFilePath text as the file path the user saves
     */
    private final ActionListener ON_FIND_FILE = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int returnVal = fileChooser.showOpenDialog(GUI.this);
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
                JOptionPane.showMessageDialog(GUI.this, "Error: " + ex.getMessage() + "not found", "Error", JOptionPane.ERROR_MESSAGE);
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