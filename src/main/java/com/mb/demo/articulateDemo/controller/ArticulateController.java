package com.mb.demo.articulateDemo.controller;

import com.mb.demo.articulateDemo.model.Attendee;
import com.mb.demo.articulateDemo.service.AttendeeService;
import com.mb.demo.articulateDemo.service.EnvironmentHelper;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author mborges
 */
@Controller
public class ArticulateController {


	private static final Logger logger = LoggerFactory.getLogger(ArticulateController.class);

	private final AttendeeService attendeeService;
	private final EnvironmentHelper environmentHelper;

	public ArticulateController(AttendeeService attendeeService, EnvironmentHelper environmentHelper) {
		this.attendeeService = attendeeService;
		this.environmentHelper = environmentHelper;
		System.out.println("Initiating sys controller");
logger.info("Initiating sys controller");
	}

	@RequestMapping("/")
	public String index(HttpServletRequest request, Model model) throws Exception {
		addAppEnv(request, model);
		return "index";
	}

	@RequestMapping("/svc")
	@ResponseBody
	public ResponseEntity svc(HttpServletRequest request, Model model) throws Exception {
		addAppEnv(request, model);
		for (int i = 0; i < 100000; i++) {
			System.out.println("Testing data");
		}
		
		System.out.println("Initiating sys svc");
		logger.info("Initiating sys svc");

		return new ResponseEntity("All OK", HttpStatus.OK);
	}

	@RequestMapping(value = "/basics", method = RequestMethod.GET)
	public String kill(HttpServletRequest request, @RequestParam(value = "doit", required = false) boolean doit,
			Model model) throws Exception {
		
		addAppEnv(request, model);

		if (doit) {
			model.addAttribute("killed", true);
			logger.warn("*** The system is shutting down. ***");
			Runnable killTask = () -> {
				try {
					String name = Thread.currentThread().getName();
					logger.warn("killing shortly " + name);
					TimeUnit.SECONDS.sleep(5);
					logger.warn("killed " + name);
					System.exit(0);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			};
			new Thread(killTask).start();
		}
		
		System.out.println("Initiating sys kill controller");
		logger.info("Initiating sys kill controller");

		return "basics";

	}

	@RequestMapping(value = "/services", method = RequestMethod.GET)
	public String attendees(HttpServletRequest request, Model model) throws Exception {
		
		model.addAttribute("attendees", attendeeService.getAttendees());
		model = clearAttendeeFormData(model);

		addAppEnv(request, model);
	
		
		System.out.println("Initiating sys attendees");
		logger.info("Initiating sys attendees");
		
		return "services";
	}

	@RequestMapping(value = "/add-attendee", method = RequestMethod.POST)
	public String addAttendee(HttpServletRequest request, @RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName, @RequestParam("emailAddress") String emailAddress, Model model)
			throws Exception {

		Attendee attendee = new Attendee();
		attendee.setEmailAddress(emailAddress);
		attendee.setFirstName(firstName);
		attendee.setLastName(lastName);

		boolean addFailed = false;
		try {
			attendeeService.add(attendee);
		} catch (Exception e) {
			addFailed = true;
			logger.error("Failed to add attendee.", e);
		}

		model.addAttribute("addFailed", addFailed);

		if (addFailed) {
			model.addAttribute("firstName", firstName);
			model.addAttribute("lastName", lastName);
			model.addAttribute("emailAddress", emailAddress);
		} else {
			model = clearAttendeeFormData(model);
		}

		model.addAttribute("attendees", attendeeService.getAttendees());
		addAppEnv(request, model);

		return "services";
	}

	private Model clearAttendeeFormData(Model model) {
		model.addAttribute("firstName", "");
		model.addAttribute("lastName", "");
		model.addAttribute("emailAddress", "");
		return model;
	}

	@RequestMapping("/bluegreen")
	public String bluegreen(HttpServletRequest request, Model model) throws Exception {
		for (String key : System.getenv().keySet()) {
			System.out.println(key + ":" + System.getenv(key));
		}
		addAppEnv(request, model);
		return "bluegreen";
	}

	private void addAppEnv(HttpServletRequest request, Model model) throws Exception {
		Map<String, Object> modelMap = environmentHelper.addAppEnv(request);
		model.addAllAttributes(modelMap);
	}

}
