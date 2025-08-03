package model;

public interface Model {

    boolean save();


    boolean delete();


    static Model find(int id) {

        throw new UnsupportedOperationException("Not implemented");
    }


    static Model[] All() {

        throw new UnsupportedOperationException("Not implemented");
    }

    static Model[] where(String condition, Object... params) {
        throw new UnsupportedOperationException("Not implemented");
    }

    static Model findBy(String field, Object value) {

        throw new UnsupportedOperationException("Not implemented");
    }
}