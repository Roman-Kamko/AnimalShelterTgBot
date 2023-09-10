package com.team2.animalshelter.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
@Slf4j
@Component
public class EntityUtils<T> {

    /**
     * Вспомогательный параметризированный метод для копирования non null полей.
     *
     * @param fromObj объект с изменениями.
     * @param toObj объект для вставки.
     * @return toObj.
     */
    public T copyNonNullFields (T fromObj, T toObj) {
        Field[] fields = fromObj.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(fromObj);
                if (value != null) {
                    field.set(toObj, value);
                }
            } catch (IllegalAccessException e) {
                log.error(e.getMessage(), e);
            }
        }
        return toObj;
    }

}
