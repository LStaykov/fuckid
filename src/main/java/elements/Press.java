package elements;

import elementfactory.base.Element;
import elementfactory.base.ImplementedBy;

@ImplementedBy(PressImpl.class)
public interface Press extends Element {

    boolean isLogoThere();

    String getContent();

    void clickReadMoreButton();
}
