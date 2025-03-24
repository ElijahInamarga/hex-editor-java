import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;


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
    private byte[] mainFileData;
    private boolean fileExists;
    private boolean isReadableFile;
    private boolean isWritableFile;

    // Init file path for the File manager
    FileManager(String filePath) throws IOException, InvalidPathException {
        setNewFilePath(filePath);
    }

    // sets the desired file path
    public void setNewFilePath(String filePath) throws IOException, InvalidPathException{
        this.filePath = filePath;
        this.mainFile = new File(filePath);

        this.fileExists = mainFile.exists();
        this.isReadableFile = mainFile.canRead();
        this.isWritableFile = mainFile.canWrite();
        getFileData();
    }

    // isFileExists, isReadableFile, and isWritableFile should be checked BEFORE this is called to prevent an error
    public byte[] getFileData() throws IOException, InvalidPathException {
        this.readFileData();
        return this.mainFileData;
    }

    private void readFileData() throws IOException, InvalidPathException {
        mainFileData = Files.readAllBytes(mainFile.toPath());
    }

    // check requirements for getFileData() before using
    public String[] getFileDataHex() throws IOException, InvalidPathException {
        byte[] fileData = getFileData();
        String[] hexFileData = new String[fileData.length];

        for (int i = 0; i < fileData.length; i++) {
            hexFileData[i] = ConversionsTemp.byteToHex(fileData[i]);
        }

        return hexFileData;
    }


    public void writeFileData() throws IOException {
        try {
            FileOutputStream writeFile = new FileOutputStream(this.getFilePath());
            writeFile.write(this.mainFileData);

        } catch (IOException e) {
            System.out.println("Problem :(");
            throw e;
        }
    }

    public void unprotectedModifyByte(int index, byte newByte) {
        this.mainFileData[index] = newByte;
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

}
