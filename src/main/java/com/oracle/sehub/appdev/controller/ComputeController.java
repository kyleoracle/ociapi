package com.oracle.sehub.appdev.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oracle.bmc.core.model.Image;
import com.oracle.bmc.core.model.Instance;
import com.oracle.bmc.core.model.Shape;
import com.oracle.bmc.identity.model.AvailabilityDomain;
import com.oracle.sehub.appdev.autoconfigure.SDKProperties;
import com.oracle.sehub.appdev.model.ListImagesResponse;
import com.oracle.sehub.appdev.service.ComputeService;
import com.oracle.sehub.appdev.service.IdentityService;

@Controller
public class ComputeController {
	private ComputeService _computeService;
	private IdentityService _identityService;

	@Autowired
	public ComputeController(ComputeService computeService, IdentityService identityService, SDKProperties sdkProperties) {
		this._computeService = computeService;
		this._identityService = identityService;
	}

	@GetMapping("/create")
	@ResponseBody()
	public String create(@RequestParam String prefix, @RequestParam Integer count) {
		if (count == null || count.intValue() < 1) {
			return "please set count > 0, if you are creating many instances it's normal to see error - TooManyRequests, check provisioning instances to know how many is being created";
		}
		_computeService.launchInstance(prefix, count);
		return "creating instance with prefix=" + prefix + ", count=" + count;
	}

	@GetMapping("/list")
	@ResponseBody
	public List<String> list(@RequestParam String status) {
		List<String> instanceList = new ArrayList<>();
		List<Instance> list = _computeService.listInstance(status).getItems();
		for (Instance i : list) {
			instanceList.add(i.getDisplayName() + "=" + _computeService.getInstancePublicIp(i.getId()));
		}
		return instanceList;
	}

	@GetMapping("/delete")
	@ResponseBody()
	public String delete(@RequestParam String prefix) {
		if (prefix == null || prefix.isEmpty()) {
			return "please set a prefix";
		}
		_computeService.delete(prefix);
		return "deleting instance with prefix=" + prefix;
	}

	@GetMapping("/getIP")
	@ResponseBody()
	public List<String> getIP(@RequestParam String instanceId) {
		return _computeService.getInstancePublicIp(instanceId);
	}

	@GetMapping("/listShape")
	@ResponseBody
	public List<String> listShape() {
		List<String> shapeNameList = new ArrayList<String>();
		List<Shape> shapeList = _computeService.listShapes().getItems();
		for (Shape shape : shapeList) {
			shapeNameList.add(shape.getShape());
		}
		return shapeNameList;
	}

	@GetMapping("/listAD")
	@ResponseBody
	public List<String> listAD() {
		List<String> adNameList = new ArrayList<String>();
		List<AvailabilityDomain> adList = _identityService.listAvailabilityDomainsResponse().getItems();
		for (AvailabilityDomain ad : adList) {
			adNameList.add(ad.getName());
		}
		return adNameList;
	}

	@GetMapping("/listImage")
	@ResponseBody
	public List<ListImagesResponse> listImage() {
		List<ListImagesResponse> list = new ArrayList<>();
		List<Image> imageList = _computeService.listImages().getItems();
		for (Image i : imageList) {
			ListImagesResponse resp = new ListImagesResponse();
			resp.setImageId(i.getId());
			resp.setImageName(i.getDisplayName());
			list.add(resp);
		}
		return list;
	}
}
