package Model;

public class File {

    private int id;
    private String name;
    private byte[] data;
    private String fileExtension;

    public File(int id, String name, byte[] data, String fileExtension) {
        this.id = id;
        this.name = name;
        this.data = data;
        this.fileExtension = fileExtension;
    }
}
