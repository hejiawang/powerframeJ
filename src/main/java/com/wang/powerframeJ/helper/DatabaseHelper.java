package com.wang.powerframeJ.helper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wang.powerframeJ.util.CollectionUtil;
import com.wang.powerframeJ.util.PropsUtil;


/**
 * 数据库操作助手类
 * @author HeJiawang
 * @date   2017.07.31
 */
public final class DatabaseHelper {

	/**
	 * logger manager
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);
	
	/**
	 * dbutil
	 */
	private static final QueryRunner QUERY_RUNNER;
	
	/**
	 * 隔离线程的容器
	 */
	private static final ThreadLocal<Connection> CONNECTION_HOLDER ;
	
	private static final BasicDataSource DATA_SOURCE;
	
	static {
		QUERY_RUNNER = new QueryRunner();
		
		CONNECTION_HOLDER = new ThreadLocal<Connection>();
		
		Properties conf = PropsUtil.loadProps("config.properties");
		String driver = conf.getProperty("jdbc.driver");
		String url = conf.getProperty("jdbc.url");
		String username = conf.getProperty("jdbc.username");
		String password = conf.getProperty("jdbc.password");
		
		DATA_SOURCE = new BasicDataSource();
		DATA_SOURCE.setDriverClassName(driver);
		DATA_SOURCE.setUrl(url);
		DATA_SOURCE.setUsername(username);
		DATA_SOURCE.setPassword(password);
	}
	
	
	/**
	 * 获取数据库链接
	 * @return
	 */
	public static Connection getConnection() {
		Connection conn = CONNECTION_HOLDER.get();
		if( conn == null ) {
			
			try {
				conn = DATA_SOURCE.getConnection();
			} catch (SQLException e) {
				LOGGER.error("get connection failure", e);
				throw new RuntimeException(e);
			} finally {
				CONNECTION_HOLDER.set(conn);
			}
		}
		
		return conn;
	}
	
	/**
	 * 开启事务
	 */
	public static void beginTransaction() {
		Connection conn = getConnection();
		if( conn != null ) {
			try {
				conn.setAutoCommit(false);
			} catch (SQLException e) {
				LOGGER.error("begin transaction failure", e);
				throw new RuntimeException(e);
			} finally {
				CONNECTION_HOLDER.set(conn);
			}
		}
	}
	
	/**
	 * 提交事物
	 */
	public static void commitTransaction() {
		Connection conn = getConnection();
		if( conn != null ) {
			try {
				conn.commit();
				conn.close();
			} catch (SQLException e) {
				LOGGER.error("commit transaction failure", e);
				throw new RuntimeException(e);
			} finally {
				CONNECTION_HOLDER.remove();
			}
		}
	}
	
	/**
	 * 回滚事物
	 */
	public static void rollbackTransaction() {
		Connection conn = getConnection();
		if( conn != null ) {
			try {
				conn.rollback();
				conn.close();
			} catch (SQLException e) {
				LOGGER.error("rollback transaction failure", e);
				throw new RuntimeException(e);
			} finally {
				CONNECTION_HOLDER.remove();
			}
		}
	}
	
	/**
	 * 查询实体列表
	 * @param entityClass
	 * @param sql
	 * @param params
	 * @return
	 */
	public static <T> List<T> queryEntityList( Class<T> entityClass, String sql, Object... params ) {
		List<T> entityList;
		try {
			Connection conn = getConnection();
			entityList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(entityClass), params); 
		} catch (SQLException e) {
			LOGGER.error("query entity list failure", e);
			throw new RuntimeException(e);
		}
		
