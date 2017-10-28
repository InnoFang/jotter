
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Author: Inno Fang
 * Time: 2017/10/28 11:28
 * Description: Singleton Factory
 * call get() method and transfer Class type to get the class singleton, use ConcurrentHashMap to store
 */
public class SingletonFactory {

    private static final ConcurrentHashMap<Class, Object> map = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> T get(@NotNull Class<T> type) {
        Object object = map.get(type);
        try {
            if (null == object) {
                synchronized (map) {
                    object = type.newInstance();
                    map.put(type, object);
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return (T) object;
    }

    public static <T> void remove(@NotNull Class<T> type) {
        map.remove(type);
    }

}
