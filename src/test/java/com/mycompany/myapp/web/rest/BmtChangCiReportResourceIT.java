package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.BmtApp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test class for the BmtChangCiReportResource REST controller.
 *
 * @see BmtChangCiReportResource
 */
@SpringBootTest(classes = BmtApp.class)
public class BmtChangCiReportResourceIT {

    private MockMvc restMockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        BmtChangCiReportResource bmtChangCiReportResource = new BmtChangCiReportResource();
        restMockMvc = MockMvcBuilders
            .standaloneSetup(bmtChangCiReportResource)
            .build();
    }

    /**
     * Test bmtReportResource
     */
    @Test
    public void testBmtReportResource() throws Exception {
        restMockMvc.perform(post("/api/bmt-chang-ci-report/bmt-report-resource"))
            .andExpect(status().isOk());
    }
}
