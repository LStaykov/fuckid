package elements;

import elementfactory.base.Element;
import elementfactory.base.ImplementedBy;

@ImplementedBy(ContactImpl.class)
public interface Contact extends Element {

    String getCountry();

    String getPhone();

    String getEmail();

    String getAddress();

}
