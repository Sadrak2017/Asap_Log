package controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import model.Clientes;

@Controller
@RequestMapping("/")
public class WebController {

	List<Clientes> _rClientList = new ArrayList<Clientes>();
	/***
	 * ESSE MÉTODO CARREGA A PÁGINA(index.html) DE LOGIN DA NOSSA APLICAÇÃO
	 * @return
	 */
	@RequestMapping("/")	
	public String index(){	
 
	    return "home";
	}
    
    @RequestMapping("/cadastroApolice")
    public ModelAndView WebApolice(){
	    ModelAndView model = new ModelAndView();
        model.setViewName("index");
       return model;
    }
    
}
