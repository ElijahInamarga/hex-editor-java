import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        FileManager fm = new FileManager("C:\\Users\\brads\\Downloads\\text.txt");
        if (fm.isFileExist()) {
            byte newByte = 101;
            fm.getFileData();
            fm.writeFileData();

        } else {
            System.out.println("Error");
        }



        GUI application = new GUI();
//        HexGUI app = new HexGUI();
    }
}