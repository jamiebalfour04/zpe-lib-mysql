
import java.util.HashMap;

import jamiebalfour.zpe.core.YASSRuntimeInterpreter;
import jamiebalfour.zpe.core.ZPEObject;
import jamiebalfour.zpe.core.ZPEStructure;
import jamiebalfour.zpe.interfaces.ZPEVariableContainer;

public class ZPEMySQLObject extends ZPEStructure {
	
	
	libSQL lib = new libSQL();
	private static final long serialVersionUID = -5823291589951678478L;

	public ZPEMySQLObject(YASSRuntimeInterpreter z, ZPEVariableContainer parent) {
		super(z, parent, "ZPEMySQLObject");
		
		addNativeMethod("connect", new connect_Command());
	}
	
	class connect_Command implements jamiebalfour.zpe.interfaces.ZPENativeMethod {

		@Override
		public String[] ParameterNames() {
			String[] s = {"host", "database", "username", "password"};
			return s;
		}

		@Override
		public Object MainMethod(HashMap<String, Object> parameters, ZPEObject parent) {
			lib.connect(parameters.get("host").toString(), parameters.get("database").toString(), parameters.get("username").toString(), parameters.get("password").toString());
			
			return true;
		}

		@Override
		public int RequiredPermissionLevel() {
			return 0;
		}
		
	}

}
