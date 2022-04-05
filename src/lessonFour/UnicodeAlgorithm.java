package lessonFour;

class UnicodeAlgorithm extends Algorithm {

    public String encrypt(String message, int key) {
        StringBuilder cipherText = new StringBuilder();

        for (char c : message.toCharArray()) {
            cipherText.append((char) (c + key));
        }

        return cipherText.toString();
    }

    public String decipher(String message, int key) {
        return encrypt(message, -key);
    }
}