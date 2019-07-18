package elementfactory;

import elementfactory.base.Element;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ElementListHandler implements InvocationHandler {
    private final ElementLocator locator;
    private final Class<?> wrappingType;

    ElementListHandler(Class<?> interfaceType, ElementLocator locator) {
        this.locator = locator;
        if (!Element.class.isAssignableFrom(interfaceType)) {
            throw new RuntimeException("interface not assignable to Element.");
        }
        wrappingType = ImplementedByProcessor.getWrapperClass(interfaceType);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        List<Object> wrappedList = new ArrayList<>();
        Constructor<?> cons = wrappingType.getConstructor(WebElement.class);

        for (WebElement element : locator.findElements()) {
            Object thing = cons.newInstance(element);
            wrappedList.add(wrappingType.cast(thing));
        }

        try {
            return method.invoke(wrappedList, args);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}
