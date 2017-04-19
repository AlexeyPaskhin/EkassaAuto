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
}
