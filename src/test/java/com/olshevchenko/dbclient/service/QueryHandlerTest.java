package com.olshevchenko.dbclient.service;

import com.olshevchenko.dbclient.entity.QueryResult;
import com.olshevchenko.dbclient.entity.SqlOperator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Oleksandr Shevchenko
 */
@ExtendWith(MockitoExtension.class)
class QueryHandlerTest {
    private final String query = "SELECT * FROM persons";
    private static final QueryResult QUERY_RESULT = new QueryResult();

    @Mock
    private DataSource dataSource;

    @BeforeAll
    static void beforeAll() {
        QUERY_RESULT.setTableName("persons");
        QUERY_RESULT.setHeaders(List.of ("id","name","age"));
        QUERY_RESULT.setValues(List.of (List.of (1,"Sasha",40)));
        QUERY_RESULT.setOperator(SqlOperator.SELECT);
    }

    @Test
    void testHandleResultSet() throws SQLException {

        QueryHandler queryHandler = new QueryHandler(dataSource);

        Connection connection = mock(Connection.class);
        when(dataSource.getConnection()).thenReturn(connection);

        Statement statement = mock(Statement.class);
        when(connection.createStatement()).thenReturn(statement);

        ResultSet rs = mock(ResultSet.class);
        when(statement.executeQuery(query)).thenReturn(rs);

        ResultSetMetaData metaData = mock(ResultSetMetaData.class);
        when(rs.getMetaData()).thenReturn(metaData);

        when(metaData.getColumnCount()).thenReturn(3);
        when(metaData.getTableName(1)).thenReturn("persons");
        when(metaData.getColumnName(1)).thenReturn("id");
        when(metaData.getColumnName(2)).thenReturn("name");
        when(metaData.getColumnName(3)).thenReturn("age");

        when(rs.next()).thenReturn(true).thenReturn(false);
        when(rs.getObject(1)).thenReturn(1);
        when(rs.getObject(2)).thenReturn("Sasha");
        when(rs.getObject(3)).thenReturn(40);

        QueryResult actual = queryHandler.handleResultSet(query, SqlOperator.SELECT);

        assertEquals(QUERY_RESULT, actual);
    }


}