package generate.scripe.bean;

public class SnakeStormBean {
	
	/**
	 * kafka地址
	 */
	private String bootstrap_servers;
	/**
	 * kafka端口
	 */
	private String port;
	/**
	 * kafka主题
	 */
	private String topics;
	/**
	 * 分组ID
	 */
	private String groupId;
	/**
	 * value 序列化
	 */
	private String value_serializer="org.common.serialization.StringSerializer";
	/**
	 * key序列化
	 */
	private String key_serializer="org.common.serialization.StringSerializer";
	/**
	 * kafka消费模式
	 */
	private String subscribeType="subscribe";
	/**
	 * kafka偏移量模式
	 */
	private String starting_Offsets="latest";
	/**
	 * 数据存储格式
	 */
	private String format="json";
	/**
	 * 分隔符
	 */
	private String separator;
	
	
	
	public String getBootstrap_servers() {
		return bootstrap_servers;
	}



	public void setBootstrap_servers(String bootstrap_servers) {
		this.bootstrap_servers = bootstrap_servers;
	}



	public String getPort() {
		return port;
	}



	public void setPort(String port) {
		this.port = port;
	}



	public String getTopics() {
		return topics;
	}



	public void setTopics(String topics) {
		this.topics = topics;
	}



	public String getGroupId() {
		return groupId;
	}



	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}



	public String getValue_serializer() {
		return value_serializer;
	}



	public void setValue_serializer(String value_serializer) {
		this.value_serializer = value_serializer;
	}



	public String getKey_serializer() {
		return key_serializer;
	}



	public void setKey_serializer(String key_serializer) {
		this.key_serializer = key_serializer;
	}



	public String getSubscribeType() {
		return subscribeType;
	}



	public void setSubscribeType(String subscribeType) {
		this.subscribeType = subscribeType;
	}



	public String getStarting_Offsets() {
		return starting_Offsets;
	}



	public void setStarting_Offsets(String starting_Offsets) {
		this.starting_Offsets = starting_Offsets;
	}



	public String getFormat() {
		return format;
	}



	public void setFormat(String format) {
		this.format = format;
	}



	public String getSeparator() {
		return separator;
	}



	public void setSeparator(String separator) {
		this.separator = separator;
	}



	@Override
	public String toString() {
		return "SnakeKafkaConsumer [bootstrap_servers=" + bootstrap_servers + ", port=" + port + ", topics=" + topics
				+ ", groupId=" + groupId + ", value_serializer=" + value_serializer + ", key_serializer="
				+ key_serializer + ", subscribeType=" + subscribeType + ", starting_Offsets=" + starting_Offsets
				+ ", format=" + format + ", separator=" + separator + "]";
	}
	
	
	
	

}
