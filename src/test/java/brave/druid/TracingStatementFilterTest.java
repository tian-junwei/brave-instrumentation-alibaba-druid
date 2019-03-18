package brave.druid;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tianjunwei
 * @date 2019/3/18 18:59
 */
@RunWith(MockitoJUnitRunner.class)
public class TracingStatementFilterTest {


    @Test
    public void druid() throws Exception {

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl("jdbc:mysql://localhost:3306/tian?user=root&password=root&useUnicode=true&characterEncoding=UTF8");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("root");

        List<Filter> filters = new ArrayList<>();
        TracingStatementFilter tracingStatementFilter = new TracingStatementFilter("test");
        filters.add(tracingStatementFilter);

        druidDataSource.setProxyFilters(filters);

        connection = druidDataSource.getConnection();

        statement = connection.createStatement();
        String sql = "select * from tbl_user ";
        resultSet = statement.executeQuery(sql);

        resultSet.beforeFirst();
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }
    }

}
