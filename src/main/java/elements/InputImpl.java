package elements;

import elementfactory.base.Element;
import elementfactory.base.ElementImpl;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class InputImpl extends ElementImpl implements Input {


    public InputImpl(WebElement wrappedElement) {
        super(wrappedElement);
    }

    @FindBy(tagName = "input")
    private Element inputField;

    @FindBy(className = "form-error")
    private Element errMessage;

    @FindBy(tagName = "label")
    private Element label;


    @Override
    public void sendKeys(CharSequence... keysToSend) {
        clear();
        inputField.sendKeys(keysToSend);
    }

    @Override
    public void clear() {
        inputField.clear();
    }

    @Override
    public String getErrorMessage() {
        return errMessage.getText();
    }

    @Override
    public String getLabel() {
        return label.getText();
    }

    @Override
    public boolean hasError() {
        return getClassAttribute().contains("has-error");
    }
}
