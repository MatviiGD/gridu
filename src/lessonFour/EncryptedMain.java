package lessonFour;

public class EncryptedMain {
    public static void main(String[] args) {
        Enigma enigma = new Enigma();
        enigma.parseArgs(args);
        enigma.execute();

    }
}