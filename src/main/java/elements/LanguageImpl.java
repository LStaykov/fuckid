package elements;

import elementfactory.base.Element;
import elementfactory.base.ElementImpl;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LanguageImpl extends ElementImpl implements Language {

    public LanguageImpl(WebElement wrappedElement) {
        super(wrappedElement);
    }


    @FindBy(xpath = "//li[@id=\"language-menu\"]// span[@class=\"menu__item__link__text\"]")
    private Element languageMenu;



    @Override
    public String changeLanguage() {
        return null;
    }

    @Override
    public String currentLanguage() {
        return languageMenu.getText();
    }
}
