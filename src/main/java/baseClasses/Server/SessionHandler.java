package baseClasses.Server;

import baseClasses.DB.UserDBHandler;
import baseClasses.User.UserCredentials;
import baseClasses.User.UserInfo;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class SessionHandler {
    private static SessionHandler INSTANCE;
    private final Map<UUID, UserInfo> Sessions = new HashMap<>();

    private SessionHandler() {

    }

    public static SessionHandler getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new SessionHandler();
        }
        return INSTANCE;
    }

    public String getUserFromSession(UUID sessionId) {
        UserInfo userInfo = Sessions.get(sessionId);
        return userInfo.username();
    }

    public UUID login(UserCredentials userCredentials) throws SQLException {

        UserDBHandler userDBHandler = new UserDBHandler();

        //System.out.println(userCredentials);

        boolean success = userDBHandler.loginUser(userCredentials.username(), userCredentials.password());

        if(!success)
            return null;

        UUID sessionId = UUID.randomUUID();
        this.Sessions.put(sessionId, new UserInfo(userCredentials.username(), userDBHandler.isAdmin(userCredentials.username())));

        return sessionId;
    }

    public boolean verifySession(UUID sessionId, boolean admin) {
        return Sessions.containsKey(sessionId) && (!admin || Sessions.get(sessionId).isAdmin());
    }

    public boolean verifySession(UUID sessionId, String username, boolean admin) {
        return Sessions.containsKey(sessionId) && (Sessions.get(sessionId).username().equals(username) || (admin && Sessions.get(sessionId).isAdmin()));
    }

    public boolean verifySession(UUID sessionId, String username) {
        return verifySession(sessionId, username, false);
    }

    public boolean verifySession(UUID sessionId) {
        return verifySession(sessionId, false);
    }
}
