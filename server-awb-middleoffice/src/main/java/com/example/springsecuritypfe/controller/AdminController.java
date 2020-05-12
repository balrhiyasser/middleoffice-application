package com.example.springsecuritypfe.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.springsecuritypfe.export.ExcelFileExporter;
import com.example.springsecuritypfe.export.FileExporter;
import com.example.springsecuritypfe.model.Parameter;
import com.example.springsecuritypfe.model.Product;
import com.example.springsecuritypfe.model.Role;
import com.example.springsecuritypfe.model.StringResponse;
import com.example.springsecuritypfe.model.User;
import com.example.springsecuritypfe.service.CourbeBDTService;
import com.example.springsecuritypfe.service.CoursBBEService;
import com.example.springsecuritypfe.service.ParameterService;
import com.example.springsecuritypfe.service.ProductService;
import com.example.springsecuritypfe.service.TransactionService;
import com.example.springsecuritypfe.service.UserService;

@RestController
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;
    
    @Autowired
    private ParameterService parameterService;
    
    @Autowired
    private CoursBBEService coursbbeService;
    
    @Autowired
    private CourbeBDTService courbebdtService;

    @Autowired
    private TransactionService transactionService;
    
    
    //==================================Récupérer les cours de billet de banque==================================

    
    @GetMapping("/coursbbe")
    public ResponseEntity<?> getlistbbe(@RequestParam("date") String date) {
    	 return new ResponseEntity<>(coursbbeService.findByDate(date), HttpStatus.OK);
    }
    
    //==================================Récupérer la liste de courbe de taux==================================

    
    @GetMapping("/courbebdt")
    public ResponseEntity<?> getlistbdt(@RequestParam("dateCourbe") String date) {
    	 return new ResponseEntity<>(courbebdtService.findByDate(date), HttpStatus.OK);
    }
    
    //=============================================DownloadFiles=================================================

    
    @GetMapping("/download/coursbbe.xlsx")
    public void downloadExcel(HttpServletResponse response, @RequestParam("date") String date) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=coursbbe.xlsx");
        ByteArrayInputStream stream = ExcelFileExporter.coursListToExcelFile(coursbbeService.findByDate(date));
        IOUtils.copy(stream, response.getOutputStream());
    }
    
    @GetMapping("/download/coursbbe.csv")
    public void downloadCsv(HttpServletResponse response, @RequestParam("date") String date) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; file=coursbbe.csv");
        FileExporter.downloadCsv(response.getWriter(),coursbbeService.findByDate(date)) ;
    }
    
    @GetMapping("/download/BAMFX03.txt")
    public void downloadTxtbam(HttpServletResponse response, @RequestParam("date") String date) throws IOException {
        response.setContentType("text/txt");
        response.setHeader("Content-Disposition", "attachment; file=BAMFX03.txt");
        FileExporter.downloadTxtbam(response.getWriter(), coursbbeService.findByDate(date)) ;
    }
    
    @GetMapping("/download/WAFACASH.txt")
    public void downloadTxtwafa(HttpServletResponse response, @RequestParam("date") String date) throws IOException {
        response.setContentType("text/txt");
        response.setHeader("Content-Disposition", "attachment; file=WAFACASH.txt ");
        FileExporter.downloadTxtwafa(response.getWriter(), coursbbeService.findByDate(date)) ;
    }  
    
    
    //========================================================================

    
    @PostMapping("/api/admin/registration")
    public ResponseEntity<?> register(@RequestBody User user){
        if(userService.findByUsername(user.getUsername()).isPresent()){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
		//default role.
        user.setRole(Role.USER);
        return new ResponseEntity<>(userService.saveOrUpdateUser(user), HttpStatus.CREATED);
    }
    
    //========================================================================


    @PutMapping("/api/admin/user-update")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Optional<User> existUser = userService.findByUsername(user.getUsername());
        if (existUser != null && !existUser.get().getId().equals(user.getId())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(userService.saveOrUpdateUser(user), HttpStatus.CREATED);
    }
    
    //========================================================================


    //This can be also @DeleteMapping.
    @PostMapping("/api/admin/user-delete")
    public ResponseEntity<?> deleteUser(@RequestBody User user){
        userService.deleteUser(user.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    //========================================================================


    @GetMapping("/api/admin/user-all")
    public ResponseEntity<?> findAllUsers(){
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
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
    
    
    //=============================Products===========================================


    
    @PostMapping("/api/admin/product-create")
    public ResponseEntity<?> createProduct(@RequestBody Product product){
        return new ResponseEntity<>(productService.saveProduct(product), HttpStatus.CREATED);
    }

    @PutMapping("/api/admin/product-update")
    public ResponseEntity<?> updateProduct(@RequestBody Product product){
        return new ResponseEntity<>(productService.updateProduct(product), HttpStatus.CREATED);
    }

  //This can be also @DeleteMapping.
    @PostMapping("/api/admin/product-delete")
    public ResponseEntity<?> deleteProduct(@RequestBody Product product){
        productService.deleteProduct(product.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/api/admin/product-all")
    public ResponseEntity<?> findAllProducts(){
        return new ResponseEntity<>(productService.findAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/api/admin/product-number")
    public ResponseEntity<?> numberOfProducts(){
        Long number = productService.numberOfProducts();
        StringResponse response = new StringResponse();
        response.setResponse(number.toString());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    

    @GetMapping("/api/admin/transaction-all")
    public ResponseEntity<?> findAllTransactions(){
        return new ResponseEntity<>(transactionService.findAllTransactions(), HttpStatus.OK);
    }

    @GetMapping("api/admin/transaction-number")
    public ResponseEntity<?> numberOfTransactions(){
        Long number = transactionService.numberOfTransactions();
        StringResponse response = new StringResponse();
        response.setResponse(number.toString());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

