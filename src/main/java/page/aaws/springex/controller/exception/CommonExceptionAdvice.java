package page.aaws.springex.controller.exception;

import java.util.Arrays;

import lombok.extern.log4j.Log4j2;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
@Log4j2
public class CommonExceptionAdvice {
    @ResponseBody
    @ExceptionHandler(ConversionNotSupportedException.class)
    public String handleConversionNotSupportedException(ConversionNotSupportedException e) {
        log.error("--------------------------------------------------------------------------------");
        log.error(e.getMessage());

        return "ConversionNotSupportedException: Failed to convert";
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        log.error("--------------------------------------------------------------------------------");
        log.error(e.getMessage());

        StringBuffer buffer = new StringBuffer("<ul>");
        buffer.append("<li>" + e.getMessage() + "</li>");
        Arrays.stream(e.getStackTrace()).forEach(stackTraceElement -> {
            buffer.append("<li>" + stackTraceElement + "</li>");
        });
        buffer.append("</ul>");

        return buffer.toString();
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoHandlerFoundException() {
        return "custom404";
    }
}
