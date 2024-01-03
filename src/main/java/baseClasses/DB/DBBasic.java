package baseClasses.DB;

public abstract class DBBasic {
    static final String DB_URL = "jdbc:postgresql://your_database_url";
    static final String USER = "your_username";
    static final String PASSWORD = "your_password";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
