package DBTest;

import baseClasses.DB.UserDBHandler;
import baseClasses.User.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserDBHandlerTest {
    /*

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private UserDBHandler userDBHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_Success() throws SQLException {
        User user = new User("testUser", 5, 3, 1500);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setString(anyInt(), anyString());
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        when(preparedStatement.executeUpdate()).thenReturn(1);

        userDBHandler.createUser(user);

        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).setString(anyInt(), anyString());
        verify(preparedStatement, times(2)).setInt(anyInt(), anyInt());
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    void getUserByUsername_Success() throws SQLException {
        User expectedUser = new User("testUser", 5, 3, 1500);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("username")).thenReturn(expectedUser.getUsername());
        when(resultSet.getInt("wins")).thenReturn(expectedUser.getUserWins());
        when(resultSet.getInt("losses")).thenReturn(expectedUser.getUserLosses());
        when(resultSet.getInt("elo")).thenReturn(expectedUser.getUserElo());

        User actualUser = userDBHandler.getUserByUsername("testUser");

        assertEquals(expectedUser, actualUser);

        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).setString(1, "testUser");
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(1)).next();
        verify(resultSet, times(1)).getString("username");
        verify(resultSet, times(1)).getInt("wins");
        verify(resultSet, times(1)).getInt("losses");
        verify(resultSet, times(1)).getInt("elo");
    }

    @Test
    void updateUser_Success() throws SQLException {
        User user = new User("testUser", 5, 3, 1500);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        when(preparedStatement.executeUpdate()).thenReturn(1);

        userDBHandler.updateUser(user);

        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(2)).setInt(anyInt(), anyInt());
        verify(preparedStatement, times(1)).setString(4, "testUser");
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    void deleteUser_Success() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        userDBHandler.deleteUser("testUser");

        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).setString(1, "testUser");
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    void getAllUsers_Success() throws SQLException {
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(new User("user1", 3, 2, 1200));
        expectedUsers.add(new User("user2", 5, 1, 1600));

        when(connection.createStatement()).thenReturn(Mockito.mock(Statement.class));
        when(connection.createStatement().executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getString("username")).thenReturn("user1", "user2");
        when(resultSet.getInt("wins")).thenReturn(3, 5);
        when(resultSet.getInt("losses")).thenReturn(2, 1);
        when(resultSet.getInt("elo")).thenReturn(1200, 1600);

        List<User> actualUsers = userDBHandler.getAllUsers();

        assertEquals(expectedUsers.size(), actualUsers.size());
        assertEquals(expectedUsers.get(0), actualUsers.get(0));
        assertEquals(expectedUsers.get(1), actualUsers.get(1));

        verify(connection, times(1)).createStatement();
        verify(connection.createStatement(), times(1)).executeQuery(anyString());
        verify(resultSet, times(3)).next();
        verify(resultSet, times(2)).getString("username");
        verify(resultSet, times(2)).getInt("wins");
        verify(resultSet, times(2)).getInt("losses");
        verify(resultSet, times(2)).getInt("elo");
    }

    @Test
    void getUsersWithWinLossRatio_Success() throws SQLException {
        List<Map<String, Object>> expectedUsersWithRatio = new ArrayList<>();
        Map<String, Object> user1Map = new HashMap<>();
        user1Map.put("username", "user1");
        user1Map.put("winLossRatio", 1.5);
        Map<String, Object> user2Map = new HashMap<>();
        user2Map.put("username", "user2");
        user2Map.put("winLossRatio", 2.0);
        expectedUsersWithRatio.add(user1Map);
        expectedUsersWithRatio.add(user2Map);

        when(connection.createStatement()).thenReturn(Mockito.mock(Statement.class));
        when(connection.createStatement().executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getString("username")).thenReturn("user1", "user2");
        when(resultSet.getInt("wins")).thenReturn(3, 4);
        when(resultSet.getInt("losses")).thenReturn(2, 2);

        List<Map<String, Object>> actualUsersWithRatio = userDBHandler.getUsersWithWinLossRatio();

        assertEquals(expectedUsersWithRatio.size(), actualUsersWithRatio.size());
        assertEquals(expectedUsersWithRatio.get(0), actualUsersWithRatio.get(0));
        assertEquals(expectedUsersWithRatio.get(1), actualUsersWithRatio.get(1));

        verify(connection, times(1)).createStatement();
        verify(connection.createStatement(), times(1)).executeQuery(anyString());
        verify(resultSet, times(3)).next();
        verify(resultSet, times(2)).getString("username");
        verify(resultSet, times(2)).getInt("wins");
        verify(resultSet, times(2)).getInt("losses");
    }

    @Test
    void closeConnection_Success() throws SQLException {
        when(connection.isClosed()).thenReturn(false);
        doNothing().when(connection).close();

        userDBHandler.closeConnection();

        verify(connection, times(1)).isClosed();
        verify(connection, times(1)).close();
    }*/
}
