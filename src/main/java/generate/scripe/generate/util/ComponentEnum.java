package generate.scripe.generate.util;

public enum ComponentEnum {

	
	 COMPONENT_KAFKA("kafka",0),
	 COMPONENT_SPARK("spark",1),
	 COMPONENT_STORM("storm",2),
	 COMPONENT_FLINK("flink",3),
	 COMPONENT_HABSE("hbase",4),
	 COMPONENT_HIVE("hive",5),
	COMPONENT_YARN("yarn",6),
	COMPONENT_MESOS("mesos",7);	
	private final String key;
    private final int value;
    
    private ComponentEnum(String key,int value){
        this.key = key;
        this.value = value;
    }
    
    
    
    //根据key获取枚举
      public static ComponentEnum getEnumByKey(String key){
          if(null == key){
              return null;
          }
          for(ComponentEnum temp:ComponentEnum.values()){
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
