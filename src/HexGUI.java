
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HexGUI extends JFrame implements ActionListener {

    JPanel filePanel;
    JLabel fileLabel;
    JButton saveButton;
    JButton openFileButton;

    JPanel hexPanel;
    JTextArea hexCode;

    JTextArea viewArea;


    /**
     * This constuctor initializes the HexGui frame and adds
     * all of its graphical components (panels)
     *
     */
    public HexGUI(){

        setSize(1000,1000);
        setTitle("Hexcode");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        setFilePanel();
        setHexPanel();
        setViewPanel();

        setVisible(true);

    }

    /**
     * This panel is designated for the file section
     * of the frame.
     * It includes a saveButton where we'll add the save
     * mechanism of our program
     * It also includes a openFileButton where we'll add
     * the function to open a file and run it through
     * our program (A filePath will be requested).
     * We will need a text box added to this panel for the
     * file path input
     */
    public void setFilePanel(){

        filePanel = new JPanel();
        filePanel.setBorder(BorderFactory.createLineBorder(Color.darkGray,5));

        fileLabel = new JLabel("File");
        fileLabel.setBorder(BorderFactory.createLineBorder(Color.darkGray,1));

        saveButton = new JButton("Save");
        saveButton.setBorder(BorderFactory.createLineBorder(Color.darkGray,1));
        saveButton.addActionListener(this);

        openFileButton = new JButton("Open");
        openFileButton.setBorder(BorderFactory.createLineBorder(Color.darkGray,1));
        openFileButton.addActionListener(this);

        filePanel.add(fileLabel);
        filePanel.add(saveButton);
        filePanel.add(openFileButton);

        add(filePanel,BorderLayout.WEST);


    }

    /**
     * This is the panel designated for the hexcode of a
     * file's binary
     * It can be scrolled when text overflows
     *
     */

    public void setHexPanel(){

        hexPanel = new JPanel();
        hexPanel.setBorder(BorderFactory.createLineBorder(Color.darkGray,5));

        hexCode = new JTextArea("HexCode");
        hexCode.setBorder(BorderFactory.createLineBorder(Color.darkGray,1));
        hexCode.setLineWrap(true);


        JScrollPane hexScroll = new JScrollPane(hexCode);
        Dimension hexSize = new Dimension(800,700);
        hexScroll.setPreferredSize(hexSize);

        hexPanel.add(hexScroll);

        add(hexPanel,BorderLayout.CENTER);

    }

    /**
     * this panel is for the when a user wants to view the
     * file's hex code in a certain code
     */
    public void setViewPanel(){

        viewArea = new JTextArea("viewArea");
        viewArea.setBorder(BorderFactory.createLineBorder(Color.darkGray,1));
        viewArea.setLineWrap(true);

        JScrollPane viewScroll = new JScrollPane(viewArea);
        Dimension hexSize = new Dimension(0,50);
        viewScroll.setPreferredSize(hexSize);

        add(viewScroll, BorderLayout.SOUTH);
    }

    /**
     * None of the buttons are connected to any action processing yet
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e){

    }

}
