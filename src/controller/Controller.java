package controller;

import model.Model;

public interface Controller<T extends Model> {
    void create(T obj);
    T read(int id);
    void update(T obj);
    void delete(int id);
}