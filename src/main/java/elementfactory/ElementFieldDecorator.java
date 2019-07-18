package elementfactory;

import elementfactory.base.Element;
import elementfactory.base.ElementImpl;
import elementfactory.base.ImplementedBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;
import org.openqa.selenium.support.pagefactory.internal.LocatingElementListHandler;

import java.lang.reflect.*;
import java.util.List;

public class ElementFieldDecorator implements FieldDecorator {
    private ElementLocatorFactory factory;

    ElementFieldDecorator(ElementLocatorFactory factory) {
        this.factory = factory;
    }

    @Override
    public Object decorate(ClassLoader loader, Field field) {
        Class<?> fieldType = field.getType();
        boolean isDecoratableElement = WebElement.class.isAssignableFrom(fieldType);

        if (!(isDecoratableElement || isDecoratableList(field))) {
            return null;
        }

        if (field.getDeclaringClass() == ElementImpl.class) {
            return null;
        }

        ElementLocator locator = factory.createLocator(field);
        if (locator == null) {
            return null;
        }

        if (WebElement.class.equals(fieldType)) {
            fieldType = Element.class;
        }

        if (isDecoratableElement) {
            return proxyForElement(loader, fieldType, locator);
        } else {
            Class<?> erasureClass = getErasureClass(field);
            assert erasureClass != null;
            return proxyForListElements(loader, erasureClass, locator);
        }
    }

    private <T> T proxyForElement(ClassLoader loader, Class<T> interfaceType, ElementLocator locator) {
        InvocationHandler handler = new ElementHandler(locator, interfaceType);

        T proxy;
        proxy = interfaceType.cast(Proxy.newProxyInstance(loader, new Class[]{interfaceType}, handler));

        return proxy;
    }

    private <T> List<T> proxyForListElements(ClassLoader loader, Class<T> interfaceType, ElementLocator locator) {
        InvocationHandler handler;
        if (interfaceType.getAnnotation(ImplementedBy.class) != null) {
            handler = new ElementListHandler(interfaceType, locator);
        } else {
            handler = new LocatingElementListHandler(locator);
        }

        List<T> proxy;
        proxy = (List<T>) Proxy.newProxyInstance(loader, new Class[]{List.class}, handler);
        return proxy;
    }

    private Class<?> getErasureClass(Field field) {
        Type genericType = field.getGenericType();
        if (!(genericType instanceof ParameterizedType)) {
            return null;
        }
        return (Class<?>) ((ParameterizedType) genericType).getActualTypeArguments()[0];
    }

    private boolean isDecoratableList(Field field) {
        if (!List.class.isAssignableFrom(field.getType())) {
            return false;
        }

        Class<?> erasureClass = getErasureClass(field);
        if (erasureClass == null) {
            return false;
        }

        if (!WebElement.class.isAssignableFrom(erasureClass)) {
            return false;
        }

        return field.getAnnotation(FindBy.class) != null ||
                field.getAnnotation(FindBys.class) != null ||
                field.getAnnotation(FindAll.class) != null;
    }
}
