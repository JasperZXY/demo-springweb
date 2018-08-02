package org.ruanwei.demo.core.interceptor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ruanwei.core.WebControllerAdvice;
import org.springframework.context.annotation.Bean;
import org.springframework.ui.Model;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;


@ControllerAdvice
// @RestControllerAdvice
public class MyControllerAdvice extends WebControllerAdvice { // extends

    // ResponseEntityExceptionHandler
    private static final Logger logger = LogManager.getLogger();


    // TODO:在XML文件中声明的没有生效，不知道为什么
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }


    /**
     * 1.Through Spring’s WebDataBinder, you can:
     * <li>use @InitBinder-annotated methods within your controller,
     * <li>use @InitBinder methods within an @ControllerAdvice class,
     * <li>or provide a custom WebBindingInitializer for
     * RequestMappingHandlerAdapter.
     *
     * 2.Registering Formatters with the FormattingConversionService.
     */
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest webRequest) {
        logger.debug("initBinder================== req=" + webRequest);
        // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // dateFormat.setLenient(false);
        // binder.registerCustomEditor(Date.class, new
        // CustomDateEditor(dateFormat, false));
        // binder.addCustomFormatter(new DateFormatter("yyyy-MM-dd"));
        // binder.addValidators(validators);
    }


    @ModelAttribute("cities")
    public List<String> modelAttribute(Model model) {
        logger.debug("modelAttribute==================");
        List<String> cityList = new ArrayList<String>();
        cityList.add("a");
        cityList.add("b");
        cityList.add("c");
        model.addAttribute("cities2", cityList);
        return cityList;
    }

}
