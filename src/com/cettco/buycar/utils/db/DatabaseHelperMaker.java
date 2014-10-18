package com.cettco.buycar.utils.db;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.cettco.buycar.entity.CarMakerEntity;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelperMaker extends OrmLiteSqliteOpenHelper {
	private Context context;
	private static final String DATABASE_NAME = "ormliteandroid_maker.db";
	private static final int DATABASE_VERSION = 1;
	private Dao<CarMakerEntity, Integer> dao;

	// private Map daos = new HashMap();

	public DatabaseHelperMaker(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase database,
			ConnectionSource connectionSource) {
		// TODO Auto-generated method stub
		try {
			TableUtils.createTable(connectionSource, CarMakerEntity.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase database,
			ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			TableUtils.dropTable(connectionSource, CarMakerEntity.class, true);
			onCreate(database, connectionSource);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static DatabaseHelperMaker instance;

	/**
	 * 单例获取该Helper
	 * 
	 * @param context
	 * @return
	 */
	public static synchronized DatabaseHelperMaker getHelper(Context context) {
		if (instance == null) {
			synchronized (DatabaseHelperMaker.class) {
				if (instance == null)
					instance = new DatabaseHelperMaker(context);
			}
		}

		return instance;
	}
	/**
	 * 获得dao
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Dao<CarMakerEntity, Integer> getDao() throws SQLException
	{
		if (dao == null)
		{
			dao = getDao(CarMakerEntity.class);
		}
		return dao;
	}

	/**
	 * 释放资源
	 */
	@Override
	public void close()
	{
		super.close();
		dao = null;
	}

}
