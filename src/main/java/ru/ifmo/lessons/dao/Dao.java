package ru.ifmo.lessons.dao;

import java.util.List;

//DAO паттерн

public interface Dao<T, PK> {
    // добавлять данные в таблицу
    void add(T t);
    // обновление данных в таблице
    void update(T t);
    // удаление данных по первичному ключу из таблицы
    void deleteByPK(PK pk);
    void delete(T t);
    // получние данных по первичному ключу
    T getByPK(PK pk);
    // получение всех записей
    List<T> getAll();

}
