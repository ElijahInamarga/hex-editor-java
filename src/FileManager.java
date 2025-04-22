import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


/* HOW TO USE:

Constructor or setFilePath() sets the file path of the desired file. The file is not accessed at this time so
errors can be handled gracefully with isFileExist(), isReadableFile(), and isWritableFile(), which can be checked
outside of this object, even though safeguards are in place.

getFileData() gets the raw byte[] of the file, before it has been changed into hex form. Ideally isFileExist(),
isReadableFile(), and isWritableFile() should be checked before this is called to gracefully handle errors.
Otherwise, if there is an error a blank byte[] will be returned with an issue printed.

 */
public class FileManager {
    private String filePath;
    private File mainFile;
    private ArrayList<Byte> mainFileData;
    private boolean fileExists;
    private boolean isReadableFile;
    private boolean isWritableFile;

    // Init file path for the File manager
    FileManager(String filePath) throws IOException, InvalidPathException {
        setNewFilePath(filePath);
    }

    // sets the desired file path and updates everywhere
    public void setNewFilePath(String filePath) throws IOException, InvalidPathException{
        this.filePath = filePath;
        this.mainFile = new File(filePath);

        this.fileExists = mainFile.exists();
        this.isReadableFile = mainFile.canRead();
        this.isWritableFile = mainFile.canWrite();
        readFileData();
    }

    public ArrayList<Byte> getFileData() {
        return this.mainFileData;
    }

    private void readFileData() throws IOException, InvalidPathException {
        byte[] fileData = Files.readAllBytes(mainFile.toPath());
        this.mainFileData = getArrayList(fileData);
    }

    public String[] getFileDataHex() throws IOException, InvalidPathException {
        ArrayList<Byte> fileData = getFileData();
        String[] hexFileData = new String[fileData.size()];

        for (int i = 0; i < fileData.size(); i++) {
            hexFileData[i] = ConversionsTemp.byteToHex(fileData.get(i));
        }

        return hexFileData;
    }


    public void writeFileData() throws IOException {
        try {
            FileOutputStream writeFile = new FileOutputStream(this.getFilePath());
            byte[] writeArray = new byte[this.mainFileData.size()];

            for (int i = 0; i < this.mainFileData.size(); i++) {
                writeArray[i] = this.mainFileData.get(i);
            }

            writeFile.write(writeArray);

        } catch (IOException e) {
            System.out.println("Problem :(");
            throw e;
        }
    }

    // O(1)
    public void modifyByte(int index, byte newByte) throws ArrayIndexOutOfBoundsException {
         this.mainFileData.set(index, newByte);
    }

    // O(n)
    public void insertByte(int index, byte newByte) throws ArrayIndexOutOfBoundsException {

    }

    public boolean isFileExist() {
        return fileExists;
    }

    public boolean isReadableFile() {
        return isReadableFile;
    }

    public boolean isWritableFile() {
        return isWritableFile;
    }

    public String getFilePath() {
        return this.filePath;
    }

    private ArrayList<Byte> getArrayList(byte[] arr) {
        ArrayList<Byte> arrayList = new ArrayList<Byte>();
        for (int i = 0; i < arr.length; i++) {
            arrayList.addLast(arr[i]);
        }
        return arrayList;
    }

}
