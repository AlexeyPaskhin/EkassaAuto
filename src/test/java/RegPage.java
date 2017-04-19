import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by user on 13.04.2017.
 */
public class RegPage extends AbstractPage {
    private Form regForm;
    @FindBy(xpath = "//input[@name='name']")
    WebElement nameField;

    public RegPage(WebDriver driver) {
        super(driver);
        initPageElements();
    }

    private void initPageElements() {
        regForm = new Form(findWithXPath("//form"));
    }

    RegPage inputToName(String text) {
        regForm.set(nameField, text);
        return this;
    }

    String getValue(WebElement field) {
        return regForm.getFieldValue(field);
    }
}
