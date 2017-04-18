import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

/**
 * Created by user on 12.04.2017.
 */
public class AbstractSearchContext<T extends SearchContext> {
    protected T context;
    public AbstractSearchContext(T context) {
        this.context = context;
    }

    WebElement findWithXPath(String xpathLocator) {
        WebElement el = context.findElement(By.xpath(xpathLocator));
        return el;
    }

}
