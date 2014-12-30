package com.cettco.buycar.utils.db;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.cettco.buycar.entity.OrderItemEntity;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelperOrder extends OrmLiteSqliteOpenHelper {
	private Context context;
	private static final String DATABASE_NAME = "ormliteandroid_order.db";
	private static final int DATABASE_VERSION = 1;
	private Dao<OrderItemEntity, Integer> dao;

	// private Map daos = new HashMap();

	public DatabaseHelperOrder(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase database,
			ConnectionSource connectionSource) {
		// TODO Auto-generated method stub
		try {
			TableUtils.createTable(connectionSource, OrderItemEntity.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase database,
			ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			TableUtils.dropTable(connectionSource, OrderItemEntity.class, true);
			onCreate(database, connectionSource);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static DatabaseHelperOrder instance;

	/**
	 * ?????Helper
	 * 
	 * @param context
	 * @return
	 */
	public static synchronized DatabaseHelperOrder getHelper(Context context) {
		if (instance == null) {
			synchronized (DatabaseHelperOrder.class) {
				if (instance == null)
					instance = new DatabaseHelperOrder(context);
			}
		}

		return instance;
	}
	/**
	 * ??dao
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Dao<OrderItemEntity, Integer> getDao() throws SQLException
	{
		if (dao == null)
		{
			dao = getDao(OrderItemEntity.class);
		}
		return dao;
	}

	/**
	 * ????
	 */
	@Override
	public void close()
	{
		super.close();
		dao = null;
	}

}
