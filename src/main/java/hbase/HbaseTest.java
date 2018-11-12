package hbase;

import java.io.File;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;


public class HbaseTest {
	
    public static Configuration conf;
    public static Connection connection;
    public static Admin admin;
	
	public static void main(String[] args) throws IOException {
//		 listTables();
		 getData("test5","rowkey001", "word","");
	}
	
	
	public static void init()  { 
		conf = HBaseConfiguration.create();
 
        try {
            connection = ConnectionFactory.createConnection(conf);
            admin = connection.getAdmin();
        } catch (IOException e) {
            e.printStackTrace(); 
        }
	}
	
	
	
	// 关闭连接
    public static void close() {
        try {
            if (null != admin)
                admin.close();
            if (null != connection)
                connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // 建表
    public static void createTable(String tableNmae, String[] cols) throws IOException {

        init();
        TableName tableName = TableName.valueOf(tableNmae);

        if (admin.tableExists(tableName)) {
            System.out.println("talbe is exists!");
        } else {
            HTableDescriptor hTableDescriptor = new HTableDescriptor(tableName);
            for (String col : cols) {
                HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(col);
                hTableDescriptor.addFamily(hColumnDescriptor);
            }
            admin.createTable(hTableDescriptor);
        }
        close();
    }

    // 删表
    public static void deleteTable(String tableName) throws IOException {
        init();
        TableName tn = TableName.valueOf(tableName);
        if (admin.tableExists(tn)) {
            admin.disableTable(tn);
            admin.deleteTable(tn);
        }
        close();
    }

    // 查看已有表
    public static void listTables() throws IOException {
        init();
        HTableDescriptor hTableDescriptors[] = admin.listTables();
        for (HTableDescriptor hTableDescriptor : hTableDescriptors) {
            System.out.println("-----------------------"+hTableDescriptor.getNameAsString());
        }
        close();
    }

    // 插入数据
    public static void insterRow(String tableName, String rowkey, String colFamily, String col, String val)
            throws IOException {
        init();
        Table table = connection.getTable(TableName.valueOf(tableName));
        Put put = new Put(Bytes.toBytes(rowkey));
        put.addColumn(Bytes.toBytes(colFamily), Bytes.toBytes(col), Bytes.toBytes(val));
        table.put(put);

        // 批量插入
        /*
         * List<Put> putList = new ArrayList<Put>(); puts.add(put);
         * table.put(putList);
         */
        table.close();
        close();
    }

    // 删除数据
    public static void deleRow(String tableName, String rowkey, String colFamily, String col) throws IOException {
        init();
        Table table = connection.getTable(TableName.valueOf(tableName));
        Delete delete = new Delete(Bytes.toBytes(rowkey));
        // 删除指定列族
        // delete.addFamily(Bytes.toBytes(colFamily));
        // 删除指定列
        // delete.addColumn(Bytes.toBytes(colFamily),Bytes.toBytes(col));
        table.delete(delete);
        // 批量删除
        /*
         * List<Delete> deleteList = new ArrayList<Delete>();
         * deleteList.add(delete); table.delete(deleteList);
         */
        table.close();
        close();
    }

    // 根据rowkey查找数据
    public static void getData(String tableName, String rowkey, String colFamily, String col) throws IOException {
        init();
        Table table = connection.getTable(TableName.valueOf(tableName));
        Get get = new Get(Bytes.toBytes(rowkey));
        // 获取指定列族数据
        // get.addFamily(Bytes.toBytes(colFamily));
        // 获取指定列数据
        // get.addColumn(Bytes.toBytes(colFamily),Bytes.toBytes(col));
        Result result = table.get(get);
        System.out.println("result:"+result);
        showCell(result);
        table.close();
        close();
    }

    // 格式化输出
    public static void showCell(Result result) {
        Cell[] cells = result.rawCells();
        for (Cell cell : cells) {
            System.out.println("RowName:" + new String(CellUtil.cloneRow(cell)) + " ");
            System.out.println("Timetamp:" + cell.getTimestamp() + " ");
            System.out.println("column Family:" + new String(CellUtil.cloneFamily(cell)) + " ");
            System.out.println("row Name:" + new String(CellUtil.cloneQualifier(cell)) + " ");
            System.out.println("value:" + new String(CellUtil.cloneValue(cell)) + " ");
        }
    }

    // 批量查找数据
    public static void scanData(String tableName, String startRow, String stopRow) throws IOException {
        init();
        Table table = connection.getTable(TableName.valueOf(tableName));
        Scan scan = new Scan();
        // scan.setStartRow(Bytes.toBytes(startRow));
        // scan.setStopRow(Bytes.toBytes(stopRow));
        ResultScanner resultScanner = table.getScanner(scan);
        for (Result result : resultScanner) {
            showCell(result);
        }
        table.close();
        close();
    }
    
//    
    
    
    
//    ==================
//    private static final String TABLE_NAME = "demo_table";
//    
//    public static Configuration conf = null;
//    public HTable table = null;
//    public HBaseAdmin admin = null;
//
//    static {
//      conf = HBaseConfiguration.create();
//      System.out.println(conf.get("hbase.zookeeper.quorum"));
//    }
//
//    /**
//     * 创建一张表
//     */
//    public static void creatTable(String tableName, String[] familys)
//        throws Exception {
//      HBaseAdmin admin = new HBaseAdmin(conf);
//      if (admin.tableExists(tableName)) {
//        System.out.println("table already exists!");
//      } else {
//        HTableDescriptor tableDesc = new HTableDescriptor(tableName);
//        for (int i = 0; i < familys.length; i++) {
//          tableDesc.addFamily(new HColumnDescriptor(familys[i]));
//        }
//        admin.createTable(tableDesc);
//        System.out.println("create table " + tableName + " ok.");
//      }
//    }
//
//    /**
//     * 删除表
//     */
//    public static void deleteTable(String tableName) throws Exception {
//      try {
//        HBaseAdmin admin = new HBaseAdmin(conf);
//        admin.disableTable(tableName);
//        admin.deleteTable(tableName);
//        System.out.println("delete table " + tableName + " ok.");
//      } catch (MasterNotRunningException e) {
//        e.printStackTrace();
//      } catch (ZooKeeperConnectionException e) {
//        e.printStackTrace();
//      }
//    }
//
//    /**
//     * 插入一行记录
//     */
//    public static void addRecord(String tableName, String rowKey,
//        String family, String qualifier, String value) throws Exception {
//      try {
//        HTable table = new HTable(conf, tableName);
//        Put put = new Put(Bytes.toBytes(rowKey));
//        put.add(Bytes.toBytes(family), Bytes.toBytes(qualifier),
//            Bytes.toBytes(value));
//        table.put(put);
//        System.out.println("insert recored " + rowKey + " to table "
//            + tableName + " ok.");
//      } catch (IOException e) {
//        e.printStackTrace();
//      }
//    }
//
//    /**
//     * 删除一行记录
//     */
//    public static void delRecord(String tableName, String rowKey)
//        throws IOException {
//      HTable table = new HTable(conf, tableName);
//      List list = new ArrayList();
//      Delete del = new Delete(rowKey.getBytes());
//      list.add(del);
//      table.delete(list);
//      System.out.println("del recored " + rowKey + " ok.");
//    }
//
//    /**
//     * 查找一行记录
//     */
//    public static void getOneRecord(String tableName, String rowKey)
//        throws IOException {
//      HTable table = new HTable(conf, tableName);
//      Get get = new Get(rowKey.getBytes());
//      Result rs = table.get(get);
//      for (KeyValue kv : rs.raw()) {
//        System.out.print(new String(kv.getRow()) + " ");
//        System.out.print(new String(kv.getFamily()) + ":");
//        System.out.print(new String(kv.getQualifier()) + " ");
//        System.out.print(kv.getTimestamp() + " ");
//        System.out.println(new String(kv.getValue()));
//      }
//    }
//
//    /**
//     * 显示所有数据
//     */
//    public static void getAllRecord(String tableName) {
//      try {
//        HTable table = new HTable(conf, tableName);
//        Scan s = new Scan();
//        ResultScanner ss = table.getScanner(s);
//        for (Result r : ss) {
//          for (KeyValue kv : r.raw()) {
//            System.out.print(new String(kv.getRow()) + " ");
//            System.out.print(new String(kv.getFamily()) + ":");
//            System.out.print(new String(kv.getQualifier()) + " ");
//            System.out.print(kv.getTimestamp() + " ");
//            System.out.println(new String(kv.getValue()));
//          }
//        }
//      } catch (IOException e) {
//        e.printStackTrace();
//      }
//    }
//    
//    
//  public static void main(String[] args) {
//      // TODO Auto-generated method stub
//       try {
//            String tablename = "test5";
//            String[] familys = { "word", "count" };
//            HbaseTest.creatTable(tablename, familys);
//
//            // add record zkb
////            HbaseTest.addRecord(tablename, "zkb", "grade", "", "5");
////            HbaseTest.addRecord(tablename, "zkb", "course", "", "90");
////            HbaseTest.addRecord(tablename, "zkb", "course", "math", "97");
////            HbaseTest.addRecord(tablename, "zkb", "course", "art", "87");
////            // add record baoniu
////            HbaseTest.addRecord(tablename, "baoniu", "grade", "", "4");
////            HbaseTest
////                .addRecord(tablename, "baoniu", "course", "math", "89");
////
////            System.out.println("===========get one record========");
////            HbaseTest.getOneRecord(tablename, "zkb");
//
//            System.out.println("===========show all record========");
//            HbaseTest.getAllRecord(tablename);
//
//            System.out.println("===========del one record========");
//            HbaseTest.delRecord(tablename, "baoniu");
//            HbaseTest.getAllRecord(tablename);
//
//            System.out.println("===========show all record========");
//            HbaseTest.getAllRecord(tablename);
//          } catch (Exception e) {
//            e.printStackTrace();
//          }
//        }

}
