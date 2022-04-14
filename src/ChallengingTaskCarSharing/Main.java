
package ChallengingTaskCarSharing;

public class Main {
    public static void main(String[] args) {
        var database = new Database(args);
        new MenuView(database);
        database.disconnect();
    }
}