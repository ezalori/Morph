import java.util.List;

import com.ververica.cdc.connectors.mysql.source.MySqlSource;
import com.ververica.cdc.connectors.mysql.table.StartupOptions;
import com.ververica.cdc.debezium.JsonDebeziumDeserializationSchema;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class CdcTest {
  public static void main(String[] args) throws Exception {
    var mysqlSource = MySqlSource.<String>builder()
      .hostname("localhost")
      .port(3306)
      .databaseList("morph")
      .tableList("morph\\..+")
      .username("root")
      .password("")
      .deserializer(new JsonDebeziumDeserializationSchema())
      .startupOptions(StartupOptions.latest())
      .build();

    var env = StreamExecutionEnvironment.getExecutionEnvironment();
    env.enableCheckpointing(3000);
    env.fromSource(mysqlSource, WatermarkStrategy.noWatermarks(), "source")
      .setParallelism(4)
      .print().setParallelism(1);

    env.execute();
  }
}
