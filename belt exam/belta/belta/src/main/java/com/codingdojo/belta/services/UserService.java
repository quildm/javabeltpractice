package com.codingdojo.belta.services;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.codingdojo.belta.models.Role;
import com.codingdojo.belta.models.User;
import com.codingdojo.belta.repositories.PackageRepository;
import com.codingdojo.belta.repositories.RoleRepository;
import com.codingdojo.belta.repositories.UserRepository;

@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PackageRepository packageRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    public UserService(UserRepository userRepository, 
    		RoleRepository roleRepository, 
    		BCryptPasswordEncoder bCryptPasswordEncoder,
    		PackageRepository packageRepository
    		)     {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.packageRepository = packageRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    
    
    // 1
    public void saveWithUserRole(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(roleRepository.findByName("ROLE_USER"));
        userRepository.save(user);
    }
     
     // 2 
    public void saveUserWithAdminRole(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(roleRepository.findByName("ROLE_ADMIN"));
        userRepository.save(user);
    }    
    
    // 3
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public List<User> findAnyAdminRole() {
        return (List<User>) userRepository.findAllByRolesNameContains("ROLE_ADMIN");
//        return (List<User>) userRepository.findAllByRolesNameContains("ROLE_USER");
    }
    public User findOne(Long id) {
    		return userRepository.findOne(id);
    }
    public List<User> findAll(){
    		return (List<User>) userRepository.findAll();
    }
    public Role findAdmin() {
    		return roleRepository.findOne(Long.parseLong("2"));
    }
    
    public Boolean ifThisUserisAdmin(Long id) {
    		Boolean answer = false;
         List<User> users =  userRepository.findAllByRolesNameContains("ROLE_ADMIN");

         for(User user : users) {
	        	 	if(user.getId() == id) { 	answer = true;		}
         }
         return answer;
    }
    
    public void delete(Long id) {
    		userRepository.delete(id);;
    }
    
    public User findById(Long id) {
    		return userRepository.findOne(id);
    }
    
    public void setSubscription(User user) {
//    		System.out.println(package_id + " " + user_id + " " + dueDate);
    		userRepository.save(user);
    		
    }
    
    
}