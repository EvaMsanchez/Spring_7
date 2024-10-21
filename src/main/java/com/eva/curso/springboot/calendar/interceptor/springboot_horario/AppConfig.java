package com.eva.curso.springboot.calendar.interceptor.springboot_horario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer
{
    @Autowired
    @Qualifier("calendarInterceptor") // nombre de la clase que queremos inyectar o si no el nombre que le hayamos puesto al componente
    private HandlerInterceptor calendar;

    @Override
    public void addInterceptors(InterceptorRegistry registry) 
    {
        registry.addInterceptor(calendar).addPathPatterns("/foo"); // rutas a aplicar
    }

}
