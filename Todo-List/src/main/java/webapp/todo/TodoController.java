package webapp.todo;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TodoController {
	int isdone;
	int rem;
	//set the login service or uservalidation - Autowiring
	@Autowired
	private TodoService service;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, false));
	}

	
	@RequestMapping(value="/list-todos", method= RequestMethod.GET)
	public String listTodos(ModelMap model) {
		
		//model.addAttribute("name", name);
		model.addAttribute("todos", service.retrieveTodos(retrieveLoggenInUsername()));
		model.put("isdone", isdone);
		rem=service.len();
		model.put("remaining", rem-isdone);
		return "list-todos";
	}
	
    
	private String retrieveLoggenInUsername() {
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		if (principal instanceof UserDetails)
			return ((UserDetails) principal).getUsername();
		return principal.toString();
	}
	
	@RequestMapping(value="/done", method= RequestMethod.GET)
	public String isDone(ModelMap model,@RequestParam String desc,@RequestParam int id) {
		isdone=service.done(desc,id);
		return "redirect:/list-todos";
	}
	
	@RequestMapping(value="/add-todo", method= RequestMethod.GET)
	public String showTodoPage(ModelMap model) {
		
		model.addAttribute("todo",new Todo(0,retrieveLoggenInUsername(),"", new Date(),false));
		return "todo";
	}
	
	@RequestMapping(value = "/add-todo", method = RequestMethod.POST)
	public String addTodo(@Valid Todo todo, BindingResult result) {
		if(result.hasErrors()) {
			return "todo";
		}
		service.addTodo(retrieveLoggenInUsername(), todo.getDesc(), todo.getTargetDate(), false);
		
		return "redirect:/list-todos";
		//return "list-todos";
	}
	
	@RequestMapping(value = "/update-todo", method = RequestMethod.GET)
	public String updateTodo(ModelMap model,@RequestParam int id) {
		//update to do
		Todo todo=service.retrieveTodo(id);
		model.addAttribute("todo",todo);
		return "todo";
	}
	
	@RequestMapping(value = "/update-todo", method = RequestMethod.POST)
	public String updateTodo(ModelMap model,@Valid Todo todo, BindingResult result) {
		if(result.hasErrors()) {
			return "todo";
		}
		todo.setUser(retrieveLoggenInUsername());
		service.updateTodo(todo);
		return "redirect:/list-todos";
	}
	
	@RequestMapping(value = "/delete-todo", method = RequestMethod.GET)
	public String deleteTodo(@RequestParam int id) {
		//Delete to do
		service.deleteTodo(id);
		return "redirect:/list-todos";
	}
	
	

}
