package lessonFour;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class Enigma {
    protected int key = 0;
    protected String mode = "enc";
    protected String data = "";
    protected String inPath = "";
    protected String outPath = "";
    Algorithm algorithm = new ShiftAlgorithm();

    public void execute() {
        readData();

        switch (this.mode) {
            case "enc":
                String cipherText = algorithm.encrypt(data, key);
                output(cipherText, outPath);
                break;
            case "dec":
                String plainText = algorithm.decipher(data, key);
                output(plainText, outPath);
                break;
            default:
                System.err.println("unknown mode");
                System.exit(1);
        }
    }


    // вивести результати шифрування /дешифрування в STDERR of "-out"

    private static void output(String message, String outPath) {
        if ("".equals(outPath)) {
            System.out.println(message);
        } else {
            File file = new File(outPath);

            try (FileWriter writer = new FileWriter(file)) {
                writer.write(message);
            } catch (IOException e) {
                System.err.println("can't write to " + outPath);
                System.exit(1);
            }
        }
    }


    //  прочитати дані з файлу "-in" якщо він був а  "-data"  не було дано
    private void readData() {
        if ("".equals(data)) {  // prefer "-data" over "-in"
            String fileName = inPath;
            File file = new File(fileName);
            try (Scanner scanner = new Scanner(file)) {
                data = scanner.nextLine();
            } catch (FileNotFoundException e) {
                System.err.println(fileName + " not found");
                System.exit(1);
            }
        }
    }

    public void parseArgs(String[] args) {
        for (int i = 0; i < args.length; i += 2) {
            switch (args[i]) {
                case "-alg":
                    switch (args[i + 1]) {
                        case "shift":
                            algorithm = new ShiftAlgorithm();
                            break;
                        case "unicode":
                            algorithm = new UnicodeAlgorithm();
                            break;
                        default:
                            System.err.println("unknown algorithm " + args[i + 1]);
                            System.exit(1);
                    }
                    break;
                case "-mode":
                    mode = args[i + 1];
                    break;
                case "-key":
                    key = Integer.parseInt(args[i + 1]);
                    break;
                case "-in":
                    inPath = args[i + 1];
                    break;
                case "-data":
                    data = args[i + 1];
                    break;
                case "-out":
                    outPath = args[i + 1];
                    break;
                default:
                    System.err.println("unknown argument");
                    System.exit(1);
            }
        }
    }
}