import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by user on 29.06.2017.
 */
public class SmsVerificationPage extends AbstractPage {
    @FindBy(xpath = "//*[contains(text(), 'Proszę sprawdzić SMS')]") WebElement smsCodeSubmitButton;
    public SmsVerificationPage(WebDriver driver) {
        super(driver);
    }
}
