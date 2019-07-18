package elementfactory;

import elementfactory.base.Element;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ElementHandler implements InvocationHandler {

    private final ElementLocator locator;
    private final Class<?> wrappingType;

    public ElementHandler(ElementLocator locator, Class<?> interfaceType) {
        this.locator = locator;

        if (!Element.class.isAssignableFrom(interfaceType)) {
            throw new RuntimeException("Interface not assignable to element");
        }
        wrappingType = ImplementedByProcessor.getWrapperClass(interfaceType);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        WebElement element;
        try {
            element = locator.findElement();
        } catch (NoSuchElementException e) {
            // return false instead of throwing NoSuChElement exception if calling method is isDisplayed()
            if ("isDisplayed".equals(method.getName()) && method.getDeclaringClass().isAssignableFrom(Element.class)) {
                return false;
            }

            if ("clickIfPresent".equals(method.getName())) {
                return false;
            }

            if ("toString".equals(method.getName())) {
                return "Proxy element for: " + locator.toString();
            }
            throw e;
        }

        Constructor<?> cons = wrappingType.getDeclaredConstructor(WebElement.class);
        cons.setAccessible(true);
        Object thing = cons.newInstance(element);

        try {
            return method.invoke(wrappingType.cast(thing), args);
        } catch (InvocationTargetException e) {
            // Unwrap the underlying exception
            throw e.getCause();
        }
    }
}
