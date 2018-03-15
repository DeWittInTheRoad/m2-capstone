package com.techelevator.model.jdbc;

import com.techelevator.model.Campground;
import com.techelevator.model.Park;
import com.techelevator.model.jdbc.JDBCCampgroundDAO;
import com.techelevator.model.jdbc.JDBCParkDAO;
import com.techelevator.model.jdbc.JDBCSiteDAO;
import com.techelevator.projects.DAOIntegrationTest;
import org.junit.*;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import javax.sql.DataSource;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class JDBCSiteDAOTest extends DAOIntegrationTest{

    private static SingleConnectionDataSource dataSource;
    private JDBCSiteDAO dao;
    private JDBCCampgroundDAO campDAO;
    private JDBCParkDAO parkDAO;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception{
        dataSource = new SingleConnectionDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");
        dataSource.setAutoCommit(false);

    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception{
        dataSource.destroy();
    }
    @Before
    public void setUp() throws Exception{
        dao = new JDBCSiteDAO(dataSource);
        campDAO = new JDBCCampgroundDAO(dataSource);
        parkDAO = new JDBCParkDAO(dataSource);
    }
    @After
    public void tearDown() throws Exception{
        dataSource.getConnection().rollback();
    }
    @Test
    public void testGetAvailableSites(){
        LocalDate arrivalDate = LocalDate.of(2018, 05, 05);
        LocalDate departureDate = LocalDate.of(2018, 05, 12);
        Park testPark = parkDAO.getAllAvailableParks().get(0);
        Campground testCamp = campDAO.getAvailableCampgrounds(testPark).get(0);


        assertEquals(1, dao.getAvailableSites(testCamp, arrivalDate, departureDate).get(0).getSiteId().longValue());
        assertEquals(2, dao.getAvailableSites(testCamp, arrivalDate, departureDate).get(1).getSiteId().longValue());
        assertEquals(3, dao.getAvailableSites(testCamp, arrivalDate, departureDate).get(2).getSiteId().longValue());
        assertEquals(4, dao.getAvailableSites(testCamp, arrivalDate, departureDate).get(3).getSiteId().longValue());

        assertEquals(1, dao.getAvailableSites(testCamp, arrivalDate, departureDate).get(0).getCampgroundId().longValue());
        assertEquals(1, dao.getAvailableSites(testCamp, arrivalDate, departureDate).get(1).getCampgroundId().longValue());
        assertEquals(1, dao.getAvailableSites(testCamp, arrivalDate, departureDate).get(2).getCampgroundId().longValue());
        assertEquals(1, dao.getAvailableSites(testCamp, arrivalDate, departureDate).get(3).getCampgroundId().longValue());

        assertEquals(1, dao.getAvailableSites(testCamp, arrivalDate, departureDate).get(0).getSiteNumber().longValue());
        assertEquals(2, dao.getAvailableSites(testCamp, arrivalDate, departureDate).get(1).getSiteNumber().longValue());


        assertEquals(6, dao.getAvailableSites(testCamp, arrivalDate, departureDate).get(0).getMaxOccupancy().longValue());
        assertEquals(6, dao.getAvailableSites(testCamp, arrivalDate, departureDate).get(1).getMaxOccupancy().longValue());
        assertEquals(6, dao.getAvailableSites(testCamp, arrivalDate, departureDate).get(2).getMaxOccupancy().longValue());
        assertEquals(6, dao.getAvailableSites(testCamp, arrivalDate, departureDate).get(3).getMaxOccupancy().longValue());

        assertEquals(false, dao.getAvailableSites(testCamp, arrivalDate, departureDate).get(0).isAccessible());
        assertEquals(false, dao.getAvailableSites(testCamp, arrivalDate, departureDate).get(1).isAccessible());
        assertEquals(false, dao.getAvailableSites(testCamp, arrivalDate, departureDate).get(2).isAccessible());
        assertEquals(false, dao.getAvailableSites(testCamp, arrivalDate, departureDate).get(3).isAccessible());

        assertEquals(0, dao.getAvailableSites(testCamp, arrivalDate, departureDate).get(0).getMaxRvLength().longValue());
        assertEquals(0, dao.getAvailableSites(testCamp, arrivalDate, departureDate).get(1).getMaxRvLength().longValue());


        assertEquals(false, dao.getAvailableSites(testCamp, arrivalDate, departureDate).get(0).isUtilities());
        assertEquals(false, dao.getAvailableSites(testCamp, arrivalDate, departureDate).get(1).isUtilities());
        assertEquals(false, dao.getAvailableSites(testCamp, arrivalDate, departureDate).get(2).isUtilities());
        assertEquals(false, dao.getAvailableSites(testCamp, arrivalDate, departureDate).get(3).isUtilities());
    }





}