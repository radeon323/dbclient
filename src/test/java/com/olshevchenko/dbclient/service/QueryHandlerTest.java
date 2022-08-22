package com.olshevchenko.dbclient.service;

import com.olshevchenko.dbclient.entity.Table;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * @author Oleksandr Shevchenko
 */
@ExtendWith(MockitoExtension.class)
class QueryHandlerTest {
    private final String query = "SELECT * FROM persons";
    private static final Table table = new Table();

    @Mock
    private DataSource dataSource;

    @BeforeAll
    static void beforeAll() {
        table.setName("persons");
        table.setHeaders(List.of ("id","name","age"));
        table.setValues(List.of (List.of (1),List.of ("Sasha"),List.of (40)));
    }

    //TODO:  x3
    @Test
    void testHandleResultSet() throws SQLException {
        QueryHandler queryHandler = new QueryHandler(dataSource, query);

        Connection connection = mock(Connection.class);
        when(dataSource.getConnection()).thenReturn(connection);

        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connection.prepareStatement(query)).thenReturn(preparedStatement);

        ResultSet rs = mock(ResultSet.class);
        when(preparedStatement.executeQuery()).thenReturn(rs);

        ResultSetMetaData metaData = mock(ResultSetMetaData.class);
        when(rs.getMetaData()).thenReturn(metaData);

        DataMapper dataMapper = mock(DataMapper.class);
        when(dataMapper.mapRow(rs)).thenReturn(table);
        dataMapper.mapRow(rs);

        queryHandler.handleResultSet(query);


    }


}