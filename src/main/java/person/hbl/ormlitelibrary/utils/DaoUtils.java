package person.hbl.ormlitelibrary.utils;

import android.util.Log;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import person.hbl.ormlitelibrary.helper.BaseDatabaseHelper;

/**
 * 数据库操作
 *
 * @author LanHe-Android
 * @param <T>
 */
public class DaoUtils<T> {
    private static final String TAG = DaoUtils.class.getSimpleName();

    private Dao<T, Integer> dao;
    private BaseDatabaseHelper helper;

    /**
     * 返回实体类对应的dao对象
     *
     * @param helper
     * @param beanClass
     * @throws SQLException
     */
    public DaoUtils(BaseDatabaseHelper helper, Class<T> beanClass) {
        this.helper = helper;
        if (dao == null) {
            try {
                dao = helper.getDao(beanClass);
            } catch (SQLException e) {
                Log.e(TAG, "获取dao失败");
            }
        }
    }


    // *************新增数据方法*********************************


    /**
     * 插入单条数据
     *
     * @param object
     * @throws SQLException
     */
    public void insertData(T object) throws SQLException {
        if (dao == null) {
            throw new SQLException("Dao is null");
        }
        dao.createOrUpdate(object);
    }

    /**
     * 批量插入数据
     *
     * @param objects
     * @throws SQLException
     */
    public void insertData(Collection<T> objects) throws SQLException {
        if (dao == null) {
            throw new SQLException("Dao is null");
        }
        dao.create(objects);
    }


    // *************查询数据方法*********************************

    /**
     * 查询所有数据
     *
     * @return
     * @throws SQLException
     */
    public List<T> getAllData() throws SQLException {
        if (dao == null) {
            throw new SQLException("Dao is null");
        }
        return dao.queryForAll();
    }

    /**
     * 通过ID查找
     *
     * @param id
     * @return
     * @throws SQLException
     */
    public T getDataByID(Integer id) throws SQLException {
        if (dao == null) {
            throw new SQLException("Dao is null");
        }
        return dao.queryForId(id);
    }


    /**
     * 条件查询
     *
     * @param clauses
     * @return
     * @throws SQLException
     */
    public List<T> getDataEqByClause(Map<String, Object> clauses) throws SQLException {
        if (dao == null) {
            throw new SQLException("Dao is null");
        }
        return dao.queryForFieldValuesArgs(clauses);
    }

    /**
     * 匹配是否相同查找
     *
     * @param condition 要比较的条件
     * @param content   内容
     * @return
     * @throws SQLException
     */
    public List<T> getDataEqByClause(String condition, Object content) throws SQLException {
        if (dao == null) {
            throw new SQLException("Dao is null");
        }
        QueryBuilder builder = dao.queryBuilder();
        builder.where().eq(condition, content);  // 等于（=）equals
        return builder.query();
    }

    // *************更新数据方法*********************************

    /**
     * 更新单条数据
     *
     * @param object
     * @throws SQLException
     */
    public void updateData(T object) throws SQLException {
        if (dao == null) {
            throw new SQLException("Dao is null");
        }
        dao.update(object);
    }


    // *************删除数据方法*********************************

    /**
     * 删除单条数据
     *
     * @param object
     * @throws SQLException
     */
    public void deleteData(T object) throws SQLException {
        if (dao == null) {
            throw new SQLException("Dao is null");
        }
        dao.delete(object);
    }

    /**
     * 批量删除数据
     *
     * @param objects
     * @throws SQLException
     */
    public void deleteData(Collection<T> objects) throws SQLException {
        if (dao == null) {
            throw new SQLException("Dao is null");
        }
        dao.delete(objects);
    }


    /**
     * 清空表格数据
     * delete from TableName;  // 清空数据
     * update sqlite_sequence SET seq = 0 where name ='TableName'; // 自增长ID为 0
     *
     * @param tableName
     * @throws SQLException
     */
    public void cleanTable(String tableName) throws SQLException {
        dao.queryRaw("delete from " + tableName);
        dao.queryRaw("update sqlite_sequence SET seq = 0 where name ='" + tableName + "'");
    }

    // *************返回dao*********************************

    /**
     * 返回dao
     *
     * @return
     */
    public Dao<T, Integer> getDao() throws SQLException {
        if (dao == null) {
            throw new SQLException("Dao is null");
        }
        return dao;
    }

}
