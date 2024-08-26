package com.kosta.KOSTA_3_final.controller.product_package;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.kosta.KOSTA_3_final.model.product_package.ProductListVO;
import com.kosta.KOSTA_3_final.service.product_package.ProductPackageService;


@Controller
public class ProductPackageController {

	@Autowired
	ProductPackageService productService;


	@GetMapping("/product/customPackaging3")
	public String selectProduct1(Model model) {
		model.addAttribute("categorylist", productService.selectCategoryAll());
		model.addAttribute("productlist", productService.selectProductAll());

		return "/product/customPackaging4";
	}

	@GetMapping("/product/adminMakeProductAndPackage")
	public String adminMakeProductAndPackage(Model model) {
		model.addAttribute("admin_check", "admin");
		model.addAttribute("categorylist", productService.selectCategoryAll());
		model.addAttribute("productlist", productService.selectProductAll());
		model.addAttribute("companylist", productService.selectCompanyAll());

		return "/product/customPackaging4";
	}

	@GetMapping("/product/productList")
	public String selectProduct(Model model, String type, String keyword) {
		model.addAttribute("productlist", productService.selectProductAll(type, keyword));

		return "/product/productList";
	}

	@PostMapping("/product/customInsert")
	public String insertPackage(Model model, @ModelAttribute ProductListVO productList) {
		productService.insertPackage(productList);

		return "redirect:/product/adminMakeProductAndPackage";
	}

	@PostMapping("/product/adminProductInsert")
	public String adminProductInsert(Model model, @ModelAttribute ProductListVO productList) throws Exception {
		productService.adminInsertProduct(productList);	    

		return "redirect:/product/adminMakeProductAndPackage";
	}

	@GetMapping("/product/adminProductDelete")
	public String adminDeleteProduct(Model model, @ModelAttribute ProductListVO productList) throws Exception {
		productService.adminDeleteProduct(productList);	    

		return "redirect:/product/adminMakeProductAndPackage";
	}


}
