import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileIO {
    public static void main(String[] args) throws IOException {
        String data = readFile("mobydick.txt");
        String stripped = data.replaceAll("[^a-zA-Z ]", " ");
        String[] words = stripped.split(" ");
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].trim();
        }
        int index = data.indexOf(longestWord(words));
        System.out.println(data.substring(index - 100, index + 101));
    }

    public static void writeDataToFile(String filePath, String data) throws IOException {
        try (FileWriter f = new FileWriter(filePath);
             BufferedWriter b = new BufferedWriter(f);
             PrintWriter writer = new PrintWriter(b)) {
            writer.println(data);
        } catch (IOException error) {
            System.err.println("there was a problem writing to the file " + filePath);
            error.printStackTrace();
        }
    }

    public static String readFile(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    public static ArrayList<String> getNLetterWords(String[] allWords, int n) {
        ArrayList<String> nLetterWords = new ArrayList<>();
        for (String word : allWords) {
            if (word.length() == n) {
                nLetterWords.add(word);
            }
        }
        return nLetterWords;
    }

    public static ArrayList<String> getWordsWithout(String[] allWords, String forbiddenLetter) {
        ArrayList<String> wordsWithout = new ArrayList<>();
        for (String word : allWords) {
            if (!word.contains(forbiddenLetter)) {
                wordsWithout.add(word);
            }
        }
        return wordsWithout;
    }

    public static String longestWord(String[] allWords) {
        int maxLength = 0;
        String longest = "";
        for (String word : allWords) {
            if (word.length() > maxLength) {
                maxLength = word.length();
                longest = word;
            }
        }
        return longest;
    }
}
