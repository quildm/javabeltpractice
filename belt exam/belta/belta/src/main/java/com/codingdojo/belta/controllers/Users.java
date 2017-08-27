package com.codingdojo.belta.controllers;


import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.codingdojo.belta.models.PackageModel;
import com.codingdojo.belta.models.User;
import com.codingdojo.belta.services.PackageService;
import com.codingdojo.belta.services.UserService;
import com.codingdojo.belta.validator.UserValidator;


@Controller
public class Users {
	
    // NEW
    private UserValidator userValidator;
	//
    private UserService userService;
    private PackageService packageService;
    public Users(
    		UserService userService, 
    		UserValidator userValidator,
    		PackageService packageService
    		) {
	    this.userService = userService;
	    this.userValidator = userValidator;
	    this.packageService = packageService;
    }
	//
    
    
    
    
    // LOGIN REG TOGETHER
    @RequestMapping("/")
    public String home(
    		Model model,
    		// LOGIN PART
    		@RequestParam(value="error", required=false) String error, @RequestParam(value="logout", required=false) String logout,
    		// registerPart
    		@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session, 
    		
    		Principal principal
    		) {

//    		System.out.println( userService.findAnyAdminRole().size() == 0 );
    	
    		try { 
    			String username = principal.getName();
    			User currentUser = userService.findByUsername(username);
    	        Boolean isAdmin = userService.ifThisUserisAdmin(currentUser.getId());
    	        if( isAdmin ) {
    	        		return "redirect:/admin";
    	        } else {
        			return "redirect:/selection";
    	        }

    		} catch (Exception e) {  }
    		
        return "loginreg.jsp";
    }
    
    // admin dashboard
	    @RequestMapping("/admin")
	    public String adminDashboard(
	    		Principal principal, Model model,
	    		@Valid @ModelAttribute("pack") PackageModel pack,
	    		@ModelAttribute("errors") String errors,
	    		BindingResult result,
	    		@RequestParam(value="error", required=false) String error
	    		) {
	
	    		try { 
	    			String username = principal.getName(); 
	    			User currentUser = userService.findByUsername(username);
	    	        model.addAttribute("currentUser", currentUser );
	    	        
	    	        // if user from Admins do this
	    	        Boolean isAdmin = userService.ifThisUserisAdmin(currentUser.getId());
	    	        if( isAdmin ) {
	    	        		model.addAttribute("adminRole", userService.findAdmin());
	    	        		model.addAttribute("allUsers", userService.findAll());
	    	    	        model.addAttribute("isAdmin", isAdmin );
	    	    	        
	    	    	        List<PackageModel> packages = packageService.findAll();
	    	    	        model.addAttribute("packages", packages );
	    	    	        return "admin.jsp";
	    	    	        
	    	    	        
	    	        } else { return "redirect:/selection"; }

	
	    		} catch (Exception e) {  }
	    		
	    		return "redirect:/";
	    }
			    // CREATE NEW Package
			    @PostMapping("/admin/newpack")
			    public String newPackage(
		    			Model model, Principal principal,
		    			@Valid @ModelAttribute("pack") PackageModel pack,
		    			BindingResult result
			    		) {
				    		if(result.hasErrors()) {
				    			System.out.println("we have errors doing package");
				    			return "admin.jsp";
				    		} else {
				    			packageService.setNewPackage(pack);
				    			return "redirect:/admin";
				    		}
			    }
			    // ACTIVATE
			    @RequestMapping("/admin/pack/{pack_id}/activate")
			    public String activatePack(
			    		@PathVariable("pack_id") Long pack_id
			    		) {
				    		if(packageService.findOne(pack_id) != null) {
				    			packageService.activatePack(pack_id);
				    		} else {}
				    		return "redirect:/admin";
			    }
			    // Deactivate
			    @RequestMapping("/admin/pack/{pack_id}/deactivate")
			    public String deactivatePack(
			    		@PathVariable("pack_id") Long pack_id
			    		) {
				    		if(packageService.findOne(pack_id) != null) {
				    			packageService.deactivatePack(pack_id);
				    		} else {}
				    		return "redirect:/admin";
			    }
			    // DELETE
			    @RequestMapping("/admin/pack/{pack_id}/delete")
			    public String deletePack(
			    		@PathVariable("pack_id") Long pack_id
			    		) {
				    		if(packageService.findOne(pack_id) != null) {
				    			packageService.deletePack(pack_id);
				    		} else {}
				    		return "redirect:/admin";
			    }
			    
			    
			    
			    
			    
			    
			    
			    
			    
