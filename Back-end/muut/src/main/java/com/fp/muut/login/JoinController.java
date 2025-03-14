package com.fp.muut.login;

import java.util.List;
import java.util.Map;

import org.springframework.boot.web.servlet.server.Session.Cookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fp.muut.entity.Customer;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/member")
@Controller
@RequiredArgsConstructor
public class JoinController {
	
	private final CustomerRepository customerRepository;
	private final CustomerService loginService;
		
	//회원가입
	@PostMapping("/join")
	public String add(@RequestBody Customer customer) throws IllegalAccessException {
		loginService.join(customer);
		return "redirect:/";
	}
	
	// 로그인
	@PostMapping("/login")
	public Customer login(@RequestBody Map<String, String> loginData, HttpServletRequest request, HttpServletResponse response) {
		String customer_id = loginData.get("customer_id");
		String customer_pw = loginData.get("customer_pw");
		Customer customer = loginService.login(customer_id, customer_pw);
			if(customer == null) {
				return null;
			}
			
		// 로그인 성공 (세션에 로그인 정보 저장)
	    HttpSession session = request.getSession(); 
	    session.setAttribute("loginCustomer", customer);
	    System.out.println("Session ID: " + session.getId());
	    System.out.println("Logged in customer: " + session.getAttribute("loginCustomer"));
	    
	    // 쿠키에 로그인 정보 저장
	    ResponseCookie idCookie = ResponseCookie.from("customer_id", customer_id)
	            .httpOnly(false) // JavaScript에서의 접근 가능여부 설정(true:불가, false:가능)
	            .secure(true)
	            .sameSite("None") // SameSite 설정
	            .path("/")
	            .build();
	    response.addHeader(HttpHeaders.SET_COOKIE, idCookie.toString());
	    
		return customer;
	}
	
	// 세션에서 로그인 정보 가져오기
	@GetMapping("/myinfo")
	public ResponseEntity<?> getMyInfo(HttpServletRequest request) {
	    HttpSession session = request.getSession(false); // 기존 세션 가져오기 (없으면 null 반환)
	    if (session == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No active session found");
	    }

	    Customer loginCustomer = (Customer) session.getAttribute("loginCustomer");
	    if (loginCustomer == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No logged-in user found in session");
	    }

	    return ResponseEntity.ok(loginCustomer); // 사용자 정보를 반환
	}

	//로그아웃
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		//세션 삭제
		HttpSession session = request.getSession(false);
		if(session != null) {
			session.invalidate();
		}
		//쿠키 삭제 
		ResponseCookie cookie = ResponseCookie.from("customer_id", "")
		            .httpOnly(false)
		            .secure(true)  // HTTPS 환경에서만 쿠키 전송
		            .sameSite("None")
		            .path("/")  // 쿠키의 경로는 로그인 시 설정한 것과 동일해야 함
		            .maxAge(0)  // 만료 시간을 0으로 설정하여 쿠키 삭제
		            .build();

		    response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());  // 쿠키 삭제 헤더 추가

		return "redirect:/";
	}
	
	//회원 탈퇴
	@GetMapping("/dropout")
	public String dropOut(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		Customer loginCustomer = (Customer) session.getAttribute("loginCustomer");
		String customer_id = loginCustomer.getCustomer_id();
		
		loginService.dropOut(customer_id);
		
		if(session != null) {
			session.invalidate();
		}
		//쿠키 삭제 
		ResponseCookie cookie = ResponseCookie.from("customer_id", "")
		            .httpOnly(false)
		            .secure(true)  // HTTPS 환경에서만 쿠키 전송
		            .sameSite("None")
		            .path("/")  // 쿠키의 경로는 로그인 시 설정한 것과 동일해야 함
		            .maxAge(0)  // 만료 시간을 0으로 설정하여 쿠키 삭제
		            .build();

		    response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());  // 쿠키 삭제 헤더 추가
		return "redirect:/";
	}
	
	//회원 조회
	@GetMapping("/customer")
	public List<Customer> customers(){
//		List<Member> members = memberService.findMembers(); <<
//		model.addAttribute("members", members); << 예전에 했던 프로젝트에서 참고용으로 가져옴
		return loginService.findMembers();
	}
	
}