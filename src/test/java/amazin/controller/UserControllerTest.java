package amazin.controller;

import amazin.service.SecurityService;
import amazin.service.UserService;
import amazin.validator.UserValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.regex.Pattern;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;
    @MockBean
    private SecurityService securityService;
    @MockBean
    private UserValidator userValidator;
    @MockBean
    UserDetailsService userDetailsService;

    @Test
    public void getLoginTest() throws Exception {
        MvcResult result = mvc.perform(get("/login")
                .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andReturn();
        String contents = result.getResponse().getContentAsString();
        assert(contents.contains("email"));
        assert(contents.contains("password"));
        assert(contents.contains("Login"));
    }

    @Test
    public void getRegisterTest() throws Exception {
        MvcResult result = mvc.perform(get("/register")
                .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andReturn();
        String contents = result.getResponse().getContentAsString();
        assert(contents.contains("firstName"));
        assert(contents.contains("lastName"));
        assert(contents.contains("email"));
        assert(contents.contains("password"));
        assert(contents.contains("Register Account"));
    }

    @Test
    public void getRegisterAdminTest() throws Exception {
        MvcResult result = mvc.perform(get("/admin/register")
                .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andReturn();
        String contents = result.getResponse().getContentAsString();
        assert(contents.contains("registrationToken"));
        assert(contents.contains("Register Account"));
    }

    @Test
    public void getForgotPasswordTest() throws Exception {
        MvcResult result = mvc.perform(get("/forgot-password")
                .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andReturn();
        String contents = result.getResponse().getContentAsString();
        assert(contents.contains("email"));
        assert(contents.contains("Reset Password"));
    }
}
