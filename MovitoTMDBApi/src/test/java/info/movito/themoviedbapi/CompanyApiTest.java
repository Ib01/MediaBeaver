package info.movito.themoviedbapi;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import info.movito.themoviedbapi.model.Collection;
import info.movito.themoviedbapi.model.Company;

import java.util.List;

import org.junit.Test;


public class CompanyApiTest extends AbstractTmdbApiTest {


    @Test
    public void testGetCompanyInfo() {
        Company company = tmdb.getCompany().getCompanyInfo(ID_COMPANY);
        assertTrue("No company information found", company.getId() > 0);
        assertNotNull("No parent company found", company.getParentCompany());
    }


    @Test
    public void testGetCompanyMovies() {
        List<Collection> result = tmdb.getCompany().getCompanyMovies(ID_COMPANY, LANGUAGE_DEFAULT, 0);
        assertTrue("No company movies found", !result.isEmpty());
    }


}
