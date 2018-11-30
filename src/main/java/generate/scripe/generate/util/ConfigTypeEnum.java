package generate.scripe.generate.util;

public enum ConfigTypeEnum {

 	CONFIG_INPUT("inputDataSource",0),//��������Դ
	CONFIG_OUTPUT("outputDataSource",1),//�������Դ
	CONFIG_SCRIPT("script",2),//Sql�ű�����
	CONFIG_CLUSTER("cluster",3);//��������
	
	private final String key;
    private final int value;
    
    private ConfigTypeEnum(String key,int value){
        this.key = key;
        this.value = value;
    }
    
    
  //����key��ȡö��
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
