package com.example.springsecuritypfe.controller;


import java.text.ParseException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.springsecuritypfe.model.AppUser;
import com.example.springsecuritypfe.model.Parameter;
import com.example.springsecuritypfe.model.Role;
import com.example.springsecuritypfe.model.StringResponse;
import com.example.springsecuritypfe.service.CourbeBDTService;
import com.example.springsecuritypfe.service.CourbeLTService;
import com.example.springsecuritypfe.service.CourbeSTService;
import com.example.springsecuritypfe.service.CoursBBEService;
import com.example.springsecuritypfe.service.ParameterService;
import com.example.springsecuritypfe.service.TMPJJService;
import com.example.springsecuritypfe.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class AdminController { // NB: pas de logique métier dans le contrôleur, mais, uniquement l'appel des services
	

    @Autowired
    private UserService userService;

    
    @Autowired
    private ParameterService parameterService;
    
    @Autowired
    private CoursBBEService coursbbeService;
    
    @Autowired
    private CourbeBDTService courbebdtService;
    
    @Autowired
    private CourbeLTService courbeltService;
    
    @Autowired
    private CourbeSTService courbestService;
    
    @Autowired
    private TMPJJService tauxService;
    

    
    //==================================Récupérer les cours de billet de banque==================================

    @GetMapping("/coursbbe")
    public ResponseEntity<?> getlistbbe(@RequestParam("dateCourbe") String date) {
    	 return new ResponseEntity<>(coursbbeService.findByDate(date), HttpStatus.OK);
    }
    
    //==================================Générer le traitement de calcul de cours de billet==================================

    
    @GetMapping("/coursbbe/generate")
    public ResponseEntity<?> generateBBE(@RequestParam("dateCourbe") String date) throws ParseException {
    	log.info("Génération des cours de billet de banque correspondants à la date " + date +" ..." );
    	return new ResponseEntity<>(coursbbeService.generateBBE(coursbbeService.findByDate(date)), HttpStatus.OK);
    }
    
   
    //==================================Récupérer la liste de courbe de taux==================================

    
    @GetMapping("/courbebdt")
    public ResponseEntity<?> getlistbdt(@RequestParam("dateCourbe") String date) {
    	log.info("Recherche des courbes de taux correspondants à la date " + date +" ..." );
    	return new ResponseEntity<>(courbebdtService.findByDate(date), HttpStatus.OK);
    }
    
  //==================================Générer le traitement de calcul de courbe de taux==================================
     
    @GetMapping("/courbebdt/generate")
    public ResponseEntity<?> generateBDT(@RequestParam("dateCourbe") String date) throws ParseException {
    	log.info("Génération des courbes de taux correspondants à la date " + date +" ..." );
    	return new ResponseEntity<>(courbebdtService.generateBDT(courbebdtService.findByDate(date)), HttpStatus.OK);
    }
    
  //================================== Les courbes de taux Short Term ==================================
    
    @GetMapping("/courbebdt/shorterm")
    public ResponseEntity<?> generateCourbeST(@RequestParam("dateCourbe") String date) throws ParseException {
    	log.info("Génération des courbes de taux court terme correspondants à la date " + date +" ..." );
    	return new ResponseEntity<>(courbestService.findByDate(date), HttpStatus.OK);
    }
    
    @GetMapping("/courbebdt/longterm")
    public ResponseEntity<?> generateCourbeLT(@RequestParam("dateCourbe") String date) throws ParseException {
    	log.info("Génération des courbes de taux long terme correspondants à la date " + date +" ..." );
    	return new ResponseEntity<>(courbeltService.findByDate(date), HttpStatus.OK);
    }
    
    @GetMapping("/tmpjj")
    public ResponseEntity<?> generateTMPJJ(@RequestParam("dateTaux") String date) throws ParseException {
    	log.info("Génération du taux TMPJJ à la date " + date +" ..." );
    	return new ResponseEntity<>(tauxService.findByDate(date), HttpStatus.OK);
    }
    
   
    //========================================================================

    
    @PostMapping("/api/admin/registration")
    public ResponseEntity<?> register(@RequestBody AppUser user){
        user.setRole(Role.USER); //default role.
        return new ResponseEntity<>(userService.saveOrUpdateUser(user), HttpStatus.CREATED);
    }
    
    //========================================================================


    @PutMapping("/api/user-update")
    public ResponseEntity<?> updateUser(@RequestBody AppUser user) {
        Optional<AppUser> existUser = userService.findByUsername(user.getUsername());
        if (existUser.isPresent() && !existUser.get().getId().equals(user.getId())) {
        	System.out.println("existe deja");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(userService.saveOrUpdateUser(user), HttpStatus.CREATED);
    }
    
    //========================================================================


    //This can be also @DeleteMapping.
    @PostMapping("/api/admin/user-delete")
    public ResponseEntity<?> deleteUser(@RequestBody AppUser user){
        userService.deleteUser(user.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    //========================================================================


    @GetMapping("/api/admin/user-all")
    public ResponseEntity<?> findAllUsers(){
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }
    
    @GetMapping("/api/user-details")
    public ResponseEntity<?> findUserDetails(@RequestParam("username") String username){
        return new ResponseEntity<>(userService.findByUsername(username), HttpStatus.OK);
    }
    
    //========================================================================


    @GetMapping("/api/admin/user-number")
    public ResponseEntity<?> numberOfUsers(){
        Long number = userService.numberOfUsers();
        StringResponse response = new StringResponse();
        response.setResponse(number.toString());
        //to return it, we will use String Response because long is not a suitable response for rest api
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    //=============================Settings===========================================

    
    
    @PostMapping("/api/admin/settings-create")
    public ResponseEntity<?> createParameter(@RequestBody Parameter parameter){
    	if(parameterService.findByCle(parameter.getCle())!=null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(parameterService.saveParameter(parameter), HttpStatus.CREATED);
    }
    

    @PutMapping("/api/admin/settings-update")
    public ResponseEntity<?> updateParameter(@RequestBody Parameter parameter){
    	Parameter existParameter = parameterService.findByCle(parameter.getCle());
        if (existParameter != null && !existParameter.getId().equals(parameter.getId())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(parameterService.updateParameter(parameter), HttpStatus.CREATED);
    }
    

  //This can be also @DeleteMapping.
    @PostMapping("/api/admin/settings-delete")
    public ResponseEntity<?> deleteParameter(@RequestBody Parameter parameter){
    	parameterService.deleteParameter(parameter.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
    

    @GetMapping("/api/admin/settings-all")
    public ResponseEntity<?> findAllParameters(){
        return new ResponseEntity<>(parameterService.findAllParameters(), HttpStatus.OK);
    }

    @GetMapping("/api/admin/settings-number")
    public ResponseEntity<?> numberOfParameters(){
        Long number = parameterService.numberOfParameters();
        StringResponse response = new StringResponse();
        response.setResponse(number.toString());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
}

