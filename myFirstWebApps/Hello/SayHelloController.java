package com.learnSpringBoot.myFirstWebApps.Hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SayHelloController {

    //say-hello
    @RequestMapping("/say-hello-html")
    @ResponseBody
    public String sayHelloHTML(){
        StringBuffer sb = new StringBuffer();
        sb.append("<!DOCTYPE html>");
        sb.append("<html lang=\"en\">");
        sb.append("<head>");
        sb.append("<meta charset=\"UTF-8\">");
        sb.append("<title>My first Web Page</title>");
        sb.append("</head>");
        sb.append("<body>");
        sb.append("My First Web page with spring ");
        sb.append("</body>");
        sb.append("</html>");
        return sb.toString();
    }


    //say-hello-jsp => sayHello.jsp
    @RequestMapping("/say-hello-jsp")
    public String sayHello(){
        return "sayHello";
    }
}
