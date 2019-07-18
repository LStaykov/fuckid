package elements;

import elementfactory.base.Element;
import elementfactory.base.ImplementedBy;

@ImplementedBy(ProductItemImpl.class)
public interface ProductItem extends Element {
    void gotToLearnMore();

    void goToOpenAccount();

    String getTitle();

    String getDescription();
}