	    // DELETE USER
	    @RequestMapping("/admin/delete/{user_id}")
	    public String delete(
	    		@PathVariable("user_id") Long user_id
	    		) {
		    	try { 
			    userService.delete(user_id);
		    	} catch (Exception e) {  }

		    return "redirect:/admin";
	    }
	    // MAKE ADMIN
	    @RequestMapping("/admin/make_admin/{user_id}")
	    public String make_admin(
	    		@PathVariable("user_id") Long user_id
	    		) {
		    	try { 
		    		User user = userService.findById(user_id);
		    		userService.saveUserWithAdminRole(user);
		    	} catch (Exception e) {  }

		    return "redirect:/admin";
	    }
	    
	    
    
    
    // SELECTION
	    @RequestMapping("/selection")
	    public String dashboard(Principal principal, Model model) {
	        // 1
	    		try { 
	    			String username = principal.getName();
	    			User currentUser = userService.findByUsername(username);
	    			Boolean isAdmin = userService.ifThisUserisAdmin(currentUser.getId());
	    			
	    			if(currentUser.getChoosed_package() == null) {
		    	        model.addAttribute("currentUser", currentUser );
		    	        model.addAttribute("lastLogin", new Date());
		    	        model.addAttribute("isAdmin", isAdmin );
		    	        
	    	    	        List<PackageModel> packages = packageService.findAll();
	    	    	        model.addAttribute("packages", packages );

					Calendar c = Calendar.getInstance();
					int monthMaxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					ArrayList<Integer> days = new ArrayList<Integer>();
					for (int i = 1; i <= monthMaxDays; i++) {
						days.add(i);
					}

					model.addAttribute("days", days );
		    	        return "selection.jsp";
	    			} else {
	    				return "redirect:/profile";
	    			}

	    		} catch (Exception e) {  }
	    		
	    		return "redirect:/";

	    }
	    // PROFILE
	    @RequestMapping("/profile")
	    public String profile(Principal principal, Model model) {
	        // 1
	    		try { 
	    			String username = principal.getName();
	    			User currentUser = userService.findByUsername(username);
	    			Boolean isAdmin = userService.ifThisUserisAdmin(currentUser.getId());
	    			
	    			if(currentUser.getChoosed_package() == null) {
	    				return "redirect:/selection";
	    			} else {
	    				
	    				PackageModel chosedPack = currentUser.getChoosed_package();
		    	        model.addAttribute("chosedPack", chosedPack );
		    	        model.addAttribute("currentUser", currentUser );
		    	        model.addAttribute("isAdmin", isAdmin );
		    	        


	    				return "profile.jsp";
	    			}

	    		} catch (Exception e) {  }
	    		
	    		return "redirect:/";

	    }
	    
	    // SUBSCRIBE POST
	    @PostMapping("/selection/subscribe")
	    public String subscribe(
	    		Principal principal, Model model,
	    		@RequestParam(value="dueDate", defaultValue="-1") int dueDate,
	    		@RequestParam(value="package_id", defaultValue="-1") Long package_id
	    		) {
	    		if(dueDate == -1 || package_id == -1 ) { return "redirect:/selection"; }
	    		try { 
	    			String username = principal.getName();
	    			User currentUser = userService.findByUsername(username);
	    			String day = Integer.toString(dueDate);
	    			if (day.length() <2) {
	    				day = "0" + day;
	    			}
	    			Date tODAY = new Date();
	    			
	    			String todayDate = stringFromDate(new Date()) + day; // 2019-08-
	    			Date dueD = dateFromString(todayDate);

	    			Date  blabla = null;
	    			if( tODAY.getTime() > dueD.getTime() ) {
	    						

	    					Calendar cal = Calendar.getInstance();
		    				cal.setTime(dueD);
	    					cal.add(Calendar.MONTH, 1);
	    					blabla = cal.getTime();


	    			} else {
	    				blabla = dueD;
	    			}
	    			
	    			
	        		User user = userService.findOne(currentUser.getId());
	        		PackageModel pack = packageService.findOne(package_id);
	        		user	.setChoosed_package(pack);
	        		user	.setDueDate(blabla);
	    			
	    			userService.setSubscription(user);
	    		} catch (Exception e) { }
	    		return "redirect:/selection";

	    }
    
    
    // REGISTRATION
	    @PostMapping("/registration")
	    public String registration(
	    		// Login part
	    		@RequestParam(value="error", required=false) String error, @RequestParam(value="logout", required=false) String logout,
	    		
	    		@Valid @ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session
	    		) {
		    
	    		// NEW
	    		userValidator.validate(user, result);
	    		if (result.hasErrors()) {
	    			System.out.println("we have error");
		        return "loginreg.jsp";
		    }
	    		
	    		if( userService.findAnyAdminRole().size() == 0 ) {
	    			userService.saveUserWithAdminRole(user);
	    		} else {
	    		    userService.saveWithUserRole(user);
	    		}

		    return "redirect:/";
	    }
	    
	    // LOGIN
	    @RequestMapping("/login")
	    public String login(
	    		@Valid @ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session,
	    		
	    		@RequestParam(value="error", required=false) String error, @RequestParam(value="logout", required=false) String logout
	    		) {
		        if(error != null) {
		            model.addAttribute("errorMessage", "Invalid Credentials, Please try again.");
		        }
		        if(logout != null) {
		            model.addAttribute("logoutMessage", "Logout Successfull!");
		        }
	        return "loginreg.jsp";
	    }
	    
	    
	    
//	    HELPER FUNCTION AND DATA
		public Date dateFromString(String stringDate) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 2019-08-09
			Date dateFromString = null;
			try {
				dateFromString = sdf.parse(stringDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return dateFromString;
			}
		
		public String stringFromDate(Date Date) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-"); // 2019-08-09
			String result = null;
			try {
				result = sdf.format(Date);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("oops");
			}
			
			return result;
			}
		

	    
}


