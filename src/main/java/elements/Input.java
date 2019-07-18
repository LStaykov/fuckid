package elements;

import elementfactory.base.Element;
import elementfactory.base.ImplementedBy;

@ImplementedBy(InputImpl.class)
public interface Input extends Element {
    String getErrorMessage();

    String getLabel();

    boolean hasError();
}
