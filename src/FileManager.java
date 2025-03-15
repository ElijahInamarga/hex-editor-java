import java.io.File;
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
    FileManager(String filePath) {
        setFilePath(filePath);
    }

    // sets the desired file path
    void setFilePath(String filePath){
        this.filePath = filePath;
        this.mainFile = new File(filePath);

        this.fileExists = mainFile.exists();
        this.isReadableFile = mainFile.canRead();
        this.isWritableFile = mainFile.canWrite();
    }

    // isFileExists, isReadableFile, and isWritableFile should be checked BEFORE this is called to prevent an error
    public byte[] getFileData() {
        try {
            this.setFileData();
            return this.mainFileData;
        } catch (IOException e) {
            System.out.println("There was an error reading the selected file. It may not exist or this program may not have permissions to open it.");
        } catch (InvalidPathException e) {
            System.out.println("There was an error reading the selected file. It likely does not exist at the specified file path");
        }
        return new byte[0];
    }


    private void setFileData() throws IOException, InvalidPathException {
        mainFileData = Files.readAllBytes(mainFile.toPath());
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
