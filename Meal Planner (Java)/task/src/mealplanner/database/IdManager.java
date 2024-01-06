package mealplanner.database;

import java.io.*;

public class IdManager implements Serializable {
    /**
     * Serialize the given object to the file
     */
    private static final long serialVersionUID = 7L;
    int ingredientId = 1;
    int mealId = 1;
    private static final String fileName = "id_file";


    public int getIngredientId() {
        int id = ingredientId;
        this.ingredientId= ingredientId + 1;
        return id;
    }

    public int getMealId() {
        int id = mealId;
        this.mealId = mealId + 1;
        return id;
    }
    public static void serialize(Object obj) throws IOException, FileNotFoundException {
        FileOutputStream fos = new FileOutputStream(fileName);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.close();
    }

    /**
     * Deserialize to an object from the file
     */
    public static IdManager deserialize() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        BufferedInputStream bis = new BufferedInputStream(fis);
        ObjectInputStream ois = new ObjectInputStream(bis);
        Object obj = ois.readObject();
        ois.close();
        return (IdManager) obj;
    }
}