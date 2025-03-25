//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.lang.StringBuilder;
//
//public class HexGUI extends JFrame implements ActionListener {
//
//    JButton fileButton;
//    JButton saveButton;
//    JButton openFileButton;
//    JLabel textLabel;
//    JTextField pathInputField;
//    JButton enterButton;
//
//    JPanel hexPanel;
//    JTextArea hexCode;
//
//    JTextArea viewArea;
//
//
//    /**
//     * This constuctor initializes the HexGui frame and adds
//     * all of its graphical components (panels)
//     *
//     */
//    public HexGUI(){
//
//        setSize(1000,1000);
//        setTitle("Hexcode");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLayout(new BorderLayout());
//
//        setFilePanel();
//        setHexPanel();
//        //setViewPanel();
//        setVisible(true);
//
//    }
//
//    /**
//     * This panel is designated for the file section
//     * of the frame.
//     * It includes a saveButton where we'll add the save
//     * mechanism of our program
//     * It also includes a openFileButton where we'll add
//     * the function to open a file and run it through
//     * our program (A filePath will be requested).
//     * We will need a text box added to this panel for the
//     * file path input
//     */
//    public void setFilePanel(){
//
//        JPanel filePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//        Dimension fieldSize = new Dimension(200,40);
//        filePanel.setPreferredSize(fieldSize);
//
//        fileButton = new JButton(" File ");
//        fileButton.setBorder(BorderFactory.createLineBorder(Color.darkGray,1));
//        fileButton.addActionListener(this);
//
//        saveButton = new JButton(" Save ");
//        saveButton.setBorder(BorderFactory.createLineBorder(Color.darkGray,1));
//        saveButton.addActionListener(this);
//        saveButton.setVisible(false);
//
//        openFileButton = new JButton(" Open ");
//        openFileButton.setBorder(BorderFactory.createLineBorder(Color.darkGray,1));
//        openFileButton.addActionListener(this);
//        openFileButton.setVisible(false);
//
//        filePanel.add(fileButton);
//        filePanel.add(saveButton);
//        filePanel.add(openFileButton);
//
//        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//        textLabel = new JLabel("FilePath:");
//        textLabel.setVisible(false);
//        pathInputField = new JTextField();
//        pathInputField.setBorder(BorderFactory.createLineBorder(Color.darkGray,1));
//        fieldSize = new Dimension(500,25);
//        pathInputField.setPreferredSize(fieldSize);
//        pathInputField.setVisible(false);
//        enterButton = new JButton(" Enter ");
//        enterButton.setVisible(false);
//        enterButton.addActionListener(this);
//        enterButton.setBorder(BorderFactory.createLineBorder(Color.darkGray,1));
//        userPanel.add(textLabel);
//        userPanel.add(pathInputField);
//        userPanel.add(enterButton);
//
//        JPanel topPanel = new JPanel(new BorderLayout());
//        topPanel.setBorder(BorderFactory.createLineBorder(Color.darkGray,5));
//        topPanel.add(filePanel, BorderLayout.WEST);
//        topPanel.add(userPanel);
//
//        add(topPanel,BorderLayout.NORTH);
//    }
//
//    /**
//     * This is the panel designated for the hexcode of a
//     * file's binary
//     * It can be scrolled when text overflows
//     *
//     */
//
//    public void setHexPanel(){
//
//        hexPanel = new JPanel();
//        hexPanel.setBorder(BorderFactory.createLineBorder(Color.darkGray,5));
//
//        hexCode = new JTextArea("HexCode");
//        hexCode.setBorder(BorderFactory.createLineBorder(Color.darkGray,1));
//        hexCode.setLineWrap(true);
//
//
//        JScrollPane hexScroll = new JScrollPane(hexCode);
//        Dimension hexSize = new Dimension(800,650);
//        hexScroll.setPreferredSize(hexSize);
//
//        hexPanel.add(hexScroll);
//
//        add(hexPanel,BorderLayout.CENTER);
//    }
//
//    /**
//     * this panel is for the when a user wants to view the
//     * file's hex code in a certain code
//     */
//    public void setViewPanel(){
//
//        viewArea = new JTextArea("viewArea");
//
//        viewArea.setLineWrap(true);
//        viewArea.setEditable(false);
//
//        JScrollPane viewScroll = new JScrollPane(viewArea);
//        Dimension hexSize = new Dimension(0,50);
//        viewScroll.setPreferredSize(hexSize);
//
//        add(viewScroll, BorderLayout.SOUTH);
//    }
//
//    /**
//     *
//     * @param e the event to be processed
//     */
//    @Override
//    public void actionPerformed(ActionEvent e){
//
//        //this action is performed when the file button is pressed.
//        // the save and open buttons become available
//        if(e.getSource() == fileButton){
//
//            saveButton.setVisible(!saveButton.isVisible());
//            openFileButton.setVisible(!openFileButton.isVisible());
//            pathInputField.setVisible(false);
//            textLabel.setVisible(false);
//            enterButton.setVisible(false);
//
//        }
//        // this action is performed when the open button is pressed
//        // the file path input window appears
//        else if(e.getSource() == openFileButton){
//            openFileButton.setVisible(false);
//            saveButton.setVisible(false);
//
//            textLabel.setVisible(!textLabel.isVisible());
//            pathInputField.setVisible(!pathInputField.isVisible());
//            enterButton.setVisible(!enterButton.isVisible());
//
//        }
//        //this action is performed when the save button is pressed
//        // we need to implement a save function here
//        else if(e.getSource() == saveButton) {
//            openFileButton.setVisible(false);
//            saveButton.setVisible(false);
//        }
//
//        // this action is performed when the path enter button is pressed.
//        // A FileManager is created and a file's data is displayed
//        else if(e.getSource() == enterButton){
//            String text = pathInputField.getText();
//
//            FileManager manager = new FileManager(text);
//            StringBuilder hexString = new StringBuilder();
//            if (manager.isFileExist() && manager.isReadableFile() && manager.isWritableFile()) {
//                byte[] fileData = manager.getFileData();
//                for (int i = 0; i < fileData.length; i++) {
//                    hexString.append(ConversionsTemp.byteToHex(fileData[i])).append(" ");
//                    }
//                }
//            hexCode.setText(hexString.toString());
//            textLabel.setVisible(false);
//            pathInputField.setText("");
//            pathInputField.setVisible(false);
//            enterButton.setVisible(false);
//        }
//
//    }
//
//}
