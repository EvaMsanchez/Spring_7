package com.eva.curso.springboot.calendar.interceptor.springboot_horario.interceptors;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CalendarInterceptor implements HandlerInterceptor
{
    // Inyectar la hora de apertura y cierre de la configuración del properties
    @Value("${config.calendar.open}")
    private Integer open;

    @Value("${config.calendar.close}")
    private Integer close;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        
        //Obtener la hora actual
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        if(hour >= open && hour < close)
        {
            StringBuilder message = new StringBuilder("Bienvenidos al horario de atención al cliente");
            message.append(", atendemos desde las ");
            message.append(open);
            message.append("hrs.");
            message.append(" hasta las ");
            message.append(close);
            message.append("hrs.");
            message.append(" Gracias por su visita!");

            /* Asi es más sencillo y fácil
            String message = String.format("Bienvenidos al horario de atención al cliente, atendemos desde las %dhrs. hasta las %dhrs. Gracias por su visita!", open, close); 
            
            o asi
            String message = "Bienvenidos al horario de atención al cliente, atendemos desde las " 
                + open + "hrs. hasta las " + close + "hrs. Gracias por su visita!"; 
            */
            
            request.setAttribute("message", message.toString());
            return true;
        }

        //Implementado cerrado en el interceptor
        // Para pasar objetos Java a JSON
        ObjectMapper mapper = new ObjectMapper();

        Map <String, String> data = new HashMap<>();
        StringBuilder message = new StringBuilder("Cerrado, fuera del horario de atención ");
        message.append("por favor visítanos desde las ");
        message.append(open);
        message.append(" y las ");
        message.append(close);
        message.append(" hrs. Gracias!");
        data.put("message", message.toString());
        data.put("date", new Date().toString());

        response.setContentType("application/json");
        response.setStatus(401);
        response.getWriter().write(mapper.writeValueAsString(data));
        return false;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            @Nullable ModelAndView modelAndView) throws Exception {
        
    
    }

}
