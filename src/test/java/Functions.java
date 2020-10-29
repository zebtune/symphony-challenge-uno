import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Functions {


    public static int randomRange(int min, int max) {
        Random random = new Random();
        return random.ints(min, max)
                .findFirst()
                .getAsInt();
    }

    public static String randomRangeToString (int min, int max){
        Random random = new Random();
        int randNumber = random.ints(min, max)
                .findFirst()
                .getAsInt();
        String numToString = Integer.toString(randNumber);
        return numToString;
    }

    public static void saveResultInTextFile(String productName, String productName2, String productName3, String productName4, String productName5) {
        try {
            FileWriter myWriter = new FileWriter("textFile.txt");
            myWriter.write(productName + "\n");
            myWriter.write(productName2 + "\n");
            myWriter.write(productName3 + "\n");
            myWriter.write(productName4 + "\n");
            myWriter.write(productName5);

            myWriter.close();
            System.out.println("Product names are successfully written into the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
