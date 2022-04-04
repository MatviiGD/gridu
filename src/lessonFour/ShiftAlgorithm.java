package lessonFour;

class ShiftAlgorithm extends Algorithm {

    public String encrypt(String message, int key) {
        StringBuilder plainText = new StringBuilder();

        for (char c : message.toCharArray()) {
            if (Character.isAlphabetic(c)) {
                int shift = Character.isUpperCase(c) ? 65 : 97;
                plainText.append((char) (modulo(c - shift + key) + shift));
            } else {
                plainText.append(c);
            }
        }

        return plainText.toString();
    }

    public String decipher(String message, int key) {
        return encrypt(message, -key);
    }

    private static int modulo(int x) {
        return (x % 26 + 26) % 26;
    }
}