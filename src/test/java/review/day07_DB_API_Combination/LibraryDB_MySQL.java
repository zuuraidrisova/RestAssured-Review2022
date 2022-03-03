package review.day07_DB_API_Combination;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import review.utility.DB_Utility;

public class LibraryDB_MySQL {

    @BeforeAll
    public static void setup(){

        DB_Utility.createConnection("library2");
    }

    @Test
    public void testLibraryDB(){

        DB_Utility.runQuery("select * from books");

    }

    @AfterAll
    public static void teardown(){

        DB_Utility.destroy();
    }


}