		return entityList;
	}
	
	/**
	 * 查询实体
	 * @param entityClass
	 * @param sql
	 * @param params
	 * @return
	 */
	public static <T> T queryEntity( Class<T> entityClass, String sql, Object...params ) {
		T entity;
		try {
			Connection conn = getConnection();
			entity = QUERY_RUNNER.query(conn, sql, new BeanHandler<T>(entityClass), params); 
		} catch (SQLException e) {
			LOGGER.error("query entity list failure", e);
			throw new RuntimeException(e);
		}
		
		return entity;
	}
	
	/**
	 * 执行查询语句
	 * @param sql
	 * @param params
	 * @return
	 */
	public static List<Map<String, Object>> executeQuery( String sql, Object...params ) {
		List<Map<String, Object>> entity;
		try {
			Connection conn = getConnection();
			entity = QUERY_RUNNER.query(conn, sql, new MapListHandler(), params); 
		} catch (SQLException e) {
			LOGGER.error("query entity list failure", e);
			throw new RuntimeException(e);
		}
		
		return entity;
	}
	
	/**
	 * 执行更新语句 ( 包括 update, insert, delete ) 
	 * @param sql
	 * @param params
	 * @return 受影响的行数
	 */
	public static int executeUpdate( String sql, Object...params ) {
		int rows = 0;
		try {
			Connection conn = getConnection();
			rows = QUERY_RUNNER.update(conn, sql, params); 
		} catch (SQLException e) {
			LOGGER.error("query entity list failure", e);
			throw new RuntimeException(e);
		}
		
		return rows;
	}
	
	/**
	 * 插入实体
	 * @param entityClass
	 * @param fieldMap
	 * @return
	 */
	public static <T> boolean insertEntity( Class<T> entityClass, Map<String, Object> fieldMap ) {
		if( CollectionUtil.isEmpty(fieldMap) ) {
			LOGGER.error("can not insert entity : fieldMap isempty");
			return false;
		}
		
		String sql = "INSERT INTO " + getTableName(entityClass);
		StringBuilder columns = new StringBuilder("(");
		StringBuilder values = new StringBuilder("(");
		for( String fieldName : fieldMap.keySet() ) {
			columns.append(fieldName).append(", ");
			values.append("?, ");
		}
		columns.replace(columns.lastIndexOf(", "), columns.length(), ")");
		values.replace(values.lastIndexOf(", "), values.length(), ")");
		sql += columns + " VALUES " + values;
		
		Object[] params = fieldMap.values().toArray();
		return 1 == executeUpdate(sql, params);
	}
	
	/**
	 * 更新实体
	 * @param entityClass
	 * @param id
	 * @param fieldMap
	 * @return
	 */
	public static <T> boolean updateEntity( Class<T> entityClass, long id, Map<String, Object> fieldMap ) {
		if( CollectionUtil.isEmpty(fieldMap) ) {
			LOGGER.error("can not update entity : fieldMap isempty");
			return false;
		}
		
		String sql = "UPDATE " + getTableName(entityClass) + " SET ";
		StringBuilder columns = new StringBuilder();
		for( String fieldName : fieldMap.keySet() ) {
			columns.append(fieldName).append("=?, ");
		}
		sql += columns.substring(0, columns.lastIndexOf(",")) + " WHERE id=?";
		
		List<Object> paramList = new ArrayList<Object>();
		paramList.addAll(fieldMap.values());
		paramList.add(id);
		Object[] params = paramList.toArray();
		
		return 1 == executeUpdate(sql, params);
	}
	
	/**
	 * 删除实体
	 * @param entityClass
	 * @param id
	 * @return
	 */
	public static <T> boolean deleteEntity( Class<T> entityClass, long id ) {
		String sql = "DELETE FROM " + getTableName(entityClass) + " WHERE ID = ?";
		return 1 == executeUpdate(sql, id);
	}
	
	/**
	 * 获取类名
	 * @param entityClass
	 * @return
	 */
	public static String getTableName( Class<?> entityClass ) {
		return entityClass.getSimpleName();
	}
	
	/**
	 * 执行 SQL 文件
	 * 仅限文件中每一行都是完整sql的文件
	 * @param filePath
	 */
	public static void executeSqlFile( String filePath ) {
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		try {
			String sql;
			while( ( sql = reader.readLine() ) != null ) {
				executeUpdate(sql);
			}
		} catch (Exception e) {
			LOGGER.error("execute sql file failure", e);
			throw new RuntimeException(e);
		}
	}
}
