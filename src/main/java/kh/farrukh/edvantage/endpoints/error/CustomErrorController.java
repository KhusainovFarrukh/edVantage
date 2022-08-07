package kh.farrukh.edvantage.endpoints.error;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static kh.farrukh.edvantage.utils.constants.ApiEndpoints.ENDPOINT_COURSE;
import static kh.farrukh.edvantage.utils.constants.ApiEndpoints.ENDPOINT_ERROR;

@Controller
public class CustomErrorController implements ErrorController {

    @GetMapping(ENDPOINT_ERROR)
    public ModelAndView renderErrorPage(HttpServletResponse response) throws IOException {
        ModelAndView errorPage = new ModelAndView("error");

        String message = "";
        int code = response.getStatus();

        // TODO: 8/7/22 needs i18n
        switch (code) {
            case 200 -> response.sendRedirect(ENDPOINT_COURSE);
            case 400 -> message = "Bad Request";
            case 401 -> message = "Unauthorized";
            case 403 -> message = "Forbidden";
            case 404 -> message = "Resource not found";
            case 500 -> message = "Internal Server Error";
            default -> message = "Unknown";
        }

        errorPage.addObject("code", code);
        errorPage.addObject("message", message);
        return errorPage;
    }
}
