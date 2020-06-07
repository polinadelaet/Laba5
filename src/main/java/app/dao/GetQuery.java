package app.dao;

import java.util.List;

public abstract class GetQuery<T> {
    public abstract List<T> execute(List<T> entities) throws DAOException;
}
