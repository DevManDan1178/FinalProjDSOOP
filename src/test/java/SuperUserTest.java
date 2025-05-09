import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class SuperUserTest {
    @Test
    public void testFaultyExportData() {
        SuperUser expected = null;
        SuperUser actual = SuperUser.fromExportData("This,is,incorrect,export,data");
        Assertions.assertEquals(expected, actual);
    }
}
