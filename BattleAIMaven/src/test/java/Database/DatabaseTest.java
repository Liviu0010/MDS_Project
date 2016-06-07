package Database;

import java.sql.SQLException;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author root
 */
public class DatabaseTest {
    public DatabaseTest() {
        DatabaseHandler.getInstance("root", "");
    }
    
    @Test
    public void connectionTest() throws SQLException {
        boolean result = DatabaseHandler.getInstance().testConnection();
        assertEquals(result, true);
    }
    
    @Test
    public void sqlInjectionTest1() {
        String username = "johnny';DROP TABLE PLAYER_DB;--";
        DatabaseHandler.getInstance().findAccount(username, "abcd");
        assertEquals(DatabaseHandler.getInstance().tableExists("PLAYER_DB"), true);
    }
    
    @Test
    public void sqlInjectionTest2() {
        String username = "johnny' OR 1=1/*";
        assertEquals(DatabaseHandler.getInstance().findName(username), false);
    }
    
    @Test
    public void sqlInjectionTest3() {
        String username = "'=''/*";
        assertEquals(DatabaseHandler.getInstance().findName(username), false);
    }
    
    @Test
    public void sqlInjectionTest4() {
        String username = "$badboypwnsyodatabase$ >:)";
        String password = "(SELECT 1 FROM DUAL)";
        DatabaseHandler.getInstance().pushPlayer(username, password);
        assertEquals(DatabaseHandler.getInstance().findAccount(username, "1"), false);
        assertEquals(DatabaseHandler.getInstance().findAccount(username, password), true);
        DatabaseHandler.getInstance().removePlayer(username, password);
        assertEquals(DatabaseHandler.getInstance().findAccount(username, password), false);
    }
}
