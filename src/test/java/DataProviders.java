import org.testng.annotations.DataProvider;

/**
 * Created by user on 19.04.2017.
 */
public class DataProviders {
    @DataProvider(name = "UsingOfHyphen")
    public static Object[][] UsingOfHyphen() {
        return new Object[][] {
                {"-"},
                {"kek-"},
                {"-roock"},
        };
    }

    @DataProvider(name = "emailValidation")
    public static Object[][] emailNonValid() {
        return new Object[][]{
                {"a.paskhin1 @gmail.com"},
                {"   a.paskhin1@gmail.com"},
                {"a.paskhin1@gmail.com  "},
                {"a.paskhin1@@gmail.com"},
                {"a.paskhin1gmail.com"},
                {"a.paskhin1@gmailcom"},
                {"ыa.paskhin1@gmail.com"},
                {"apaskhin1.gmail@com"},
                {"a.paskhin1@gmail.commm"},
                {"a.paskhin1@gmail.c"},
                {"a.paskhin1@gmail..com"},
                {"a.paskhin1@gmail.сщь"},
                {"a.paskhin1@gmail.com-"}
        };
    }

    @DataProvider
    public static Object[][] passwordValidation() {
        return new Object[][]{
                {"1234q"},
                {"12345ыц"},
                {"qwerty"},
                {"123456"},
                {"123 456q"},
                {" 123456q "}
        };
    }
}
