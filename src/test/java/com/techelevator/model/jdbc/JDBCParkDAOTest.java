package com.techelevator.model.jdbc;

import com.techelevator.model.jdbc.JDBCParkDAO;
import com.techelevator.projects.DAOIntegrationTest;
import org.junit.*;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class JDBCParkDAOTest extends DAOIntegrationTest {

    private static SingleConnectionDataSource dataSource;
    private JDBCParkDAO dao;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        dataSource = new SingleConnectionDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");
        dataSource.setAutoCommit(false);

    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        dataSource.destroy();
    }

    @Before
    public void setUp() throws Exception {
        dao = new JDBCParkDAO(dataSource);
    }

    @After
    public void tearDown() throws Exception {
        dataSource.getConnection().rollback();
    }

    @Test
    public void testGetAllAvailableParks() {

        assertEquals("Acadia", dao.getAllAvailableParks().get(0).getName());
        assertEquals("Maine", dao.getAllAvailableParks().get(0).getLocation());
        assertEquals(LocalDate.of(1919, 02, 26), dao.getAllAvailableParks().get(0).getEstablishedDate());
        assertEquals(47389, dao.getAllAvailableParks().get(0).getArea().longValue());

    }

    @Test
    public void testGetParkInformation() {

        assertEquals("Acadia", dao.getParkInformation("Acadia").getName());
        assertEquals("Maine", dao.getParkInformation("Acadia").getLocation());
        assertEquals(LocalDate.of(1919, 02, 26), dao.getParkInformation("Acadia").getEstablishedDate());
        assertEquals(47389, dao.getParkInformation("Acadia").getArea().longValue());
        assertEquals("Acadia", dao.getAllAvailableParks().get(0).getName());
        assertEquals("Maine", dao.getAllAvailableParks().get(0).getLocation());
        assertEquals(LocalDate.of(1919, 02, 26), dao.getAllAvailableParks().get(0).getEstablishedDate());
        assertEquals(47389, dao.getAllAvailableParks().get(0).getArea().longValue());


    }
}