package elements;

import elementfactory.base.Element;
import elementfactory.base.ImplementedBy;

@ImplementedBy(LanguageImpl.class)
public interface Language extends Element {


    String changeLanguage();

    String currentLanguage();

}
