import java.util.HashMap;

import jamiebalfour.HelperFunctions;
import jamiebalfour.zpe.core.YASSByteCodes;
import jamiebalfour.zpe.core.YASSRuntimeInterpreter;
import jamiebalfour.zpe.interfaces.ZPECustomFunction;

public class ZPEPlugin implements jamiebalfour.zpe.interfaces.ZPELibrary  {
	
	
	@Override
	public ZPECustomFunction[] functions() {
		ZPECustomFunction[] arr = new ZPECustomFunction[1];
		arr[0] = new MySQLConnect();
		return arr;
	}

	@Override
	public String GetName() {
		return "lib_mysql";
	}
	
	public class MySQLConnect implements jamiebalfour.zpe.interfaces.ZPECustomFunction{

		@Override
		public String CommandString() {
			return "mysql_connect";
		}

		@Override
		public String ConvertToLanguage(String arg0, String arg1) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String ImportLines(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object MainMethod(HashMap<String, Object> params, YASSRuntimeInterpreter runtime) {

			String host = params.get("host").toString();
			String db = params.get("database").toString();
			String user = params.get("user").toString();
			String password = params.get("password").toString();
			
			int port = 8889;
			if(params.containsKey("port")) {
				port = HelperFunctions.StringToInteger(params.get("port").toString());
			}
			
			lib_mysql.ZPEMySQLObject o = new lib_mysql.ZPEMySQLObject(runtime, runtime.GetCurrentZPEFunction());
			if(o.connect(host, port, db, user, password)) {
				return o;
			} else {
				return false;
			}
		}

		@Override
		public String ManualEntry() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String ManualHeader() {
			return "mysql_connect";
		}

		@Override
		public int MinimumParameters() {
			return 4;
		}

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
		public int RequiredPermissionLevel() {
			return 3;
		}

		@Override
		public byte ReturnType() {
			return YASSByteCodes.MIXED_TYPE;
		}
		
	}

    
    public static void main(String[] args) {
    	System.out.println("This JAR file is a plugin for the ZPE Programming Environment. To use it properly, copy it to the plugins folder within the .zpe_tools folder in your home directory.");
    	
    }

	@Override
	public HashMap<String, Class<?>> objects() {
		HashMap<String, Class<?>> objects = new HashMap<String, Class<?>>();
		objects.put("ZPEMySQL", lib_mysql.ZPEMySQLObject.class);
		return objects;
	}

	@Override
	public String GetVersionInfo() {
		return "1.0";
	}

}
