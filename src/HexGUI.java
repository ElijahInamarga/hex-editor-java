
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

    public void setViewPanel(){

        viewArea = new JTextArea("viewArea");
        viewArea.setBorder(BorderFactory.createLineBorder(Color.darkGray,1));
        viewArea.setLineWrap(true);

        JScrollPane viewScroll = new JScrollPane(viewArea);
        Dimension hexSize = new Dimension(0,50);
        viewScroll.setPreferredSize(hexSize);

        add(viewScroll, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e){

    }

}
