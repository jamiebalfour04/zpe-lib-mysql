package lib_mysql;
import java.sql.SQLException;
import java.util.HashMap;

import jamiebalfour.zpe.types.ZPEList;
import jamiebalfour.HelperFunctions;
import jamiebalfour.zpe.core.YASSRuntimeInterpreter;
import jamiebalfour.zpe.interfaces.ZPEPropertyWrapper;
import jamiebalfour.zpe.core.ZPEObject;

public class ZPEMySQLObject extends ZPEObject {
		
	private static final long serialVersionUID = 5811011685776858084L;
	
	MySQLAccess sql;
	ZPEMySQLObject _this = this;

	public ZPEMySQLObject(YASSRuntimeInterpreter z, ZPEPropertyWrapper parent) {
		super(z, parent, "ZPEMySQLConnection");
		System.out.println("MySQL loaded");
		addNativeMethod("connect", new connect_Command());
		addNativeMethod("query", new query_Command());
		addNativeMethod("prepare", new prepare_Command());
		addNativeMethod("get_columns", new get_columns_Command());
		addNativeMethod("get_tables", new get_tables_Command());
		addNativeMethod("query_to_json", new query_to_json_Command());
	}
	
	public boolean connect(String host, int port, String db, String user, String password) {
		try {
			sql = new MySQLAccess();
		} catch(Exception e) {
			System.err.println("Cannot create MySQL connection.");
			return false;
		}
			
		try {
			sql.connect(host, port, db, user, password);
			return true;
		} catch (SQLException e) {
			System.err.println("Cannot connect to MySQL database.");
		}
		
		return false;
		
	}
	
	class connect_Command implements jamiebalfour.zpe.interfaces.ZPEObjectNativeMethod {

		
		@Override
		public String[] ParameterNames() {
			String[] params = new String[5];
			params[0] = "host";
			params[1] = "database";
			params[2] = "user";
			params[3] = "password";
			params[4] = "port";
			
			return params;
		}

		@Override
		public Object MainMethod(HashMap<String, Object> parameters, ZPEObject parent) {

			try {
				String host = parameters.get("host").toString();
				int port = HelperFunctions.StringToInteger(parameters.get("port").toString());
				String database = parameters.get("database").toString();
				String username = parameters.get("user").toString();
				String password = parameters.get("password").toString();
				
				return ((ZPEMySQLObject) parent).connect(host, port, database, username, password);
			} catch (Exception e) {
				return false;
			}
		}
		
		@Override
		public int RequiredPermissionLevel() {
			return 0;
		}

		@Override
		public String name() {
			return "connect";
		}
		
	}
	
	
	class get_tables_Command implements jamiebalfour.zpe.interfaces.ZPEObjectNativeMethod {

		@Override
		public String[] ParameterNames() {
			String[] l = {};
			return l;
		}

		@Override
		public Object MainMethod(HashMap<String, Object> parameters, ZPEObject parent) {

			try {
				return sql.getTableNames();
			} catch (Exception e) {
				return false;
			}
		}
		
		@Override
		public int RequiredPermissionLevel() {
			return 3;
		}

		@Override
		public String name() {
			return "get_tables";
		}
		
	}
	
	class query_to_json_Command implements jamiebalfour.zpe.interfaces.ZPEObjectNativeMethod {

		@Override
		public String[] ParameterNames() {
			String[] l = {"query_str"};
			return l;
		}

		@Override
		public Object MainMethod(HashMap<String, Object> parameters, ZPEObject parent) {

			try {
				ZPEList l = sql.query(parameters.get("query_str").toString());
				
				jamiebalfour.parsers.json.ZenithJSONParser parser = new jamiebalfour.parsers.json.ZenithJSONParser();

				String out = parser.jsonEncode(l);
				
				return out;
			} catch (Exception e) {
				return false;
			}
		}
		
		@Override
		public int RequiredPermissionLevel() {
			return 0;
		}

		@Override
		public String name() {
			return "query_to_json";
		}
		
	}
	
	class get_columns_Command implements jamiebalfour.zpe.interfaces.ZPEObjectNativeMethod {

		@Override
		public String[] ParameterNames() {
			String[] l = {"table"};
			return l;
		}

		@Override
		public Object MainMethod(HashMap<String, Object> parameters, ZPEObject parent) {

			try {
				return sql.getColumnNames(parameters.get("table").toString());
			} catch (Exception e) {
				return false;
			}
		}
		
		@Override
		public int RequiredPermissionLevel() {
			return 0;
		}

		@Override
		public String name() {
			return "get_columns";
		}
		
	}
	
	class query_Command implements jamiebalfour.zpe.interfaces.ZPEObjectNativeMethod {

		@Override
		public String[] ParameterNames() {
			String[] l = {"query_str"};
			return l;
		}

		@Override
		public Object MainMethod(HashMap<String, Object> parameters, ZPEObject parent) {

			try {
				return sql.query(parameters.get("query_str").toString());
			} catch (Exception e) {
				return false;
			}
		}
		
		@Override
		public int RequiredPermissionLevel() {
			return 0;
		}

		@Override
		public String name() {
			return "query";
		}
		
	}
	
	class prepare_Command implements jamiebalfour.zpe.interfaces.ZPEObjectNativeMethod {

		@Override
		public String[] ParameterNames() {
			String[] l = {"query_str"};
			return l;
		}

		@Override
		public Object MainMethod(HashMap<String, Object> parameters, ZPEObject parent) {
			
			try {
				ZPEMySQLPreparedStatementObject prep = new ZPEMySQLPreparedStatementObject(GetRuntime(), parent);
				prep.sqlConn = _this;
				
				prep.prepare(parameters.get("query_str").toString());
				
				return prep;
			} catch (Exception e) {
				return false;
			}
		}
		
		@Override
		public int RequiredPermissionLevel() {
			return 0;
		}

		@Override
		public String name() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}

}


