package generate.scripe.bean;

public class SnakeKafkaBean extends BaseBean {
	
	/**
	 * kafka��ַ
	 */
	private String bootstrapServers;
	/**
	 * kafka�˿�
	 */
	private String port;
	/**
	 * kafka����
	 */
	private String topics;
	/**
	 * ����ID
	 */
	private String groupId;
	/**
	 * value ���л�
	 */
	private String valueSerializer="org.common.serialization.StringSerializer";
	/**
	 * key���л�
	 */
	private String keySerializer="org.common.serialization.StringSerializer";
	/**
	 * kafka����ģʽ
	 */
	private String subscribeType="subscribe";
	/**
	 * kafkaƫ����ģʽ
	 */
	private String startingOffsets="latest";
	/**
	 * ���ݸ�ʽ
	 */
	private String format="json";
	/**
	 * �ָ���
	 */
	private String separator;
	
	
	/**
	 * ����
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
