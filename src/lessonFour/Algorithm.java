package lessonFour;

abstract class Algorithm {

    abstract String encrypt(String message, int key);

    abstract String decipher(String message, int key);
}