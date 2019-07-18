package elementfactory;

import elementfactory.base.ImplementedBy;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class ElementFactory {

    public static <T> T initElements(WebElement wrappedElement, Class<T> elementObjectToProxy) {
        // when wrapped element is the search context
        T elementObject = instantiateElement(wrappedElement, elementObjectToProxy);
        return initElements(wrappedElement, elementObject);
    }

    public static <T> T initElements(WebDriver driver, Class<T> elementObjectToProxy) {
        // when the driver is the search context
        WebElement wrappedElement = driver.findElement(By.tagName("body"));
        T elementObject = instantiateElement(wrappedElement, elementObjectToProxy);
        return initElements(wrappedElement, elementObject);
    }

    public static <T> T initElements(SearchContext searchContext, T object) {
        ElementLocatorFactory factory = new DefaultElementLocatorFactory(searchContext);
        initElements(factory, object);
        return object;
    }

    public static void initElements(ElementLocatorFactory factory, Object object) {
        FieldDecorator decorator = new ElementFieldDecorator(factory);
        initElements(decorator, object);
    }

    public static void initElements(FieldDecorator decorator, Object object) {
        Class<?> proxyIn = object.getClass();
        while (proxyIn != Object.class) {
            proxyFields(decorator, object, proxyIn);
            proxyIn = proxyIn.getSuperclass();
        }
    }

    private static void proxyFields(FieldDecorator decorator, Object object, Class<?> proxyIn) {
        Field[] fields = proxyIn.getDeclaredFields();
        for (Field field : fields) {
            Object value = decorator.decorate(object.getClass().getClassLoader(), field);
            if (value != null) {
                try {
                    field.setAccessible(true);
                    field.set(object, value);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private static <T> T instantiateElement(WebElement wrappedElement, Class<T> elementObjectToProxy) {
        if (elementObjectToProxy.isAnnotationPresent(ImplementedBy.class)) {
            elementObjectToProxy = (Class<T>) ImplementedByProcessor.getWrapperClass(elementObjectToProxy);
        }

        try {
            try {
                Constructor<T> constructor = elementObjectToProxy.getConstructor(WebElement.class);
                return constructor.newInstance(wrappedElement);
            } catch (NoSuchMethodException e) {
                return elementObjectToProxy.newInstance();
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
