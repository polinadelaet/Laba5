package app.dao;

import adapter.LoggerAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public final class GetWithField<T> extends GetQuery<T> {
    private static final LoggerAdapter LOGGER_ADAPTER = LoggerAdapter.createDefault(GetWithField.class.getSimpleName());


    private final String field;
    private final Object value;
    private final Class<?> clazz;

    public GetWithField(Class<?> clazz,
                        String field,
                        Object value){
        this.field = field;
        this.value = value;
        this.clazz = clazz;
    }

    @Override
    public List<T> execute(List<T> entities) throws DAOException {
        Field clazzField;
        try {
            clazzField = clazz.getDeclaredField(field);
            clazzField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            LOGGER_ADAPTER.errorThrowable(e);
            throw new DAOException(e);
        }

        List<T> result = new ArrayList<>();

        try {
            for (T user : entities) {
                if (clazzField.get(user).equals(value)) {
                    result.add(user);
                }
            }
        } catch (IllegalAccessException e) {
            LOGGER_ADAPTER.errorThrowable(e);
            throw new DAOException(e);
        }

        clazzField.setAccessible(false);

        return result;
    }
}
