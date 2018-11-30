package generate.scripe.bean;

public class SnakeKafkaBean extends BaseBean {
	
	/**
	 * kafka地址
	 */
	private String bootstrapServers;
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
	private String valueSerializer="org.common.serialization.StringSerializer";
	/**
	 * key序列化
	 */
	private String keySerializer="org.common.serialization.StringSerializer";
	/**
	 * kafka消费模式
	 */
	private String subscribeType="subscribe";
	/**
	 * kafka偏移量模式
	 */
	private String startingOffsets="latest";
	/**
	 * 数据格式
	 */
	private String format="json";
	/**
	 * 分隔符
	 */
	private String separator;
	
	
	/**
	 * 分区
	 */
	private int numPartitions=1;
	
	
	
	public int getNumPartitions() {
		return numPartitions;
	}
	public void setNumPartitions(int numPartitions) {
		this.numPartitions = numPartitions;
	}
	 
	public String getBootstrapServers() {
		return bootstrapServers;
	}
	public void setBootstrapServers(String bootstrapServers) {
		this.bootstrapServers = bootstrapServers;
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
	public String getValueSerializer() {
		return valueSerializer;
	}
	public void setValueSerializer(String valueSerializer) {
		this.valueSerializer = valueSerializer;
	}
	public String getKeySerializer() {
		return keySerializer;
	}
	public void setKeySerializer(String keySerializer) {
		this.keySerializer = keySerializer;
	}
	public String getSubscribeType() {
		return subscribeType;
	}
	public void setSubscribeType(String subscribeType) {
		this.subscribeType = subscribeType;
	}
	public String getStartingOffsets() {
		return startingOffsets;
	}
	public void setStartingOffsets(String startingOffsets) {
		this.startingOffsets = startingOffsets;
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
		return "SnakeKafkaBean [bootstrapServers=" + bootstrapServers + ", port=" + port + ", topics=" + topics
				+ ", groupId=" + groupId + ", valueSerializer=" + valueSerializer + ", keySerializer=" + keySerializer
				+ ", subscribeType=" + subscribeType + ", startingOffsets=" + startingOffsets + ", format=" + format
				+ ", separator=" + separator + ", numPartitions=" + numPartitions + "]";
	}
}
