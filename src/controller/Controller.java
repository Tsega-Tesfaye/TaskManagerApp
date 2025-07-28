package controller;

import model.Model;

/**
 * Generic controller interface that provides operations on a model.
 */
public interface Controller<T extends Model> {
    void create(T obj);
    T read(int id);
    void update(T obj);
    void delete(int id);
}