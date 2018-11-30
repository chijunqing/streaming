package generate.scripe.generate.util;

public enum ConfigTypeEnum {

 	CONFIG_INPUT("inputDataSource",0),//数据输入源
	CONFIG_OUTPUT("outputDataSource",1),//数据输出源
	CONFIG_SCRIPT("script",2),//Sql脚本配置
	CONFIG_CLUSTER("cluster",3);//发布配置
	
	private final String key;
    private final int value;
    
    private ConfigTypeEnum(String key,int value){
        this.key = key;
        this.value = value;
    }
    
    
  //根据key获取枚举
    public static ConfigTypeEnum getEnumByKey(String key){
        if(null == key){
            return null;
        }
        for(ConfigTypeEnum temp:ConfigTypeEnum.values()){
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
