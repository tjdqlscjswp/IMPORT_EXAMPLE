package com.kosta.KOSTA_3_final.controller;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.kosta.KOSTA_3_final.model.product_package.PackageVO;
import com.kosta.KOSTA_3_final.model.product_package.ProductListVO;
import com.kosta.KOSTA_3_final.model.subscribe.Subscribe;
import com.kosta.KOSTA_3_final.model.user.Member;
import com.kosta.KOSTA_3_final.persistance.product_package.PackageRepository;
import com.kosta.KOSTA_3_final.persistance.subscribe.SubscribeRepository;
import com.kosta.KOSTA_3_final.security.UserSecurity;
import com.kosta.KOSTA_3_final.service.product_package.PackageId;
import com.kosta.KOSTA_3_final.service.product_package.ProductPackageService;
import com.kosta.KOSTA_3_final.service.subscribe.DeliveryService;
import com.kosta.KOSTA_3_final.service.subscribe.GetPayementStatus;
import com.kosta.KOSTA_3_final.service.subscribe.ImportPay;
import com.kosta.KOSTA_3_final.service.subscribe.ReqPaymentScheduler;
import com.kosta.KOSTA_3_final.service.subscribe.RequestSubPayment;
import com.kosta.KOSTA_3_final.service.subscribe.SchedulePayment;
import com.kosta.KOSTA_3_final.service.subscribe.SubscribeService;
import com.kosta.KOSTA_3_final.service.user.UserService;

import lombok.Setter;
import lombok.extern.java.Log;

@Log
@Controller
public class PaymentController {
	@Setter(onMethod_ = @Autowired)
	private ImportPay pay;
	@Autowired
	RequestSubPayment reqPay;
	@Autowired
	ProductPackageService packService;
	@Autowired
	PackageRepository packRepo;
	@Autowired
	SubscribeRepository repo;
	@Autowired
	SubscribeService subscribeService;
	@Autowired
	UserService userService;
	@Autowired
	SchedulePayment setSchedulePay;
	@Autowired
	GetPayementStatus getPayementStatus;
	@Autowired
	ReqPaymentScheduler scheduler;
	@Autowired
	PackageId packageId;
	@Autowired
	RequestSubPayment reqSubpay;
	@Autowired
	DeliveryService deliService;

	@PostMapping("/payment1")
	public @ResponseBody void getImportToken(@RequestParam Map<String, Object> map)
			throws JsonMappingException, JsonProcessingException {
		long customer_uid = Long.parseLong((String) map.get("customer_uid"));
		int price = Integer.parseInt((String) map.get("price"));
		long merchant_uid =  Long.parseLong((String) map.get("merchant_uid"));  
		long packageId =  Long.parseLong((String) map.get("packageId"));  
	
		reqSubpay.requestSubPay(customer_uid,merchant_uid, price);
		scheduler.startScheduler(customer_uid,price,packageId);

		}
	
	
	
	
	 @GetMapping("/payments/payment") public void payment(Model model, Long pno) {
	  model.addAttribute("pack", subscribeService.findById(pno));
	 }
	 
	 @PostMapping("payment") 
	 public String payment (Model model, @ModelAttribute
	 ProductListVO productList) {
	  PackageVO  newPackageVO =packService.insertPackage(productList);
	 return "redirect:/payments/payment?pno="+newPackageVO.getPackageId();
	 }
		 
	@GetMapping("/payments/shop")
	public void packlist(Model model) {
		model.addAttribute("plist", packRepo.findByPackageType2(0));
	}

	@GetMapping("/index")
	public void index(Model model) {
		model.addAttribute("plist", packRepo.findByPackageType(0));	
	}

	@GetMapping("/payments/product_details")
	public void product_details(Model model, Long pno) {
		model.addAttribute("pack", subscribeService.findById(pno));
		model.addAttribute("plist", packRepo.findAll());
		model.addAttribute("packlist", packRepo.findByPackageType3(0));
		model.addAttribute("productList", packService.findProductbyPackageNo(pno));
	}
	
	

	@Autowired
	SubscribeService subService;
	@GetMapping("/insertSubscribe")
	@ResponseBody
	public void insertSubscribe(long package_id, int customer_id) {
		subService.insertSubscribe(package_id, customer_id);
		log.info("구독정보 입력성공");
	}
	



	@GetMapping("/delivery/deliveryInsert")
	public @ResponseBody void deliveryInsert(@RequestParam Map<String, Object> map)
			throws JsonMappingException, JsonProcessingException{
		long customerId = Long.parseLong((String) map.get("customerId"));
		long packageId =  Long.parseLong((String) map.get("packageId")); 
		Date deliveryDate = Date.valueOf((String) map.get("deliveryDate"));
		deliService.deliveryInsert(packageId, customerId, deliveryDate);
	}
	
	@GetMapping("/subscribe")
	   public String viewed(Model model) {
	      //구독 조회
	      Object principal=SecurityContextHolder.getContext().getAuthentication().getPrincipal();//로그인시 정보 받아오기
	      UserSecurity usersecurity=(UserSecurity)principal;
	      Member mem=userService.getMemberInfo(usersecurity.getUsername());//정보 찾기
	      
	      List<Subscribe> lel=subService.findByCustomer(mem);
	      System.out.println(lel);
	      model.addAttribute("subscribe",lel);

	      return "/subscribe";
	   }



}


