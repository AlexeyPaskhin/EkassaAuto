import org.openqa.selenium.WebElement;

/**
 * Created by user on 12.04.2017.
 */
public abstract class AbstractElement extends AbstractSearchContext<WebElement> {
    public AbstractElement(WebElement element) {
        super(element);
    }


}
