package generate.scripe.generate.util;

public enum ScriptLanguageEnum {

 	PYTHON("python",0),
	JAVA("java",1),
	SCALA("scala",2);
	
	private final String key;
    private final int value;
    
    private ScriptLanguageEnum(String key,int value){
        this.key = key;
        this.value = value;
    }
    
    
  //根据key获取枚举
    public static ScriptLanguageEnum getEnumByKey(String key){
        if(null == key){
            return null;
        }
        for(ScriptLanguageEnum temp:ScriptLanguageEnum.values()){
            if(temp.getKey().equals(key)){
                return temp;
            }
        }
        return null;
    }
    public String getKey() {
        return key;
    }
    public int getValue() {
        return value;
    }
}
