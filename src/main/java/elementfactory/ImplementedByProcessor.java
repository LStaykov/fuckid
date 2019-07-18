package elementfactory;

import elementfactory.base.Element;
import elementfactory.base.ImplementedBy;

class ImplementedByProcessor {

    private ImplementedByProcessor() {
    }

    static <T> Class<?> getWrapperClass(Class<T> iface) {
        if (iface.isAnnotationPresent(ImplementedBy.class)) {
            ImplementedBy annotation = iface.getAnnotation(ImplementedBy.class);
            Class<?> clazz = annotation.value();
            if (Element.class.isAssignableFrom(clazz)) {
                return annotation.value();
            }
        }
        throw new UnsupportedOperationException("Apply @ImplementedBy annotation to your Interface " +
                iface.getCanonicalName() + " if you want to extend ");
    }
}
